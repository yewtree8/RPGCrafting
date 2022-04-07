package uk.co.fordevelopment.rpg.util;

/**
 * Created by matty on 12/08/2017.
 */
public class TimeFormatter {

    public static String convertsSecondsToMinutesColonFormat(int totalSeconds)
    {
        int seconds = totalSeconds % 60;
        int minutes = (totalSeconds / 60) % 60;
        String newTime = String.format("%02d:%02d", minutes, seconds);
        return newTime;
    }



}
