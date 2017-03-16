package com.prendus.prendus.firebase;

import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.prendus.prendus.objects.PrendusObject;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Semaphore;

/**
 * Created by mcrowder65 on 3/16/17.
 */

public class Firebase {
    private static final String TAG = "MY APP";
    private Semaphore semaphore;

    public Firebase() {
        semaphore = new Semaphore(1);
    }
    public List<String> getAllAsJson(String path, final PrendusObject desiredClass) {
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference(path);
        final List<String> objects = new ArrayList<>();
        Log.wtf(TAG, "objects initialized");
        ref.addValueEventListener(new ValueEventListener() {

            @Override
            public void onCancelled(DatabaseError arg0) {

            }

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.wtf(TAG, "on data change");
                for (DataSnapshot child : dataSnapshot.getChildren()) {
                    PrendusObject obj = child.getValue(desiredClass.getClass());
                    objects.add(obj.toJson());
                    Log.wtf(TAG, obj.toJson());
                }
                semaphore.release();
            }

        });
        try {
            semaphore.acquire();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return objects;
    }
    public List<PrendusObject> getAllAsObjects(String path, final PrendusObject desiredClass) {
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference(path);
        final List<PrendusObject> objects = new ArrayList<>();
        ref.addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onCancelled(DatabaseError arg0) {

            }

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot child : dataSnapshot.getChildren()) {
                    PrendusObject obj = child.getValue(desiredClass.getClass());
                    objects.add(obj);
                }
                semaphore.release();
            }

        });

        try {
            semaphore.acquire();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return objects;

    }

    public PrendusObject get(String path, final PrendusObject desiredClass) {
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference(path);
        final List<PrendusObject> objects = new ArrayList<>();
        ref.addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onCancelled(DatabaseError arg0) {

            }

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot child : dataSnapshot.getChildren()) {
                    PrendusObject obj = child.getValue(desiredClass.getClass());
                    objects.add(obj);
                }
                semaphore.release();
            }

        });

        try {
            semaphore.acquire();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return objects.get(0);

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
