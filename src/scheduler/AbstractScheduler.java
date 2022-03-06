package scheduler;

import java.util.logging.Level;
import java.util.logging.Logger;

import job.CronJob;

public abstract class AbstractScheduler {
	protected static final Logger LOGGER = Logger.getLogger("Scheduler Logger");
	String id;
	static final int CAPACITY = 10;
	protected boolean isRunning = false;
	
	public abstract void start();
	public abstract boolean isSchedulerFull();
	public abstract int getJobCount();
	public abstract int getRemSchedulerCapacity();
	public abstract boolean addJob(CronJob job);
	public abstract boolean cancelJob(CronJob job);


	public void shutdown() {
		// TODO: wait till any running job finish and store history
		this.isRunning = false;
		LOGGER.log(Level.INFO, "Scheduler shutdown");
	}
}
