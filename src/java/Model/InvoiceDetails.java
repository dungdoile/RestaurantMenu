/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

/**
 *
 * @author asus
 */
public class InvoiceDetails {
    private int invoiceDetailId;
    private int invoiceId;
    private int dishId;
    private int quantity;

    public InvoiceDetails() {
    }

    public InvoiceDetails(int InvoiceDetailID, int invoiceId, int dishId, int quantity) {
        this.invoiceDetailId = InvoiceDetailID;
        this.invoiceId = invoiceId;
        this.dishId = dishId;
        this.quantity = quantity;
    }

    public int getInvoiceDetailID() {
        return invoiceDetailId;
    }

    public int getInvoiceId() {
        return invoiceId;
    }

    public int getDishId() {
        return dishId;
    }

    public int getQuantity() {
        return quantity;
    }

}
