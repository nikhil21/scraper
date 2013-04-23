/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.michael.webScraper;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlTableBody;
import com.gargoylesoftware.htmlunit.html.HtmlTableCell;
import com.gargoylesoftware.htmlunit.html.HtmlTableRow;
import com.gargoylesoftware.htmlunit.html.DomElement;
import com.gargoylesoftware.htmlunit.html.HtmlDivision;
import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.michael.webScraper.VO.BookVO;
import com.michael.webScraper.VO.SellerVO;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.apache.http.client.utils.URIBuilder;

/**
 *
 * @author shweta
 */
public class AbeWebScraper implements IWebScraper {

    public static final String abeURL = "www.abebooks.com/servlet/SearchResults?isbn=";
    
    @Override
    public void fetchDetails(String searchString) {
        
    }

    public void showDetails(String isbnNo) throws URISyntaxException, IOException {
        // create the final URL
        String finalURL = abeURL + isbnNo;
        // now create the final URL
        URIBuilder builder = new URIBuilder();
        builder.setScheme("http").setHost(finalURL);
        URI uri = builder.build();
        System.out.println("Abe URL found :" + uri.toString());

        // fetch details
        final WebClient webClient = new WebClient();
        final HtmlPage page = webClient.getPage(uri.toString());

        //get list of all tbody elements which has class as "result"
        final List<?> resultList = page.getByXPath("//div[@class='result']");
        Iterator it = resultList.iterator();

        try{
          DatabaseUtil.establishDatabaseConnection();
          DatabaseUtil.initialize();
          
          List<SellerVO> sellerList = new ArrayList<SellerVO>();
          while (it.hasNext()) {
                  HtmlDivision divElement = (HtmlDivision) it.next();
                  System.out.println("Div 1: " + divElement);
                  System.out.println("Div 2: " +divElement.getElementsByAttribute("div", "class", "result-data"));
                  String className ;         
                  SellerVO seller;
//                  for (HtmlTableRow row : tableBodyElement.getRows()) {
//                      System.out.println("------------------ Next Row -----------------");
//                      seller = new SellerVO();
//                      for (HtmlTableCell cell : row.getCells()) {
//                          for(DomElement element : cell.getChildElements()){
//                              className = element.getAttribute("class");
//                              if(className.equalsIgnoreCase("price")){
//                                  System.out.println("Found Price : "+element.asText());
//                                  seller.setPrice(element.asText().trim());
//                              } else if(className.equalsIgnoreCase("condition")){
//                                  System.out.println("Found Condition : "+element.asText());
//                                  seller.setCondition(element.asText().trim());
//                              } else if(className.equalsIgnoreCase("sellerInformation")){
//                                  for(DomElement child1 : element.getChildElements()){
//                                     for(DomElement child2 : child1.getChildElements()){                                   
//                                         if(child2.getAttribute("class").equalsIgnoreCase("seller")){
//                                             for(DomElement child3 : child2.getChildElements()){
//                                                  if(!child3.getAttribute("class").equalsIgnoreCase("sellerHeader")){
//                                                      System.out.println("Found Seller : "+child3.asText());
//                                                      seller.setName(child3.asText().trim());
//                                                  }   
//                                             }
//                                         } else if(child2.getAttribute("class").equalsIgnoreCase("rating")){
//                                             for(DomElement child3 : child2.getChildElements()){
//                                                  if(!child3.getAttribute("class").equalsIgnoreCase("ratingHeader") || !child3.getAttribute("class").equalsIgnoreCase("olpSellerStars")){
//                                                      if(child3.getId().startsWith("rating")){
//                                                          System.out.println("Found Rating : "+child3.asText());
//                                                          seller.setRating(child3.asText().trim());                                               
//                                                      }                                                
//                                                   }   
//                                             }
//                                         }                        
//                                      }
//                                   } 
//                              }   
//                          }
//                      }
//                      System.out.println("SellerVO : "+seller);
//                      sellerList.add(seller);                                        
//                  }
              }
                
//              HtmlElement elm = page.getHtmlElementById("olpProductByLine");
//              String bookName  = elm.getPreviousElementSibling().asText().trim();
//              String author  = elm.asText().trim();
//              System.out.println("Book name :"+bookName);
//              System.out.println("Author :"+author);
//              BookVO book = BookVO.create(bookName, author, sellerList);          
//              System.out.println("BookVO : "+book);       
//
//              if(DatabaseUtil.create(book, Source.ABE)){
//                  LogUtil.log(book);
//              }  
                           
        } catch(Exception e){
            System.out.println ("Exception :"+e);
        } finally {
            webClient.closeAllWindows(); 
            
            DatabaseUtil.finish();
        }      
    }
}
