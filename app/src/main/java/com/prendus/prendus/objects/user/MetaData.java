package com.prendus.prendus.objects.user;

import com.prendus.prendus.objects.PrendusObject;

/**
 * Created by mcrowder65 on 3/23/17.
 */

public class MetaData extends PrendusObject {
    private String email;
    private String firstName;
    private String lastName;
    private String institution;
    private String uid;

    public MetaData() {
    }

    public MetaData(String email, String firstName, String lastName, String institution, String uid) {
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.institution = institution;
        this.uid = uid;
    }

    public MetaData(String email, String firstName, String lastName, String institution) {

        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.institution = institution;
    }

    public MetaData(String email, String firstName, String lastName) {

        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public MetaData(String email, String firstName) {

        this.email = email;
        this.firstName = firstName;
    }

    public MetaData(String email) {

        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getInstitution() {
        return institution;
    }

    public void setInstitution(String institution) {
        this.institution = institution;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    
}
