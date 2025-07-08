package com.connectgold;

import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.view.View;
import android.widget.RelativeLayout;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.ads.*;
import com.google.android.gms.ads.rewarded.RewardedAdLoadCallback;
import com.google.android.gms.ads.rewarded.RewardItem;
import com.google.android.gms.ads.rewarded.RewardedAd;

public class MainActivity extends AppCompatActivity {

    private AdView bannerAdView;
    private InterstitialAd interstitialAd;
    private RewardedAd rewardedAd;
    private WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        MobileAds.initialize(this, initializationStatus -> {});

        RelativeLayout layout = new RelativeLayout(this);
        webView = new WebView(this);
        bannerAdView = new AdView(this);
        bannerAdView.setAdSize(AdSize.BANNER);
        bannerAdView.setAdUnitId("ca-app-pub-1171216593802007/8884489855"); // Banner Ad

        RelativeLayout.LayoutParams webParams = new RelativeLayout.LayoutParams(
            RelativeLayout.LayoutParams.MATCH_PARENT,
            RelativeLayout.LayoutParams.MATCH_PARENT
        );
        webParams.addRule(RelativeLayout.ABOVE, bannerAdView.getId());

        webView.setWebViewClient(new WebViewClient());
        webView.getSettings().setJavaScriptEnabled(true);
        webView.loadUrl("https://connectgold.sbs");

        layout.addView(webView, webParams);

        RelativeLayout.LayoutParams adParams = new RelativeLayout.LayoutParams(
            RelativeLayout.LayoutParams.MATCH_PARENT,
            RelativeLayout.LayoutParams.WRAP_CONTENT
        );
        adParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        layout.addView(bannerAdView, adParams);

        setContentView(layout);

        bannerAdView.loadAd(new AdRequest.Builder().build());
        loadInterstitialAd();
        loadRewardedAd();
    }

    private void loadInterstitialAd() {
        AdRequest adRequest = new AdRequest.Builder().build();
        InterstitialAd.load(this,
                "ca-app-pub-1171216593802007/4945244849", // Interstitial ID
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
                "ca-app-pub-1171216593802007/1435861786", // Rewarded ID
                adRequest,
                new RewardedAdLoadCallback() {
                    @Override
                    public void onAdLoaded(RewardedAd ad) {
                        rewardedAd = ad;
                        rewardedAd.show(MainActivity.this, rewardItem -> {
                            // Reward logic
                        });
                    }
                });
    }
}
