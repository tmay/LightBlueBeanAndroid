package com.terrymay.lightbluebean;

import android.app.Activity;
import android.app.ActionBar;
import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
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
import nl.littlerobots.bean.BeanDiscoveryListener;
import nl.littlerobots.bean.BeanListener;
import nl.littlerobots.bean.BeanManager;

@WindowFeature({Window.FEATURE_INDETERMINATE_PROGRESS})
@EActivity(R.layout.activity_main)
public class Main extends Activity {


    @AfterViews
    void onAfterViews() {
        //showBeanFinder();
        BeanManager.getInstance().startDiscovery(new BeanDiscoveryListener() {
            @Override
            public void onBeanDiscovered(Bean bean) {
                BeanManager.getInstance().cancelDiscovery();
                connect(bean);
            }

            @Override
            public void onDiscoveryComplete() {

            }
        });
    }

    private void connect(Bean bean) {
        bean.connect(this, new BeanListener() {
            @Override
            public void onConnected() {
                Log.i("mainBean", "connect");
            }

            @Override
            public void onConnectionFailed() {
                Log.i("mainBean", "connectfailed");
            }

            @Override
            public void onDisconnected() {
                Log.i("mainBean", "disconnected");
            }

            @Override
            public void onSerialMessageReceived(byte[] data) {

            }

            @Override
            public void onScratchValueChanged(int bank, byte[] value) {

            }
        });
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
