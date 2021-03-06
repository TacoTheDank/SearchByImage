package rikka.searchbyimage.utils;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Binder;
import android.os.IBinder;
import android.text.TextUtils;
import android.util.Log;

import androidx.browser.customtabs.CustomTabsService;

import java.util.ArrayList;
import java.util.List;

/**
 * Helper class for Custom Tabs.
 */
public class CustomTabsHelper {
    private static final String STABLE_PACKAGE = "com.android.chrome";
    private static final String BETA_PACKAGE = "com.chrome.beta";
    private static final String DEV_PACKAGE = "com.chrome.dev";
    private static final String CANARY_PACKAGE = "com.chrome.canary";
    private static final String LOCAL_PACKAGE = "com.google.android.apps.chrome";
    private static final String BROMITE_PACKAGE = "org.bromite.bromite";
    private static final String TAG = "CustomTabsHelper";
    private static final String EXTRA_CUSTOM_TABS_KEEP_ALIVE =
            "android.support.customtabs.extra.KEEP_ALIVE";

    private static String sPackageNameToUse;

    private CustomTabsHelper() {
    }

    public static void addKeepAliveExtra(Context context, Intent intent) {
        Intent keepAliveIntent = new Intent().setClassName(
                context.getPackageName(), KeepAliveService.class.getCanonicalName());
        intent.putExtra(EXTRA_CUSTOM_TABS_KEEP_ALIVE, keepAliveIntent);
    }

    /**
     * Goes through all apps that handle VIEW intents and have a warmup service. Picks
     * the one chosen by the user if there is one, otherwise makes a best effort to return a
     * valid package name.
     * <p>
     * This is <strong>not</strong> threadsafe.
     *
     * @param context {@link Context} to use for accessing {@link PackageManager}.
     * @return The package name recommended to use for connecting to custom tabs related components.
     */
    static String getPackageNameToUse(Context context) {
        if (sPackageNameToUse != null) return sPackageNameToUse;

        PackageManager pm = context.getPackageManager();
        // Get default VIEW intent handler.
        Intent activityIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://example.org"));
        ResolveInfo defaultViewHandlerInfo = pm.resolveActivity(activityIntent, 0);
        String defaultViewHandlerPackageName = null;
        if (defaultViewHandlerInfo != null) {
            defaultViewHandlerPackageName = defaultViewHandlerInfo.activityInfo.packageName;
        }

        // Get all apps that can handle VIEW intents.
        List<ResolveInfo> resolvedActivityList = pm.queryIntentActivities(activityIntent, 0);
        List<String> packagesSupportingCustomTabs = new ArrayList<>();
        for (ResolveInfo info : resolvedActivityList) {
            Intent serviceIntent = new Intent();
            serviceIntent.setAction(CustomTabsService.ACTION_CUSTOM_TABS_CONNECTION);
            serviceIntent.setPackage(info.activityInfo.packageName);
            if (pm.resolveService(serviceIntent, 0) != null) {
                packagesSupportingCustomTabs.add(info.activityInfo.packageName);
            }
        }

        // Now packagesSupportingCustomTabs contains all apps that can handle both VIEW intents
        // and service calls.
        if (packagesSupportingCustomTabs.isEmpty()) {
            sPackageNameToUse = null;
        } else if (packagesSupportingCustomTabs.size() == 1) {
            sPackageNameToUse = packagesSupportingCustomTabs.get(0);
        } else if (!TextUtils.isEmpty(defaultViewHandlerPackageName)
                && !hasSpecializedHandlerIntents(context, activityIntent)
                && packagesSupportingCustomTabs.contains(defaultViewHandlerPackageName)) {
            sPackageNameToUse = defaultViewHandlerPackageName;
        } else if (packagesSupportingCustomTabs.contains(STABLE_PACKAGE)) {
            sPackageNameToUse = STABLE_PACKAGE;
        } else if (packagesSupportingCustomTabs.contains(BETA_PACKAGE)) {
            sPackageNameToUse = BETA_PACKAGE;
        } else if (packagesSupportingCustomTabs.contains(DEV_PACKAGE)) {
            sPackageNameToUse = DEV_PACKAGE;
        } else if (packagesSupportingCustomTabs.contains(CANARY_PACKAGE)) {
            sPackageNameToUse = CANARY_PACKAGE;
        } else if (packagesSupportingCustomTabs.contains(LOCAL_PACKAGE)) {
            sPackageNameToUse = LOCAL_PACKAGE;
        } else if (packagesSupportingCustomTabs.contains(BROMITE_PACKAGE)) {
            sPackageNameToUse = BROMITE_PACKAGE;
        }
        return sPackageNameToUse;
    }

    /**
     * Used to check whether there is a specialized handler for a given intent.
     *
     * @param intent The intent to check with.
     * @return Whether there is a specialized handler for the given intent.
     */
    private static boolean hasSpecializedHandlerIntents(Context context, Intent intent) {
        try {
            PackageManager pm = context.getPackageManager();
            List<ResolveInfo> handlers = pm.queryIntentActivities(
                    intent,
                    PackageManager.GET_RESOLVED_FILTER);
            if (handlers == null || handlers.size() == 0) {
                return false;
            }
            for (ResolveInfo resolveInfo : handlers) {
                IntentFilter filter = resolveInfo.filter;
                if (filter == null) continue;
                if (filter.countDataAuthorities() == 0 || filter.countDataPaths() == 0) continue;
                if (resolveInfo.activityInfo == null) continue;
                return true;
            }
        } catch (RuntimeException e) {
            Log.e(TAG, "Runtime exception while getting specialized handlers");
        }
        return false;
    }

    /**
     * @return All possible chrome package names that provide custom tabs feature.
     */
    private static String[] getPackages() {
        return new String[]{"", STABLE_PACKAGE, BETA_PACKAGE, DEV_PACKAGE, CANARY_PACKAGE, LOCAL_PACKAGE, BROMITE_PACKAGE};
    }

    public static boolean getIsChromeInstalled(Context context) {
        return !getInstalledChromePackageName(context).isEmpty();
    }

    private static List<String> getInstalledChromePackageName(Context context) {
        PackageManager pm = context.getPackageManager();
        ArrayList<String> list = new ArrayList<>();

        for (String name : getPackages()) {
            if (name.isEmpty()) {
                continue;
            }

            try {
                pm.getPackageInfo(name, PackageManager.GET_ACTIVITIES);
                list.add(name);
            } catch (PackageManager.NameNotFoundException ignored) {
            }
        }

        return list;
    }

    public static class KeepAliveService extends Service {
        private static final Binder sBinder = new Binder();

        @Override
        public IBinder onBind(Intent intent) {
            return sBinder;
        }
    }
}
