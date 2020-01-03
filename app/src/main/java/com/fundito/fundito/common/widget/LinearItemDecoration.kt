package com.fundito.fundito.common.widget

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.fundito.fundito.common.util.toPixel
import kotlin.math.roundToInt

/**
 * Created by mj on 26, December, 2019
 */
class LinearItemDecoration(private val spaceDp : Int) : RecyclerView.ItemDecoration() {
    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        val idx = parent.getChildLayoutPosition(view)
        if(idx != 0)
            outRect.top = spaceDp.toPixel().roundToInt()
    }
}