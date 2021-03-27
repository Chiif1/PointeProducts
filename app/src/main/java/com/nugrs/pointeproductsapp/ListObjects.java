package com.nugrs.pointeproductsapp;

//Class used to populate list of products
public class ListObjects {
    String image;
    String productName;
    String productPrice;
    String productDescription;

    public ListObjects(String image, String productName, String productPrice, String productDescription) {
        this.image = image;
        this.productName = productName;
        this.productPrice = productPrice;
        this.productDescription = productDescription;
    }

    public String getImage() {
        return image;
    }

    public String getProductName() {
        return productName;
    }

    public String getProductPrice() {
        return productPrice;
    }

    public String getProductDescription() {
        return productDescription;
    }
}
