/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.michael.webScraper;

/**
 * Interface intended to be implemented when a new scraper is written
 */
public interface IWebScraper {
    
    public void fetchDetails(String searchString);
    
}
