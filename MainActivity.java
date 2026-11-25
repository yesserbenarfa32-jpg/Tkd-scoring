package com.tkdjudge.app;

import android.app.Activity;
import android.os.Bundle;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable;
import android.view.View;
import android.view.Gravity;
import android.view.inputmethod.InputMethodManager;
import android.content.Context;
import android.webkit.CookieManager;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.net.Uri;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.FrameLayout;

public class MainActivity extends Activity {
    private static final String SITE = "https://6b074be32dcb6989c1f77d5f--amazing-haupia-bee8be.netlify.app/";
    private WebView webView;
    private LinearLayout startScreen;

    private int dp(float value) {
        return (int) (value * getResources().getDisplayMetrics().density + 0.5f);
    }

    private GradientDrawable rounded(int color, int radiusDp, int strokeColor) {
        GradientDrawable d = new GradientDrawable();
        d.setColor(color);
        d.setCornerRadius(dp(radiusDp));
        if (strokeColor != Color.TRANSPARENT) d.setStroke(dp(1), strokeColor);
        return d;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setStatusBarColor(Color.rgb(4, 12, 32));
        getWindow().setNavigationBarColor(Color.rgb(4, 12, 32));
        showStartScreen();
    }

    private TextView text(String value, int size, int color, boolean bold) {
        TextView t = new TextView(this);
        t.setText(value);
        t.setTextSize(size);
        t.setTextColor(color);
        t.setGravity(Gravity.CENTER);
        if (bold) t.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
        return t;
    }

    private void showStartScreen() {
        startScreen = new LinearLayout(this);
        startScreen.setOrientation(LinearLayout.VERTICAL);
        startScreen.setGravity(Gravity.CENTER);
        startScreen.setPadding(dp(26), dp(24), dp(26), dp(24));
        startScreen.setBackground(new GradientDrawable(
            GradientDrawable.Orientation.TL_BR,
            new int[]{Color.rgb(4, 13, 37), Color.rgb(9, 26, 62), Color.rgb(30, 5, 30)}
        ));

        TextView logo = text("TKD", 48, Color.WHITE, true);
        logo.setTypeface(Typeface.create("sans-serif-condensed", Typeface.BOLD_ITALIC));
        TextView judge = text("JUDGE", 34, Color.rgb(255, 55, 65), true);
        judge.setTypeface(Typeface.create("sans-serif-condensed", Typeface.BOLD_ITALIC));
        TextView subtitle = text("WORLD TAEKWONDO KYORUGI", 14, Color.LTGRAY, true);
        subtitle.setPadding(0, 0, 0, dp(34));

        TextView heading = text("ENTER ROOM CODE", 20, Color.WHITE, true);
        heading.setPadding(0, 0, 0, dp(16));

        EditText room = new EditText(this);
        room.setSingleLine(true);
        room.setHint("Room code");
        room.setHintTextColor(Color.GRAY);
        room.setTextColor(Color.WHITE);
        room.setTextSize(18);
        room.setInputType(android.text.InputType.TYPE_CLASS_TEXT | android.text.InputType.TYPE_TEXT_FLAG_CAP_CHARACTERS);
        room.setPadding(dp(18), dp(14), dp(18), dp(14));
        room.setBackground(rounded(Color.rgb(19, 34, 65), 12, Color.rgb(55, 100, 180)));
        LinearLayout.LayoutParams inputParams = new LinearLayout.LayoutParams(-1, dp(58));
        inputParams.bottomMargin = dp(16);
        startScreen.addView(logo);
        startScreen.addView(judge);
        startScreen.addView(subtitle);
        startScreen.addView(heading);
        startScreen.addView(room, inputParams);

        Button connect = new Button(this);
        connect.setText("CONNECT");
        connect.setTextColor(Color.WHITE);
        connect.setTextSize(17);
        connect.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
        connect.setAllCaps(false);
        connect.setBackground(rounded(Color.rgb(0, 92, 255), 12, Color.rgb(50, 145, 255)));
        LinearLayout.LayoutParams buttonParams = new LinearLayout.LayoutParams(-1, dp(58));
        buttonParams.bottomMargin = dp(18);
        startScreen.addView(connect, buttonParams);

        TextView note = text("Enter the match room code provided by the organizer.", 13, Color.LTGRAY, false);
        startScreen.addView(note);

        connect.setOnClickListener(v -> {
            String code = room.getText().toString().trim();
            if (code.isEmpty()) {
                room.setError("Enter a room code");
                return;
            }
            ((InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE))
                .hideSoftInputFromWindow(room.getWindowToken(), 0);
            openJudge(code);
        });

        setContentView(startScreen);
    }

    private void openJudge(String code) {
        webView = new WebView(this);
        webView.setBackgroundColor(Color.rgb(4, 12, 32));
        WebSettings settings = webView.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setDomStorageEnabled(true);
        settings.setDatabaseEnabled(true);
        settings.setLoadsImagesAutomatically(true);
        settings.setJavaScriptCanOpenWindowsAutomatically(true);
        settings.setMediaPlaybackRequiresUserGesture(false);
        CookieManager.getInstance().setAcceptCookie(true);
        CookieManager.getInstance().setAcceptThirdPartyCookies(webView, true);
        webView.setWebViewClient(new WebViewClient());
        webView.setWebChromeClient(new WebChromeClient());
        setContentView(webView);
        String url = SITE + "?role=juge&room=" + Uri.encode(code);
        webView.loadUrl(url);
    }

    @Override
    public void onBackPressed() {
        if (webView != null && webView.getVisibility() == View.VISIBLE && webView.canGoBack()) {
            webView.goBack();
        } else if (webView != null) {
            webView = null;
            showStartScreen();
        } else {
            super.onBackPressed();
        }
    }
}
