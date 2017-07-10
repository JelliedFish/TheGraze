package com.example.myapplication.Abstract;

public abstract class LayoutSetter {

    public static int[] getMargins(int screenWidth, int screenHeight, double relativeDimension_WtoH, int left, int up, int right, int down) {
        /* Возвращает массив из 4 отступов (в таком порядке: левый, верхний, правый, нижний), необходимые для вписания
         * окна с отношением ширины к высоте, равной relativeDimension_WtoH, и гарантированно минимальными отступами,
         * равными left, up, right и down
         */
        int[] res = {left, up, right, down};

        int maxResWidth = screenWidth - left - right;
        int maxResHeight = screenHeight - up - down;
        int additionalMargin;

        if (relativeDimension_WtoH < (double) maxResWidth / maxResHeight) {
            additionalMargin = (int) (maxResWidth - (maxResHeight * relativeDimension_WtoH)) / 2;   // если не вписывается по горизонтали - делаем дополнительные вертикальные отступы
            res[0] += additionalMargin;
            res[2] += additionalMargin;
        } else {
            additionalMargin = (int) (maxResHeight - (maxResWidth / relativeDimension_WtoH)) / 2;
            res[1] += additionalMargin;                                                             // если по вертикали - доп. горизонтальные
            res[3] += additionalMargin;
        }

        return res;
    }

    public static int[] getRelativeMargins(int screenWidth, int screenHeight, double relativeDimension_WtoH, double left, double up, double right, double down) {
        // Всё аналогично предыдущему методу, но гарантированные минимальные отступы не абсолютны, а относительны размерам экрана
        int absLeft = (int) (screenWidth * left);
        int absRight = (int) (screenWidth * right);
        int absUp = (int) (screenHeight * up);
        int absDown = (int) (screenHeight * down);
        return getMargins(screenWidth, screenHeight, relativeDimension_WtoH, absLeft, absUp, absRight, absDown);
    }

}
