package com.chowchow.os.chowchow.utils;

import android.util.Log;

import com.chowchow.os.chowchow.model.Tag;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class CommonUtils {
    public static String getTourFavorite (List<Tag> arrFavorite) {
        String favorite = "";
        int size = arrFavorite.size();
        for (int i = 0; i < size - 1; i++) {
            favorite = favorite.concat(arrFavorite.get(i).getName()).concat(", ");
        }
        favorite += arrFavorite.get(size - 1).getName();

        return favorite;
    }

    public static String convertText (String duration, String str) {
        duration = duration + " " + str;
        return duration;
    }

    public static String convertCost (String cost) {
        DecimalFormat formatter = new DecimalFormat("#,###,###");
        cost = formatter.format(Integer.parseInt(cost)) + " VND";
        return cost;
    }

    public static Date convertStringToDate (String strDate) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.US);
        Date date = new Date();
        try{
            date = dateFormat.parse(strDate);
        }catch(ParseException ex){
            // handle parsing exception if date string was different from the pattern applying into the SimpleDateFormat contructor
            Log.d("ParseException", ex.getMessage());
        }

        return date;
    }

    public static Date getNextDay (Date currentDate) {
        Calendar c = Calendar.getInstance();
        c.setTime(currentDate);
        c.add(Calendar.DATE, 1);
        Date nextDay = c.getTime();

        return nextDay;
    }
}
