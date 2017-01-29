package ir.amin.schedule.algorithm;

import ir.amin.schedule.Config;
import ir.amin.schedule.entities.Job;
import ir.amin.schedule.JobScheduler;
import ir.amin.schedule.entities.Resource;

/**
 * Created by amin on 1/12/17.
 */
public class MaxMinAlgorithm implements ScheduleAlgorithm {


    @Override
    public void run(JobScheduler jobScheduler) {
        while (jobScheduler.returnedJobs.size() != Config.JOBS_NUMBER) {
            for (int i = 0; i < jobScheduler.resources.size(); i++) {
                Resource fastestResource = jobScheduler.getFastestFreeResource();
                if (fastestResource != null) {
                    if (!jobScheduler.readyQueue.isEmpty()) {
                        Job j = jobScheduler.readyQueue.remove(jobScheduler.getLongestJob());
                        j.setStartTime(jobScheduler.proccessorTime);
                        fastestResource.allocate(j);
                    }
                }
            }
            jobScheduler.proccessorTime++;
            jobScheduler.trigger();
        }
    }
}
