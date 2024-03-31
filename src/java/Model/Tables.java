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
public class Tables {
    private int tableId;
    private int tableCapacity;
    private String customerPhoneNumber;
    private Date dateTimeUsed;
    private int invoiceRequest;

    public Tables() {
    }

    public Tables(int TableID, int tableType) {
        this.tableId = TableID;
        this.tableCapacity = tableType;
    }

    public Tables(int tableId, int tableCapacity, String customerPhoneNumber, Date dateTimeUsed, int invoiceRequest) {
        this.tableId = tableId;
        this.tableCapacity = tableCapacity;
        this.customerPhoneNumber = customerPhoneNumber;
        this.dateTimeUsed = dateTimeUsed;
        this.invoiceRequest = invoiceRequest;
    }

    public int getTableId() {
        return tableId;
    }
    
    public int getTableCapacity() {
        return tableCapacity;
    }

    public void setTableCapacity(int tableType) {
        this.tableCapacity = tableType;
    }

    public String getCustomerPhoneNumber() {
        return customerPhoneNumber;
    }

    public void setCustomerPhoneNumber(String customerPhoneNumber) {
        this.customerPhoneNumber = customerPhoneNumber;
    }

    public Date getDateTimeUsed() {
        return dateTimeUsed;
    }

    public void setDateTimeUsed(Date dateTimeUsed) {
        this.dateTimeUsed = dateTimeUsed;
    }

    public int getInvoiceRequest() {
        return invoiceRequest;
    }

    public void setInvoiceRequest(int invoiceRequest) {
        this.invoiceRequest = invoiceRequest;
    }
    
}
