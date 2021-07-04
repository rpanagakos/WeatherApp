package com.example.weatherapp.ui.widget

import android.annotation.SuppressLint
import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.widget.RemoteViews
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.weatherapp.MainActivity
import com.example.weatherapp.R
import com.example.weatherapp.database.LocalDataSource
import com.example.weatherapp.network.weather.WeatherRemoteRepository
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import javax.inject.Inject


@AndroidEntryPoint
class WeatherAppWidget : AppWidgetProvider() {

    private val job = SupervisorJob()
    val coroutineScope = CoroutineScope(Dispatchers.IO + job)
    private lateinit var location : String
    @Inject lateinit var weatherRemoteRepository: WeatherRemoteRepository
    @Inject lateinit var localDataSource: LocalDataSource

    override fun onReceive(context: Context, intent: Intent?) {
        super.onReceive(context, intent)
        coroutineScope.launch {
            kotlin.runCatching {
                localDataSource.getLatestLocation()
            }.onSuccess {
                getLocationDetails(it.location, context)
            }
        }
    }

    override fun onEnabled(context: Context) {
        // Enter relevant functionality for when the first widget is created
    }

    override fun onDisabled(context: Context) {
        // Enter relevant functionality for when the last widget is disabled
        job.cancel()
    }

    private fun getLocationDetails(location : String, context: Context) {
        val appWidgetManager = AppWidgetManager.getInstance(context)
        val man = AppWidgetManager.getInstance(context)
        val ids = man.getAppWidgetIds(ComponentName(context, WeatherAppWidget::class.java))

        coroutineScope.launch {
            kotlin.runCatching {
                weatherRemoteRepository.getCurrentWeather(location)
            }.onSuccess {
                when {
                    it.isSuccessful -> {
                        it.body()?.let {
                            it.data?.let {
                                for (appWidgetId in ids){
                                    updateAppWidget(context, appWidgetManager, appWidgetId,
                                        it.request[0].query, it.current_condition[0].temp_C, it.current_condition[0].weatherIconUrl[0].value, it.current_condition[0].observation_time, it.current_condition[0].weatherDesc[0].value)
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@SuppressLint("RemoteViewLayout")
internal fun updateAppWidget(
    context: Context,
    appWidgetManager: AppWidgetManager,
    appWidgetId: Int,
    city : String,
    temp : String,
    weatherUrl : String,
    observationTime : String,
    weatherDesc : String
) {
    // Construct the RemoteViews object
    val views = RemoteViews(context.packageName, R.layout.weather_app_widget)
    views.setTextViewText(R.id.cityName, city.substringBefore(","))
    views.setTextViewText(R.id.degreesText, temp + "º")
    views.setTextViewText(R.id.timeObserv, observationTime)
    views.setTextViewText(R.id.weatherDesc, weatherDesc)

    try {
        val bitmap: Bitmap = Glide.with(context)
            .asBitmap()
            .load(weatherUrl)
            .apply(RequestOptions.circleCropTransform())
            .submit(512, 512)
            .get()
        views.setImageViewBitmap(R.id.weatherImage, bitmap)
    } catch (e: Exception) {
        e.printStackTrace()
    }

    views.setOnClickPendingIntent(R.id.widget_layout,
        getPendingIntentActivity(context))
    // Instruct the widget manager to update the widget
    appWidgetManager.updateAppWidget(appWidgetId, views)
}

private fun getPendingIntentActivity(context: Context): PendingIntent
{
    // Construct an Intent which is pointing this class.
    val intent = Intent(context, MainActivity::class.java)
    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
    intent.setFlags(Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS)
    // And this time we are sending a broadcast with getBroadcast
    return PendingIntent.getActivity(context, 0, intent, 0)
}