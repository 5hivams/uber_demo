package com.flickr.shivam.flickrsearch.Parser;

import android.util.Log;

import com.flickr.shivam.flickrsearch.Utils.Constants;
import com.flickr.shivam.flickrsearch.model.Image_model;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class JSONParser {

    private static String result = "";
    private static JSONObject jObj = null;

    // constructor
    public JSONParser() {
    }

    public List<Image_model> getJSONFromStream(InputStream is) {

        if (is != null) {
            BufferedReader reader = new BufferedReader(new InputStreamReader(is));
            StringBuilder sb = new StringBuilder();

            String line;
            try {
                while ((line = reader.readLine()) != null) {
                    sb.append(line).append('\n');
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            result = sb.toString();
            // try parse the string to a JSON object
            try {
                jObj = new JSONObject(result);
            } catch (JSONException e) {
                Log.e("JSON Parser", "Error parsing data " + e.toString());
            } finally {
                try {
                    if (is != null)
                        is.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        // return JSON String
        return getListOfImages(jObj);

    }

    private List<Image_model> getListOfImages(JSONObject jObj) {
        List<Image_model> entryList = new ArrayList<>();
        if (jObj != null && jObj.has("photos"))
            try {
                JSONArray jsonArray = jObj.getJSONObject("photos").getJSONArray(Constants.TAG_JSON_ARRAY);
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject object = jsonArray.getJSONObject(i);
                    Image_model entry = new Image_model();
                    entry.setId(object.getString(Constants.TAG_ID));
                    entry.setTitle(object.getString(Constants.TAG_TITLE));
                    entry.setUrl("http://farm" + object.getString(Constants.TAG_FARM) + ".static.flickr.com/" + object.getString(Constants.TAG_SERVER) + "/" + object.getString(Constants.TAG_ID) + "_" + object.getString(Constants.TAG_SECRET) + "_m.jpg");
                    entryList.add(entry);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        return entryList;
    }


}
