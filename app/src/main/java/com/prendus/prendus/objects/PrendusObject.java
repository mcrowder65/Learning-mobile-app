package com.prendus.prendus.objects;

import com.prendus.prendus.utilities.Utilities;

/**
 * Created by mcrowder65 on 3/16/17.
 */

public abstract class PrendusObject {


    protected String id;
    private long timestamp;

    final public String toJson() {
        return Utilities.g.toJson(this);
    }

    final public String getId() {
        return id;
    }

    final public void setId(String id) {
        this.id = id;
    }

    final public long getTimestamp() {
        return timestamp;
    }

    final public void setTimestamp(long timestamp) {
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

    @Override
    final public String toString() {
        return toJson();
    }

}
