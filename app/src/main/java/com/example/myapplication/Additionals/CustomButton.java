package com.example.myapplication.Additionals;

import android.content.Context;
import android.util.AttributeSet;

public class CustomButton extends android.support.v7.widget.AppCompatImageButton {
    int state;
    public int getState(){
        return this.state;
    }
    /*
     * state - состояние клетки.
     * 0 - пустая клетка
     * 1 - занята 1-й командой
     * 2 - убита 1-й командой
     * 3 - занята 2-й командой
     * 4 - убита 2-й командой
     */
    public void setState(int st){
        this.state=st;
    }


    public CustomButton(Context context) {
        super(context);
    }

    public CustomButton(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomButton(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected void onMeasure(final int widthMeasureSpec, final int heightMeasureSpec) {
        final int width = getDefaultSize(getSuggestedMinimumWidth(),widthMeasureSpec);
        setMeasuredDimension(width, width);
    }

    @Override
    protected void onSizeChanged(final int w, final int h, final int oldw, final int oldh) {
        super.onSizeChanged(w, w, oldw, oldh);
    }

}