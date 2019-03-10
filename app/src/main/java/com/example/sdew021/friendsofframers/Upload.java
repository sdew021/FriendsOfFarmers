package com.example.sdew021.friendsofframers;

public class Upload {
    private String mName;
    private String mImageurl;
    public Upload(){}

    public Upload(String mName, String mImageurl) {
        this.mName = mName;
        this.mImageurl = mImageurl;
    }

    public String getmName() {
        return mName;
    }

    public void setmName(String mName) {
        this.mName = mName;
    }

    public String getmImageurl() {
        return mImageurl;
    }

    public void setmImageurl(String mImageurl) {
        this.mImageurl = mImageurl;
    }
}
