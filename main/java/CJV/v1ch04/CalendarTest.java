package CJV.v1ch04;

import java.time.DayOfWeek;
import java.time.LocalDate;

public class CalendarTest {
    public static void main(String[] args)
    {
        LocalDate date  = LocalDate.now();
        int month = date.getMonthValue();
        int today = date.getDayOfMonth();

        date = date.minusDays(today - 1);
        DayOfWeek weekday = date.getDayOfWeek();
        int value = weekday.getValue();
    }
}
