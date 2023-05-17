package com.idrisov.utils

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

/**
 * Класс, расширяющий [RecyclerView.ItemDecoration] для отрисовки кастомных декораторов.
 * Конструктор класса является приватным, поэтому для создания экземпляра класса необходимо использовать [DividerItemDecorator.Builder]
 */
class DividerItemDecorator private constructor(
    private val orientation: Int,
    private val itemOffset: Int,
    private val dividerColor: Int,
    private val dividerWidth: Int,
    private val isShouldLastOffset: Boolean,
    private val isShouldLastDivider: Boolean
): RecyclerView.ItemDecoration() {

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {

        val isLastItem = parent.getChildAdapterPosition(view) == parent.adapter?.itemCount?.minus(1)

        if (orientation == LinearLayoutManager.VERTICAL) {
            outRect.set(
                /* left = */ 0,
                /* top = */ itemOffset.toDp / 2,
                /* right = */ 0,
                /* bottom = */ if (isLastItem && !isShouldLastOffset) 0 else itemOffset.toDp / 2
            )
        } else if (orientation == LinearLayoutManager.HORIZONTAL) {
            outRect.set(
                /* left = */ (itemOffset / 2).toDp,
                /* top = */ 0,
                /* right = */ if (isLastItem && !isShouldLastOffset) 0 else (itemOffset / 2).toDp,
                /* bottom = */ 0
            )
        }
    }

    override fun onDraw(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        if (orientation == LinearLayoutManager.HORIZONTAL) {
            drawHorizontalDividers(c, parent)
        } else if (orientation == LinearLayoutManager.VERTICAL) {
            drawVerticalDividers(c, parent)
        }
    }

    /**
     * Отрисовка декораторов для горизонтальной ориентации
     */
    private fun drawHorizontalDividers(canvas: Canvas, parent: RecyclerView) {

        val paint = Paint(Paint.ANTI_ALIAS_FLAG)
        paint.color = dividerColor

        val range = if (isShouldLastDivider) parent.childCount else parent.childCount - 1

        for (i in 0 until range) {

            val child = parent.getChildAt(i)

            canvas.drawRect(
                /* left = */ child.right.toFloat() + itemOffset.toDp / 2,
                /* top = */ child.top.toFloat(),
                /* right = */ child.right.toFloat() + itemOffset.toDp / 2 + dividerWidth.toDp,
                /* bottom = */ child.bottom.toFloat(),
                /* paint = */ paint
            )
        }
    }

    /**
     * Отрисовка декораторов для вертикальной ориентации
     */
    private fun drawVerticalDividers(canvas: Canvas, parent: RecyclerView) {

        val paint = Paint(Paint.ANTI_ALIAS_FLAG)
        paint.color = dividerColor

        val range = if (isShouldLastDivider) parent.childCount else parent.childCount - 1

        for (i in 0 until range) {

            val child = parent.getChildAt(i)

            canvas.drawRect(
                /* left = */ child.left.toFloat(),
                /* top = */ child.bottom.toFloat() + itemOffset.toDp / 2,
                /* right = */ child.right.toFloat(),
                /* bottom = */ child.bottom.toFloat() + itemOffset.toDp / 2 + dividerWidth.toDp,
                /* paint = */ paint
            )
        }

    }

    /**
     * Класс, использующийся для создания экземпляра класса [DividerItemDecorator].
     */
    class Builder {

        private var orientation: Int = 0
        private var itemOffset: Int = 0
        private var dividerColor: Int = Color.TRANSPARENT
        private var dividerWidth: Int = 1
        private var isShouldLastOffset: Boolean = false
        private var isShouldLastDivider: Boolean = false

        /**
         * Устанавливает значение ориентации.
         * @param orientation значение ориентации. [LinearLayoutManager.VERTICAL] или [LinearLayoutManager.HORIZONTAL]
         */
        fun setOrientation(orientation: Int): Builder {
            this.orientation = orientation
            return this
        }

        /**
         * Устанавливает отступы для каждого элемента.
         *
         * @param itemOffset значение отступа для каждого элемента. Брать значения из [DividerItemDecorator.Offsets]
         */
        fun setItemOffset(itemOffset: Int): Builder {
            this.itemOffset = itemOffset
            return this
        }

        /**
         * Задает цвет для разделителя между каждым элементом.
         * @param dividerColor идентификатор цветового ресурса
         */
        fun setDividerColor(dividerColor: Int): Builder {
            this.dividerColor = dividerColor
            return this
        }

        /**
         * Задает ширину для разделителя между каждым элементом.
         * @param dividerWidth ширина для разделителя. По умолчанию 1
         */
        fun setDividerWidth(dividerWidth: Int): Builder {
            this.dividerWidth = dividerWidth
            return this
        }

        /**
         * Устанавливает флаг должен ли быть отступ у последнего элемента
         * @param isShouldLastOffset флаг. По умолчанию значение false
         */
        fun setShouldLastOffset(isShouldLastOffset: Boolean): Builder {
            this.isShouldLastOffset = isShouldLastOffset
            return this
        }

        /**
         * Устанавливает флаг должен ли быть виден последний элемент
         * @param isShouldLastDivider флаг. По умолчанию значение false
         */
        fun setShouldLastDivider(isShouldLastDivider: Boolean): Builder {
            this.isShouldLastDivider = isShouldLastDivider
            return this
        }

        /**
         * Возвращает созданный объект
         */
        fun build(): DividerItemDecorator {
            return DividerItemDecorator(
                orientation,
                itemOffset,
                dividerColor,
                dividerWidth,
                isShouldLastOffset,
                isShouldLastDivider
            )
        }
    }

    /**
     * Класс, хранящий константы отступов для каждого элемента.
     * Значения передаются в метод [DividerItemDecorator.Builder.setItemOffset], внутри которого конвертируются в dp.
     */
    class Offsets {

        companion object {

            const val space4 = 4
            const val space8 = 8
            const val space12 = 12
            const val space16 = 16
            const val space20 = 20
            const val space24 = 24
            const val space32 = 32
        }
    }
}