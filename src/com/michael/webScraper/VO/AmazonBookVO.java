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
    
    String author;
    String bookName;
    List<SellerVO> sellers = new ArrayList<SellerVO>();
    
}
