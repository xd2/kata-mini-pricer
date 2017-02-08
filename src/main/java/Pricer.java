import java.time.LocalDate;
import static java.time.temporal.ChronoUnit.DAYS;

/**
 * Created by Xavier on 07/02/2017.
 */
public class Pricer {

    public double price(double strike, double volatility, LocalDate maturity){

        long days = DAYS.between(LocalDate.now(),maturity);

        double price = strike;

        for(int i=1;i<=days;i++)
        {
            price = price * (1 + volatility / 100);
        }
        return price;
    }


}
