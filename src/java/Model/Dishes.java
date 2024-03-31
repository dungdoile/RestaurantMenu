/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

/**
 *
 * @author asus
 */
public class Dishes {
    private int dishId;
    private String dishName;
    private String description;
    private double price;
    private int dishTypeId;
    private String imageLink;

    public Dishes() {
    }

    public Dishes(int dishId, String dishName, String description, double price, int dishTypeId, String imageLink) {
        this.dishId = dishId;
        this.dishName = dishName;
        this.description = description;
        this.price = price;
        this.dishTypeId = dishTypeId;
        this.imageLink = imageLink;
    }

    public int getDishId() {
        return dishId;
    }

    public String getDishName() {
        return dishName;
    }

    public void setDishName(String DishName) {
        this.dishName = DishName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String Description) {
        this.description = Description;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double Price) {
        this.price = Price;
    }

    public int getDishTypeId() {
        return dishTypeId;
    }

    public void setDishTypeId(int DishType) {
        this.dishTypeId = DishType;
    }

    public String getImageLink() {
        return imageLink;
    }

    public void setImageLink(String imageLink) {
        this.imageLink = imageLink;
    }

}
