package seiko.neiko.app;

import android.app.Application;
import android.content.Context;
import android.os.Environment;
import android.os.Handler;
import android.os.StrictMode;
import android.util.DisplayMetrics;

import org.noear.sited.SdAdapter;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import seiko.neiko.dao.engine.DdApi;
import seiko.neiko.dao.engine.DdNodeFactory;
import seiko.neiko.dao.mPath;
import seiko.neiko.ui.main.MainActivity;
import seiko.neiko.utils.FileUtil;

/**
 * Created by Seiko on 2016/8/28. YiKu
 */
public class App extends Application {

    private static App mCurrent;
    private String _PATH;

    public void onCreate() {
        super.onCreate();
        mCurrent = this;

        _PATH = Environment.getExternalStorageDirectory().getAbsolutePath() + "/Neiko/";
        if (!_PATH.endsWith("/")) {
            _PATH += "/";
        }

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        DdApi.tryInit(new DdNodeFactory());
    }

    public static App getCurrent() {
        return mCurrent;
    }

    public static Context getContext() {
        return mCurrent.getApplicationContext();
    }

    public static DisplayMetrics getDisplayMetrics() {
        return getContext().getResources().getDisplayMetrics();
    }

    public String getBasePath() {
        return _PATH;
    }

}
