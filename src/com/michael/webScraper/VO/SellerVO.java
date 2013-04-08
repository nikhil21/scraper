/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.michael.webScraper.VO;

/**
 *
 * @author nikhil
 */
public class SellerVO {

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
    
    String name;
    String rating;
    String price;
    String condition;
    
    public static SellerVO create(String name, String rating, String price, String condition){
        SellerVO vo = new SellerVO();
        vo.name=name;
        vo.rating=rating;
        vo.price=price;
        vo.condition=condition;
        
        return vo;
    }
}
