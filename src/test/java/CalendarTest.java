import org.junit.Assert;
import org.junit.Test;

import java.time.LocalDate;
import java.time.MonthDay;

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

    @Test
    public void should_have_two_days_off() {
        Calendar calendar = new Calendar(MonthDay.of(6,15),MonthDay.of(6,19));
        Assert.assertEquals(2,calendar.offDays.size() );
    }


    @Test
    public void should_count_two_working_days(){
        //GIVEN
        LocalDate startDate = LocalDate.of(2017,6,14);
        LocalDate targetedDate = LocalDate.of(2017,6,20);
        //WHEN
        Calendar calendar = new Calendar(MonthDay.of(6,15),MonthDay.of(6,19));
        int count = calendar.countWorkingDays(startDate,targetedDate);
        //THEN
        Assert.assertEquals(2,count);
    }

}