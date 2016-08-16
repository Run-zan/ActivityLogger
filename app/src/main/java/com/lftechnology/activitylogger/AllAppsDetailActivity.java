package com.lftechnology.activitylogger;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;

import android.support.v7.widget.CardView;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * Created by DevilDewzone on 8/12/2016.
 */
public class AllAppsDetailActivity extends Activity {


    @BindView(R.id.app_icon)
    ImageView appIcon;

    @BindView(R.id.installed_date)
    TextView installedDate;

    @BindView(R.id.total_data_used)
    TextView totalDataUsed;

    @BindView(R.id.total_time_used)
    TextView totalTimeUsed;

    @BindView(R.id.last_time_used)
    TextView lastTimeUsed;

    @BindView(R.id.app_name)
    TextView appName;


    Intent packageNameIntent = new Intent();

    String packageName = packageNameIntent.getData().getSchemeSpecificPart();

    // Drawable icon =(Drawable) getContext().getPackageManager().getApplicationIcon(packageIcon);

    @Override
    public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        setContentView(R.layout.card_view_app_details_layout);
        ButterKnife.bind(this);
        try {
            Drawable icon = getPackageManager().getApplicationIcon(packageName);
            appIcon.setImageDrawable(icon);
        } catch (PackageManager.NameNotFoundException e) {
            return;
        }
        final PackageManager name = getApplicationContext().getPackageManager();
        ApplicationInfo info;
        try {
            info = name.getApplicationInfo(this.getPackageName(), 0);

        } catch (final PackageManager.NameNotFoundException e) {
            info = null;
        }
        final String applicationName = (String) (info != null ? name.getApplicationLabel(info) : "Unknown");
        appName.setText(applicationName);





    }
}
