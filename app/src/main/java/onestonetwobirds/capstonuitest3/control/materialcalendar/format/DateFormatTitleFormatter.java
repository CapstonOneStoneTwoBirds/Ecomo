package onestonetwobirds.capstonuitest3.control.materialcalendar.format;

import onestonetwobirds.capstonuitest3.control.materialcalendar.CalendarDay;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Locale;

/**
 * Format using a {@linkplain DateFormat} instance.
 */
public class DateFormatTitleFormatter implements TitleFormatter {

    private final DateFormat dateFormat;

    /**
     * Uses "MMMM yyyy" for formatting
     */
    public DateFormatTitleFormatter() {
        this.dateFormat = new SimpleDateFormat(
                "MMMM yyyy", Locale.getDefault()
        );
    }

    /**
     * @param format the format to use
     */
    public DateFormatTitleFormatter(DateFormat format) {
        this.dateFormat = format;
    }

    @Override
    public CharSequence format(CalendarDay day) {
        return dateFormat.format(day.getDate());
    }
}
