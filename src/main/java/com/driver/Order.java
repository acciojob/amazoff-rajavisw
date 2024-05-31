package com.driver;

public class Order {

    private String id;
    private int deliveryTime;
    private boolean unassigned;

    public Order(String id, String deliveryTime) {

        // The deliveryTime has to converted from string to int and then stored in the attribute
        //deliveryTime  = HH*60 + MM  ok
        this.id = id;
        String[] time = deliveryTime.split(":"); // split the time string into hours and minutes
        this.deliveryTime = Integer.parseInt(time[0]) * 60 + Integer.parseInt(time[1]); // convert to minutes
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


    public Integer getDeliveryTime() {
        return deliveryTime;
    }

}


