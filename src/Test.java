import java.util.Calendar;
import java.util.TimerTask;
import Helpers.TimeInterval;

public class Test {

	public static void main(String[] args) {
		Scheduler sched = new Scheduler("sched 1");
		TimeInterval initDelay1 = new TimeInterval(0, 0, 0, 0, 0, 0);
		TimeInterval initDelay2 = new TimeInterval(0, 0, 0, 0, 0, 5);
		TimeInterval interval1 = new TimeInterval(0, 0, 0, 0, 0, 10);
		TimeInterval interval2 = new TimeInterval(0, 0, 0, 0, 0, 5);

		TimerTask task1 = new TimerTask() {

			@Override
			public void run() {
				System.out.println("Task 1 running at" + Calendar.getInstance().getTime());
			}

		};
		TimerTask task2 = new TimerTask() {

			@Override
			public void run() {
				System.out.println("Task 2 running at" + Calendar.getInstance().getTime());
			}

		};
		CronJob job1 = new CronJob("job 1", task1, initDelay1 , interval1);
		CronJob job2 = new CronJob("job 2", task2, initDelay2 , interval2);

		sched.addJob(job1);
		sched.addJob(job2);

		Thread thread = new Thread(new Runnable() {
		    public void run()
		    {
		    	sched.start();
		    }});  
		thread.start();

	}

}
