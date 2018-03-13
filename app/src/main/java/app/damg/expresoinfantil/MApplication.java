package app.damg.expresoinfantil;

import android.support.multidex.MultiDexApplication;

import com.teliver.sdk.core.TLog;
import com.teliver.sdk.core.Teliver;

public class MApplication extends MultiDexApplication {

    @Override
    public void onCreate() {
        super.onCreate();
        Teliver.init(this,"d9e95fae3b87d375a43b76c6f882dada");
        TLog.setVisible(true);
    }
}
