/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.michael.webScraper;

import com.michael.webScraper.VO.BookVO;
import com.michael.webScraper.VO.SellerVO;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.Date;
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
                    + "author varchar(500),"
                    + "source varchar(500),"
                    + "scraped_date timestamp"
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
                    + "price varchar(100),"
                    + "rating varchar(100)"
                    + ");";
            stmt.execute(query);
        } catch (SQLException ex) {
            System.out.println("Exception at creating seller table : "+ ex);
        }        
    }
    
    public static void done(){
        try {
            conn.commit();
        } catch (SQLException ex) {
            System.out.println("Exception at commiting : "+ ex);
        }
    }
    
    public static void finish(){
        try {
            stmt.close();
            conn.close();
        } catch (SQLException ex) {
            System.out.println("Exception at disconnecting : "+ ex);
        }
        
    }
    
    public static boolean create(BookVO book, Source source){
        boolean done = false;
        Integer book_id;
        Integer seller_id;
        try {
            query = "select max(id)+1 from book";
            book_id = getId(query, 1); 
            System.out.println("New Book ID : "+book_id);
                
            // PreparedStatements can use variables and are more efficient
            preparedStatement = conn
              .prepareStatement("insert into  book values (?, ?, ?, ?, ?)");
            preparedStatement.setInt(1, book_id);
            preparedStatement.setString(2, StringEscapeUtils.escapeEcmaScript(book.getBookName()));
            preparedStatement.setString(3, StringEscapeUtils.escapeEcmaScript(book.getAuthor()));
            preparedStatement.setString(4, StringEscapeUtils.escapeEcmaScript(source.getValue()));
            preparedStatement.setTimestamp(5, new Timestamp(new Date().getTime()));

            preparedStatement.executeUpdate();
            
            System.out.println("Inserted a row into book : "+book_id);
            
            for(SellerVO seller : book.getSellers()){
                 query = "select max(id)+1 from seller";
                 seller_id = getId(query, 1); 
                 System.out.println("New Seller ID : "+seller_id);
               
                  preparedStatement = conn
                    .prepareStatement("insert into  seller values (?, ?, ?, ?, ?, ?)");
                  preparedStatement.setInt(1, seller_id);
                  preparedStatement.setInt(2, book_id);
                  preparedStatement.setString(3, StringEscapeUtils.escapeEcmaScript(seller.getName()));
                  preparedStatement.setString(4, StringEscapeUtils.escapeEcmaScript(seller.getCondition()));
                  preparedStatement.setString(5, StringEscapeUtils.escapeEcmaScript(seller.getRating()));
                  preparedStatement.setString(6, StringEscapeUtils.escapeEcmaScript(seller.getPrice()));

                  preparedStatement.executeUpdate();
                 System.out.println("Inserted a row into seller : "+seller_id);
           }
           done = true; 
           
           //commit
           done();
        } catch (SQLException ex) {
            System.out.println("Exception at inserting into tables : "+ ex);
            try {
                conn.rollback();
            } catch (SQLException ex1) {
                System.out.println("Exception at rollback : "+ex1);
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

        return (id<1)?1:id;
    }
    
}
