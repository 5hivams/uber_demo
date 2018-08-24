package com.flickr.shivam.flickrsearch.Netwrok;

import android.os.AsyncTask;
import android.util.Log;

import com.flickr.shivam.flickrsearch.Listener.ApiListener;
import com.flickr.shivam.flickrsearch.Parser.JSONParser;
import com.flickr.shivam.flickrsearch.model.Image_model;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import static com.flickr.shivam.flickrsearch.Utils.Constants.FlickrApiKey;
import static com.flickr.shivam.flickrsearch.Utils.Constants.FlickrPage_format;
import static com.flickr.shivam.flickrsearch.Utils.Constants.FlickrQuery_key;
import static com.flickr.shivam.flickrsearch.Utils.Constants.FlickrQuery_page;
import static com.flickr.shivam.flickrsearch.Utils.Constants.FlickrQuery_tag;
import static com.flickr.shivam.flickrsearch.Utils.Constants.FlickrQuery_url;
import static com.flickr.shivam.flickrsearch.Utils.Constants.ResponseFormat;


public class DownloadImageTask extends AsyncTask<String, Void, List<Image_model>> {

    private JSONParser jsonParser;

    public DownloadImageTask() {
        this.jsonParser = new JSONParser();
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        ApiListener.getInstance().onCallStarted();
    }

    @Override
    protected List<Image_model> doInBackground(String... q) {

        try {
            InputStream result = new RequestImageSearch().downloadImageFromUrl(generateQueryURL(q[0], q[1]));
            return jsonParser.getJSONFromStream(result);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return null;
        }

    }


    @Override
    protected void onPostExecute(final List<Image_model> entries) {

        Log.d("onpostexecute", "in on post execute");
        if (entries != null) {
            ApiListener.getInstance().onCallCompleted(entries);

        } else {
            ApiListener.getInstance().onCallStopped("Failed");
        }

    }

    @Override
    protected void onCancelled() {
        super.onCancelled();
        ApiListener.getInstance().onCallStopped("Stopped");
    }

    private String generateQueryURL(String query, String pageNo) {
        return FlickrQuery_url + FlickrQuery_key + FlickrApiKey
                + FlickrQuery_tag + query + FlickrPage_format + ResponseFormat + FlickrQuery_page + pageNo + "&nojsoncallback=2&safe_search=1";
    }
}
