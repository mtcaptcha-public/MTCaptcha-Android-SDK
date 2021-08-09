package com.mtca.mtcaptcha;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.webkit.WebSettings;
import android.webkit.WebView;

import androidx.annotation.Nullable;

public class MTCaptcha extends WebView {
    private static WebView webView;
    static String token="";



    public MTCaptcha(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        inflate(context, R.layout.customview, this);
        webView = (WebView) findViewById(R.id.webview);
    }

    @SuppressLint({"AddJavascriptInterface", "SetJavaScriptEnabled"})

    public static void init(Context context, String url, String key) {

        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webView.addJavascriptInterface(new WebAppInterface(context), "Android");

        // Create an unencoded HTML string
        String unencodedHtml =
                "<!-- Configuration to construct the captcha widget.\n" +
                        "      Sitekey is a Mandatory Parameter-->\n" +
                        "<script type=\"text/javascript\">\n" +
                        "    function showAndroidToast() {\n" +
                        "var token=window.mtcaptcha.getVerifiedToken();" +
                        "        Android.getToken(token);\n" +
                        "    }\n" +
                        "</script>" +
                        "<script type=\"text/javascript\">\n" +
                        "    var mtcaptchaConfig = {\n" +
                        "    \"sitekey\": \"" + key + "\", // Get tie site key from Sites page of MTCaptcha admin site \n" +
                        "    \"widgetSize\": \"mini\",\n" +
                        "    \"theme\": \"overcast\",\n" +
                        "    \"verified-callback\": \"showAndroidToast\",\n"+
                        "};\n" +
                        "   (function(){var mt_service = document.createElement('script');mt_service.async = true;mt_service.src = 'https://qa-service.sadtron.com/mtcv1/client/mtcaptcha.min.js';(document.getElementsByTagName('head')[0] || document.getElementsByTagName('body')[0]).appendChild(mt_service);\n" +
                        "   var mt_service2 = document.createElement('script');mt_service2.async = true;mt_service2.src = 'https://qa-service2.sadtron.com/mtcv1/client/mtcaptcha2.min.js';(document.getElementsByTagName('head')[0] || document.getElementsByTagName('body')[0]).appendChild(mt_service2);}) ();\n" +
                        "   </script>" +
                        "<!-- MTCap library by default looks for a DIV of class='mtcaptcha' to place the captcha widget -->\n" +
//                        "<input type=\"button\" value=\"Say hello\" onClick=\"showAndroidToast()\" />" +
                        " <div class=\"mtcaptcha\"/>";
        String baseUrl = "http://" + url;

        webView.loadDataWithBaseURL(baseUrl, unencodedHtml, "text/html", null, baseUrl);
    }
    public static String getVerifiedToken(){
        return token;
    }
}
