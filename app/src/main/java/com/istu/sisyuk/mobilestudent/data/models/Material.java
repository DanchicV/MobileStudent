package com.istu.sisyuk.mobilestudent.data.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Material {

    @SerializedName("name")
    @Expose
    long name;

    @SerializedName("link")
    @Expose
    String link;

    public long getName() {
        return name;
    }

    public void setName(long name) {
        this.name = name;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }
}
