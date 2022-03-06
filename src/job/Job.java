package job;
import java.util.TimerTask;

public class Job {
	String id;
	TimerTask task;
	
	public Job(String id, TimerTask task) {
		this.id = id;
		this.task = task;
	}
	public String getId() {
		return this.id;
	}
	public void execute() {
		task.run();
	}
}
