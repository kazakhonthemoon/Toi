package kz.eugales.toi.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Adil on 06.08.2016.
 */
public class Utils {

    public static boolean isNetworkAvailable(Context context){
        ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = manager.getActiveNetworkInfo();
        return  networkInfo != null && networkInfo.isConnectedOrConnecting();
    }

    public static byte[] getBytesOfPictire(Bitmap bitmap){
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        if(bitmap != null)
            bitmap.compress(Bitmap.CompressFormat.PNG, 0, stream);
        return stream.toByteArray();
    }

    public static Bitmap getPictureFromBytes(byte[] bytes){
        Bitmap bitmap = null;
        if(bytes.length > 0)
            bitmap = BitmapFactory.decodeByteArray(bytes,0,bytes.length);
        return bitmap;
    }

    public static String getSizeName(Context context) {
        int screenLayout = context.getResources().getConfiguration().screenLayout;
        screenLayout &= Configuration.SCREENLAYOUT_SIZE_MASK;

        switch (screenLayout) {
            case Configuration.SCREENLAYOUT_SIZE_SMALL:
                return "small";
            case Configuration.SCREENLAYOUT_SIZE_NORMAL:
                return "normal";
            case Configuration.SCREENLAYOUT_SIZE_LARGE:
                return "large";
            case Configuration.SCREENLAYOUT_SIZE_XLARGE:
                return "xlarge";
            default:
                return "undefined";
        }
    }

    public static Map<String, String> getPrefs(SharedPreferences preferences ,List<String> prefs){
        Map<String, String> result = new HashMap<>();
        for(String pref:prefs) {
            result.put(pref, preferences.getString(pref, "Constants.CONFIG.PREF_NOT_EXIST"));
        }
        return result;
    }

    public static List<String> setPrefs(SharedPreferences preferences ,Map<String, String> prefValues){
        List<String> result = new ArrayList<>();
        SharedPreferences.Editor ed = preferences.edit();
        for(String pref:prefValues.keySet()) {
            ed.putString(pref, prefValues.get(pref));
            ed.commit();
            result.add(pref);
        }
        return result;
    }

}
