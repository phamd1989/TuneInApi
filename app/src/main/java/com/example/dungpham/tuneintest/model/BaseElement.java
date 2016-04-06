package com.example.dungpham.tuneintest.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by dungpham on 4/5/16.
 */
public class BaseElement implements Serializable {
    String text;
    String subtext;
    String type;
    List<BaseElement> children;
    String URL;
    String image;

    public String getURL() {
        return URL;
    }

    public String getImageUrl() {return image;}

    public String getType() {
        return type;
    }

    public String getText() {
        return text;
    }

    public String getSubtext() { return subtext; }

    public List<BaseElement> getChildren() {
        return children;
    }
}
