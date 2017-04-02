package com.prendus.prendus.firebase;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.prendus.prendus.objects.PrendusObject;

/**
 * Created by mcrowder65 on 3/16/17.
 */

public class Firebase {

    public Firebase() {

    }


    public void update(String path, PrendusObject obj) {

        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference(path);
        ref.setValue(obj);
    }

    public void update(String path, String str) {

        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference(path);
        ref.setValue(str);
    }

    public void create(String path, PrendusObject obj) {
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference(path);
        DatabaseReference pushedRef = ref.push();
        pushedRef.setValue(obj);

    }

    public void delete(String path) {
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference(path);
        ref.setValue(null);

    }


}
