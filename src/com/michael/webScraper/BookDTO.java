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
    
    String author;
    String bookName;
    List<SellerDTO> sellers = new ArrayList<SellerDTO>();
    
    @Override
    public String toString() {
        return "AmazonBook{" + "author=" + author + ", bookName=" + bookName + ", sellers=" + sellers + '}';
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public void setSellers(List<SellerDTO> sellers) {
        this.sellers = sellers;
    }

    public String getAuthor() {
        return author;
    }

    public String getBookName() {
        return bookName;
    }

    public List<SellerDTO> getSellers() {
        return sellers;
    }
           
    public static void save(AmazonBookVO vo){
        
        // Code for insertion of records in the table
        List<SellerVO> sellers = vo.getSellers();  
        
        //Connection con = new Workers().doConnect(databaseName, userName, password);
              
    }
    
}
