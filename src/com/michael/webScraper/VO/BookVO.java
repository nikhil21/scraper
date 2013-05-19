/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.michael.webScraper.VO;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * This class is supposed to show the book information we 
 * get after scraping the web page. This class is used for persistence as well
 */
public class BookVO {
    
    Integer id;
    String author;
    String bookName;
    String source;
    Date scrapedDate;
    List<SellerVO> sellers = new ArrayList<SellerVO>();

    @Override
    public String toString() {
        return "BookVO{" + "author=" + author + ", bookName=" + bookName + ", source="+ source + ", scraedDate="+ scrapedDate + '}';
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
    
    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public Date getScrapedDate() {
        return scrapedDate;
    }

    public void setScrapedDate(Date scrapedDate) {
        this.scrapedDate = scrapedDate;
    }
                  
    public static BookVO create(String bookName, String author, List<SellerVO> sellers){
        BookVO vo = new BookVO();
        vo.author=author;
        vo.bookName=bookName;
        vo.sellers=sellers;
                
        return vo;      
    }
}
