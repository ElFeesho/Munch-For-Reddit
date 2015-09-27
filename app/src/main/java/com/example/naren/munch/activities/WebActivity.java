package com.example.naren.munch.activities;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.example.naren.munch.R;
import com.hannesdorfmann.swipeback.Position;
import com.hannesdorfmann.swipeback.SwipeBack;

public class WebActivity extends AppCompatActivity {

    private static String MOBILE_USER_AGENT_STRING = null;
    private static final String DESKTOP_USER_AGENT_STRING = "Mozilla/5.0 (Windows NT 6.1; Win64; x64; rv:25.0) Gecko/20100101 Firefox/25.0";
    private WebView mWebView;
    private String webUrl;
    private static final String READABILITY_PREFIX = "http://www.readability.com/m?url=";

    public static void startWebActivity(Context context, String webUrl)
    {
        Intent intent = new Intent(context, WebActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("web_link", webUrl);
        intent.putExtras(bundle);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SwipeBack.attach(this, Position.LEFT)
                .setContentView(R.layout.activity_web)
                .setSwipeBackView(R.layout.swipeback_custom);

        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Back");

        Bundle bundle = getIntent().getExtras();
        webUrl = bundle.getString("web_link");

        mWebView = (WebView) findViewById(R.id.webView);
        if(MOBILE_USER_AGENT_STRING == null)
        {
            // There is no nice way to get the default user agent before JellyBean
            MOBILE_USER_AGENT_STRING = mWebView.getSettings().getUserAgentString();
        }

        mWebView.getSettings().setLoadsImagesAutomatically(true);
        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
        mWebView.getSettings().setLoadWithOverviewMode(true);
        mWebView.getSettings().setUseWideViewPort(true);
        mWebView.getSettings().setBuiltInZoomControls(true);
        mWebView.getSettings().setSupportZoom(true);
        mWebView.getSettings().setDomStorageEnabled(true);

        // Configure the client to use when opening URLs
        mWebView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });
        // Load the initial URL
        mWebView.loadUrl(webUrl);
    }

    @Override
    protected void onPause() {
        super.onPause();
        mWebView.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mWebView.onResume();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_web, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        switch (item.getItemId()) {

            case android.R.id.home:
                finish();
                return true;

            case R.id.action_goBack:
                if (mWebView.canGoBack()) {
                    mWebView.goBack();
                }
                return true;

            case R.id.action_goForward:
                if (mWebView.canGoForward()) {
                    mWebView.goForward();
                }
                return true;


            case R.id.action_refresh:
                mWebView.loadUrl(webUrl);
                return true;

            case R.id.action_share:
                shareCurrentPage();
                return true;

            case R.id.action_readability:
                if (item.isChecked())
                {
                    mWebView.loadUrl(webUrl);
                }
                else {
                    mWebView.loadUrl(READABILITY_PREFIX + webUrl);
                }

                item.setChecked(!item.isChecked());
                return true;

            case R.id.action_desktop:
                if(item.isChecked())
                {
                    mWebView.getSettings().setUserAgentString(MOBILE_USER_AGENT_STRING);
                }
                else
                {
                    mWebView.getSettings().setUserAgentString(DESKTOP_USER_AGENT_STRING);
                }

                mWebView.loadUrl(webUrl);

                item.setChecked(!item.isChecked());
                return true;

            case R.id.action_browser:
                launchExternalBrowser();
                return true;

            default:
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    private void launchExternalBrowser() {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(webUrl));
        startActivity(intent);
    }

    private void shareCurrentPage() {
        Intent intent = new Intent(Intent.ACTION_SEND);
        Uri comicUri = Uri.parse(webUrl);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_TEXT, comicUri.toString());
        startActivity(Intent.createChooser(intent, "Share with"));
    }
}
