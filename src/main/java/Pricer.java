import java.time.LocalDate;
import java.util.Random;

import static java.time.temporal.ChronoUnit.DAYS;


public class Pricer {

    protected final int TOTAL_SAMPLING = 1000;


    protected Calendar calendar = null;
    protected Random random = null;

    public Pricer(){
        calendar = new Calendar();
        random = new Random();
    }
    public Pricer(Calendar calendar, Random random){
        this.calendar = calendar;
        this.random = random;
    }

    protected double basicPrice(double spot, int volatility, LocalDate maturity){

        long days = calendar.countWorkingDays(LocalDate.now(),maturity) ;

        double price = spot;

        for(int i=1;i<days;i++)
        {
            int slope = getRandomFactor();
            price = price * (1.0d + slope * volatility / 100d);
        }
        return price;
    }


    public double price(double spot, int volatility, LocalDate maturity) {
        double price = 0;
        for (int i=1; i <= TOTAL_SAMPLING ; i++){
            double aPrice = basicPrice(spot, volatility, maturity);
            price += aPrice;
        }
        price = price / TOTAL_SAMPLING;
        return price;
    }

    public int getRandomFactor(){
        return random.nextInt(3) - 1;
    }

}
