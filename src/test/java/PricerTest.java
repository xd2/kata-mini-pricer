import org.junit.Assert;
import org.junit.Test;

import java.time.LocalDate;

public class PricerTest {

    @Test
    public void shouldPrice51_WhenMaturityEquals1_AndStrikeEquals50_WithVolatilityEquals2(){
        //Given
        double initialPrice = 50;
        double volatility = 2;
        //When
        Pricer pricer = new Pricer();
        double price = pricer.price(initialPrice,volatility, LocalDate.now().plusDays(1));
        //Then
        Assert.assertEquals(51,price,0);
    }

    @Test
    public void shouldPrice49_WhenMaturityEquals1_AndStrikeEquals50_WithVolatilityEqualsMinus2(){
        //Given
        double initialPrice = 50;
        double volatility = -2;
        //When
        Pricer pricer = new Pricer();
        double price = pricer.price(initialPrice,volatility, LocalDate.now().plusDays(1));
        //Then
        Assert.assertEquals(49,price,0);
    }
}
