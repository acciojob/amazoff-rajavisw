package com.driver;

import java.util.*;

import org.springframework.stereotype.Repository;

@Repository
public class OrderRepository {

    private HashMap<String, Order> orderMap;
    private HashMap<String, DeliveryPartner> partnerMap;
    private HashMap<String, HashSet<String>> partnerToOrderMap;
    private HashMap<String, String> orderToPartnerMap;

    public OrderRepository(){
        this.orderMap = new HashMap<String, Order>();
        this.partnerMap = new HashMap<String, DeliveryPartner>();
        this.partnerToOrderMap = new HashMap<String, HashSet<String>>();
        this.orderToPartnerMap = new HashMap<String, String>();
    }

    public void saveOrder(Order order){
        // your code here
        if(order!=null && order.getId()!=null){
            String id=order.getId();
            order.setUnassigned(false);
            orderMap.put(id,order);
        }

    }

    public void savePartner(String partnerId){
        // your code here
        // create a new partner with given partnerId and save it
        for(String key:partnerMap.keySet()){
            if(key.equals(partnerId)){
                throw new RuntimeException("Partner Id must be unique");
            }
        }
        DeliveryPartner deliveryPartner=new DeliveryPartner(partnerId);
        partnerMap.put(partnerId,deliveryPartner);
    }

    public void saveOrderPartnerMap(String orderId, String partnerId){
        if(orderMap.containsKey(orderId) && partnerMap.containsKey(partnerId)){
            // your code here
            //add order to given partner's order list
            //increase order count of partner
            //assign partner to this order
            Order order=orderMap.get(orderId);
            order.setUnassigned(true);
            orderMap.put(orderId,order);

            HashSet<String> partnerOrderSet=partnerToOrderMap.get(partnerId);
            if(partnerOrderSet==null){
                partnerOrderSet=new HashSet<>();
            }
            partnerOrderSet.add(orderId);

            DeliveryPartner deliveryPartner=partnerMap.get(partnerId);
            deliveryPartner.setNumberOfOrders(deliveryPartner.getNumberOfOrders()+1);

            orderToPartnerMap.put(orderId,partnerId);

            partnerToOrderMap.put(partnerId,partnerOrderSet);
        }
        else{
            throw new RuntimeException("Invalid order/partner id");
        }
    }

    public Order findOrderById(String orderId){
        // your code here
        return orderMap.get(orderId);
    }

    public DeliveryPartner findPartnerById(String partnerId){
        // your code here
        return partnerMap.get(partnerId);
    }

    public Integer findOrderCountByPartnerId(String partnerId){
        // your code here
        HashSet<String> setOfOrders=partnerToOrderMap.get(partnerId);
        if(setOfOrders==null) return null;
        return setOfOrders.size();
    }

    public List<String> findOrdersByPartnerId(String partnerId){
        // your code here
        HashSet<String> setOfOrders=partnerToOrderMap.get(partnerId);
        if(setOfOrders==null) return null;
        List<String> listOfOrders=new ArrayList<>(setOfOrders);
        return listOfOrders;
    }

    public List<String> findAllOrders(){
        // your code here
        // return list of all orders
        List<String> allorders=new ArrayList<>(orderMap.keySet());
        return allorders;
    }

    public void deletePartner(String partnerId){
        // your code here
        // delete partner by ID
        HashSet<String> assignedOrders=partnerToOrderMap.get(partnerId);
        if(assignedOrders!=null){
            for(String orderId:assignedOrders){
                Order order=orderMap.get(orderId);
                order.setUnassigned(false);
            }
            partnerToOrderMap.remove(partnerId);
        }
        partnerMap.remove(partnerId);

        orderToPartnerMap.keySet().removeIf(key -> (orderToPartnerMap.get(key).equals(partnerId)));

    }

    public void deleteOrder(String orderId){
        // your code here
        // delete order by ID

        if(orderMap.get(orderId)==null){
            throw new RuntimeException("OrderId is not found.");
        }
        orderMap.remove(orderId);

        String partnerId=orderToPartnerMap.get(orderId);
        orderToPartnerMap.remove(orderId);

        HashSet<String> orderList=partnerToOrderMap.get(partnerId);
        orderList.remove(orderId);
        partnerToOrderMap.put(partnerId,orderList);

    }

    public Integer findCountOfUnassignedOrders(){
        // your code here
        Integer countOfOrders=0;
        for(Order order:orderMap.values()){
            if(!order.isUnassigned()) countOfOrders++;
        }
        return countOfOrders;
    }

    public Integer findOrdersLeftAfterGivenTimeByPartnerId(String timeString, String partnerId){
        // your code here
        HashSet<String> allOrders=partnerToOrderMap.get(partnerId);
        if(allOrders==null){
            throw new RuntimeException("partnerId is invalid");
        }

        Integer leftOrders=0;
        for(String id:allOrders){
            Order order=orderMap.get(id);

            int timeInMins=order.getDeliveryTime();

            String[] currTime=timeString.split(":");
            int currTimeInMins=(Integer.parseInt(currTime[0])*60)+Integer.parseInt(currTime[1]);
            if(timeInMins>currTimeInMins){
                leftOrders++;
            }
        }
        return leftOrders;
    }

    public String findLastDeliveryTimeByPartnerId(String partnerId){
        // your code here
        // code should return string in format HH:MM

        String lastDeliveryTime="";
        if(partnerId!=null && partnerToOrderMap.get(partnerId)!=null){
            int maxTime=Integer.MIN_VALUE;
            HashSet<String> allOrders=partnerToOrderMap.get(partnerId);
            for(String id:allOrders){
                Order order=orderMap.get(id);

                int timeInMins=order.getDeliveryTime();

                if(timeInMins>maxTime){
                    maxTime=timeInMins;
                }
            }
            if((maxTime/60)<10) lastDeliveryTime+='0';
            lastDeliveryTime+=maxTime/60;
            lastDeliveryTime+=':';
            if((maxTime%60)<10) lastDeliveryTime+='0';
            lastDeliveryTime+=maxTime%60;
        }
        else return null;
        return lastDeliveryTime;
    }
}