/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.michael.webScraper;

import Employees.Workers;
import com.michael.webScraper.VO.BookVO;
import com.michael.webScraper.VO.SellerVO;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * @author nikhil
 */
public class BookDTO {
    
    String author;
    String bookName;
    String source;
    Date scrapedDate;
    List<SellerDTO> sellers = new ArrayList<SellerDTO>();
    
    @Override
    public String toString() {
        return "BookDTO{" + "author=" + author + ", bookName=" + bookName + ", source="+ source + ", scraedDate="+ scrapedDate + '}';
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
}
