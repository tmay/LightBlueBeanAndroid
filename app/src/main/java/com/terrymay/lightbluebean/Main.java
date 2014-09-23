package com.terrymay.lightbluebean;

import android.app.Activity;
import android.app.ActionBar;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.os.Build;
import android.view.Window;

import com.terrymay.lightbluebean.fragments.BeanDeviceDisplay;
import com.terrymay.lightbluebean.fragments.BlueBeanFinder;

import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.WindowFeature;

import nl.littlerobots.bean.Bean;

@WindowFeature({Window.FEATURE_INDETERMINATE_PROGRESS})
@EActivity(R.layout.activity_main)
public class Main extends Activity {


    @AfterViews
    void onAfterViews() {
        showBeanFinder();
    }

    private void showBeanFinder() {
        BlueBeanFinder beanFinder = BlueBeanFinder.newInstance();

        getFragmentManager().beginTransaction()
                .replace(R.id.container, beanFinder, BlueBeanFinder.class.getSimpleName())
                .commit();

        beanFinder.setOnBeanSelectedListener(new BlueBeanFinder.OnBeanSelectionListener() {
            @Override
            public void onBeanSelected(Bean bean) {
                showBeanDevice(bean);
            }
        });
    }

    @UiThread
    void showBeanDevice(Bean bean) {
        getFragmentManager().beginTransaction()
                .replace(R.id.container, BeanDeviceDisplay.newInstance(bean))
                .commit();
    }
}
