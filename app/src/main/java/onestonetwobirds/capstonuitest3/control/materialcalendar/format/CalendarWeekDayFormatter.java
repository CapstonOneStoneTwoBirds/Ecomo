package onestonetwobirds.capstonuitest3.control.materialcalendar.format;

import onestonetwobirds.capstonuitest3.control.materialcalendar.CalendarUtils;

import java.util.Calendar;
import java.util.Locale;

/**
 * Use a {@linkplain Calendar} to get week day labels.
 *
 * @see Calendar#getDisplayName(int, int, Locale)
 */
public class CalendarWeekDayFormatter implements WeekDayFormatter {

    private final Calendar calendar;

    /**
     * @param calendar Calendar to retrieve formatting information from
     */
    public CalendarWeekDayFormatter(Calendar calendar) {
        this.calendar = calendar;
    }

    public CalendarWeekDayFormatter() {
        calendar = CalendarUtils.getInstance();
    }

    @Override
    public CharSequence format(int dayOfWeek) {
        calendar.set(Calendar.DAY_OF_WEEK, dayOfWeek);
        return calendar.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.SHORT, Locale.getDefault());
    }
}
