package com.example.myapplication.Data;

/* Внутриигровое хранилище карт непрямоугольной формы.
 * Является обычным классом (т.е. можно создавать объекты этого класса)
 * Хранит в себе (в виде полей) все те же данные, которые передаются по интенту в Gameplay
 * Содержит публичный метод, способный возвращать объект MapData по количеству команд и номеру карты
 *
 * Для справки: если координаты клетки, в которую хотите поставить базу, равны (x;y) (начиная отсчёт с (0;0) с лево-верхнего угла), то
 * номер этой клетки равен x + y * width
 *
 * Шаблон:
 * case :
                map.setWidth();
                map.setHeight();
                map.setTeamsCount();
                map.setCastleCoords("");
                map.setButtonsData("");
                return map;
 */

public class MapData {



    private int width;
    public int getWidth() {
        return width;
    }
    private void setWidth(int width) {
        this.width = width;
    }

    private int height;
    public int getHeight() {
        return height;
    }
    private void setHeight(int height) {
        this.height = height;
    }

    private int teamsCount;
    private int getTeamsCount() {
        return teamsCount;
    }
    private void setTeamsCount(int teamsCount) {
        this.teamsCount = teamsCount;
    }

    private String castleCoords;
    public String getCastleCoords() {
        return castleCoords;
    }
    private void setCastleCoords(String castleCoords) {
        this.castleCoords = castleCoords;
    }

    private String buttonsData;
    public String getButtonsData() {
        return buttonsData;
    }
    private void setButtonsData(String buttonsData) {
        this.buttonsData = buttonsData;
    }



    public static MapData getMap(int teamsCount, int number) {
        switch (teamsCount) {
            case 2:
                return getMapFor2p(number);
            case 3:
                return getMapFor3p(number);
            case 4:
                return getMapFor4p(number);
        }
        return null;
    }



    public static int[] decryptCastleCoords(String intent) {
        String[] castlesCoordsInString = intent.split(";");
        int[] res = new int[castlesCoordsInString.length];
        for (int i = 0; i < castlesCoordsInString.length; i++) {
            res[i] = Integer.parseInt(castlesCoordsInString[i]);
        }
        return res;
    }

    public static boolean[] decryptButtonsData(String intent) {
        char ch;
        boolean[] res = new boolean[intent.length()];
        for (int i = 0; i < intent.length(); i++) {
            ch = intent.charAt(i);
            switch (ch) {
                case '0':
                    res[i] = false;
                    break;
                case '1':
                    res[i] = true;
                    break;
            }
        }
        return res;
    }



    private static MapData getMapFor2p(int number) {
        MapData map = new MapData();
        switch (number) {
            case 1:
                map.setWidth(11);
                map.setHeight(15);
                map.setTeamsCount(2);
                map.setCastleCoords("5;159");
                map.setButtonsData("00001110000" + "00011111000" + "00111111100" + "00111111100" + "00111111100" + "01111111110" + "11111011111" + "11110001111" + "11111011111" + "01111111110" + "00111111100" + "00111111100" + "00111111100" + "00011111000" + "00001110000");
                return map;
            case 2:
                map.setWidth(11);
                map.setHeight(13);
                map.setTeamsCount(2);
                map.setCastleCoords("5;137");
                map.setButtonsData("00001110000" + "00111111100" + "01111111110" + "11101110111" + "11001110011" + "11011111011" + "11111111111" + "11011111011" + "11001110011" + "11101110111" + "01111111110" + "00111111100" + "00001110000");
                return map;
            case 3:
                map.setWidth(14);
                map.setHeight(14);
                map.setTeamsCount(2);
                map.setCastleCoords("0;195");
                map.setButtonsData("11000000000000" + "11100000000000" + "01111000000000" + "00111111000000" + "00111111110000" + "00011111110000" + "00011111111000" + "00011111111000" + "00001111111000" + "00001111111100" + "00000011111100" + "00000000011110" + "00000000000111" + "00000000000011");
                return map;
        }
        return null;
    }

    private static MapData getMapFor3p(int number) {
        MapData map = new MapData();
        switch (number) {
            case 1:
                map.setWidth(11);
                map.setHeight(11);
                map.setTeamsCount(3);
                map.setCastleCoords("5;110;120");
                map.setButtonsData("00000100000" + "00001110000" + "00001110000" + "00011111000" + "00011111000" + "00111111100" + "00111111100" + "01111111110" + "01111111110" + "11111111111" + "11111111111");
                return map;
            case 2:
                map.setWidth(11);
                map.setHeight(12);
                map.setTeamsCount(3);
                map.setCastleCoords("0;10;126");
                map.setButtonsData("11100000111" + "11111011111" + "11111011111" + "11111011111" + "11111111111" + "01111111110" + "01111111110" + "01111111110" + "01111111110" + "00011111000" + "00011111000" + "00001110000");
                return map;
        }
        return null;
    }

    private static MapData getMapFor4p(int number) {
        MapData map = new MapData();
        switch (number) {
            case 1:
                map.setWidth(13);
                map.setHeight(13);
                map.setTeamsCount(4);
                map.setCastleCoords("78;6;90;162");
                map.setButtonsData("0000011100000" + "0001111111000" + "0011111111100" + "0111111111110" + "0111111111110" + "1111111111111" + "1111111111111" + "1111111111111" + "0111111111110" + "0111111111110" + "0011111111100" + "0001111111000" + "0000011100000");
                return map;
            case 2:
                map.setWidth(15);
                map.setHeight(15);
                map.setTeamsCount(4);
                map.setCastleCoords("105;7;119;217");
                map.setButtonsData("000000111000000" + "000111111111000" + "001111111111100" + "011100010001110" + "011001111100110" + "011011111110110" + "111011111110111" + "111111111111111" + "111011111110111" + "011011111110110" + "011001111100110" + "011100010001110" + "001111111111100" + "000111111111000" + "000000111000000");
                return map;
            case 3:
                map.setWidth(15);
                map.setHeight(15);
                map.setTeamsCount(4);
                map.setCastleCoords("16;28;208;196");
                map.setButtonsData("000000111000000" + "011111111111110" + "011111101111110" + "011111000111110" + "011111111111110" + "011111111111110" + "111011000110111" + "110011000110011" + "111011000110111" + "011111111111110" + "011111111111110" + "011111000111110" + "011111101111110" + "011111111111110" + "000000111000000");
                return map;
            case 4:
                map.setWidth(15);
                map.setHeight(15);
                map.setTeamsCount(4);
                map.setCastleCoords("105;7;119;217");
                map.setButtonsData("000000111000000" + "001111111111100" + "011111111111110" + "011000111000110" + "011011111110110" + "011011111110110" + "111111101111111" + "111111000111111" + "111111101111111" + "011011111110110" + "011011111110110" + "011000111000110" + "011111111111110" + "001111111111100" + "000000111000000");
                return map;
            case 5:
                map.setWidth(15);
                map.setHeight(15);
                map.setTeamsCount(4);
                map.setCastleCoords("0;14;224;210");
                map.setButtonsData("111000000000111" + "111111000111111" + "111111111111111" + "011111111111110" + "011111000111110" + "011111101111110" + "001101111101100" + "001100111001100" + "001101111101100" + "011111101111110" + "011111000111110" + "011111111111110" + "111111111111111" + "111111000111111" + "111000000000111");
                return map;
        }
        return null;
    }
}
