/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Utils;

import Model.DineInOrders;
import Model.Invoices;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Admin
 */
public class Calculator {
    public static double calculateTotalPrice(Map<Integer, Integer> cart, Map<Integer, Double> listOfDishPrices) {
        return cart.keySet().stream().mapToDouble(item -> listOfDishPrices.get(item) * cart.get(item)).sum();
    }
    
    public static double calculateTotalPrice(List<DineInOrders> listOfDineInOrders, Map<Integer, Double> listOfDishPrices) {
        return listOfDineInOrders.stream().mapToDouble(order -> listOfDishPrices.get(order.getDishId()) * order.getQuantity()).sum();
    }
    
    public static double calculateTotalRevenue(List<Invoices> listOfInvoices) {
        return listOfInvoices.stream().mapToDouble(invoice -> invoice.getNetPrice()).sum();
    }
}
