package br.goiasrp.launcher;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.Gravity;
import android.widget.LinearLayout;
import android.widget.TextView;

public class SplashActivity extends Activity {
    @Override
    protected void onCreate(Bundle state) {
        super.onCreate(state);
        getWindow().setStatusBarColor(Color.rgb(5, 10, 18));
        getWindow().setNavigationBarColor(Color.rgb(5, 10, 18));

        LinearLayout root = new LinearLayout(this);
        root.setOrientation(LinearLayout.VERTICAL);
        root.setGravity(Gravity.CENTER);
        root.setBackgroundColor(Color.rgb(5, 10, 18));

        TextView icon = new TextView(this);
        icon.setText("🚘");
        icon.setTextSize(64);
        icon.setGravity(Gravity.CENTER);
        root.addView(icon, new LinearLayout.LayoutParams(-1, 100));

        TextView title = new TextView(this);
        title.setText("BRASIL CARROS");
        title.setTextColor(Color.WHITE);
        title.setTextSize(28);
        title.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
        title.setGravity(Gravity.CENTER);
        root.addView(title);

        TextView sub = new TextView(this);
        sub.setText("Catálogo de carros brasileiros");
        sub.setTextColor(Color.LTGRAY);
        sub.setTextSize(14);
        sub.setGravity(Gravity.CENTER);
        root.addView(sub);

        setContentView(root);

        new Handler(Looper.getMainLooper()).postDelayed(() -> {
            startActivity(new Intent(this, MainActivity.class));
            finish();
        }, 1200);
    }
}
