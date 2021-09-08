package com.mtca.mtcaptcha;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;

public class MTCaptcha extends WebView {
    private static WebView webView;
    static LinearLayout layout;

    static String token = "";
    static String domain = "";
    static String sitekey = "";
    static String theme = "";
    static String widgetSize = "";
    static String customStyle = "";
    static String action = "";

    static String config;
    private static LinearLayout.LayoutParams params;


    public MTCaptcha(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        inflate(context, R.layout.customview, this);
        webView = new WebView(context);
        layout=(LinearLayout) findViewById(R.id.mtca_layout);
    }

    public static void init(String domain, String sitekey) {
        setDomain(domain);
        setSitekey(sitekey);
        config = generateConfiguration();

    }

    private static String generateConfiguration() {
        String config = "{\n";
        config += "    \"sitekey\": \"" + getSitekey() + "\", // Get the site key from Sites page of MTCaptcha admin site \n";
        if (getWidgetSize() != null)
            config += "    \"widgetSize\": \"" + getWidgetSize() + "\",\n";
        if (getTheme() != null)
            config += "    \"theme\": \"" + getTheme() + "\",\n";
        if (getAction() != null)
            config += "    \"action\": \"" + getAction() + "\",\n";
        if (getCustomStyle() != null)
            config += "    \"customStyle\": " + getCustomStyle() + ",\n";
        config += "};\n";
        return config;
    }

    public static void render(Context context) {

//        // height is 0, weight is 1
//        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 0, 1);

        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webView.addJavascriptInterface(new WebAppInterface(context), "MTCaptcha");
        params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 0, 1);
        layout.addView(webView, params);



        // Create an unencoded HTML string
        String unencodedHtml =
                "<!-- Configuration to construct the captcha widget.\n" +
                        "      Sitekey is a Mandatory Parameter-->\n" +
                        "<script type=\"text/javascript\">\n" +
                        "    function getToken() {\n" +
                        "var token=window.mtcaptcha.getVerifiedToken();" +
                        "        MTCaptcha.setToken(token);\n" +
                        "    }\n" +
                        "</script>" +
                        "<script type=\"text/javascript\">\n" +
                        "    var mtcaptchaConfig = " + config +
                        "   (function(){var mt_service = document.createElement('script');mt_service.async = true;mt_service.src = 'https://qa-service.sadtron.com/mtcv1/client/mtcaptcha.min.js';(document.getElementsByTagName('head')[0] || document.getElementsByTagName('body')[0]).appendChild(mt_service);\n" +
                        "   var mt_service2 = document.createElement('script');mt_service2.async = true;mt_service2.src = 'https://qa-service2.sadtron.com/mtcv1/client/mtcaptcha2.min.js';(document.getElementsByTagName('head')[0] || document.getElementsByTagName('body')[0]).appendChild(mt_service2);}) ();\n" +
                        "   </script>" +
                        "<!-- MTCap library by default looks for a DIV of class='mtcaptcha' to place the captcha widget -->\n" +
//                        "<input type=\"button\" value=\"Say hello\" onClick=\"showAndroidToast()\" />" +
                        " <div class=\"mtcaptcha\"/>";
        webView.loadDataWithBaseURL(getDomain(), unencodedHtml, "text/html", null, getDomain());
    }

    private static String getDomain() {
        return domain;
    }

    private static void setDomain(String url) {
        domain = url;
    }

    private static String getSitekey() {
        return sitekey;
    }

    private static void setSitekey(String key) {
        sitekey = key;
    }

    public static void setTheme(String th) {
        theme = th;
    }

    private static String getTheme() {
        return theme;
    }

    public static void setWidgetSize(String size) {
        widgetSize = size;
    }

    private static String getWidgetSize() {
        return widgetSize;
    }

    public static void setCustomStyle(String style) {
        customStyle = style;
    }

    private static String getCustomStyle() {
        return customStyle;
    }

    private static String getAction() {
        return action;
    }

    public static void setAction(String act) {
        action = act;
    }

    public static String getVerifiedToken() {
        return token;
    }
}
