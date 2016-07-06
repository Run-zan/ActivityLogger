package com.lftechnology.activitylogger;

import android.app.usage.UsageStats;
import android.app.usage.UsageStatsManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;


/**
 * Created by sparsha on 6/29/2016.
 * Returns raw information of apps that are run within an Interval Provided.
 * Default is set to daily
 */
public class RawAppInfo {
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat();
    private static int interval;

    }

    protected static List<UsageStats> getUsageStatsAppList(Context context){
        UsageStatsManager usageStatsManager = getUsageStatsManager(context);
        Calendar calendar = Calendar.getInstance();
        long endTime = calendar.getTimeInMillis();
        calendar.add(Calendar.YEAR, -1);
        long startTime = calendar.getTimeInMillis();
        Log.d("LOG","Date Start:\t"+ dateFormat.format(startTime));//TODO remove
        Log.d("LOG","Date End:\t"+ dateFormat.format(endTime));//TODO remove
        List<UsageStats> usageStatsList =
                usageStatsManager.queryUsageStats(interval,startTime,endTime);
        return usageStatsList;
    }


    /**
     * Sets desired interval that the developer requires
     *
     * Eg. RawAppInfo.printCurrentUsageStats(Context context, int mInterval)
     * 
     * 
     * @param context give context from the activity that you are calling (EG. "this")
     * @param mInterval Note: Integer Value = 0 For Daily
     *                                        1 For Weekly
     *                                        2 For Monthly
     *                                        3 For Yearly
     *                                        4 For From the Beginning
     */
    protected static void printCurrentUsageStats(Context context, int mInterval){
        interval = mInterval;
        printUsageStats(getUsageStatsAppList(context),context);
    }

    /**
     * Package info of each app can be set here seperately
     * @param usageStatsList gets list of apps used within the set interval
     * @param context
     * 
     */

    private static void printUsageStats(List<UsageStats> usageStatsList,Context context){
       
        for(UsageStats u : usageStatsList){
            String mNameOfPackage = u.getPackageName();
            long totalTimeInForeground = u.getTotalTimeInForeground();
            Log.d("LOG","Package Name = "+mNameOfPackage+"\tForeground Time: "+totalTimeInForeground);//TODO remove
        }
    }


    @SuppressWarnings("ResourceType")
    private static UsageStatsManager getUsageStatsManager(Context context) {
        return (UsageStatsManager) context.getSystemService("usagestats");
    }
}