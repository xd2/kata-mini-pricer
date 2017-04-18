import org.junit.Assert;
import org.junit.Test;

import java.time.LocalDate;

import static org.junit.Assert.*;

public class CalendarTest {
    @Test
    public void shouldGet21WorkingDays_whenStartDateIs20170430_andMaturityDateis20170531() throws Exception {
        //Given
        LocalDate start = LocalDate.parse("2017-04-30");
        LocalDate maturity = LocalDate.parse("2017-05-31");;
        //When
        Calendar calendar = new Calendar();
        long days = calendar.countWorkingDays(start,maturity);
        //Then
        Assert.assertEquals(21,days);
    }


    @Test
    public void shoulGet0_whenStartDate_andMaturity_areEquals() {
        //Given
        LocalDate start = LocalDate.parse("2017-02-13");
        LocalDate maturity =  LocalDate.parse("2017-02-13");
        //When
        Calendar calendar = new Calendar();
        long days = calendar.countWorkingDays(start,maturity);
        //Then
        Assert.assertEquals(0,days);
    }


    @Test
    public void shoulGet2_WhenStartDateIsMonday_andMaturityIsTuesday() {
        //Given
        LocalDate start = LocalDate.parse("2017-02-13");
        LocalDate maturity =  LocalDate.parse("2017-02-14");
        //When
        Calendar calendar = new Calendar();
        long days = calendar.countWorkingDays(start,maturity);
        //Then
        Assert.assertEquals(1,days);
    }

    @Test
    public void shouldReturnMay15th_whenAdding5WorkingDays_AndDateIsMay5th(){
        //Given
        LocalDate date = LocalDate.parse("2017-05-05");
        int workingDays = 5;
        //When
        Calendar calendar = new Calendar();
        LocalDate nextDate = calendar.addWorkingDays(date,workingDays);
        //Then
        Assert.assertEquals(LocalDate.parse("2017-05-15"),nextDate);
    }

}