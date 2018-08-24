package com.flickr.shivam.flickrsearch.Activity;


import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.Toast;

import com.flickr.shivam.flickrsearch.Adapter.LazyAdapter;
import com.flickr.shivam.flickrsearch.Listener.ApiListener;
import com.flickr.shivam.flickrsearch.Netwrok.DownloadImageTask;
import com.flickr.shivam.flickrsearch.R;
import com.flickr.shivam.flickrsearch.model.Image_model;

import java.util.ArrayList;
import java.util.List;


public class ResultActivity extends Activity implements OnItemClickListener, ApiListener.OnApiCallListener {

    private List<Image_model> imageList = new ArrayList<>();
    private ListView list;
    private LazyAdapter adapter;
    private String queryString;
    private int pageNo = 1;
    private ProgressDialog dialog;
    private int visibleThreshold = 15;
    private int previousTotal = 0;
    private boolean loading = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        Bundle extras = getIntent().getExtras();
        GridView gridView = findViewById(R.id.grid_view);
        gridView.setOnItemClickListener(this);

        dialog = new ProgressDialog(this);

        ApiListener.getInstance().setListener(this);


        if (extras != null) {
            queryString = extras.getString("QUERY");
        }

        adapter = new LazyAdapter(ResultActivity.this, imageList);
        gridView.setAdapter(adapter);

        gridView.setOnScrollListener(new AbsListView.OnScrollListener() {

            @Override
            public void onScroll(AbsListView view,
                                 int firstVisibleItem, int visibleItemCount,
                                 int totalItemCount) {
                //Algorithm to check if the last item is visible or not
                //final int lastItem = firstVisibleItem + visibleItemCount +18;
                if (loading) {
                    if (totalItemCount > previousTotal) {
                        loading = false;
                        previousTotal = totalItemCount;
                        pageNo++;
                    }
                }
                if (!loading && (totalItemCount - visibleItemCount) <= (firstVisibleItem + visibleThreshold)) {
                    // I load the next page of gigs using a background task,
                    // but you can call any function here.
                    searchImages(queryString, pageNo);
                    loading = true;
                }
            }

            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                //blank, not required in your case
            }
        });

        searchImages(queryString, pageNo);

    }

    private void searchImages(String queryString, int pageNo) {
        DownloadImageTask task = new DownloadImageTask();
        task.execute(queryString, "" + pageNo);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.result, menu);
        return true;
    }


    @Override
    public void onItemClick(AdapterView<?> arg0, View view, int pos, long arg3) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(imageList.get(pos).getUrl().replace("_m.jpg", "_c.jpg")));
        startActivity(intent);
    }

    @Override
    public void callStarted() {
        if (pageNo == 1) {
            dialog.setMessage(getString(R.string.loading_msg));
            dialog.show();
        } else
            Toast.makeText(this, R.string.loading_msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void callStopped(String query) {
        if (dialog != null && dialog.isShowing())
            dialog.dismiss();

    }

    @Override
    public void callCompleted(List<Image_model> entries) {
        if (dialog != null && dialog.isShowing())
            dialog.dismiss();
        if (entries == null || entries.size() == 0) {
            Toast.makeText(this, R.string.loading_error, Toast.LENGTH_LONG).show();
        } else {
            imageList.addAll(entries);
            adapter.notifyDataSetChanged();
        }
    }
}



