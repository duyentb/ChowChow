package com.chowchow.os.chowchow.utils;

import android.content.Context;
import android.content.res.Resources;
import android.util.Log;

import com.chowchow.os.chowchow.model.Tag;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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

    public static int convertDpToPx(Context context, float dips)
    {
        return (int) (dips * context.getResources().getDisplayMetrics().density);
    }

    public static float convertPxToDp(Context context, float px) {
        return px / context.getResources().getDisplayMetrics().density;
    }

    public static int getNumberMatchFavorite (ArrayList<Tag> tourFavorite, ArrayList<String> selectedFavorite) {
        int count = 0;
        for (Tag tag : tourFavorite) {
            for (int i = 0 ; i < selectedFavorite.size(); i++) {
                Log.d("ChauNB", "selectedFavorite[" + i +"] = " + selectedFavorite.get(i));
                Log.d("ChauNB", "tourFavorite = " + tag.getTagId());
                if (selectedFavorite.get(i).equals(tag.getTagId())) {
                    count++;
                }
            }
        }
        return count;
    }

    public static int getScreenWidth() {
        return Resources.getSystem().getDisplayMetrics().widthPixels;
    }

    public static int getScreenHeight() {
        return Resources.getSystem().getDisplayMetrics().heightPixels;
    }
}
