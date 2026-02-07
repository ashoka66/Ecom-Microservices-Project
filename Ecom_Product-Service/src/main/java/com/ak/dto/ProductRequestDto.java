package com.ak.dto;

import lombok.Data;

//@Data
public class ProductRequestDto {
	
private String name;
	
	private String description;
	
	private Double price;
	
	private Integer stock;
	
	private String category;
	
	private String imageUrl;
	
	
	// Getter and Setter for name
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    // Getter and Setter for description
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    // Getter and Setter for price
    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    // Getter and Setter for stock
    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    // Getter and Setter for category
    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
    
    public void setImageUrl(String imageUrl) {
    	
    	this.imageUrl=imageUrl;
    }
    
    public String getImageUrl() {
    	return imageUrl;
    	
    	
   	}

    // toString method
    @Override
    public String toString() {
        return "Product{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", price=" + price +
                ", stock=" + stock +
                ", category='" + category + '\'' + 
                ", imageUrl='" + imageUrl + '\'' + 
                
                '}';
    }

}
