package com.mtca.mtcaptcha;

import android.content.Context;
import android.util.AttributeSet;
import android.webkit.WebSettings;
import android.webkit.WebView;

import androidx.annotation.Nullable;

public class MTCaptcha extends WebView {

    static String token = "";
    static String domain = "";
    static String sitekey = "";
    static String theme = "";
    static String widgetSize = "mini";
    static String customStyle = "";
    static String action = "";

    static String config;


    public MTCaptcha(Context context) {
        super(context);

    }

    public MTCaptcha(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public void init(Context context, String domain, String sitekey) {
        setDomain(domain);
        setSitekey(sitekey);
        config = generateConfiguration();
        render(context);

    }

    private String generateConfiguration() {
        String config = "{\n";
        config += "    \"sitekey\": \"" + getSitekey() + "\",\n";
        if (!isEmpty(getWidgetSize()))
            config += "    \"widgetSize\": \"" + getWidgetSize() + "\",\n";
        if (!isEmpty(getTheme()))
            config += "    \"theme\": \"" + getTheme() + "\",\n";
        if (!isEmpty(getAction()))
            config += "    \"action\": \"" + getAction() + "\",\n";
        if (!isEmpty(getCustomStyle()))
            config += "    \"customStyle\": " + getCustomStyle() + ",\n";
        config += "};\n";
        return config;
    }

    private Boolean isEmpty(String value) {
        if (value != null || value == "") {
            return true;
        } else {
            return false;
        }
    }

    public void render(Context context) {

        WebSettings webSettings = this.getSettings();
        webSettings.setJavaScriptEnabled(true);
        this.addJavascriptInterface(new WebAppInterface(context), "MTCaptcha");

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
        this.loadDataWithBaseURL(getDomain(), unencodedHtml, "text/html", null, getDomain());
    }


    private static String getDomain() {

        return domain;
    }

    // Domain name must have http or https
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


