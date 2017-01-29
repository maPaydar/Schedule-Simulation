package ir.amin.schedule.trigger;

import ir.amin.schedule.Config;
import ir.amin.schedule.entities.Job;
import ir.amin.schedule.entities.Period;
import ir.amin.schedule.entities.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

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
            if (r.allocatedJob.getRemainingTime() == 0) {
                //if (time - r.allocatedJob.getRemainingTime() >= mapBurstTime(r.allocatedJob.getBurstTime())) {
                r.allocatedJob.setReturnedTime(time);
                logger.info("\nJob {} \narrival at {}\nstart at {}\nburst time {}\nreturned time {}\n"
                        , r.allocatedJob
                        , r.allocatedJob.getArrivalTime()
                        , r.allocatedJob.getStartTime()
                        , r.allocatedJob.getBurstTime()
                        , r.allocatedJob.getReturnedTime());
                List<Period> ps = r.allocatedJob.runningTimePeriods;
                ps.get(ps.size() - 1).end = time;
                r.finishJob();
                r.setFree(true);
                r.allocatedJob = null;
            } else if (time - r.allocatedJob.getStartTime() == Config.RR_QUANTUM) {
                List<Period> ps = r.allocatedJob.runningTimePeriods;
                ps.get(ps.size() - 1).end = time;
                r.unallocate();
            }
        }
        return null;
    }
}
