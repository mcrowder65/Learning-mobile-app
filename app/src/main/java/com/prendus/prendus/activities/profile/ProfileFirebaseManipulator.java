package com.prendus.prendus.activities.profile;

import android.util.Log;
import android.widget.EditText;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.prendus.prendus.constants.Constants;
import com.prendus.prendus.objects.user.MetaData;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mcrowder65 on 3/23/17.
 */

public class ProfileFirebaseManipulator {

    private EditText firstName;
    private EditText lastName;
    private EditText institution;
    private EditText email;

    public EditText getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName.setText(firstName);
    }

    public EditText getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName.setText(lastName);
    }


    public EditText getInstitution() {
        return institution;
    }

    public void setInstitution(String institution) {
        this.institution.setText(institution);
    }

    public EditText getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email.setText(email);
    }


    public ProfileFirebaseManipulator(EditText firstName, EditText lastName, EditText institution, EditText email) {

        this.firstName = firstName;
        this.lastName = lastName;
        this.institution = institution;
        this.email = email;
    }

    public void initUI(String id) {
        setInUI("users/" + id + "/metaData");
    }

    public void setInUI(String path) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference(path);
        final ProfileFirebaseManipulator self = this;
        ref.addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onCancelled(DatabaseError arg0) {
                Log.wtf(Constants.TAG, arg0.toString());
            }

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                MetaData obj = dataSnapshot.getValue(MetaData.class);
                self.setEmail(obj.getEmail());
                self.setFirstName(obj.getFirstName());
                self.setLastName(obj.getLastName());
                self.setInstitution(obj.getInstitution());
            }

        });

    }
}
