package com.flickr.shivam.flickrsearch.Listener;

import com.flickr.shivam.flickrsearch.model.Image_model;

import java.util.List;

public class ApiListener {

    private static ApiListener mInstance;
    private OnApiCallListener mListener;

    public static ApiListener getInstance() {
        if (mInstance == null)
            mInstance = new ApiListener();
        return mInstance;
    }

    public void setListener(OnApiCallListener listener) {
        mListener = listener;
    }

    public void onCallStopped(String query) {
        if (mListener != null) {
            mListener.callStopped(query);
        }
    }

    public void onCallCompleted(List<Image_model> entries) {
        if (mListener != null) {
            mListener.callCompleted(entries);
        }
    }

    public void onCallStarted() {
        if (mListener != null) {
            mListener.callStarted();
        }
    }


    public interface OnApiCallListener {

        void callStarted();

        void callStopped(String status);

        void callCompleted(List<Image_model> entries);

    }


}
