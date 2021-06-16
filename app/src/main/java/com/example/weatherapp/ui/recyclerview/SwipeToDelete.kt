package com.example.weatherapp.ui.recyclerview

import android.graphics.Canvas
import android.view.View
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.weatherapp.ui.recyclerview.holders.LocationViewHolder
import kotlinx.android.synthetic.main.holder_location_item.view.*

abstract class SwipeToDelete : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
    var firstTime = true
    override fun onMove(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        target: RecyclerView.ViewHolder
    ): Boolean {
        val animation = (viewHolder as LocationViewHolder).itemView.deleteImage
        animation.playAnimation()
        return true
    }

    override fun clearView(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder
    ) {
        val foregroundView: View = (viewHolder as LocationViewHolder).itemView.locationConstraint
        getDefaultUIUtil().clearView(foregroundView)
    }

    override fun onChildDraw(
        c: Canvas,
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        dX: Float,
        dY: Float,
        actionState: Int,
        isCurrentlyActive: Boolean
    ) {
        val animation = (viewHolder as LocationViewHolder).itemView.deleteImage
        when{
            isCurrentlyActive && firstTime -> {
                animation.playAnimation()
            firstTime = false}
            !isCurrentlyActive -> {
                animation.cancelAnimation()
                firstTime = true}
        }
        val foregroundView: View = (viewHolder as LocationViewHolder).itemView.locationConstraint
        getDefaultUIUtil().onDraw(c, recyclerView, foregroundView, dX, dY, actionState, isCurrentlyActive)

    }

    override fun onChildDrawOver(
        c: Canvas,
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder?,
        dX: Float,
        dY: Float,
        actionState: Int,
        isCurrentlyActive: Boolean
    ) {
        val foregroundView: View = (viewHolder as LocationViewHolder).itemView.locationConstraint
        getDefaultUIUtil().onDrawOver(c, recyclerView, foregroundView, dX, dY, actionState, isCurrentlyActive)
    }

}