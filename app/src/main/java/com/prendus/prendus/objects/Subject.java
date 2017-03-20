package com.prendus.prendus.objects;

import com.prendus.prendus.utilities.Utilities;

/**
 * Created by mcrowder65 on 3/16/17.
 */

public class Subject extends PrendusObject {

    private String disciplineId;
    private String title;

    public Subject(String disciplineId, String title, String id, long timestamp) {
        super(timestamp);
        this.disciplineId = disciplineId;
        this.title = title;
        this.id = id;
    }
    public Subject(){}
    public String getDisciplineId() {
        return disciplineId;
    }

    public void setDisciplineId(String disciplineId) {
        this.disciplineId = disciplineId;
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String toJson() {
        String jsonString = Utilities.g.toJson(this);
        return jsonString;
    }

}
