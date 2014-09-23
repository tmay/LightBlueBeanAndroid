package com.terrymay.lightbluebean.adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.terrymay.lightbluebean.views.BlueBeanListItem;
import com.terrymay.lightbluebean.views.BlueBeanListItem_;

import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.RootContext;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import nl.littlerobots.bean.Bean;
import nl.littlerobots.bean.BeanManager;

/**
 * Created by Terry on 9/19/14.
 */
@EBean
public class BlueBleanAdapter extends BaseAdapter {

    public ArrayList<Bean> beans;

    @RootContext
    Context context;


    public BlueBleanAdapter() {
        beans = new ArrayList<Bean>();
    }

    public void setBeans(ArrayList<Bean> beans) {
        this.beans = beans;
        this.notifyDataSetChanged();
    }

    public void addBean(Bean bean) {
        if (!this.beans.contains(bean)) {
            this.beans.add(bean);
        }
    }

    public void removeBean(Bean bean) {
        if (this.beans.contains(bean)) {
            beans.remove(bean);
        }
    }

    public void clear() {
        if (beans != null)
            beans.clear();
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return beans.size();
    }

    @Override
    public Bean getItem(int i) {
        return beans.get(i);
    }

    @Override
    public long getItemId(int i) {
        return beans.get(i).getDevice().hashCode();
    }

    @Override
    public View getView(int i, View convertView, ViewGroup viewGroup) {
        BlueBeanListItem beanItemView;
        if (convertView == null) {
            beanItemView = BlueBeanListItem_.build(context);
        } else {
            beanItemView = (BlueBeanListItem) convertView;
        }

        beanItemView.bind(getItem(i));

        return beanItemView;
    }
}
