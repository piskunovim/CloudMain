package com.example.cloudint.cloudmain;

import android.util.Log;

import java.util.ArrayList;

/**
 * Created by PiskunovI on 26.11.2014.
 */
public class CloudikeObject {
    String path;
    Boolean folder;
    String mbytes;
    ArrayList<CloudikeObject> result = new ArrayList<CloudikeObject>();

}

class FilesArray{

    String LOG_TAG = "FilesArray";
    ArrayList<CloudikeObject> items = new ArrayList<CloudikeObject>();

    FilesArray(ArrayList<CloudikeObject> result)
    {
        this.items = result;
    }
}
