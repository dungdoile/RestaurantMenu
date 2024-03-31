/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

/**
 *
 * @author asus
 */
public class Customers {
    private String customerPhoneNumber;
    private String customerName;
    
    public Customers() {
    }

    public Customers(String customerPhoneNumber, String customerName) {
        this.customerPhoneNumber = customerPhoneNumber;
        this.customerName = customerName;
    }
    
    public String getCustomerPhoneNumber() {
        return customerPhoneNumber;
    }

    public void setCustomerPhoneNumber(String CustomerPhoneNumber) {
        this.customerPhoneNumber = CustomerPhoneNumber;
    }

    public String getCustomerName() {
        return customerName;
    }

}
