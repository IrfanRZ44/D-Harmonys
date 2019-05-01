package com.exomatik.irfanrz.dharmonis.Recycler;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.exomatik.irfanrz.dharmonis.Model.ModelVideo;
import com.exomatik.irfanrz.dharmonis.R;

import java.util.List;

/**
 * Created by IrfanRZ on 03/11/2018.
 */

public class AdapterGridVideo extends BaseAdapter {
    private Context context;
    private List<ModelVideo> dataVideo;
    WebView videoWeb;
    Activity activity;

    public AdapterGridVideo(Context context, List<ModelVideo> dataVideo, Activity activity) {
        this.context = context;
        this.dataVideo = dataVideo;
        this.activity = activity;
    }

    @Override
    public int getCount() {
        return dataVideo.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = View.inflate(context, R.layout.list_video, null);

//         videoWeb = (WebView) v.findViewById(R.id.web_view);

//        videoWeb.getSettings().setJavaScriptEnabled(true);
//        videoWeb.getSettings().setPluginState(WebSettings.PluginState.ON);
//        videoWeb.getSettings().setJavaScriptEnabled(true);
//        videoWeb.getSettings().setAppCacheEnabled(true);
//        videoWeb.getSettings().setBuiltInZoomControls(true);
//        videoWeb.getSettings().setSaveFormData(true);
//        videoWeb.setWebViewClient(new Browser_home());
//        videoWeb.setWebChromeClient(new MyChrome());
//        videoWeb.loadData(dataVideo.get(position).getVideoUrl(), "text/html", "utf-8");
//        loadWebsite();

        return v;
    }

    private void loadWebsite() {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        if (netInfo != null && netInfo.isConnectedOrConnecting()) {
            videoWeb.loadUrl("https://www.youtube.com/");
        } else {
            videoWeb.setVisibility(View.GONE);
        }
    }

    class Browser_home extends WebViewClient {

        Browser_home() {
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);

        }

        @Override
        public void onPageFinished(WebView view, String url) {
//            setTitle(view.getTitle());
//            progressBar.setVisibility(View.GONE);
            super.onPageFinished(view, url);

        }
    }

    private class MyChrome extends WebChromeClient {

        private View mCustomView;
        private WebChromeClient.CustomViewCallback mCustomViewCallback;
        protected FrameLayout mFullscreenContainer;
        private int mOriginalOrientation;
        private int mOriginalSystemUiVisibility;

        MyChrome() {}

        public Bitmap getDefaultVideoPoster()
        {
            if (mCustomView == null) {
                return null;
            }
            return BitmapFactory.decodeResource(context.getResources(), 2130837573);
        }

        public void onHideCustomView()
        {
            ((FrameLayout)activity.getWindow().getDecorView()).removeView(this.mCustomView);
            this.mCustomView = null;
            activity.getWindow().getDecorView().setSystemUiVisibility(this.mOriginalSystemUiVisibility);
            activity.setRequestedOrientation(this.mOriginalOrientation);
            this.mCustomViewCallback.onCustomViewHidden();
            this.mCustomViewCallback = null;
        }

        public void onShowCustomView(View paramView, WebChromeClient.CustomViewCallback paramCustomViewCallback)
        {
            if (this.mCustomView != null)
            {
                onHideCustomView();
                return;
            }
            this.mCustomView = paramView;
            this.mOriginalSystemUiVisibility = activity.getWindow().getDecorView().getSystemUiVisibility();
            this.mOriginalOrientation = activity.getRequestedOrientation();
            this.mCustomViewCallback = paramCustomViewCallback;
            ((FrameLayout)activity.getWindow().getDecorView()).addView(this.mCustomView, new FrameLayout.LayoutParams(-1, -1));
            activity.getWindow().getDecorView().setSystemUiVisibility(3846);

        }
    }

}
