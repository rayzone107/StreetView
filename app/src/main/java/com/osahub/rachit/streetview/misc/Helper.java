package com.osahub.rachit.streetview.misc;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Environment;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.View;

import com.activeandroid.ActiveAndroid;
import com.activeandroid.Cache;
import com.activeandroid.Model;
import com.activeandroid.TableInfo;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Helper Class for generic constants and methods
 * Created by Rachit on 22-Apr-16.
 */
public class Helper {

    private static final String LOG_TAG = "World Tour 3D: " + Helper.class.getSimpleName();

    public static final int MAX_PER_LIST_ON_HOME_SCREEN = 10;
    public static final String CATEGORY_EXTRA = "category";

    public static final String DATE_FORMAT_DEFAULT = "yyyy-MM-dd hh:mm:ss";

    public static final String CATEGORY_TYPE_SPECIAL = "special";
    public static final String LOCATION_FRAGMENT_BLANK_TAG = "BLANK";

    public static Date convertStringToDate(String date) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT_DEFAULT, Locale.getDefault());
        return sdf.parse(date);
    }

    public static String convertDateToString(Date date) {
        return (new SimpleDateFormat(DATE_FORMAT_DEFAULT, Locale.getDefault())).format(date);
    }

    public static String urlGenerator(String name, int position) {
        return ("http://rachitgoyal.com/images/world_tour/high_res/location_images/" + name + "/" + name + "_" + position + ".jpg");
    }

    public static class HorizontalSpaceItemDecoration extends RecyclerView.ItemDecoration {

        private final int mHorizontalSpaceWidth;

        public HorizontalSpaceItemDecoration(int mHorizontalSpaceWidth) {
            this.mHorizontalSpaceWidth = mHorizontalSpaceWidth;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            if (parent.getChildAdapterPosition(view) != parent.getAdapter().getItemCount() - 1) {
                outRect.left = mHorizontalSpaceWidth;
            } else {
                outRect.right = mHorizontalSpaceWidth;
                outRect.left = mHorizontalSpaceWidth;
            }
        }
    }

    public static Uri getLocalBitmapUri(Context context, Bitmap bmp) {
        Uri bmpUri = null;
        try {
            File file = new File(context.getExternalFilesDir(Environment.DIRECTORY_PICTURES), "share_image.jpg");
            FileOutputStream out = new FileOutputStream(file);
            bmp.compress(Bitmap.CompressFormat.JPEG, 90, out);
            out.close();
            bmpUri = Uri.fromFile(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bmpUri;
    }

    public static List<String> generateImageLinksArrayFromLinksString(String imageLinks) {
        List<String> imageLinksList = Arrays.asList(imageLinks.split("\\s*,\\s*"));
        Collections.sort(imageLinksList);
        return imageLinksList;
    }

    public static int convertDpToPixel(Context context, int dpMeasure) {
        return (int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,
                dpMeasure,
                context.getResources().getDisplayMetrics()
        );
    }

    public static void clearTable(Class<? extends Model> type) {
        TableInfo tableInfo = Cache.getTableInfo(type);
        ActiveAndroid.execSQL(String.format("DELETE FROM %s;", tableInfo.getTableName()));
        ActiveAndroid.execSQL(
                String.format("DELETE FROM sqlite_sequence WHERE name='%s';", tableInfo.getTableName()));
    }
}