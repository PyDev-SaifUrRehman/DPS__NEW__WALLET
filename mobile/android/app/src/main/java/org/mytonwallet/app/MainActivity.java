package org.mytonwallet.app;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import androidx.core.splashscreen.SplashScreen;
import androidx.core.view.WindowCompat;
import androidx.core.view.WindowInsetsControllerCompat;
import androidx.interpolator.view.animation.FastOutSlowInInterpolator;

import com.getcapacitor.BridgeActivity;

public class MainActivity extends BridgeActivity {
  private boolean keep = true;
  private final int DELAY = 1000;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    SplashScreen splashScreen = SplashScreen.installSplashScreen(this);
    splashScreen.setKeepOnScreenCondition(() -> keep);
    splashScreen.setOnExitAnimationListener(splashScreenView -> {
      AnimatorSet animationSet = new AnimatorSet();

      View view = splashScreenView.getView();
      ObjectAnimator scaleY = ObjectAnimator.ofFloat(view, View.SCALE_Y, 4f);
      ObjectAnimator scaleX = ObjectAnimator.ofFloat(view, View.SCALE_X, 4f);
      ObjectAnimator opacity = ObjectAnimator.ofFloat(view, View.ALPHA, 0.0f);

      animationSet.setInterpolator(new FastOutSlowInInterpolator());
      animationSet.setDuration(350L);
      animationSet.playTogether(scaleX, scaleY, opacity);

      animationSet.addListener(new AnimatorListenerAdapter() {
        @Override
        public void onAnimationEnd(Animator animation) {
          splashScreenView.remove();
          updateStatusBarStyle();
        }
      });

      animationSet.start();
      makeStatusBarTransparent();
      makeNavigationBarTransparent();
      updateStatusBarStyle();
    });

    Handler handler = new Handler();
    handler.postDelayed(() -> keep = false, DELAY);
  }

  private void makeStatusBarTransparent() {
    Window window = getWindow();
    View decorView = window.getDecorView();
    decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
    window.setStatusBarColor(android.graphics.Color.TRANSPARENT);
  }

  private void makeNavigationBarTransparent() {
    Window window = getWindow();

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
      WindowCompat.setDecorFitsSystemWindows(window, false);
      window.setNavigationBarColor(Color.TRANSPARENT);
      window.setNavigationBarContrastEnforced(false);
    } else {
      window.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
    }
  }

  private void updateStatusBarStyle() {
    String style = ((MTWApplication) getApplicationContext()).getCurrentStatusBar();
    if (style == null || style.equals("DEFAULT"))
      return;

    Window window = getWindow();
    View decorView = window.getDecorView();

    WindowInsetsControllerCompat windowInsetsControllerCompat = WindowCompat.getInsetsController(window, decorView);
    windowInsetsControllerCompat.setAppearanceLightStatusBars(!style.equals("DARK"));
    windowInsetsControllerCompat.setAppearanceLightNavigationBars(!style.equals("DARK"));
  }
}