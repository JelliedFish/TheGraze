package com.example.myapplication.Data;

import com.example.myapplication.R;

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



    public static int getPlayerTextureID(int playerNum) {
        switch (GameSettings.getPlayers_textureState(playerNum - 1)) {
            case 1:
                return R.drawable.grnd_black;
            case 2:
                return R.drawable.grnd_grace;
            case 3:
                return R.drawable.grnd_lava;
            case 4:
                return R.drawable.grnd_sand;
        }
        return 0;
    }

    public static int getPlayerTextureID(int playerNum, int playerState) {
        if (playerState == Const.STATE_MISSING)
            return R.drawable.ic_alpha;
        else if (playerState == Const.STATE_NEUTRAL) {
            return R.drawable.ic_grnd_main;
        }
        else {
            int textureID = playerState * 4 + GameSettings.getPlayers_textureState(playerNum - 1) - 5;
            switch (textureID) {
                case 0: return R.drawable.grnd_black;
                case 1: return R.drawable.grnd_grace;
                case 2: return R.drawable.grnd_lava;
                case 3: return R.drawable.grnd_sand;
                case 4: return R.drawable.black_kill;
                case 5: return R.drawable.grace_kill;
                case 6: return R.drawable.lava_kill;
                case 7: return R.drawable.sand_kill;
                case 8: return R.drawable.ctl_black;
                case 9: return R.drawable.ctl_grace;
                case 10: return R.drawable.ctl_lava;
                case 11: return R.drawable.ctl_sand;
                case 12: return R.drawable.ctl_black_die;
                case 13: return R.drawable.ctl_grace_die;
                case 14: return R.drawable.ctl_lava_die;
                case 15: return R.drawable.ctl_sand_die;
            }
        }
        return 0;
    }



}
