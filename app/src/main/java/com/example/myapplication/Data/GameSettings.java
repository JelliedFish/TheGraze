package com.example.myapplication.Data;

public class GameSettings {



    private static boolean musicState = false;
    public static boolean getMusicState() {
        return musicState;
    }
    public static void setMusicState(boolean musicState) {
        GameSettings.musicState = musicState;
    }

    private static byte[] players_textureState = {1, 2, 3, 4};
    public static byte[] getPlayers_textureState() {
        return players_textureState;
    }
    public static byte getPlayers_textureState(int index) {
        return players_textureState[index];
    }
    public static void setPlayers_textureState(byte[] players_textureState) {
        GameSettings.players_textureState = players_textureState;
    }
    public static void setPlayers_textureState(int index, byte newTextureState) {
        players_textureState[index] = newTextureState;
    }



    public static void addToTextureState(int index, int count) {
        players_textureState[index] += count;
    }

    public static void reset() {
        byte[] newTS = {1, 2, 3, 4};

        setMusicState(false);
        setPlayers_textureState(newTS);
    }



}
