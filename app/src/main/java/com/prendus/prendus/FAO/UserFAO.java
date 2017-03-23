package com.prendus.prendus.FAO;

import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.prendus.prendus.objects.PrendusObject;
import com.prendus.prendus.objects.user.MetaData;
import com.prendus.prendus.objects.user.User;
import com.prendus.prendus.utilities.Utilities;

import java.util.concurrent.ExecutionException;

/**
 * Created by mcrowder65 on 3/23/17.
 */

public class UserFAO {

    static public MetaData getUserMetaData(String id) {
        String path = "users/" + id + "/metaData/";

        Task<PrendusObject> metaData = null;
        metaData = Utilities.firebase.getTask(path, new MetaData());
        //(MetaData)Utilities.firebase.get(path, new MetaData());
        return null;
    }

}
