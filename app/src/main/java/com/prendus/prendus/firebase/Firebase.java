package com.prendus.prendus.firebase;

import android.app.Activity;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.prendus.prendus.activities.login.LoginActivity;
import com.prendus.prendus.constants.Constants;
import com.prendus.prendus.objects.PrendusObject;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Semaphore;

/**
 * Created by mcrowder65 on 3/16/17.
 */

public class Firebase {
    private static final String TAG = "MY APP";
    private Semaphore semaphore;

    public Firebase() {
        semaphore = new Semaphore(10000);
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
        return null;

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
