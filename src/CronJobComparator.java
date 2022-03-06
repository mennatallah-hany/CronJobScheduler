import java.util.Comparator;

public class CronJobComparator implements Comparator<CronJob>{

	@Override
	public int compare(CronJob j1, CronJob j2) {
        return j1.getNextRunScheduledTime().compareTo(j2.getNextRunScheduledTime());
	}

}
