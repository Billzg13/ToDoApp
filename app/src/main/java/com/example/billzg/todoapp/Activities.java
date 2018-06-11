package com.example.billzg.todoapp;

import java.sql.Time;

public class Activities {
    private int _id;
    private int _uid;
    private String _name;


    public Activities(int _uid, String _name) {
        this._uid = _uid;
        this._name = _name;
    }

    public Activities() {
    }

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public int get_uid() {
        return _uid;
    }

    public void set_uid(int _uid) {
        this._uid = _uid;
    }

    public String get_name() {
        return _name;
    }

    public void set_name(String _name) {
        this._name = _name;
    }


}
