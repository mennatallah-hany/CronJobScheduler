import java.util.PriorityQueue;

public class Scheduler {
	String id;
	static final int CAPACITY = 10;
	private boolean isRunning = false;
	private PriorityQueue<CronJob> jobQueue ;
	
	public Scheduler(String id) {
		this.id = id;
		jobQueue = new PriorityQueue<>(new CronJobComparator());
		
	}
	
	public void start() {
		this.isRunning = true;
		System.out.println("Scheduler started");
		while(isRunning) {
			if(!jobQueue.isEmpty()) {
				CronJob nextJobToRun = jobQueue.peek();
				if(nextJobToRun.shouldRunNow()) {
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
	
	public void shutdown() {
		// TODO: wait till any running job finish and store history
		this.isRunning = false;
		System.out.println("Scheduler shutdown");

	}
	
	private boolean isFullScheduler() {
		int jobQueueSize = jobQueue.size();
		if(jobQueueSize == CAPACITY) return true;
		return false;
	} 
	/*
	 *  true :  job added to scheduler successfully
	 *  false : job failed to add to scheduler
	 */
	public boolean addJob(CronJob job) {
		if(job == null) {
			System.out.println("Err: Invalid job to add to scheduler");
			return false;
		}
		if(isFullScheduler()) {
			System.out.println("Err: Scheduler is full, cannot add extra jobs");
			return false;
		}
		job.initializeForScheduler();
		if(jobQueue.add(job)) {
			System.out.println("Job : "+ job.getId() + " was added successfully to scheduler");
			return true;
		}
		return false;
	}
	/*
	 *  true :  job cancelled from scheduler
	 *  false : failed to remove job from scheduler
	 */
	public boolean cancelJob(CronJob job) {
		if(jobQueue.remove(job)) {
			System.out.println("Job : "+ job.getId() + " was removed from scheduler");
			return true;
		}
		return false;
	}
	
	public int getJobCount() {
		if(!isRunning) {
			System.out.println("Err: Scheduler is not working, no running jobs");
			return 0;
		}
		return jobQueue.size();
	}
	
	public int getRemSchedulerCapacity() {
		return (CAPACITY - jobQueue.size());
	}
	
	private void executeJobAsync(CronJob nextJobToRun) {
		Thread thread = new Thread(new Runnable() {
		    public void run()
		    {
		    	nextJobToRun.execute();
		    }});  
		thread.start();
	}
}
