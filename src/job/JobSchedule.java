package job;
import java.util.Calendar;
import java.util.logging.Level;
import java.util.logging.Logger;

import helpers.Helper;
import helpers.TimeInterval;

public class JobSchedule {
	private static final Logger LOGGER = Logger.getLogger("Job Schedule Logger");
	TimeInterval initialDelay;
	TimeInterval interval;
	private Calendar assignedAt;
	private Calendar scheduledTime;
	boolean isFirstRun = true;
	
	public JobSchedule(TimeInterval initialDelay, TimeInterval interval) {
		this.initialDelay = initialDelay;
		this.interval = interval;
	}
	public void initialize() {
		initJobAssignedAt();
		updateScheduledTime();
		isFirstRun = false;
	}
	public Calendar updateScheduledTime() {
		if(isFirstRun) {
			scheduledTime = Helper.addTimeIntervalToCalendar(assignedAt, initialDelay);
		}else {
			scheduledTime = Helper.addTimeIntervalToCalendar(scheduledTime, interval);
		}
		//LOGGER.log(Level.INFO, "scheduled time" + scheduledTime.getTime());
		return scheduledTime;
	}

	public Calendar initJobAssignedAt(){
		assignedAt = Calendar.getInstance();
		//LOGGER.log(Level.INFO, "Set assigned at" + assignedAt.getTime());

		return assignedAt;
	}
	
	public Calendar getNextRunScheduledTime() {
		if(scheduledTime == null) {
			LOGGER.log(Level.WARNING,  "Job not scheduled yet");
		}
		return scheduledTime;
	}
}
