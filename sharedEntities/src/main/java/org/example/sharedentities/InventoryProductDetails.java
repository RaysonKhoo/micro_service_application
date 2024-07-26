package org.example.sharedentities;

public class InventoryProductDetails {
    private Product product;
    private Integer quantity;

    public InventoryProductDetails(Product product, int quantity) {
        this.product = product;
        this.quantity = quantity;
    }
    // Getters and setters
    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

}