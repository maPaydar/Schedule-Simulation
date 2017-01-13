package ir.amin.schedule.trigger;

import ir.amin.schedule.Config;
import ir.amin.schedule.entities.Job;
import ir.amin.schedule.entities.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by amin on 1/12/17.
 */
public class RRTrigger implements Trigger<Job> {

    private final Logger logger = LoggerFactory.getLogger(RRTrigger.class);

    @Override
    public Job trigger(Resource r, int time) {
        if (r.allocatedJob != null) {
            // ToDo update remaning time
            r.allocatedJob.setRemainingTime(r.allocatedJob.getRemainingTime() - 1);
            logger.info("\nJob {} \narrival at {}\nstart at {}\nburst time {}\nremaining time {}\n"
                    , r.allocatedJob
                    , r.allocatedJob.getArrivalTime()
                    , r.allocatedJob.getStartTime()
                    , r.allocatedJob.getBurstTime()
                    , r.allocatedJob.getRemainingTime());
            if (time - r.allocatedJob.lastExecTime >= Config.RR_QUANTUM) {
                r.unallocate();
            } else if (r.allocatedJob.getRemainingTime() <= 0) {
                //if (time - r.allocatedJob.getRemainingTime() >= mapBurstTime(r.allocatedJob.getBurstTime())) {
                r.allocatedJob.setReturnedTime(time);
                r.setFree(true);
                logger.info("\nJob {} \narrival at {}\nstart at {}\nburst time {}\nreturned time {}\n"
                        , r.allocatedJob
                        , r.allocatedJob.getArrivalTime()
                        , r.allocatedJob.getStartTime()
                        , r.allocatedJob.getBurstTime()
                        , r.allocatedJob.getReturnedTime());
                r.allocatedJob = null;
                return r.allocatedJob;
            }
        }
        return null;
    }
}
