package com.mantas.bvstatsapp.ui

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import androidx.core.content.ContextCompat
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import com.mantas.bvstatsapp.R
import com.mantas.bvstatsapp.database.AppDatabase
import com.mantas.bvstatsapp.entity.Line
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class StatsDrawingView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : View(context, attrs) {

    private val paint = Paint().apply {
        strokeWidth = 5f
        style = Paint.Style.STROKE
        isAntiAlias = true
    }

    private val lineDao = AppDatabase.getInstance(context).lineDao()
    private val lines = mutableListOf<Line>()

    private var startX: Float = 0f
    private var startY: Float = 0f
    private var isStartPointSet: Boolean = false
    private var isDrawingEnabled: Boolean = false
    private var currentColor: Int = android.graphics.Color.BLUE

    init {
        // Load lines without requiring a LifecycleOwner
        CoroutineScope(Dispatchers.Main).launch {
            val savedLines = withContext(Dispatchers.IO) {
                lineDao.getAllLinesDirect()
            }
            lines.clear()
            lines.addAll(savedLines)
            invalidate() // Redraw with loaded lines
        }
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        if (!isDrawingEnabled) return false

        if (event.action == MotionEvent.ACTION_DOWN) {
            if (!isStartPointSet) {
                startX = event.x
                startY = event.y
                isStartPointSet = true
            } else {
                val endX = event.x
                val endY = event.y

                val newLine = Line(
                    startX = startX,
                    startY = startY,
                    endX = endX,
                    endY = endY,
                    color = currentColor
                )

                lines.add(newLine)
                CoroutineScope(Dispatchers.IO).launch {
                    lineDao.insert(newLine)
                }
                isStartPointSet = false
                invalidate()
            }
        }
        return true
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        for (line in lines) {
            paint.color = line.color
            canvas.drawLine(line.startX, line.startY, line.endX, line.endY, paint)
        }
    }

    fun setDrawingEnabled(action: String, type: String) {
        isDrawingEnabled = true
        currentColor = when (action) {
            "Spike" -> when (type) {
                "Defended" -> ContextCompat.getColor(context, R.color.lightblue)
                "Kills" -> ContextCompat.getColor(context, R.color.blue)
                else -> android.graphics.Color.GRAY
            }
            "Shot" -> when (type) {
                "Defended" -> ContextCompat.getColor(context, R.color.pastelred)
                "Kills" -> ContextCompat.getColor(context, R.color.red)
                else -> android.graphics.Color.GRAY
            }
            "Blocked" -> when (type) {
                "Defended" -> ContextCompat.getColor(context, R.color.lightgreen)
                "Kills" -> ContextCompat.getColor(context, R.color.green)
                else -> android.graphics.Color.GRAY
            }
            "Pokey" -> when (type) {
                "Defended" -> ContextCompat.getColor(context, R.color.grey)
                "Kills" -> android.graphics.Color.BLACK
                else -> android.graphics.Color.GRAY
            }

            else -> {
                isDrawingEnabled = false
                android.graphics.Color.TRANSPARENT
            }
        }
    }
    fun clearLines() {
        lines.clear()
        invalidate() // Redraw the view with no lines
    }



}
