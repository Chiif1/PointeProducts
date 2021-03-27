package com.nugrs.pointeproductsapp;

public class PurchaseClass {
    String productName;
    String productPrice;
    String id;

    public PurchaseClass(String id, String productName, String productPrice) {
        this.productName = productName;
        this.productPrice = productPrice;;
        this.id = id;
    }


    public String getProductName() {
        return productName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(String productPrice) {
        this.productPrice = productPrice;
    }


}
