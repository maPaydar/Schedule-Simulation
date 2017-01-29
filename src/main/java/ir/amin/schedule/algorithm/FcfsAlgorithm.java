package ir.amin.schedule.algorithm;

import ir.amin.schedule.Config;
import ir.amin.schedule.entities.Job;
import ir.amin.schedule.JobScheduler;
import ir.amin.schedule.entities.Resource;

/**
 * Created by amin on 1/12/17.
 */
public class FcfsAlgorithm implements ScheduleAlgorithm {

    @Override
    public void run(JobScheduler jobScheduler) {
        while (jobScheduler.returnedJobs.size() != Config.JOBS_NUMBER) {
            for (int i = 0; i < jobScheduler.resources.size(); i++) {
                Resource fastestResource = jobScheduler.getFastestFreeResource();
                if (fastestResource != null) {
                    // select a job using an algorithm
                    if (!jobScheduler.readyQueue.isEmpty()) {
                        Job j = jobScheduler.readyQueue.remove(0);
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
