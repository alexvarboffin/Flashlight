package com.bovada.unoprom.fragment;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.webkit.WebView;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import androidx.appcompat.ExtendedWebView;
import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;


import com.google.android.material.tabs.TabLayout;
import com.bovada.unoprom.R;
import com.bovada.flashlights.lib.GameMainView;

import org.apache.P;
import org.apache.Utils;
import org.apache.cordova.ChromeView;
import org.apache.cordova.GConfig;
import org.apache.cordova.domen.BodyClass;

import org.apache.mvp.presenter.MainFragmentPresenter;

public class WebFragment extends Fragment implements ChromeView {

    protected GameMainView listener;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof GameMainView) {
            this.listener = (GameMainView) context;
        } else {
            throw new RuntimeException(context + " must implement IMainView");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        listener = null;
    }

//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // handle arrow click here
//        if (item.getItemId() == android.R.id.home) {
//            getActivity().onBackPressed();
//        }
//
//        return super.onOptionsItemSelected(item);
//    }

    private static final boolean WEBTITLE_ENABLE = false;
    private ViewTreeObserver.OnScrollChangedListener mOnScrollChangedListener;
    private ExtendedWebView mWebView;
    //Views

    public SwipeRefreshLayout swipeRefreshLayout;

    private String launchUrl;
    private GConfig aaa;

    protected RelativeLayout main;
    protected FrameLayout clazz1;
    private ProgressBar progressBar;


    private MainFragmentPresenter presenter;
    private String ShowOrHideWebViewInitialUse = "show";

    public static WebFragment newInstance(String url, GConfig config) {
        WebFragment fragment = new WebFragment();
        Bundle bundle = new Bundle();
        bundle.putString(P.ARG_PARAM1, url);
        bundle.putSerializable(P.ARG_PARAM2, config);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        init(getArguments());
        return inflater.inflate(R.layout.fragment_about, container, false);
    }

    public void init(Bundle arguments) {
        if (arguments != null) {
            launchUrl = arguments.getString(P.ARG_PARAM1);
            aaa = (GConfig) arguments.getSerializable(P.ARG_PARAM2);
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        clazz1 = view.findViewById(R.id.browser);
        main = view.findViewById(R.id.main);
        progressBar = view.findViewById(R.id.progressBar1);

        if (listener != null) {
            TabLayout tabLayout = listener.getTabs();
            if (tabLayout != null) {
                tabLayout.setVisibility(View.GONE);
            }
        }

        if (aaa.PROGRESSBAR_ENABLED) {
            progressBar.setVisibility(View.VISIBLE);
        } else {
            progressBar.setVisibility(View.GONE);
        }
        //Generate Dynamic Gui
        onViewCreated(clazz1, getActivity());
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new MainFragmentPresenter(getActivity());
    }

    protected void onViewCreated(ViewGroup view, Context context) {
        //mWebView = privacy.findViewById(R.id.web_view);
        //swipeRefreshLayout = privacy.findViewById(R.id.refresh);
        swipeRefreshLayout = new SwipeRefreshLayout(context);
        mWebView = new ExtendedWebView(new ContextThemeWrapper(context, R.style.AppTheme));
        FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT
        );
        swipeRefreshLayout.setLayoutParams(lp);
        mWebView.setLayoutParams(lp);
        view.addView(swipeRefreshLayout);
        swipeRefreshLayout.addView(mWebView);
        swipeRefreshLayout.getViewTreeObserver().addOnScrollChangedListener(mOnScrollChangedListener =
                () -> {
                    if (mWebView.getScrollY() == 0)
                        swipeRefreshLayout.setEnabled(true);
                    else
                        swipeRefreshLayout.setEnabled(false);

                });

        swipeRefreshLayout.setOnRefreshListener(() -> {
            mWebView.reload();
            if (swipeRefreshLayout.isRefreshing()) {
                swipeRefreshLayout.setRefreshing(false);
            }
        });
        //mWebView.setVisibility(View.INVISIBLE);
        //webview.setOverScrollMode(WebView.OVER_SCROLL_NEVER);

//        JavaScriptInterface jsInterface = new JavaScriptInterface(getActivity());
//        mWebView.addJavascriptInterface(jsInterface, "JSInterface");
        Utils.a123(this, mWebView);
        //mWebView.addJavascriptInterface(new MyJavascriptInterface(getContext(), mWebView), "Client");
    }

    public void webOrGame(boolean web) {
        clazz1.setVisibility((web) ? View.VISIBLE : View.GONE);
        main.setVisibility((web) ? View.GONE : View.VISIBLE);
        //mWebView.setVisibility((web) ? View.VISIBLE : View.GONE);
        progressBar.setVisibility((web) && aaa.PROGRESSBAR_ENABLED ? View.VISIBLE : View.GONE);
    }


    @Override
    public void onStop() {
        swipeRefreshLayout.getViewTreeObserver().removeOnScrollChangedListener(mOnScrollChangedListener);
        super.onStop();
    }

    public void onPageStarted() {
        // only make it invisible the FIRST time the app is run
        if (ShowOrHideWebViewInitialUse.equals("show")) {
            mWebView.setVisibility(View.INVISIBLE);
        }
        if (aaa.PROGRESSBAR_ENABLED) {
            progressBar.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onPageFinished(WebView view, String url) {
        String title = view.getTitle();
        if (WEBTITLE_ENABLE && !TextUtils.isEmpty(title) && listener != null) {
            if (title != null && title.startsWith(view.getUrl())) {
                listener.setActionBarTitle(title);
            }
        }
        if (ShowOrHideWebViewInitialUse.equals("show")) {
            ShowOrHideWebViewInitialUse = "hide";
            mWebView.setVisibility(View.VISIBLE);
        }
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void webClientError(int errorCode, String description, String failingUrl) {
//        if (getActivity().getRequestedOrientation() != SCREEN_ORIENTATION_LANDSCAPE) {
//            getActivity().setRequestedOrientation(SCREEN_ORIENTATION_LANDSCAPE);
//        }
        //this.webOrGame(false);
        if (listener != null) {
            listener.homeScreen();
        }
    }


    @Override
    public void mAcceptPressed(String url) {

    }

    @Override
    public void eventRequest(BodyClass body) {
        presenter.event(body);
    }


    protected void actionRefresh() {
        String url = mWebView.getUrl();
        if (url != null) {
            mWebView.reload();
            //getContent(url);
            Utils.snack(getActivity(), "Данные обновлены");
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        webOrGame(true);
        presenter.loadUrl(launchUrl, mWebView);
    }
}
