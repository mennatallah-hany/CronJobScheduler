package scheduler;
import java.util.PriorityQueue;
import java.util.logging.Level;

import job.CronJob;
import job.CronJobComparator;

public class ObserverScheduler extends AbstractScheduler{
	protected PriorityQueue<CronJob> jobQueue ;

	public ObserverScheduler(String id) {
		this.id = id;
		jobQueue = new PriorityQueue<>(new CronJobComparator());
	}

	@Override
	public void start() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean addJob(CronJob job) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean cancelJob(CronJob job) {
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
