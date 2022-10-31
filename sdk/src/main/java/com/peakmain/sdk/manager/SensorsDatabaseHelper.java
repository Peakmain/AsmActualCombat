package com.peakmain.sdk.manager;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;

/**
 * author ：Peakmain
 * createTime：2022/3/31
 * mail:2726449200@qq.com
 * describe：
 */
public class SensorsDatabaseHelper {
    private static final String SensorsDataContentProvider = ".SensorsDataContentProvider/";
    public static final String APP_STARTED = "$app_started";
    public static final String APP_END_STATE = "$app_end_state";
    public static final String APP_PAUSED_TIME = "$app_paused_time";
    private final ContentResolver mContentResolver;
    private final Uri mAppStart;
    private final Uri mAppEndState;
    private final Uri mAppPausedTime;

    public SensorsDatabaseHelper(Context context, String packageName) {
        mContentResolver = context.getContentResolver();
        mAppStart = Uri.parse("content://" + packageName + SensorsDataContentProvider + "app_started");
        mAppEndState = Uri.parse("content://" + packageName + SensorsDataContentProvider + "app_end_state");
        mAppPausedTime = Uri.parse("content://" + packageName + SensorsDataContentProvider + "app_paused_time");
    }

    public void saveAppStartState(boolean isAppStart) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(APP_STARTED, isAppStart);
        mContentResolver.insert(mAppStart, contentValues);
    }

    public void saveAppPauseTime(long isPausedTime) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(APP_PAUSED_TIME, isPausedTime);
        mContentResolver.insert(mAppPausedTime, contentValues);
    }

    public long getAppPausedTime() {
        long pausedTime = 0;
        Cursor cursor = mContentResolver.query(mAppPausedTime, new String[]{APP_PAUSED_TIME}, null, null, null);
        if (cursor != null && cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                pausedTime = cursor.getLong(0);
            }
        }
        if (cursor != null)
            cursor.close();
        return pausedTime;
    }

    public void saveAppEndState(boolean isAppEnd) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(APP_END_STATE, isAppEnd);
        mContentResolver.insert(mAppEndState, contentValues);
    }
    public boolean getAppEndState() {
        boolean state = true;
        Cursor cursor = mContentResolver.query(mAppEndState, new String[]{APP_END_STATE}, null, null, null);
        if (cursor != null && cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                state = cursor.getInt(0) > 0;
            }
        }

        if (cursor != null) {
            cursor.close();
        }
        return state;
    }
    public Uri getAppStartUri() {
        return mAppStart;
    }

}
