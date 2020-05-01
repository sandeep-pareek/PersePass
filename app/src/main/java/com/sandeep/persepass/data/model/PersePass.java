package com.sandeep.persepass.data.model;

import androidx.annotation.NonNull;

/**
 * @author sandeep
 */
public class PersePass {

    public PersePass(String key, String pass){
        this.key = key;
        this.pass = pass;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    private String key;

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    private String pass;

    @NonNull
    @Override
    public String toString() {
        return "{" + key + "=" + pass + "}";
    }
}
