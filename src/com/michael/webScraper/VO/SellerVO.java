/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.michael.webScraper.VO;

/**
 *
 * @author 
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
        return "SellerVO{" + "name=" + name + ", rating=" + rating + ", price=" + price + ","
                + " condition=" + condition +" location=" +zip_location+ '}';
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
    
    Integer id;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getBook_id() {
        return book_id;
    }

    public String getZip_location() {
        return zip_location;
    }

    public void setZip_location(String zip_location) {
        this.zip_location = zip_location;
    }

    public void setBook_id(Integer book_id) {
        this.book_id = book_id;
    }
    Integer book_id;
    String name;
    String rating;
    String price;
    String condition;
    String zip_location;
    
    public static SellerVO create(String name, String rating, String price, String condition){
        SellerVO vo = new SellerVO();
        vo.name=name;
        vo.rating=rating;
        vo.price=price;
        vo.condition=condition;
        
        return vo;
    }
}
