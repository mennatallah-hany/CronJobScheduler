package scheduler;

import job.CronJob;

public class AsyncScheduler extends AbstractScheduler{
	
	public AsyncScheduler(String id) {
		this.id = id;
	}

	@Override
	public void start() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean isSchedulerFull() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public int getJobCount() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getRemSchedulerCapacity() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean addJob(CronJob job) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean cancelJob(CronJob job) {
		// TODO Auto-generated method stub
		return false;
	}
}
