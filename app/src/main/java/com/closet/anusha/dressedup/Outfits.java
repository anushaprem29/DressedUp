package com.closet.anusha.dressedup;


public class Outfits {

    int id;
    int accId1;
    int accId2;
    int accId3;
    int accId4;
    int clothId1;
    int clothId2;
    int shoeId1;
    int shoeId2;
    String date;
    String repeat;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getAccId1() {
        return accId1;
    }

    public void setAccId1(int accId1) {
        this.accId1 = accId1;
    }

    public int getAccId2() {
        return accId2;
    }

    public void setAccId2(int accId2) {
        this.accId2 = accId2;
    }

    public int getAccId3() {
        return accId3;
    }

    public void setAccId3(int accId3) {
        this.accId3 = accId3;
    }

    public int getAccId4() {
        return accId4;
    }

    public void setAccId4(int accId4) {
        this.accId4 = accId4;
    }

    public int getClothId1() {
        return clothId1;
    }

    public void setClothId1(int clothId1) {
        this.clothId1 = clothId1;
    }

    public int getClothId2() {
        return clothId2;
    }

    public void setClothId2(int clothId2) {
        this.clothId2 = clothId2;
    }

    public int getShoeId1() {
        return shoeId1;
    }

    public void setShoeId1(int shoeId1) {
        this.shoeId1 = shoeId1;
    }

    public int getShoeId2() {
        return shoeId2;
    }

    public void setShoeId2(int shoeId2) {
        this.shoeId2 = shoeId2;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
    public String getRepeat() {
        return repeat;
    }

    public void setRepeat(String repeat) {
        this.repeat = repeat;
    }

    public Outfits(int id, int accId1, int accId2, int accId3, int accId4, int clothId1, int clothId2, int shoeId1, int shoeId2, String date,String repeat) {
        this.id = id;
        this.accId1 = accId1;
        this.accId2 = accId2;
        this.accId3 = accId3;
        this.accId4 = accId4;
        this.clothId1 = clothId1;
        this.clothId2 = clothId2;
        this.shoeId1 = shoeId1;
        this.shoeId2 = shoeId2;
        this.date = date;
        this.repeat = repeat;
    }

    public Outfits(){}

}
