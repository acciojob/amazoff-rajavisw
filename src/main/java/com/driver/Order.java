package com.driver;

public class Order {

    private String id;
    private int deliveryTime;
    private String stringTime;
    private boolean unassigned;

    public Order(String id, String deliveryTime) {

        // The deliveryTime has to converted from string to int and then stored in the attribute
        //deliveryTime  = HH*60 + MM  ok
        this.id=id;
        int dTime=Integer.parseInt(deliveryTime.substring(0,2))*60+Integer.parseInt(deliveryTime.substring(3,5));
        this.deliveryTime=dTime;
        this.unassigned=false;
        this.stringTime=deliveryTime;
    }

    public String getStringTime() {
        return stringTime;
    }

    public Order() {
    }

    public boolean isUnassigned() {
        return unassigned;
    }

    public void setUnassigned(boolean unassigned) {
        this.unassigned = unassigned;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getDeliveryTime() {
        return deliveryTime;
    }

}


