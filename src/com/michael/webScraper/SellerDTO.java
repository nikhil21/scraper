/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.michael.webScraper;

/**
 *
 * @author nikhil
 */
public class SellerDTO {
        
    Integer id;   
    String name;    
    String rating;
    String price;
    String condition;
        
    public Integer getId() {
        return id;
    } 

    public void setId(Integer id) {
        this.id = id;
    }
    
    public String getName() {
        return name;
    }

    public String getRating() {
        return rating;
    }

    @Override
    public String toString() {
        return "SellerVO{" + "name=" + name + ", rating=" + rating + ", price=" + price + ", condition=" + condition + '}';
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public String getPrice() {
        return price;
    }

    public String getCondition() {
        return condition;
    }
       
}
