package job;

import java.util.Calendar;

public interface IJobSchedule {	
	public void initialize();
	public Calendar updateScheduledTime();
	public Calendar getNextRunScheduledTime() ;	
}
