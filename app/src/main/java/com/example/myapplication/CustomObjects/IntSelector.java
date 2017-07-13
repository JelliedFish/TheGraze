package com.example.myapplication.CustomObjects;

import java.util.ArrayList;
import java.util.List;

public class IntSelector {

    /* Реализует поведение int-величины, которую можно изменять в игре (например, номер карты, количество команд и т.д.),
     * с учётом минимума/максимума возможных значений и "запретных" значений
     */

    private int val;                                                                                // текущее значение селектора
    private int minVal;                                                                             // минимальное значение селектора
    private int maxVal;                                                                             // максимальное значение селектора
    private List<Integer> blackList;                                                                // чёрный список значений, которые НЕ может принимать селектор

    public int getVal() {
        return this.val;
    }
    public void setVal(int newVal) {
        this.val = newVal;
    }



    public IntSelector(int startVal, int min, int max) {
        this.val = startVal;
        this.minVal = min;
        this.maxVal = max;
        this.blackList = new ArrayList<>();
    }

    public void clearBL() {
        this.blackList.clear();
    }

    public void addToBL(int newBLvalue) {
        this.blackList.add(newBLvalue);
    }



    public boolean isMin() {
        return this.val == this.minVal;
    }

    public boolean isMax() {
        return this.val == this.maxVal;
    }

    public boolean isOutOfBounds() {
        return (this.val < this.minVal) || (this.val >= this.maxVal);
    }

    public boolean isRelatedToBL(){
        for (int i : this.blackList)
            if (i == val)
                return true;
        return false;
    }



    public void plus(int N) {
        this.val += N;
        if (this.isOutOfBounds())
            this.val = this.maxVal;
        while (this.isRelatedToBL())
            this.plus();
    }

    public void plus() {
        this.val++;
        if (this.isOutOfBounds())
            this.val = this.maxVal;
        while (this.isRelatedToBL())
            this.plus();
    }

    public void minus(int N) {
        this.val -= N;
        if (this.isOutOfBounds())
            this.val = this.minVal;
        while (this.isRelatedToBL())
            this.minus();
    }

    public void minus() {
        this.val--;
        if (this.isOutOfBounds())
            this.val = this.minVal;
        while (this.isRelatedToBL())
            this.minus();
    }



}
