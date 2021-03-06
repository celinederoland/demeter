package accountancy.model.selection;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class DatesIterator {

    private final Calendar calendar = (new GregorianCalendar());
    private final Date     now      = new Date();
    private final Date start;

    public DatesIterator() {

        this(2017, Calendar.JANUARY, 1);
    }

    public DatesIterator(int year, int month, int day) {

        calendar.set(year, month, day);
        start = calendar.getTime();
    }

    public boolean hasNext() {

        return !(calendar.getTime().after(now));
    }

    public Date next() {

        calendar.add(Calendar.MONTH, 1);
        return calendar.getTime();
    }

    public Date current() {

        return calendar.getTime();
    }

    public Date min() {

        return start;
    }

    public Date max() {

        return now;
    }
}
