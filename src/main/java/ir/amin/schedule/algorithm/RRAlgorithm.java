package ir.amin.schedule.algorithm;

import ir.amin.schedule.Job;
import ir.amin.schedule.JobScheduler;
import ir.amin.schedule.Resource;

/**
 * Created by amin on 1/12/17.
 */
public class RRAlgorithm implements ScheduleAlgorithm {


    @Override
    public void run(JobScheduler jobScheduler) {
        while (!jobScheduler.jobs.isEmpty() && !jobScheduler.readyQueue.isEmpty()) {
            jobScheduler.proccessorTime++;
            for (int i = 0; i < jobScheduler.resources.size(); i++) {
                Resource fastestResource = jobScheduler.getFastestFreeResource();
                if (fastestResource != null) {
                    if (!jobScheduler.readyQueue.isEmpty()) {
                        Job j = jobScheduler.readyQueue.remove(0);
                        if (j.getStartTime() == -1)
                            j.setStartTime(jobScheduler.proccessorTime);
                        fastestResource.reallocate(j);
                    }
                }
            }
            jobScheduler.trigger();
        }
    }
}
