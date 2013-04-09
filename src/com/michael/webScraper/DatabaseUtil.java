/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.michael.webScraper;

import com.michael.webScraper.VO.AmazonBookVO;
import com.michael.webScraper.VO.SellerVO;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Shweta
 */
public class DatabaseUtil {
    public static Connection conn= null;
    public static Statement stmt=null;
    public static String query="";
    
    public static void establishDatabaseConnection(){
        System.out.println("Checking the connection to the DB..");
        try {
            String url = "jdbc:mysql://localhost:3306/";
            String dbName = "zipcode";
            String driver = "com.mysql.jdbc.Driver";
            String userName = "root";
            String password = "root123";

            Class.forName(driver).newInstance();
            conn = DriverManager.getConnection(url + dbName, userName, password);
            conn.setAutoCommit(false);
            
            stmt = conn.createStatement();
            System.out.println("Connected to the database...");
        } catch (Exception e) {
            System.out.println("An error occurred while trying to connect to the DB");
            e.printStackTrace();
        }
    }
    
    public static void initialize(){
        try {
            query = "create table book "
                    + "(id number NOT NULL,"
                    + "book_name varchar2(100),"
                    + "author varchar2(100)"
                    + ");";
            stmt.execute(query);
        } catch (SQLException ex) {
            System.out.println("Exception at creating book table : "+ ex);
        }
        
        try {
            query = "create table seller "
                    + "(id number NOT NULL,"
                    + "book_id number NOT NULL,"
                    + "name varchar2(100),"
                    + "condition varchar2(100),"
                    + "price number(8,2),"
                    + "rating number(8,2),"
                    + ");";
            stmt.execute(query);
        } catch (SQLException ex) {
            System.out.println("Exception at creating seller table : "+ ex);
        }        
    }
    
    public static boolean create(AmazonBookVO book){
        boolean done = false;
        Integer book_id;
        Integer seller_id;
        try {
            query = "select max(id)+1 from book";
            book_id = getId(query, 1); 
            
            query = "insert into book values "
                + "(id, book_name, author) "
                + "("+book_id+", "
                    +book.getBookName()+", "
                    +book.getAuthor()+");";
            stmt.execute(query);
            System.out.println("Inserted row into book : "+book_id);
            
            for(SellerVO seller : book.getSellers()){
                query = "select max(id)+1 from seller";
                 seller_id = getId(query, 1); 

                 query = "insert into seller values "
                     + "(id, book_id, name, condition, rating, price) "
                     + "("+seller_id
                         +", "+book_id
                         +", "+seller.getName()
                         +", "+seller.getCondition()
                         +", "+seller.getRating()
                         +", "+seller.getPrice()
                         +");";
                 stmt.execute(query);
                 System.out.println("Inserted row into seller : "+seller_id);
           }
           done = true; 
        } catch (SQLException ex) {
            System.out.println("Exception at inserting into tables : "+ ex);
        }                 
        return done;
    }
    
    public static int getId(String query, int defaultValue) {
        Integer id = defaultValue;
        try {
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                id = rs.getInt(1);
            }
        } catch (Exception e) {
            System.out.println("Exception at fetching id : " + e);
        }

        return id;
    }
    
}
