package model;

/**
 * Created by Xavier on 14/02/2017.
 */
public class Instrument {
    String mnemonic;
    double price;
    int volatility;

    public Instrument(String mnemonic, double price, int volatility) {
        this.mnemonic = mnemonic;
        this.price = price;
        this.volatility = volatility;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getVolatility() {
        return volatility;
    }
}
