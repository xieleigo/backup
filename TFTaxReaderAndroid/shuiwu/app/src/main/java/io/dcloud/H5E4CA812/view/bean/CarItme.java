package io.dcloud.H5E4CA812.view.bean;

public class CarItme {
    int CartItemID;

    public CarItme(int CartItemID) {
        this.CartItemID = CartItemID;
    }

    public int getCartItemID() {
        return CartItemID;
    }

    public void setCartItemID(int cartItemID) {
        CartItemID = cartItemID;
    }

    @Override
    public String toString() {
        return "CarItme{" +
                "CartItemID=" + CartItemID +
                '}';
    }
}
