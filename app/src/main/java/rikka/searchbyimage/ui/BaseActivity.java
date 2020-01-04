package rikka.searchbyimage.ui;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

/**
 * Created by Rikka on 2016/3/3.
 */
public abstract class BaseActivity extends AppCompatActivity {
    //private Tracker mTracker;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //mTracker = ((Application) getApplication()).getDefaultTracker();
    }

    @Override
    protected void onResume() {
        super.onResume();
        /*mTracker.setScreenName(this.getClass().getSimpleName());
        mTracker.send(new HitBuilders.ScreenViewBuilder().build());*/
    }
}
