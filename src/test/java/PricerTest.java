import org.junit.Assert;
import org.junit.Test;

/**
 * Created by Xavier on 07/02/2017.
 */

/*
    P1 = P*(1+V/100)
    F(P=50;v=2) => 50*0.02=1

 */
public class PricerTest {

    double INITIAL_PRICE = 50;
    double VOLATILITY = 2;

    @Test
    public void shouldPrice51WhenMaturityEquals1AndStrikeEquals50(){
        Pricer pricer = new Pricer();
        double price = pricer.price(INITIAL_PRICE,VOLATILITY,1);
        Assert.assertEquals(51,price,0);
    }
}
