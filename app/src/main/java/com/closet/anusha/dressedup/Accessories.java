package com.closet.anusha.dressedup;

public class Accessories {
    int _id;
    int _type;
    double _rating;
    String _lastWorn;
    int _noOfTimes;
    int _color;
    String _comment;
    int _available;
    int _topBot;
    byte [] _photograph;

    public Accessories(int _type, int _rating, String _lastWorn, int _noOfTimes, int _color, String _comment, int _available, int _topBot, byte[] _photograph) {
        this._type = _type;
        this._rating = _rating;
        this._lastWorn = _lastWorn;
        this._noOfTimes = _noOfTimes;
        this._color = _color;
        this._comment = _comment;
        this._available = _available;
        this._topBot = _topBot;
        this._photograph = _photograph;
    }
    public Accessories(){}
    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public int get_type() {
        return _type;
    }

    public void set_type(int _type) {
        this._type = _type;
    }

    public double get_rating() {
        return _rating;
    }

    public void set_rating(double _rating) {
        this._rating = _rating;
    }

    public String get_lastWorn() {
        return _lastWorn;
    }

    public void set_lastWorn(String _lastWorn) {
        this._lastWorn = _lastWorn;
    }

    public int get_noOfTimes() {
        return _noOfTimes;
    }

    public void set_noOfTimes(int _noOfTimes) {
        this._noOfTimes = _noOfTimes;
    }

    public int get_color() {
        return _color;
    }

    public void set_color(int _color) {
        this._color = _color;
    }

    public String get_comment() {
        return _comment;
    }

    public void set_comment(String _comment) {
        this._comment = _comment;
    }

    public int get_available() {
        return _available;
    }

    public void set_available(int _available) {
        this._available = _available;
    }

    public int get_topBot() {
        return  _topBot;
    }

    public void set_topBot(int _topBot) {
        this._topBot = _topBot;
    }

    public byte[] get_photograph() {
        return _photograph;
    }

    public void set_photograph(byte[] _photograph) {
        this._photograph = _photograph;
    }

}