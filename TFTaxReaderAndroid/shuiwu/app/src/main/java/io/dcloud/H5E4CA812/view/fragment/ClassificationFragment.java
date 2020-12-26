package io.dcloud.H5E4CA812.view.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;

import io.dcloud.H5E4CA812.R;

import io.dcloud.H5E4CA812.base.BaseFragment;

/**
 * 分类
 */
public class ClassificationFragment extends BaseFragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_classification, container, false);
        return view;
    }


}
