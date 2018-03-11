package app.qk.teliver.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import app.qk.teliver.utils.Constants;
import app.qk.teliver.utils.MPreference;


public class ActivityStart extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        MPreference preference = new MPreference(this);
        Class className;

        // preference.clearAllPreference();

        if (preference.getBoolean(Constants.IS_LOGGED_IN) &&  !preference.getString(Constants.LOGGE_IN_TOKEN).equals(null)) {
            if (preference.getBoolean(Constants.LOGGED_IN_PADRE))
                className = ActivityCustomer.class;
            else className = ActivityDriver.class;
        }
        else className = LoginActivity.class;

        startActivity(new Intent(this, className));

        finish();
    }
}
