import org.junit.Assert;
import org.junit.Test;

import java.time.LocalDate;

public class PricerTest {

    double INITIAL_PRICE = 50;
    double VOLATILITY = 2;

    @Test
    public void shouldPrice51WhenMaturityEquals1AndStrikeEquals50(){
        Pricer pricer = new Pricer();
        double price = pricer.price(INITIAL_PRICE,VOLATILITY, LocalDate.now().plusDays(1));
        Assert.assertEquals(51,price,0);
    }
}
