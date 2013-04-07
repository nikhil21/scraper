/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.michael.webScraper;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlDivision;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import java.io.IOException;
import java.net.URI;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.client.utils.URIUtils;

/**
 *
 * @author nikhil
 */
public class AmazonWebScraper implements IWebScraper {

    private static final String amazonBookSearchURL = "http://www.amazon.com/s/ref=nb_sb_ss_i_0_21?url=search-alias%3Dstripbooks&field-keywords=harry%20potter%20and%20the%20sorcerer's%20stone&sprefix=harry+potter+and+the+%2Cstripbooks%2C542&rh=i%3Astripbooks%2Ck%3Aharry%20potter%20and%20the%20sorcerer's%20stone";
    private static final String amazonBookSearchURL1 = "http://www.amazon.com/s/ref=nb_sb_ss_i_0_21?url=search-alias%3Dstripbooks&field-keywords=#&sprefix=#+%2Cstripbooks%2C542&rh=i%3Astripbooks%2Ck%3A#";
    private static final String amazonBookSearchURL2 = "http://www.amazon.com/s/?url=search-alias%3Dstripbooks&field-keywords=&";
    private static final String amazonAllLink = "http://www.amazon.com/gp/offer-listing/059035342X/";
    
    private static final String amazonBookFinalURL = "http://www.amazon.com/gp/offer-listing/";
    
    @Override
    public void fetchDetails(String searchString) {
        try {
            String searchURL = resolveURL(searchString);
            // find the ISBN no
            findISBNNo(searchString);
        } catch (Exception e) {
            System.out.println("Exception...");
        }
    }

    public String resolveURL(String searchString) throws Exception {
        URIBuilder builder = new URIBuilder();
        builder.setScheme("http").setHost("www.amazon.com/s").setPath("/ref=nb_sb_ss_i_0_21")
                .setParameter("url", "search-alias%3Dstripbooks")
                .setParameter("field-keywords", searchString)
                .setParameter("sprefix", searchString)
                .setParameter("rh", searchString);
        URI uri = builder.build();
        System.out.println(">>>>>>>>>>>>" + uri.toString());
        String newStr = uri.toString().replaceFirst("%25", "%");
        System.out.println("<><><><><> " + newStr);
        return newStr;
    }

    /**
     * Finds the ISBN no of the book if we provide the URL of the Amazon web page to it.
     * @param url 
     */
    public String findISBNNo(String url) throws IOException {
        final WebClient webClient = new WebClient();
  //      final HtmlPage page = webClient.getPage("http://www.amazon.com/s/ref=nb_sb_ss_i_3_10?url=search-alias%3Dstripbooks&field-keywords=harry+potter+and+the+sorcerer%27s+stone&sprefix=harry+pott%2Cstripbooks%2C1159");
        //Assert.assertEquals("HtmlUnit - Welcome to HtmlUnit", page.getTitleText());
        final HtmlPage page = webClient.getPage(url);
        final String pageAsXml = page.asXml();
        //Assert.assertTrue(pageAsXml.contains("<body class=\"composite\">"));

        final String pageAsText = page.asText();
        //Assert.assertTrue(pageAsText.contains("Support for the HTTP and HTTPS protocols"));


        final HtmlDivision div = page.getHtmlElementById("result_0");
        //System.out.println(">>>>> "+div.getFirstChild().asText());
        System.out.println("Div found .. " + div.getAttribute("name"));
        //final HtmlAnchor anchor = page.getAnchorByName("anchor_name");
        String isbnNo = div.getAttribute("name");
        webClient.closeAllWindows();
        return isbnNo;
    }
    
    
}
