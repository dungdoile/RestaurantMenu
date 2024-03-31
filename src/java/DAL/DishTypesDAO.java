/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAL;

import Model.DishTypes;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Admin
 */
public class DishTypesDAO extends DBContext {
    private static final String INSERT_NEW_DISHTYPE = "INSERT INTO [DishTypes](dishTypeName) VALUES (?)";
    private static final String GET_ALL_DISHTYPE = "SELECT dishTypeId, dishTypeName FROM [DishTypes]";
    private static final String GET_DISHTYPE_BY_ID = "SELECT dishTypeId, dishTypeName FROM [DishTypes] WHERE dishTypeId=?";
    private static final String UPDATE_DISHTYPE = "UPDATE [DishTypes] SET dishTypeName=? WHERE dishTypeId=?";
    
    public List<DishTypes> getAllDishTypes() throws SQLException {
        List<DishTypes> list = new ArrayList<>();
        PreparedStatement st = null;
        ResultSet rs = null;
        try {
            st = connection.prepareStatement(GET_ALL_DISHTYPE);
            rs = st.executeQuery();
            while (rs.next()) {
                list.add(new DishTypes(rs.getInt("DishTypeId"),
                        rs.getString("dishTypeName")));
            }
        } catch(SQLException e) {
            System.out.println(e);
        } finally {
            closeResources(st, rs);
        }
        return list;
    }
    
    public DishTypes getDishTypeById() throws SQLException {
        PreparedStatement st = null;
        ResultSet rs = null;
        try {
            st = connection.prepareStatement(GET_DISHTYPE_BY_ID);
            rs = st.executeQuery();
            if (rs!=null && rs.next()) {
                return new DishTypes(rs.getInt("DishTypeId"), rs.getString("dishTypeName"));
            }
        } catch(SQLException e) {
            System.out.println(e);
        } finally {
            closeResources(st, rs);
        }
        return null;
    }
    
    public boolean insertNewDishType(String dishTypeName) throws SQLException {
        return insertUpdateDelete(INSERT_NEW_DISHTYPE, dishTypeName);
    }
    
    public boolean updateDishType(String dishTypeName, int dishTypeId) throws SQLException {
        return insertUpdateDelete(UPDATE_DISHTYPE, dishTypeName, dishTypeId);
    }
}
