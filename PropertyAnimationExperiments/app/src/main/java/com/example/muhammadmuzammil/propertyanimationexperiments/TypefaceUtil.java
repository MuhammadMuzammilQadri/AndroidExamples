package com.example.muhammadmuzammil.propertyanimationexperiments;

import android.content.Context;
import android.graphics.Typeface;
import android.util.Log;

import java.lang.reflect.Field;
import java.util.Map;

/**
 * Created by Muhammad Muzammil on 02-Jan-16.
 */
public class TypefaceUtil {
    /**
     * Using reflection to override default typefaces
     * NOTICE: DO NOT FORGET TO SET TYPEFACE FOR APP THEME AS DEFAULT TYPEFACE WHICH WILL BE
     * OVERRIDDEN
     *
     * @param typefaces map of fonts to replace
     */
    public static void overrideFonts(Map<String, Typeface> typefaces) {
        try {
            final Field field = Typeface.class.getDeclaredField("sSystemFontMap");
            field.setAccessible(true);
            Map<String, Typeface> oldFonts = (Map<String, Typeface>) field.get(null);
            if (oldFonts != null) {
                oldFonts.putAll(typefaces);
            } else {
                oldFonts = typefaces;
            }
            field.set(null, oldFonts);
            field.setAccessible(false);
        } catch (Exception e) {
            Log.e("TypefaceUtil", "Can not set custom fonts");
        }
    }

    public static Typeface getTypeface(int fontType, Context context) {
        // here you can load the Typeface from asset or use default ones
        Typeface typeface= Typeface.createFromAsset(context.getAssets(), "fonts/myfont.TTF");
        return typeface;
    }
}