package com.flickr.shivam.flickrsearch.Netwrok;

import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class RequestImageSearch {

    HttpURLConnection conn;

    public InputStream downloadImageFromUrl(String url) throws IOException {
        InputStream in = null;

        Log.d("downloadurl", url);

        try {
            URL imageUrl = new URL(url);
            conn = (HttpURLConnection) imageUrl.openConnection();
            conn.setConnectTimeout(30000);
            conn.setReadTimeout(30000);
            //conn.setInstanceFollowRedirects(true);
            in = conn.getInputStream();

        } catch (Exception e) {
            // TODO Auto-generated catch block
            Log.d("download url", "client protocol exception");
            e.printStackTrace();
        } finally {
            /*if(conn!=null)
                conn.disconnect();*/
        }
        return in;

    }


}
