package scheduler;

import enums.SchedulerType;

public class SchedulerFactory {
	   public AbstractScheduler getScheduler(SchedulerType schedulerType, String id){	
		      if(schedulerType == SchedulerType.PRIORITY_SCHEDULER){
		         return new PriorityScheduler(id);
		         
		      } else if(schedulerType == SchedulerType.ASYNC_SCHEDULER){
		         return new AsyncScheduler(id);
		         
		      } else if(schedulerType == SchedulerType.OBSERVER_SCHEDULER){
		         return new ObserverScheduler(id);
		      }
		      //it is the default scheduler
		      return new PriorityScheduler(id);
	   }
}
