/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.michael.webScraper;

/**
 *
 * @author Shweta
 */
public enum Source {

    AMAZON("amazon.com"), ABE("abs.com");
    
    private String value;

    private Source(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
    
};
