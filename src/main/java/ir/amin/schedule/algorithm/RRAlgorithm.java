package ir.amin.schedule.algorithm;

import ir.amin.schedule.Config;
import ir.amin.schedule.JobScheduler;
import ir.amin.schedule.entities.Job;
import ir.amin.schedule.entities.Period;
import ir.amin.schedule.entities.Resource;

/**
 * Created by amin on 1/12/17.
 */
public class RRAlgorithm implements ScheduleAlgorithm {


    @Override
    public void run(JobScheduler jobScheduler) {
        while (jobScheduler.returnedJobs.size() != Config.JOBS_NUMBER) {
            jobScheduler.trigger();
            for (int i = 0; i < jobScheduler.resources.size(); i++) {
                Resource fastestResource = jobScheduler.getFastestFreeResource();
                if (fastestResource != null) {
                    if (!jobScheduler.readyQueue.isEmpty()) {
                        Job j = jobScheduler.readyQueue.remove(0);
                        //Job j = jobScheduler.readyQueue.remove(jobScheduler.getFirstUnStrtedJob());
                        if (j.getStartTime() == -1) {
                            j.setStartTime(jobScheduler.proccessorTime);
                            j.runningTimePeriods.add(new Period(jobScheduler.proccessorTime, -1));
                            fastestResource.reallocate(j);
                        }
                    }
                }
            }
            jobScheduler.proccessorTime++;
        }
    }
}
