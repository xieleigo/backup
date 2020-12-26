package io.dcloud.H5E4CA812.util.loading;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import io.dcloud.H5E4CA812.util.loading.avi.AVLoadingIndicatorView;
import io.dcloud.H5E4CA812.R;

public class Loading extends Dialog {
    private AVLoadingIndicatorView avi;
    public static Loading loading = null;

    public static Loading getInstance(Context context) {
        if (loading == null) {
            loading = new Loading(context);
        }
        return loading;
    }

    public static Loading getLoading() {

        return loading;
    }

    public Loading(@NonNull Context context) {
        super(context);
    }

    public Loading(@NonNull Context context, int themeResId) {
        super(context, themeResId);
    }

    protected Loading(@NonNull Context context, boolean cancelable, @Nullable OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        View view = View.inflate(this.getContext(), R.layout.loading, null);
        setContentView(view);
        avi = (AVLoadingIndicatorView) view.findViewById(R.id.avi);
        avi.setIndicator("BallScaleMultipleIndicator");
    }

    @Override
    public void show() {
        super.show();
        avi.show();
    }

    @Override
    public void hide() {
        super.hide();
        avi.hide();
    }
}
