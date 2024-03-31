/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAL;

/**
 *
 * @author Admin
 */
import Model.Dishes;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class DishesDAO extends DBContext {

    private static final String INSERT_NEW_DISH = "INSERT INTO [Dishes] (DishName, Description, Price, DishTypeId, ImageLink) VALUES (?, ?, ?, ?, ?)";
    private static final String GET_ALL_DISHES = "SELECT DishID, DishName, Description, Price, DishTypeId, ImageLink FROM [Dishes]";
    private static final String GET_ALL_DISH_NAMES = "SELECT DishId, DishName FROM [Dishes]";
    private static final String GET_ALL_DISH_PRICES = "SELECT DishID, Price FROM [Dishes]";
    private static final String GET_DISHES_BY_DISH_TYPE_ID = "SELECT DishID, DishName, Description, Price, DishTypeId, ImageLink FROM [Dishes] WHERE DishTypeId=?"; 
    private static final String GET_DISH_BY_ID = "SELECT DishID, DishName, Description, Price, DishTypeId, ImageLink FROM [Dishes] WHERE DishID=?";
    private static final String UPDATE_DISH = "UPDATE [Dishes] SET DishName = ?, Description = ?, Price = ?, DishTypeId = ?, ImageLink = ? WHERE DishID = ?";
    private static final String DELETE_DISH = "DELETE FROM [Dishes] WHERE DishID = ?";

    public List<Dishes> getAllDishes() throws SQLException {
        List<Dishes> list = new ArrayList<>();
        PreparedStatement st = null;
        ResultSet rs = null;

        try {
            st = connection.prepareStatement(GET_ALL_DISHES);
            rs = st.executeQuery();

            while (rs.next()) {
                int dishId = rs.getInt("DishID");
                Dishes dish = new Dishes(dishId,
                        rs.getString("DishName"),
                        rs.getString("Description"),
                        rs.getDouble("Price"),
                        rs.getInt("DishTypeId"),
                        rs.getString("ImageLink"));
                list.add(dish);
            }
        } catch (SQLException e) {
            System.out.println(e);
        } finally {
            closeResources(st, rs);
        }

        return list;
    }
    
    public Map<Integer,String> getAllDishNames() throws SQLException {
        Map<Integer,String> list = new LinkedHashMap<>();
        PreparedStatement st = null;
        ResultSet rs = null;

        try {
            st = connection.prepareStatement(GET_ALL_DISH_NAMES);
            rs = st.executeQuery();

            while (rs.next()) {
                int dishId = rs.getInt("DishID");
                list.put(dishId, rs.getString("DishName"));
            }
        } catch (SQLException e) {
            System.out.println(e);
        } finally {
            closeResources(st, rs);
        }

        return list;
    }
    
    public Map<Integer, Double> getAllDishPrices() throws SQLException {
        Map<Integer, Double> list = new LinkedHashMap<>();
        PreparedStatement st = null;
        ResultSet rs = null;

        try {
            st = connection.prepareStatement(GET_ALL_DISH_PRICES);
            rs = st.executeQuery();

            while (rs.next()) {
                int dishId = rs.getInt("DishID");
                list.put(dishId, rs.getDouble("Price"));
            }
        } catch (SQLException e) {
            System.out.println(e);
        } finally {
            closeResources(st, rs);
        }

        return list;
    }
    
    public List<Dishes> getDishesByDishTypeId(int dishTypeId) throws SQLException {
        List<Dishes> list = new ArrayList<>();
        PreparedStatement st = null;
        ResultSet rs = null;

        try {
            st = connection.prepareStatement(GET_DISHES_BY_DISH_TYPE_ID);
            st.setInt(1, dishTypeId);
            rs = st.executeQuery();

            while (rs.next()) {
                int dishId = rs.getInt("DishID");
                Dishes dish = new Dishes(dishId,
                        rs.getString("DishName"),
                        rs.getString("Description"),
                        rs.getDouble("Price"),
                        rs.getInt("DishTypeId"),
                        rs.getString("ImageLink"));
                list.add(dish);
            }
        } catch (SQLException e) {
            System.out.println(e);
        } finally {
            closeResources(st, rs);
        }

        return list;
    }

    public Dishes getDishById(int id) throws SQLException {
        PreparedStatement st = null;
        ResultSet rs = null;

        try {
            st = connection.prepareStatement(GET_DISH_BY_ID);
            st.setInt(1, id);
            rs = st.executeQuery();

            if (rs != null && rs.next()) {
                return new Dishes(rs.getInt("DishID"),
                        rs.getString("DishName"),
                        rs.getString("Description"),
                        rs.getDouble("Price"),
                        rs.getInt("DishTypeId"),
                        rs.getString("ImageLink"));
            }
        } catch (SQLException e) {
            System.out.println(e);
        } finally {
            closeResources(st, rs);
        }

        return null;
    }

    public boolean insertNewDish(String dishName, String description, double price, int dishTypeId, String imageLink) throws SQLException {
        return insertUpdateDelete(INSERT_NEW_DISH, dishName, description, price, dishTypeId, imageLink);
    }

    public boolean updateDish(String dishName, String description, double price, int dishTypeId, int dishId, String imageLink) throws SQLException {
        return insertUpdateDelete(UPDATE_DISH, dishName, description, price, dishTypeId, imageLink, dishId);
    }

    public boolean deleteDish(int dishId) throws SQLException {
        return insertUpdateDelete(DELETE_DISH, dishId);
    }
    
    public static void main(String[] args) {
        DishesDAO dDao = new DishesDAO();
        try {
            System.out.println(dDao.deleteDish(2));
        } catch(SQLException e) {
            System.out.println(e);
        }
    }
}

