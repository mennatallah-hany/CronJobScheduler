package scheduler;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.logging.Level;

import enums.TriggerState;
import helpers.TimeInterval;
import job.CronJob;
import job.JobSchedule;

public class AsyncScheduler extends AbstractScheduler{
	HashSet<CronJob> jobs;
	Queue<CronJob> jobsToRunQueue;
	
	public AsyncScheduler(String id) {
		this.id = id;
		jobs = new HashSet<>();
		jobsToRunQueue = new LinkedList<CronJob>();
	}

	@Override
	public void start() {
		this.isRunning = true;
		LOGGER.log(Level.INFO, "Scheduler started");
		while(!jobsToRunQueue.isEmpty()) {
			CronJob nextJobToRun = jobsToRunQueue.poll();
			runJobAsync(nextJobToRun);
		}		
	}
	@Override
	public boolean addJob(CronJob job) {
		if(job == null) {
			LOGGER.log(Level.WARNING, "Invalid job, Can not add to scheduler");
			return false;
		}
		if(isSchedulerFull()) {
			LOGGER.log(Level.WARNING, "Scheduler is full, cannot add extra jobs");
			return false;
		}
		boolean isAdded = false;
		if(isRunning) {
			isAdded = runJobAsync(job);
		}else {
			isAdded = jobsToRunQueue.add(job);
		}
		if(isAdded) {
			LOGGER.log(Level.INFO, "Job : "+ job.getId() + " was added successfully to scheduler");
			return true;
		}
		LOGGER.log(Level.WARNING, "Job : "+ job.getId() + " not added to scheduler");
		return false;
	}
	private boolean runJobAsync(CronJob job) {
		if(job == null) {
			LOGGER.log(Level.WARNING, "Invalid job, Can not add to scheduler");
			return false;
		}

		Thread thread = new Thread(new Runnable() {
		    public void run()
		    {
		    	// TODO: add methods to get timeintervals from JobSchedule directly
				JobSchedule jobSchedule = job.getJobSchedule();
				TimeInterval jobInitialDelay = jobSchedule.getInitialDelay();
				TimeInterval jobInterval = jobSchedule.getInterval();
				// Initial Delay Sleep
				try {
					Thread.sleep(jobInitialDelay.getMillis());
				} catch (InterruptedException e) {
					LOGGER.log(Level.SEVERE, "Error in intial delay of Job : "+ job.getId());
				}
				while(job.getState()!= TriggerState.ENDED) {
					// Execute Job
					job.execute();
					//Interval sleep
					try {
						Thread.sleep(jobInterval.getMillis());
					} catch (InterruptedException e) {
						LOGGER.log(Level.SEVERE, "Error in interval between Job executions : "+ job.getId());
					}
				}
		    }});
		thread.start();
		return isRunning;		
	}
	@Override
	public boolean cancelJob(CronJob job) {
		if(isRunning) {
			job.end();
			LOGGER.log(Level.INFO, "Job : "+ job.getId() + " stopped");
			return true;
		}
		if(jobsToRunQueue.remove(job)) {
			LOGGER.log(Level.INFO, "Job : "+ job.getId() + " was removed from scheduler");
			return true;
		}
		LOGGER.log(Level.WARNING, "Job : "+ job.getId() + "not removed from scheduler");
		return false;
	}
	
	@Override
	public int getJobCount() {
		return jobs.size();
	}
	
	@Override
	public boolean isSchedulerFull() {
		int currentJobsCount = getJobCount();
		if(currentJobsCount == CAPACITY) return true;
		return false;
	}

	@Override
	public int getRemSchedulerCapacity() {
		return (CAPACITY - getJobCount());
	}
	
	@Override
	public void shutdown() {
		// TODO: wait till any running job finish and store history
		this.isRunning = false;
		stopAllJobs();
		LOGGER.log(Level.INFO, "Scheduler shutdown");
	}
	private void stopAllJobs() {
        for (CronJob job : jobs) {
            job.end();
        }
	}
	
}
