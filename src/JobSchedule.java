import java.util.Calendar;

import Helpers.Helper;
import Helpers.TimeInterval;

public class JobSchedule {
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
//		System.out.println("scheduled time" + scheduledTime.getTime());
		return scheduledTime;
	}

	public Calendar initJobAssignedAt(){
		assignedAt = Calendar.getInstance();
//		System.out.println("Set assigned at");
//		System.out.println(assignedAt.getTime());

		return assignedAt;
	}
	
	public Calendar getNextRunScheduledTime() {
		if(scheduledTime == null) {
			System.out.println("Err : Job not scheduled yet");
		}
		return scheduledTime;
	}
}
