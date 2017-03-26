package com.prendus.prendus.objects;

import com.prendus.prendus.utilities.Utilities;

/**
 * Created by mcrowder65 on 3/16/17.
 */

public abstract class PrendusObject {


    protected String id;
    private long timestamp;

    public String toJson() {
        return Utilities.g.toJson(this);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public PrendusObject() {
        this.timestamp = System.currentTimeMillis();
    }

    public PrendusObject(long timestamp) {
        this.timestamp = timestamp;
    }

    public PrendusObject(long timestamp, String id) {
        this.timestamp = timestamp;
        this.id = id;
    }

}
