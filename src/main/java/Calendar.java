import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.MonthDay;
import java.time.temporal.Temporal;
import java.util.Arrays;
import java.util.List;

/**
 * No quotation on Saturday, Sunday, neither on 01/01, 01/05, 08/05
 */
public class Calendar {

    List<MonthDay> offDays = Arrays.asList(
            MonthDay.of(1,1),
            MonthDay.of(5,1),
            MonthDay.of(5,8)
    );

    public Calendar(MonthDay... daysOff){
        offDays = Arrays.asList(daysOff);
    }

    public LocalDate addWorkingDays(LocalDate date, int days){
        for(int i=0;i<days;i++) {
            do {
                date = date.plusDays(1);
            } while (!isWorkingDay(date));
        }
        return date;
    }

    public int countWorkingDays(LocalDate start, LocalDate maturity){
        int workingDays = 0;
        for(LocalDate date = start.plusDays(1); date.isBefore(maturity)||date.isEqual(maturity); date = date.plusDays(1)){
            if(isWorkingDay(date)){
                workingDays++;
            }
        }
        return workingDays;
    }

    protected boolean isWorkingDay(LocalDate date){
        return !isWeekend(date) && !isDayOff(date);
    }

    protected boolean isWeekend(LocalDate date){
        return date.getDayOfWeek() == DayOfWeek.SATURDAY
                || date.getDayOfWeek() == DayOfWeek.SUNDAY;
    }

    protected boolean isDayOff(LocalDate date){
        MonthDay monthDay = MonthDay.of(date.getMonth(),date.getDayOfMonth());
        return offDays.contains(monthDay);
    }
}
