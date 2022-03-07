package job;

import java.util.Calendar;

import enums.TriggerState;

public interface ICronJob {
	
	public String getId();
	public JobSchedule getJobSchedule();
	public TriggerState getState();
	public void initializeForScheduler();
	public boolean shouldRunNow();
	public void execute();
	public Calendar getNextRunScheduledTime();
	public void updateScheduledTime();
	public void end();

}
