package job;

import java.util.Calendar;

public interface ICronJob {
	
	public String getId();
	public void initializeForScheduler();
	public boolean shouldRunNow();
	public void execute();
	public Calendar getNextRunScheduledTime();
	public void updateScheduledTime();
}
