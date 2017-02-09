import org.junit.Assert;
import org.junit.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Random;

public class PricerTest {



    // STEP 1 //
    @Test
    public void shouldPrice51_WhenMaturityEquals1_AndStrikeEquals50_WithVolatilityEquals2(){
        //Background
        Pricer pricer = new Pricer(){
            public int getRandomFactor(){
                return 1;
            }
        };
        //Given
        double spot = 50;
        int volatility = 2;
        //When
        double price = pricer.price(spot, volatility, LocalDate.now().plusDays(1));
        //Then
        Assert.assertEquals(51,price,0);
    }

    // STEP 1 //
    @Test
    public void shouldPrice49_WhenMaturityEquals1_AndStrikeEquals50_WithVolatilityEqualsMinus2(){
        //Background
        Pricer pricer = new Pricer(){
            public int getRandomFactor(){
                return -1;
            }
        };
        //Given
        double spot = 50;
        int volatility = 2;
        //When
        double price = pricer.price(spot,volatility, LocalDate.now().plusDays(1));
        //Then
        Assert.assertEquals(49,price,0);
    }


    // STEP 2.0 : Pseudo random generator for Random(1).next(3)-1

    //  Step |  1 |  2 |  3 |  4 |  5 |  6 |  7 |  8 |  9 | 10 | 11 | 12 | 13 | 14 | 15
    // ------+----+----+----+----+----+----+----+----+----+----+----+----+----+----+----
    // Slope | -1 |  0 |  0 | -1 |  1 |  0 |  1 |  0 |  0 | 0  |  0 |  0 |  0 | -1 | -1
    //
    @Test
    public void shouldGetExpectedSequenceWhenSeedEquals1(){
        Random random = new Random(1) ;
        Pricer pricer = new Pricer(null, random);
        Assert.assertEquals(-1,pricer.getRandomFactor());
        Assert.assertEquals(0,pricer.getRandomFactor());
        Assert.assertEquals(0,pricer.getRandomFactor());
        Assert.assertEquals(-1,pricer.getRandomFactor());
        Assert.assertEquals(1,pricer.getRandomFactor());
        Assert.assertEquals(0,pricer.getRandomFactor());
    }

    // STEP 2.1 : pseudo random pricing for 5 steps
    /*
        For volatility = 20 :
        ---------------------
        Slope = -1 => P1 = P0 * 0.98
        Slope = 0  => P1 = P0
        Slope = 1  => P1 = P0 * 1.02

        Given the  price * (1 + slope * volatility / 100);
        P00=50
        P01=49
        P02=49
        P03=49
        P04=48.02
        P05=48,9804
        P06=48,9804
        P07=49,960008
        P08=49,960008
        P09=49,960008
        P10=49,960008
        P11=49,960008
        P12=49,960008
        P13=49,960008
        P14=48,96080784
        P15=47,9815916832
     */
    @Test
    public void shouldReturn47_9815916832_WhenMaturityIs15Days_AndStrikeEquals50_WithVolatilityEquals2(){
        //Background
        Random random = new Random(1) ;
        Calendar calendar = new Calendar();
        Pricer pricer = new Pricer(calendar, random);

        //Given
        LocalDate start = LocalDate.now();
        LocalDate maturity = calendar.addWorkingDays(start,15);
        double spot = 50;
        int volatility = 2;

        //When
        double price = pricer.price(spot, volatility, maturity);

        //Then
        Assert.assertEquals(47.9815916832,round(price),0);
    }

    // STEP2.2 : extremums test//
    @Test
    public void shouldReturnValueAboveSpot_WhenMaturityIsMax_AndVolatilitySlopeIsPositive(){
        //Background
        Pricer pricer = new Pricer(){
            public int getRandomFactor(){
                return 1;
            }
        };
        //Given
        double spot = 50;
        int volatility = 2;

        //When
        double price = pricer.price(spot, volatility, LocalDate.now().plusYears(50));
        System.out.println(price);
        //Then
        Assert.assertTrue(spot<price);
    }

    @Test
    public void shouldReturnValueBelowSpot_WhenMaturityIsMax_AndVolatilitySlopeIsNegative(){
        //Background
        Pricer pricer = new Pricer(){
            public int getRandomFactor(){
                return -1;
            }
        };
        //Given
        double spot = 50;
        int volatility = 2;

        //When
        double price = pricer.price(spot, volatility, LocalDate.now().plusYears(50));
        System.out.println(price);
        //Then
        Assert.assertTrue(spot>price);
    }

    private static double round(double d) {
        BigDecimal bd = new BigDecimal(Double.toString(d));
        bd = bd.setScale(10, BigDecimal.ROUND_HALF_UP);
        return bd.doubleValue();
    }

}
