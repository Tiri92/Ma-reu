package com.example.maru.util;

import android.content.res.Resources;

import androidx.annotation.ColorInt;
import androidx.annotation.ColorRes;
import androidx.core.content.res.ResourcesCompat;

import com.example.maru.R;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Random;

public class Utils {

    public static LocalDate epochMilliToLocalDate(Long millisecondsFromEpoch) {
        Instant instant = Instant.ofEpochMilli(millisecondsFromEpoch);
        LocalDateTime dateTime = LocalDateTime.ofInstant(instant, ZoneId.systemDefault());
        return dateTime.toLocalDate();
    }

    @ColorInt
    public static int getRandomColor(Resources resources) {
        @ColorRes int[] colorArray = {R.color.ROSE, R.color.MAUVE, R.color.BLUE, R.color.VIOLET, R.color.ROUGE, R.color.BLEUCIEL,
                R.color.VERTCLAIR, R.color.JAUNEBEIGE, R.color.BEIGE, R.color.JAUNEORANGE, R.color.SAUMON,
                R.color.ORANGE, R.color.CYAN  };

        int randomIndex = new Random().nextInt(colorArray.length);
        @ColorRes int randomColorResId = colorArray[randomIndex];
        @ColorInt int randomColorInt = ResourcesCompat.getColor(resources, randomColorResId, null);
        return randomColorInt;
    }

}
