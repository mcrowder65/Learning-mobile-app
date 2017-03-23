package com.prendus.prendus.objects.user;

import com.prendus.prendus.objects.PrendusObject;
import com.prendus.prendus.utilities.Utilities;

/**
 * Created by mcrowder65 on 3/23/17.
 */

public class User extends PrendusObject {
    private MetaData metaData;

    public User(){}
    public User(MetaData metaData) {
        this.metaData = metaData;
    }
    public MetaData getMetaData() {
        return metaData;
    }

    public void setMetaData(MetaData metaData) {
        this.metaData = metaData;
    }


    @Override
    public String toJson() {
        return Utilities.g.toJson(this);
    }
}
