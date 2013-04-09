/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.michael.webScraper;

import Employees.Workers;
import com.michael.webScraper.VO.AmazonBookVO;
import com.michael.webScraper.VO.SellerVO;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author nikhil
 */
public class BookDTO {
    
    
    @Override
    public String toString() {
        return "AmazonBookVO{" + "author=" + author + ", bookName=" + bookName + ", sellers=" + sellers + '}';
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public void setSellers(List<SellerVO> sellers) {
        this.sellers = sellers;
    }

    public String getAuthor() {
        return author;
    }

    public String getBookName() {
        return bookName;
    }

    public List<SellerVO> getSellers() {
        return sellers;
    }
    
    String author;
    String bookName;
    List<SellerVO> sellers = new ArrayList<SellerVO>();
    
    public static void save(AmazonBookVO vo){
        
        // Code for insertion of records in the table
        List<SellerVO> sellers = vo.getSellers();  
        
        //Connection con = new Workers().doConnect(databaseName, userName, password);
              
    }
    
}
