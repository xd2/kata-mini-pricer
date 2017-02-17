import model.Basket;
import model.Instrument;
import org.junit.Assert;
import org.junit.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
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
    //
    //  Step |  1 |  2 |  3 |  4 |  5 |  6 |  7 |  8 |  9 | 10 | 11 | 12 | 13 | 14 | 15
    // ------+----+----+----+----+----+----+----+----+----+----+----+----+----+----+----
    // Value | -1 |  0 |  0 | -1 |  1 |  0 |  1 |  0 |  0 | 0  |  0 |  0 |  0 | -1 | -1
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
        Calendar calendar = new Calendar();
        Pricer pricer = new Pricer(calendar,new Random()){
            protected double basicPrice(double spot, int volatility, LocalDate maturity){
                //Step3: Reset Random on each new sampling for testing purpose
                random = new Random(1) ;
                return super.basicPrice(spot,volatility,maturity);
            }
        };
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

    // STEP2.2 : maximum test//
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
        //Then
        Assert.assertTrue(spot<price);
    }

    // STEP2.2 : minimum test//
    @Test
    public void shouldReturnValueBellowSpot_WhenMaturityIsMax_AndVolatilitySlopeIsNegative(){
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
        //Then
        Assert.assertTrue(spot>price);
    }

    // STEP 4 : Basket pricing
    @Test
    public void basketPrice(){
        //Background
        Calendar calendar = new Calendar();
        Pricer pricer = new Pricer(calendar,new Random()){
            protected double basicPrice(double spot, int volatility, LocalDate maturity){
                //Reset Random on each pricing for testing purpose
                random = new Random(1) ;
                return super.basicPrice(spot,volatility,maturity);
            }
        };
        //Given
        LocalDate start = LocalDate.now();
        LocalDate maturity = calendar.addWorkingDays(start,15);

        Instrument pivot = new Instrument("pivot", 50,2);
        Instrument gmc = new Instrument("gmc", 50,2);
        Instrument bnp = new Instrument("bnp", 100,0);
        Instrument edf = new Instrument("edf", 35,0);
        Instrument total = new Instrument("total",48,0);
        Instrument google = new Instrument("google",59,0);
        Map<Instrument,Double> content = addKeyValues(new HashMap(),
                gmc, 100d,
                bnp,100d,
                edf, -100d,
                total, 0d,
                google, 102d);
        Basket basket = new Basket(pivot,content);

        //When
        pricer.price(basket, maturity);

        //Then
        // bnp = strike * adjustedVar * corel = 50 * 0,959631833664 * 100% = 47,9815916832
        Assert.assertEquals(47.9815916832, round(gmc.getPrice()),0);
        // bnp = strike * adjustedVar * corel = 100 * 0,959631833664 * 100% = 95,9631833664
        Assert.assertEquals(95.9631833664, round(bnp.getPrice()),0);
        // edf =  35 * 0,959631833664 *  (- 100/100) = -33,58711417824
        Assert.assertEquals(-33.5871141782, round(edf.getPrice()),0);
        // tot =  48 * 0,959631833664 *  (0/100) = 0
        Assert.assertEquals(0, round(total.getPrice()),0);
        // goo =  59 * 0,959631833664 *  (102/100) = 57,75064374989952
        Assert.assertEquals(57.7506437499, round(google.getPrice()),0);
    }

    private static double round(double d) {
        BigDecimal bd = new BigDecimal(Double.toString(d));
        bd = bd.setScale(10, BigDecimal.ROUND_HALF_UP);
        return bd.doubleValue();
    }

    private Map addKeyValues(Map map, Object... keyValues){
        assert(keyValues.length%2==0);
        for(int i=0;i<keyValues.length;i+=2){
            map.put(keyValues[i],keyValues[i+1]);
        }
        return map;
    }

}
