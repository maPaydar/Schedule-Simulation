package ir.amin.schedule.trigger;

import ir.amin.schedule.entities.Job;
import ir.amin.schedule.entities.Period;
import ir.amin.schedule.entities.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * Created by amin on 1/12/17.
 */
public class SRTFTrigger implements Trigger<Job> {

    private final Logger logger = LoggerFactory.getLogger(SRTFTrigger.class);

    @Override
    public Job trigger(Resource r, int time) {
        Job sj = null;
        if (r.allocatedJob != null) {
            r.allocatedJob.setRemainingTime(r.allocatedJob.getRemainingTime() - 1);
            if (r.allocatedJob.getRemainingTime() == 0) {
                //if (time - r.allocatedJob.lastExecTime >= r.mapBurstTime(r.allocatedJob.getBurstTime())) {
                r.allocatedJob.setReturnedTime(time);
                r.setFree(true);
                logger.info("\narrival at {}\nstart at {}\nburst time {}\nreturned time {}\n"
                        , r.allocatedJob.getArrivalTime()
                        , r.allocatedJob.getStartTime()
                        , r.mapBurstTime(r.allocatedJob.getBurstTime())
                        , r.allocatedJob.getReturnedTime());
                List<Period> ps = r.allocatedJob.runningTimePeriods;
                ps.get(ps.size() - 1).end = time;
                r.finishJob();
            }
        }
        return sj;
    }
}
