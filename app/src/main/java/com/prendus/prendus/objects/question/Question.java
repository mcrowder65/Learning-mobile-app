package com.prendus.prendus.objects.question;

import com.google.gson.internal.LinkedTreeMap;
import com.prendus.prendus.objects.PrendusObject;
import com.prendus.prendus.utilities.Utilities;

/**
 * Created by matt on 3/26/17.
 */

public class Question extends PrendusObject {
    private String code;
    private String text;
    private String uid;
    private String visibility;
    private String discipline;
    private String concept;
    private String subject;
    private String previewQuestionId;

    public Question() {
    }

    public Question(LinkedTreeMap map) {
        code = (String) map.get("code");
        text = (String) map.get("text");
        uid = (String) map.get("uid");
        visibility = (String) map.get("visibility");
        discipline = (String) map.get("discipline");
        concept = (String) map.get("concept");
        subject = (String) map.get("subject");
        previewQuestionId = (String) map.get("previewQuestionId");
        cleanedAnswer = true;
        strippedHtml = true;
    }

    public Question(String code, String text, String uid, String visibility, String discipline, String concept, String subject, String previewQuestionId) {
        this.code = code;
        this.text = text;
        this.uid = uid;
        this.visibility = visibility;
        this.discipline = discipline;
        this.concept = concept;
        this.subject = subject;
        this.previewQuestionId = previewQuestionId;
    }

    public Question(long timestamp, String code, String text, String uid, String visibility, String discipline, String concept, String subject, String previewQuestionId) {
        super(timestamp);
        this.code = code;
        this.text = text;
        this.uid = uid;
        this.visibility = visibility;
        this.discipline = discipline;
        this.concept = concept;
        this.subject = subject;
        this.previewQuestionId = previewQuestionId;
    }

    public Question(long timestamp, String id, String code, String text, String uid, String visibility, String discipline, String concept, String subject, String previewQuestionId) {
        super(timestamp, id);
        this.code = code;
        this.text = text;
        this.uid = uid;
        this.visibility = visibility;
        this.discipline = discipline;
        this.concept = concept;
        this.subject = subject;
        this.previewQuestionId = previewQuestionId;
    }

    private boolean cleanedAnswer = false;

    public String getCode() {
        if (!cleanedAnswer) {
            setCode(Utilities.stripEverything(this.code));
            int index = "answer = '".length();
            this.code = this.code.substring(index);
            this.code = this.code.substring(0, this.code.indexOf('\''));
            cleanedAnswer = true;
        }
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    private boolean strippedHtml = false;

    public String getText() {
        if (!strippedHtml) {
            setText(Utilities.stripHtml(this.text));
            strippedHtml = true;
        }

        return text;
    }

    /**
     * Calls getText()
     *
     * @return
     */
    public String getQuestion() {
        return getText();
    }

    /**
     * Calls getCode()
     *
     * @return
     */
    public String getAnswer() {
        return getCode();
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getVisibility() {
        return visibility;
    }

    public void setVisibility(String visibility) {
        this.visibility = visibility;
    }

    public String getDiscipline() {
        return discipline;
    }

    public void setDiscipline(String discipline) {
        this.discipline = discipline;
    }

    public String getConcept() {
        return concept;
    }

    public void setConcept(String concept) {
        this.concept = concept;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getPreviewQuestionId() {
        return previewQuestionId;
    }

    public void setPreviewQuestionId(String previewQuestionId) {
        this.previewQuestionId = previewQuestionId;
    }
}
