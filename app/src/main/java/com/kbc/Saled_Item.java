package com.kbc;

public class Saled_Item {
    private String buyerNameInSaled, productNameInSaled, pickupDateInSaled, pickupTimeInSaled;
    private int saledQuantity;


    public Saled_Item(String buyerNameInSaled, String productNameInSaled, String pickupDateInSaled, String pickupTimeInSaled, int saledQuantity) {
        this.buyerNameInSaled = buyerNameInSaled;
        this.productNameInSaled = productNameInSaled;
        this.pickupDateInSaled = pickupDateInSaled;
        this.pickupTimeInSaled = pickupTimeInSaled;
        this.saledQuantity = saledQuantity;
    }

    public String getBuyerNameInSaled() {
        return buyerNameInSaled;
    }
    public String getProductNameInSaled() {
        return productNameInSaled;
    }
    public String getPickupDateInSaled() {
        return pickupDateInSaled;
    }
    public String getPickupTimeInSaled() {
        return pickupTimeInSaled;
    }
    public int getSaledQuantity() {
        return saledQuantity;
    }


    public void setBuyerNameInSaled(String buyerNameInSaled) { this.buyerNameInSaled = buyerNameInSaled; }
    public void setProductNameInSaled(String productNameInSaled) { this.productNameInSaled = productNameInSaled; }
    public void setPickupDateInSaled(String pickupDateInSaled) { this.pickupDateInSaled = pickupDateInSaled; }
    public void setPickupTimeInSaled(String pickupTimeInSaled) { this.pickupTimeInSaled = pickupTimeInSaled; }
    public void setSaledQuantity(int saledQuantity) {
        this.saledQuantity = saledQuantity;
    }

}