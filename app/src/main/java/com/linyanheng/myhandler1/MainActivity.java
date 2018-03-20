package com.linyanheng.myhandler1;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    WebView web;
    String urlString = "https://sites.google.com/view/JackyCafe01/android-%E7%AD%86%E8%A8%98/httpurlconnection";
    Handler handler = new HtmlHandler();
    String html;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        web = (WebView) findViewById(R.id.webview);
        new Thread(new Runnable() {
            @Override
            public void run() {
                html = getData();
                Message msg = new Message();
                msg.what = 1;
                Bundle data = new Bundle();
                data.putString("html",html);
                msg.setData(data);
                handler.sendMessage(msg);
            }
        }).start();
    }


    public String getData(){

        try {
            URL url = new URL(urlString);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            InputStream in = conn.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));
            String text ;
            StringBuilder sb = new StringBuilder();

            while ((text=reader.readLine())!=null)
            {
                sb.append(text);
            }
            return sb.toString();
        } catch (MalformedURLException e) {
            e.printStackTrace();return null;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

    }

    class HtmlHandler extends Handler{
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Bundle data =  msg.getData();
            String html = data.getString("html");
          //  web.loadData(html,"text/html",null);
            web.loadDataWithBaseURL(null,html,"text/html","UTF-8",null);
        }
    }
}
