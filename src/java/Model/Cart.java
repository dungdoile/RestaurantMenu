/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

import DAL.DishesDAO;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 *
 * @author Admin
 */
public class Cart {
    //cart string format: dish1Id:quantity/dish2Id:quantity/...

    public static String cartToString(Map<Integer, Integer> cart) {
        StringBuilder cookieTextCart = new StringBuilder();
        if(!cart.isEmpty()) {
            for (Map.Entry<Integer, Integer> item : cart.entrySet()) {
                cookieTextCart.append(item.getKey()).append(":").append(item.getValue()).append("/");
            }
            return cookieTextCart.toString();
        }
        return "";
    }
    
    public static Map<Integer, Integer> getAllItemsFromCart(String cookieTextCart, DishesDAO dDao) throws NumberFormatException {
        Map<Integer, Integer> cart = new LinkedHashMap<>();
        int dishId, quantity;
        if(cookieTextCart!=null && cookieTextCart.length()!=0) {
            String[] items = cookieTextCart.split("/");
            for(String item : items) {
                String[] n = item.split(":");
                dishId = Integer.parseInt(n[0]);
                quantity = Integer.parseInt(n[1]);
                cart.put(dishId, quantity);
            }
        }
        return cart;
    }
}
