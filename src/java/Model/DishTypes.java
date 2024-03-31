/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

/**
 *
 * @author Admin
 */
public class DishTypes {
    private int dishTypeId;
    private String dishTypeName;

    public DishTypes() {
    }
    
    public DishTypes(int dishTypeId, String dishTypeName) {
        this.dishTypeId = dishTypeId;
        this.dishTypeName = dishTypeName;
    }

    public int getDishTypeId() {
        return dishTypeId;
    }

    public String getDishTypeName() {
        return dishTypeName;
    }

    public void setDishTypeName(String dishTypeName) {
        this.dishTypeName = dishTypeName;
    }
    
}
