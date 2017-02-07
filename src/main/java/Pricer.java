/**
 * Created by Xavier on 07/02/2017.
 */
public class Pricer {
    public double price(double strike, double volatility, int days){
        return strike * (1 + volatility / 100);
    }
}
