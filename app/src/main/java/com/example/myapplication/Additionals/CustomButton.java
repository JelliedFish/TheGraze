package com.example.myapplication.Additionals;

import android.content.Context;
import android.util.AttributeSet;

public class CustomButton extends android.support.v7.widget.AppCompatImageButton {

    int team;
    /* Номер команды, которой принадлежит клетка (от 1 до 4)
     * Может принимать значение 0 для нейтральных состояний клетки (state = -1; state = 0)
     */
    public int getTeam() {
        return team;
    }
    public void setTeam(int team) {
        this.team = team;
    }

    int state;
    /* состояние клетки:
     * 0 - нейтральная клетка (никому не принадлежит)
     * 1 - в клетке живой клоп данной команды
     * 2 - в клетке клоп, убитый данной командой
     * 3 - в клетке база данной команды
     * 4 - в клетке уничтоженная база данной команды
     * -1 - клетка отсутствует (сейчас не используется, но в будущем м.б. будет использоваться для карт произвольной формы)
     */
    public int getState(){
        return this.state;
    }
    public void setState(int st){
        this.state=st;
    }

    boolean checkable;
    public boolean getCheckable(){
        return checkable;
    }
    public void setCheckable(boolean bl){
        checkable=bl;
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