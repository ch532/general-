package com.connectgold;

import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.gms.ads.*;

public class MainActivity extends AppCompatActivity {

    private AdView bannerAdView;
    private InterstitialAd interstitialAd;
    private RewardedAd rewardedAd;
    private WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MobileAds.initialize(this, initializationStatus -> {});

        webView = findViewById(R.id.webView);
        bannerAdView = findViewById(R.id.adView);

        // Enable JavaScript for Umami and WebView
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebViewClient(new WebViewClient());
        webView.loadUrl("https://connectgold.sbs");  // Umami already works here

        // Load banner ad
        AdRequest adRequest = new AdRequest.Builder().build();
        bannerAdView.loadAd(adRequest);

        // Load interstitial and rewarded
        loadInterstitialAd();
        loadRewardedAd();
    }

    private void loadInterstitialAd() {
        AdRequest adRequest = new AdRequest.Builder().build();
        InterstitialAd.load(this,
                "ca-app-pub-1171216593802007/4945244849",
                adRequest,
                new InterstitialAdLoadCallback() {
                    @Override
                    public void onAdLoaded(InterstitialAd ad) {
                        interstitialAd = ad;
                        interstitialAd.show(MainActivity.this);
                    }
                });
    }

    private void loadRewardedAd() {
        AdRequest adRequest = new AdRequest.Builder().build();
        RewardedAd.load(this,
                "ca-app-pub-1171216593802007/1435861786",
                adRequest,
                new RewardedAdLoadCallback() {
                    @Override
                    public void onAdLoaded(RewardedAd ad) {
                        rewardedAd = ad;
                        rewardedAd.show(MainActivity.this, rewardItem -> {
                            // Reward logic (optional)
                        });
                    }
                });
    }
}
