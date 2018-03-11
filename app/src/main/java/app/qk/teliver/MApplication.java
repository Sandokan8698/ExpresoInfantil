package app.qk.teliver;

import android.support.multidex.MultiDexApplication;

import com.teliver.sdk.core.TLog;
import com.teliver.sdk.core.Teliver;

public class MApplication extends MultiDexApplication {

    @Override
    public void onCreate() {
        super.onCreate();
        Teliver.init(this,"b0c03f77755518685a30096d3a1b08ab");
        TLog.setVisible(true);
    }
}
