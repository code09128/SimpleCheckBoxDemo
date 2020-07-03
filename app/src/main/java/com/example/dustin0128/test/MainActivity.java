package com.example.dustin0128.test;

import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.URLSpan;
import android.text.util.Linkify;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.example.dustin0128.test.R;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {
    private CheckBox allbox;
    private CheckBox eatbox;
    private CheckBox drinkbox;
    private CheckBox sleepbox;
    private final String regex = "(https?|ftp|file)://[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        allbox =(CheckBox) findViewById(R.id.all);
        eatbox =(CheckBox) findViewById(R.id.eat);
        drinkbox =(CheckBox) findViewById(R.id.drink);
        sleepbox =(CheckBox) findViewById(R.id.sleep);
        TextView addGcmBody = findViewById(R.id.textView);

        AllCheckListener allCheckListener=new AllCheckListener();
        allbox.setOnClickListener(allCheckListener);

        BoxCheckListener boxCheckListener=new BoxCheckListener();
        drinkbox.setOnCheckedChangeListener(boxCheckListener);
        eatbox.setOnCheckedChangeListener(boxCheckListener);
        sleepbox.setOnCheckedChangeListener(boxCheckListener);
    }

    class AllCheckListener implements View.OnClickListener{
        @Override
        public void onClick(View v) {
            CheckBox all=(CheckBox)v;
            eatbox.setChecked(all.isChecked());
            drinkbox.setChecked(all.isChecked());
            sleepbox.setChecked(all.isChecked());

        }
    }

    class BoxCheckListener implements CompoundButton.OnCheckedChangeListener{
        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            if(!isChecked){
                allbox.setChecked(false);
            }
        }
    }

    public void setText(CharSequence text, TextView.BufferType type) {
        String str = text.toString();

        SpannableString spannableString = new SpannableString(str);
        Matcher urlMatcher = Pattern.compile(regex).matcher(str);
        while (urlMatcher.find()) {
            String url = urlMatcher.group();
            int start = urlMatcher.start();
            int end = urlMatcher.end();
            spannableString.setSpan(new URLSpan(url), start, end, 0);
        }
    }

    public class GoToURLSpan extends ClickableSpan {

        private final String url;

        public GoToURLSpan(@NonNull String url) {
            this.url = url;
        }

        @Override
        public void onClick(View view)  {

            Uri webPage = Uri.parse(url)  ;
            Intent intent = new Intent(Intent.ACTION_VIEW, webPage);
            view.getContext().startActivity(intent);
        }
    }
}
