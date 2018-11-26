package test.com.myappwazegmaps;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
/**
  * Created by lmartinezd on 24/11/18.
 */

public class MainActivity extends AppCompatActivity {
    private static final String LAT = "-23.60003437";
    private static final String LON = "-46.6867125";
    private final String[] NAME_OF_APPS = new String[] { "com.google.android.apps.maps", "com.waze"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btnOpenMaps = findViewById(R.id.btnOpen);

        btnOpenMaps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                NavigationApps navApps = new NavigationApps(getBaseContext());

                final PackageManager packageManager = getBaseContext().getPackageManager();

                Intent mainIntent = new Intent(Intent.ACTION_MAIN, null);
                mainIntent.addCategory(Intent.CATEGORY_LAUNCHER);
                List<ResolveInfo> appInfoList = packageManager.queryIntentActivities(mainIntent, 0);
//
//                Collections.sort(
//                        appInfoList,
//                        new ResolveInfo.DisplayNameComparator(getBaseContext().getPackageManager()));

                List<Intent> targetedOpenIntents = new ArrayList<Intent>();

                for (ResolveInfo appInfo : appInfoList) {
                    if (Arrays.asList(NAME_OF_APPS).contains(appInfo.activityInfo.packageName))
                    {
                        targetedOpenIntents.add(navApps.setDestination(appInfo.activityInfo.packageName,LAT,LON));
                    }
                }

//                 create the intent chooser. delete the first member in the list(the default activity)
                Intent chooserIntent = Intent.createChooser(targetedOpenIntents.remove(0), "Choose one:")
                        .putExtra(Intent.EXTRA_INITIAL_INTENTS, targetedOpenIntents.toArray(new Parcelable[]{}));

                startActivity(chooserIntent);
            }
        });
    }

}
