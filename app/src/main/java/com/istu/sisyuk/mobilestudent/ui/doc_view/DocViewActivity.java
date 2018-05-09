package com.istu.sisyuk.mobilestudent.ui.doc_view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.MenuItem;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.istu.sisyuk.mobilestudent.R;
import com.istu.sisyuk.mobilestudent.base.BaseActivity;
import com.istu.sisyuk.mobilestudent.data.models.Material;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DocViewActivity extends BaseActivity {

    private static final String GOOGLE_DOC_VIEWER = "https://docs.google.com/gview?embedded=true&url=";
    private static final String KEY_NAME = "NAME";
    private static final String KEY_LINK = "LINK";

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.web_view)
    WebView webView;

    public static void start(Context context, Material material) {
        Intent intent = new Intent(context, DocViewActivity.class);
        intent.putExtra(KEY_NAME, material.getName());
        intent.putExtra(KEY_LINK, material.getLink());
        context.startActivity(intent);
    }

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doc_view);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setHomeButtonEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        String name = getIntent().getStringExtra(KEY_NAME);
        String link = getIntent().getStringExtra(KEY_LINK);

        if (!TextUtils.isEmpty(name) && !TextUtils.isEmpty(link)) {
            setActionBarTitle(name);
        }

        webView.setWebChromeClient(new WebChromeClient());
        webView.setWebViewClient(new WebViewClient());
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setSupportZoom(true);
        webView.clearCache(true);
        webView.loadUrl(GOOGLE_DOC_VIEWER + link);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}