package com.kbc.Purchase;

public class Purchased_Item {
    private String buyerNameInSaled, productNameInSaled, pickupDateInSaled, pickupTimeInSaled;


    public Purchased_Item(String buyerNameInSaled, String productNameInSaled, String pickupDateInSaled, String pickupTimeInSaled) {
        this.buyerNameInSaled = buyerNameInSaled;
        this.productNameInSaled = productNameInSaled;
        this.pickupDateInSaled = pickupDateInSaled;
        this.pickupTimeInSaled = pickupTimeInSaled;
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



    public void setBuyerNameInSaled(String buyerNameInSaled) { this.buyerNameInSaled = buyerNameInSaled; }
    public void setProductNameInSaled(String productNameInSaled) { this.productNameInSaled = productNameInSaled; }
    public void setPickupDateInSaled(String pickupDateInSaled) { this.pickupDateInSaled = pickupDateInSaled; }
    public void setPickupTimeInSaled(String pickupTimeInSaled) { this.pickupTimeInSaled = pickupTimeInSaled; }

}