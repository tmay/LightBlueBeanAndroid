package com.terrymay.lightbluebean.views;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.terrymay.lightbluebean.R;
import com.terrymay.lightbluebean.interfaces.IBlueBeanListItem;

import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.RootContext;
import org.androidannotations.annotations.ViewById;

import nl.littlerobots.bean.Bean;
import nl.littlerobots.bean.BeanListener;
import nl.littlerobots.bean.message.Callback;

/**
 * Created by Terry on 9/19/14.
 */
@EViewGroup(R.layout.blue_bean_list_item)
public class BlueBeanListItem extends LinearLayout implements IBlueBeanListItem {

    @ViewById(R.id.mac_id)
    TextView macIdDisplay;

    @ViewById(R.id.temp)
    TextView tempReadingDisplay;

    Context context;

    private Bean bean;

    public BlueBeanListItem(Context context) {
        super(context);
        this.context = context;
    }

    public BlueBeanListItem(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public BlueBeanListItem(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }


    public void bind(Bean bean) {
        this.bean = bean;
        macIdDisplay.setText(bean.getDevice().getAddress());
    }

    @Override
    public Bean getBean() {
        return this.bean;
    }
}
