/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

import java.util.Date;

/**
 *
 * @author asus
 */
public class DineInOrders {
    private int tableId;
    private int dishId;
    private int quantity;
    private Date dateTimeOrdered;
    private String serviceStatus; //Pending, Cooking, Serving
    private int rush;

    public DineInOrders() {
    }

    public DineInOrders(int tableId, int dishId, int quantity, Date dateTimeOrdered) {
        this.tableId = tableId;
        this.dishId = dishId;
        this.quantity = quantity;
        this.dateTimeOrdered = dateTimeOrdered;
        this.serviceStatus = "Pending";
    }

    public DineInOrders(int tableId, int dishId, int quantity, Date dateTimeOrdered, String serviceStatus, int rush) {
        this.tableId = tableId;
        this.dishId = dishId;
        this.quantity = quantity;
        this.dateTimeOrdered = dateTimeOrdered;
        this.serviceStatus = serviceStatus;
        this.rush = rush;
    }

    public int getTableId() {
        return tableId;
    }

    public int getDishId() {
        return dishId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Date getDateTimeOrdered() {
        return dateTimeOrdered;
    }

    public String getServiceStatus() {
        return serviceStatus;
    }

    public void setServiceStatus(String serviceStatus) {
        this.serviceStatus = serviceStatus;
    }

    public int getRush() {
        return rush;
    }

    public void setRush(int rush) {
        this.rush = rush;
    }

}
