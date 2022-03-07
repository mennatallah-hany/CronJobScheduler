package scheduler;
import java.util.PriorityQueue;
import java.util.logging.Level;

import job.CronJob;
import job.CronJobComparator;

public class PriorityScheduler extends AbstractScheduler{
	protected PriorityQueue<CronJob> jobQueue ;
	
	public PriorityScheduler(String id) {
		this.id = id;
		jobQueue = new PriorityQueue<>(new CronJobComparator());
	}
	
	@Override
	public void start() {
		this.isRunning = true;
		LOGGER.log(Level.INFO, "Scheduler started");
		while(isRunning) {
			if(!jobQueue.isEmpty()) {
				CronJob nextJobToRun = jobQueue.peek();
				if(nextJobToRun.shouldRunNow()) {
					LOGGER.log(Level.FINER, "Job " + nextJobToRun.getId() + " should run now");
					executeJobAsync(nextJobToRun);
					jobQueue.poll();
					nextJobToRun.updateScheduledTime();
					jobQueue.add(nextJobToRun);
				}
			}
//			try {
//				Thread.sleep(1000); // 1s to avoid resource exhaustion
//			} catch (InterruptedException e) {
//				e.printStackTrace();
//			}
//			
		}
	}
	
	

	/*
	 *  true :  job added to scheduler successfully
	 *  false : job failed to add to scheduler
	 */
	public boolean addJob(CronJob job) {
		if(job == null) {
			LOGGER.log(Level.WARNING, "Invalid job, Can not add to scheduler");
			return false;
		}
		if(isSchedulerFull()) {
			LOGGER.log(Level.WARNING, "Scheduler is full, cannot add extra jobs");
			return false;
		}
		job.initializeForScheduler();
		if(jobQueue.add(job)) {
			LOGGER.log(Level.INFO, "Job : "+ job.getId() + " was added successfully to scheduler");
			return true;
		}
		return false;
	}
	
	private void executeJobAsync(CronJob nextJobToRun) {
		Thread thread = new Thread(new Runnable() {
		    public void run()
		    {
		    	nextJobToRun.execute();
		    }});  
		thread.start();
	}

	/*
	 *  true :  job cancelled from scheduler
	 *  false : failed to remove job from scheduler
	 */
	@Override
	public boolean cancelJob(CronJob job) {
		if(job != null) job.end();
		if(jobQueue.remove(job)) {
			LOGGER.log(Level.INFO, "Job : "+ job.getId() + " was removed from scheduler");
			return true;
		}
		return false;
	}
	
	@Override
	public boolean isSchedulerFull() {
		int jobQueueSize = getJobCount();
		if(jobQueueSize == CAPACITY) return true;
		return false;
	} 
	
	@Override
	public int getJobCount() {
		return jobQueue.size();
	}
	
	@Override
	public int getRemSchedulerCapacity() {
		return (CAPACITY - getJobCount());
	}
}
