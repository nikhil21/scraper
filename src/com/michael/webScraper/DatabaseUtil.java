/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.michael.webScraper;

import com.michael.webScraper.VO.AmazonBookVO;
import com.michael.webScraper.VO.SellerVO;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.lang3.StringEscapeUtils;

/**
 *
 * @author Shweta
 */
public class DatabaseUtil {
    public static Connection conn= null;
    public static Statement stmt=null;
    public static String query="";
    private static PreparedStatement preparedStatement = null;
    
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
        
        establishDatabaseConnection();
        try {
            query = "create table book "
                    + "(id INT NOT NULL,"
                    + "book_name varchar(500),"
                    + "author varchar(500)"
                    + ");";
            stmt.execute(query);
        } catch (SQLException ex) {
            System.out.println("Exception at creating book table : "+ ex);
        }
        
        try {
            query = "create table seller "
                    + "(id INT NOT NULL,"
                    + "book_id INT NOT NULL,"
                    + "name varchar(100),"
                    + "book_condition varchar(100),"
                    + "price DECIMAL(8,2),"
                    + "rating DECIMAL(8,2)"
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
            System.out.println("Book ID is ***** "+book_id);
            System.out.println("Book name is ***** "+book.getBookName());
            System.out.println("Escaped name is ***** "+StringEscapeUtils.escapeEcmaScript(book.getBookName()));
//            query = "insert into book values "
//                + "(id, book_name, author) "
//                + "("+book_id+", "
//                    +"'"+StringEscapeUtils.escapeEcmaScript(book.getBookName())+"'"+", "
//                    +"'"+StringEscapeUtils.escapeEcmaScript(book.getAuthor())+"'"+");";
//            stmt.execute(query);
            
            // PreparedStatements can use variables and are more efficient
        preparedStatement = conn
          .prepareStatement("insert into  book values (?, ?, ?)");
      // "myuser, webpage, datum, summery, COMMENTS from FEEDBACK.COMMENTS");
      // Parameters start with 1
        preparedStatement.setInt(1, book_id);
        preparedStatement.setString(2, StringEscapeUtils.escapeEcmaScript(book.getBookName()));
        preparedStatement.setString(3, StringEscapeUtils.escapeEcmaScript(book.getAuthor()));
        
        preparedStatement.executeUpdate();
      
      
            System.out.println("Inserted row into book : "+book_id);
            
            for(SellerVO seller : book.getSellers()){
                query = "select max(id)+1 from seller";
                 seller_id = getId(query, 1); 

                 query = "insert into seller values "
                     + "(id, book_id, name, book_condition, rating, price) "
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
           conn.commit();
        } catch (SQLException ex) {
            System.out.println("Exception at inserting into tables : "+ ex);
            try {
                conn.rollback();
            } catch (SQLException ex1) {
                Logger.getLogger(DatabaseUtil.class.getName()).log(Level.SEVERE, null, ex1);
                System.out.println("Caught Exception.. "+ex1);
            }
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
