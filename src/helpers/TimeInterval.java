package helpers;

public class TimeInterval {
	int second = 0;
	int minute = 0;
	int hour = 0;
	int day = 0;
	int month = 0;
	int year = 0;
	
	public TimeInterval(int y, int m, int d, int hr, int min, int sec) {
		this.second = sec;
		this.minute = min;
		this.hour = hr;
		this.day = d;
		this.month = m;
		this.year = y;
	} 
	/* This function converts the time interval to milliseconds 
	 * to be implemented now for simplicity it just converts seconds to milliseconds
	 * ignoring all other fields (mins, hours,days,months,years) 
	 * */
	public long getMillis(){
		return this.second * 1000;
	}
}
