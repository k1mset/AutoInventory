package javaeetutorial.bookmarks;

import javax.enterprise.inject.Model;
import java.sql.*;
import java.util.*;

@Model
public class Hello {

    static final String DB_URL = "jdbc:derby://localhost:1527/CarDB";
    static final String DB_USER = "jsmith";
    static final String DB_PASS = "password";
    
    private String vehicleMake;
    private String vehicleModel;
    private String vehicleYear;
    private String vehicleValue;
    private String vehicleCondition;
    
    private String[] viewSelection;
    private ArrayList<String> viewHandle;
        
    Connection conn;
    Statement stmt;
    
    public Hello() {
        conn = null;
        stmt = null;
        vehicleMake = "";
        vehicleModel = "";
        vehicleYear = "";
        vehicleValue = "";
        vehicleCondition = null;
        viewHandle = new ArrayList<String>();
    }
    
    public String getVehicleMake() {
        return vehicleMake;
    }
    
    public String getVehicleModel() {
        return vehicleModel;
    }
    
    public String getVehicleYear() {
        return vehicleYear;
    }
    
    public String getVehicleValue() {
        return vehicleValue;
    }
    
    public String getVehicleCondition() {
        return vehicleCondition;
    }
    
    public String[] getViewSelection() {
        return viewSelection;
    }
    
    public ArrayList<String> getViewHandle() {
        return viewHandle;
    }
    
    public void setVehicleMake(String vehicleMake) {
        this.vehicleMake = vehicleMake;
    }
    
    public void setVehicleModel(String vehicleModel) {
        this.vehicleModel = vehicleModel;
    }
    
    public void setVehicleYear(String vehicleYear) {
        this.vehicleYear = vehicleYear;
    }
    
    public void setVehicleValue(String vehicleValue) {
        this.vehicleValue = vehicleValue;
    }
    
    public void setVehicleCondition(String vehicleCondition) {
        this.vehicleCondition = vehicleCondition;
    }
    
    public void setViewSelection(String[] viewSelection) {
        this.viewSelection = viewSelection;
    }
    
    public void setViewHandle(ArrayList<String> viewHandle) {
        this.viewHandle = viewHandle;
    }
    
    public void insertInformation() {
        try {
            System.out.println("Connecting to the database...");
            conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
            System.out.println("Connected to the database...");
            
            System.out.println("Creating statement...");
            stmt = conn.createStatement();
            
            String sql = "INSERT INTO INVENTORY" +
                         " VALUES ('" + vehicleMake + "', '" + vehicleModel + "', '" + vehicleYear + "', '" + vehicleValue + "', '" + vehicleCondition + "')";
            System.out.println("Inserted: " + sql);
            stmt.executeUpdate(sql);
        } catch(SQLException se) {
            se.printStackTrace();
        } catch(Exception e) {
            e.printStackTrace();
        } finally {
            try{
                if(stmt!=null){
                    conn.close();
                    System.out.println("Closing Database...");
                }
            } catch(SQLException se) {
                
            }
            
            try {
                if (conn!=null) {
                    conn.close();
                    System.out.println("Closing Database...");
                }
            } catch(SQLException se) {
                se.printStackTrace();
            }
        }
    }
    
    public void viewInformation() {
        try {
            System.out.println("Connecting to the database...");
            conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
            System.out.println("Connected to the database...");
            
            System.out.println("Creating statement...");
            stmt = conn.createStatement();
            
            String sql;
            String whereSql = " WHERE";
            
            sql = "SELECT " + viewSelection[0];

            if(viewSelection.length > 1) {
                for(int i=1; i<viewSelection.length;i++) {
                    if (viewSelection[i].equals("Year")) {
                        sql += ", Caryear";
                    } else {
                        sql += ", " + viewSelection[i];
                    }
                }
            }

            sql += " FROM INVENTORY";
            
            System.out.println(sql);
            
            if (vehicleCondition == null) {
                vehicleCondition = "";
            }
            
            if (!vehicleMake.equals(""))
                whereSql += " make = '" + vehicleMake + "'";
            
            if (!vehicleModel.equals("") && !whereSql.equals(" WHERE")) {
                whereSql += " AND";
                whereSql += " model = '" + vehicleModel + "'";
            } else if (!vehicleModel.equals(""))
                whereSql += " model = '" + vehicleModel + "'";
            
            if (!vehicleYear.equals("") && !whereSql.equals(" WHERE")) {
                whereSql += " AND";
                whereSql += " caryear = '" + vehicleYear + "'";
            } else if (!vehicleYear.equals(""))
                whereSql += " caryear = '" + vehicleYear + "'";
            
            if (!vehicleValue.equals("") && !whereSql.equals(" WHERE")) {
                whereSql += " AND";
                whereSql += " value = '" + vehicleValue + "'";
            } else if (!vehicleValue.equals(""))
                whereSql += " value = '" + vehicleValue + "'";
            
            System.out.println(whereSql);
            System.out.println(vehicleCondition);
            if ((!vehicleCondition.equals("") && !whereSql.equals(" WHERE"))) {
                whereSql += " AND";
                whereSql += " condition = '" + vehicleCondition + "'";
            } else if (!vehicleCondition.equals(""))
                whereSql += " condition = '" + vehicleCondition + "'";
                        
            System.out.println(whereSql);
            
            if(!whereSql.equals(" WHERE")) {
                sql += whereSql;
            }
            
            System.out.println(sql);
            
            ResultSet rs = stmt.executeQuery(sql);
            
            System.out.println(rs);
                     
            String str = null;
            
            while(rs.next()) {
                str = "";
                for(int i=0; i<viewSelection.length;i++) {
                    if (viewSelection[i].equals("Year")) {
                        str += "(caryear) ";
                    } else if (viewSelection[i].equals("Value")) {
                        str += "$" + (rs.getString(viewSelection[i]) + ".00 ");
                    } else {
                        str += (rs.getString(viewSelection[i]) + " ");
                    }
                }
                
                viewHandle.add(str);
            }
            
            System.out.println(str);
        } catch(SQLException se) {
            se.printStackTrace();
        } catch(Exception e) {
            e.printStackTrace();
        } finally {
            try{
                if(stmt!=null){
                    conn.close();
                    System.out.println("Closing Database...");
                }
            } catch(SQLException se) {
                
            }
            
            try {
                if (conn!=null) {
                    conn.close();
                    System.out.println("Closing Database...");
                }
            } catch(SQLException se) {
                se.printStackTrace();
            }
        }
    }
}

