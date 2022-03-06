package job;
import java.util.Calendar;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;

import enums.TriggerState;
import helpers.TimeInterval;

public class CronJob implements ICronJob{
	private static final Logger LOGGER = Logger.getLogger("Cron Job Logger");

	Job job;
	JobSchedule jobSchedule;
	TriggerState state = TriggerState.WAITING;
	//TODO: int runCounts = 0;
	//TODO: HashMap<> history  (run#, runStartTime, wasSuccessful?)
	
	public CronJob(Job job, JobSchedule jobSchedule) {
		this.job = job;
		this.jobSchedule = jobSchedule;
	}
	public CronJob(String id, TimerTask task, TimeInterval initialDelay, TimeInterval interval) {
		this.job = new Job(id, task);
		this.jobSchedule = new JobSchedule(initialDelay, interval);
	}
	public boolean shouldRunNow(){
		Calendar timeNow = Calendar.getInstance();
		Calendar scheduledTime = jobSchedule.getNextRunScheduledTime();
		if(scheduledTime == null) {
			return false;
		}
		if(scheduledTime.getTimeInMillis() - timeNow.getTimeInMillis() > 0) {
			return false;
		}
		return true;
	}
	public Calendar getNextRunScheduledTime() {
		Calendar scheduledTime = jobSchedule.getNextRunScheduledTime();
		return scheduledTime; // TODO: add validation
	}
	public void updateScheduledTime() {
		jobSchedule.updateScheduledTime();
	}
	public void execute() {
		if(this.job != null) {
			state = TriggerState.RUNNING;
			this.job.execute();
			state = TriggerState.ININTERVAL;
		}
	}
	public String getId() {
		if(job == null || job.getId() == null) {
			LOGGER.log(Level.WARNING,  "Invalid job id");
			return null;
		}
		return job.getId();
	}
	public void initializeForScheduler() {
		jobSchedule.initialize();
	}
}