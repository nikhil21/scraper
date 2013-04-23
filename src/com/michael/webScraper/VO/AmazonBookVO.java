/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.michael.webScraper.VO;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * This class is supposed to show the information we get after scraping the 
 * amazon web page.
 * @author nikhil
 */
public class AmazonBookVO {

    @Override
    public String toString() {
        return "AmazonBookVO{" + "author=" + author + ", bookName=" + bookName + '}';
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
    
    Integer id;

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }
    String author;
    String bookName;
    List<SellerVO> sellers = new ArrayList<SellerVO>();
    
    public static AmazonBookVO create(String bookName, String author, List<SellerVO> sellers){
        AmazonBookVO vo = new AmazonBookVO();
        vo.author=author;
        vo.bookName=bookName;
        vo.sellers=sellers;
                
        return vo;      
    }
}
