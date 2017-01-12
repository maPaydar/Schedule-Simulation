package ir.amin.schedule.algorithm;

import ir.amin.schedule.Job;
import ir.amin.schedule.JobScheduler;
import ir.amin.schedule.Resource;

/**
 * Created by amin on 1/12/17.
 */
public class SjfAlgorithm implements ScheduleAlgorithm {


    @Override
    public void run(JobScheduler jobScheduler) {
        for (int i = 0; i < jobScheduler.resources.size(); i++) {
            Resource fastestResource = jobScheduler.getFastestFreeResource();
            if (fastestResource != null) {
                if (!jobScheduler.readyQueue.isEmpty()) {
                    Job j2 = jobScheduler.readyQueue.remove(jobScheduler.getShortestJob());
                    j2.setStartTime(jobScheduler.proccessorTime);
                    fastestResource.allocate(j2);
                }
            }
        }
    }
}
