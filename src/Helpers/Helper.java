package Helpers;

import java.util.Calendar;

public class Helper {
	
	public static Calendar addTimeIntervalToCalendar(Calendar calendar, TimeInterval t) {
		Calendar newCalendar = calendar;
		newCalendar.add(Calendar.DATE, t.day);
		newCalendar.add(Calendar.MONTH, t.month);
		newCalendar.add(Calendar.YEAR, t.year);
		newCalendar.add(Calendar.HOUR, t.hour);
		newCalendar.add(Calendar.MINUTE, t.minute);
		newCalendar.add(Calendar.SECOND, t.second);
		return newCalendar;
	}
}
