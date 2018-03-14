package app.damg.expresoinfantil.activities;

import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.teliver.sdk.core.Teliver;
import com.teliver.sdk.models.UserBuilder;

import app.damg.expresoinfantil.Models.User;
import app.damg.expresoinfantil.R;
import app.damg.expresoinfantil.fragments.FragmentDriver;
import app.damg.expresoinfantil.utils.Constants;
import app.damg.expresoinfantil.utils.JsonUtils;
import app.damg.expresoinfantil.utils.Utils;
import app.damg.expresoinfantil.views.CustomToast;

public class ActivityDriver extends AppCompatActivity implements FragmentManager.OnBackStackChangedListener {

    private Toolbar toolbar;

    private FragmentManager fragmentManager;

    private View rootView;

    private Snackbar snackbar;

    private FragmentDriver fragmentDriver;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setLogo(R.drawable.ic_toolbar);
        setSupportActionBar(toolbar);
        Utils.setUpToolBar(this, toolbar, getSupportActionBar(), getString(R.string.app_name));
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        rootView = findViewById(R.id.view_root);
        fragmentManager = getSupportFragmentManager();
        fragmentManager.addOnBackStackChangedListener(this);
        changeFragment(0);

        User user = JsonUtils.getUser(this);
        Teliver.identifyUser(new UserBuilder(user.getNombre()).setUserType(UserBuilder.USER_TYPE.OPERATOR).build());
    }

    private void changeFragment(int caseValue) {
        if (caseValue == 0) {
            if (fragmentDriver == null)
                fragmentDriver = new FragmentDriver();
            switchView(fragmentDriver, getString(R.string.app_name));
        }
    }

    private void switchView(final Fragment fragment, final String title) {
        try {
            toolbar.setTitle(title);
            FragmentTransaction mFragmentTransaction = fragmentManager.beginTransaction();
            Fragment mTempFragment = fragmentManager.findFragmentById(R.id.view_container);
            if (!fragment.equals(mTempFragment)) {
                String className = fragment.getClass().getName();
                boolean isAdded = fragmentManager.popBackStackImmediate(className, 0);
                if (!isAdded) {
                    mFragmentTransaction.addToBackStack(className);
                    mFragmentTransaction.add(R.id.view_container, fragment, title);
                }
            }
            mFragmentTransaction.commitAllowingStateLoss();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public void onBackStackChanged() {
        Fragment fragment = fragmentManager.findFragmentById(R.id.view_container);
        if (fragment == null)
            return;
        String tag = fragment.getTag();
        toolbar.setTitle(tag);
    }

    @Override
    public void onBackPressed() {
        if (fragmentManager.getBackStackEntryCount() > 1)
            fragmentManager.popBackStackImmediate();
        else if (snackbar != null && snackbar.isShown()) {
            snackbar.dismiss();
            finish();
        } else {
            snackbar = Snackbar.make(rootView, R.string.txt_press_back, 3000);
            snackbar.show();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String permissions[],
                                           @NonNull int[] grantResults) {
        if (requestCode != Constants.PERMISSION_REQ_CODE)
            return;
        if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            if (fragmentDriver != null)
                fragmentDriver.validateTrip();
        } else
            CustomToast.showToast(this, getString(R.string.text_location_permission));
    }
}
