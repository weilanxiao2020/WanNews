package com.miyako.wannews.util;

import android.app.usage.UsageStats;
import android.app.usage.UsageStatsManager;
import android.content.Context;

import com.miyako.architecture.util.LogUtils;
import com.miyako.architecture.util.Utils;

import java.util.List;

/**
 * @Description
 * @Author Miyako
 * @Date 2022-01-20-0020
 */
public class JNIUtils {

    public static native String getPsShell(String[] pkgArr);
    public static void get() {
        Context context = Utils.Companion.getApp();
        UsageStatsManager usageManager=(UsageStatsManager) context.getSystemService(Context.USAGE_STATS_SERVICE);
        if (usageManager != null) {
            int intervalType = UsageStatsManager.INTERVAL_BEST;
            long endTime = System.currentTimeMillis();
            long startTime = endTime - 10000;

            List<UsageStats> applicationList = usageManager.queryUsageStats(intervalType, startTime, endTime);
            for (UsageStats usageStats : applicationList) {
                LogUtils.INSTANCE.d("miyako", usageStats.getPackageName());
            }
        }
    }
}
