package ir.amin.schedule.trigger;

import ir.amin.schedule.entities.Job;
import ir.amin.schedule.entities.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by amin on 1/12/17.
 */
public class NonPrimtiveTrigger implements Trigger<Job> {

    private final Logger logger = LoggerFactory.getLogger(NonPrimtiveTrigger.class);

    @Override
    public Job trigger(Resource r, int time) {
        Job sj = null;
        if (r.allocatedJob != null) {
            // ToDo update remaning time
            r.allocatedJob.setRemainingTime(r.allocatedJob.getReturnedTime());
            // if (allocatedJob.getRemainingTime() <= 0)
            if (time - r.allocatedJob.getStartTime() >= r.mapBurstTime(r.allocatedJob.getBurstTime())) {
                r.allocatedJob.setReturnedTime(time);
                r.setFree(true);
                logger.info("\narrival at {}\nstart at {}\nburst time {}\nreturned time {}\n"
                        , r.allocatedJob.getArrivalTime()
                        , r.allocatedJob.getStartTime()
                        , r.mapBurstTime(r.allocatedJob.getBurstTime())
                        , r.allocatedJob.getReturnedTime());
                sj = r.allocatedJob;
                r.allocatedJob = null;
            }
        }
        return sj;
    }
}
