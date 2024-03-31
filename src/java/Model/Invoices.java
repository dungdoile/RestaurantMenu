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
public class Invoices {
    private int invoiceId;
    private String customerPhoneNumber;
    private Date dateTimeCreated;
    private double grossPrice;
    private double vat;
    private double discount;
    private double netPrice;
    private String paymentMethod;

    public Invoices() {
    }

    public Invoices(int invoiceId, String customerPhoneNumber, Date dateTimeCreated, double grossPrice, double vat, double discount, double netPrice, String paymentMethod) {
        this.invoiceId = invoiceId;
        this.customerPhoneNumber = customerPhoneNumber;
        this.dateTimeCreated = dateTimeCreated;
        this.grossPrice = grossPrice;
        this.vat = vat;
        this.discount = discount;
        this.netPrice = netPrice;
        this.paymentMethod = paymentMethod;
    }

    public int getInvoiceId() {
        return invoiceId;
    }

    public String getCustomerPhoneNumber() {
        return customerPhoneNumber;
    }

    public Date getDateTimeCreated() {
        return dateTimeCreated;
    }

    public double getGrossPrice() {
        return grossPrice;
    }

    public double getVat() {
        return vat;
    }

    public double getDiscount() {
        return discount;
    }

    public double getNetPrice() {
        return netPrice;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

}

