package com.example.cloudint.cloudmain;

import java.util.ArrayList;

/**
 * Created by PiskunovI on 26.11.2014.
 */
public class CloudikeObject {
    String path;
    Boolean isFolder;
    String mbytes;
}

class FilesArray{

    ArrayList<CloudikeObject> items = new ArrayList<CloudikeObject>();

    FilesArray(ArrayList<CloudikeObject> result)
    {
        this.items = result;
    }
}
