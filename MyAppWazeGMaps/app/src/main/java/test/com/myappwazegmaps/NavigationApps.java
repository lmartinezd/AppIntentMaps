package test.com.myappwazegmaps;

import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;

public class NavigationApps extends ContextWrapper {

    private static final String GOOGLE ="com.google.android.apps.maps";
    private static final String WAZE = "com.waze";

    private static final Intent INTENT_GOOGLE = new Intent(Intent.ACTION_VIEW)
            .setPackage("com.google.android.apps.maps");
    private final Intent INTENT_WAZE = new Intent(Intent.ACTION_VIEW)
            .setPackage("com.waze");

    //App link to Google Play Market
    private static final String MARKET_LINK = "market://details?id=%s";
    private static final String GOOGLE_NAVIGATION = "google.navigation:ll=%s,%s";
    private static final String WAZE_NAVIGATION = "waze://?ll=%s,%s&navigate=yes";

    public NavigationApps(Context base) {
        super(base);
    }

    /**
     * Set geo coordinates point of destination point
     */
    public Intent setDestination(final String flag, String latitude, String longitude) {
        Intent mIntent = new Intent();
        switch (flag) {
            case GOOGLE:
                mIntent = INTENT_GOOGLE.setData(Uri.parse(String.format(GOOGLE_NAVIGATION, latitude, longitude)));
                break;

            case WAZE:
                mIntent = INTENT_WAZE.setData(Uri.parse(String.format(WAZE_NAVIGATION, latitude, longitude)));
                break;
        }
        return mIntent;
    }


    /**
     * Check selected navigation application is installed or not
     */
    public boolean checkPackage(String flag) {
        boolean result = true;
        Intent intent;
        switch (flag) {
            case "GOOGLE":
                intent = INTENT_GOOGLE;
                break;

            case "WAZE":
                intent = INTENT_WAZE;
                break;

            default:
                return result;
        }

        if (!isPackageInstalled(intent)) {
            result = false;
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(String.format(MARKET_LINK, intent.getPackage()))));
        }
        return result;
    }

    private boolean isPackageInstalled(Intent intent) {
        PackageManager pm = getApplicationContext().getPackageManager();
        try {
            pm.getPackageInfo(intent.getPackage(), 0) ;
            return true;
        } catch (PackageManager.NameNotFoundException e) {
            return false;
        }
    }
}