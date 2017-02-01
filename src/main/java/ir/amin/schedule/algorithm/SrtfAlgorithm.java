package ir.amin.schedule.algorithm;

import ir.amin.schedule.Config;
import ir.amin.schedule.JobScheduler;
import ir.amin.schedule.entities.Job;
import ir.amin.schedule.entities.Period;
import ir.amin.schedule.entities.Resource;

import java.util.List;

/**
 * Created by amin on 1/12/17.
 */
public class SrtfAlgorithm implements ScheduleAlgorithm {

    @Override
    public void run(JobScheduler jobScheduler) {
        while (jobScheduler.returnedJobs.size() != Config.JOBS_NUMBER) {
            jobScheduler.trigger();
            for (int i = 0; i < jobScheduler.resources.size(); i++) {
                Resource fastestResource = jobScheduler.getFastestFreeResource();
                if (fastestResource != null) {
                    if (!jobScheduler.readyQueue.isEmpty()) {
                        Job j2 = jobScheduler.readyQueue.remove(jobScheduler.getShortestRemainJob());
                        j2.setStartTime(jobScheduler.proccessorTime);
                        j2.runningTimePeriods.add(new Period(jobScheduler.proccessorTime, -1));
                        fastestResource.allocate(j2);
                    }
                } else {
                    if (!jobScheduler.readyQueue.isEmpty()) {
                        for (int r = 0; r < jobScheduler.resources.size(); r++) {
                            Resource rr = jobScheduler.resources.get(r);
                            int minRemainJob = jobScheduler.getShortestRemainJob();
                            if (rr.allocatedJob.getRemainingTime() > jobScheduler.readyQueue.get(minRemainJob).getRemainingTime()) {
                                Job j2 = jobScheduler.readyQueue.remove(minRemainJob);
                                j2.setStartTime(jobScheduler.proccessorTime);
                                List<Period> ps = rr.allocatedJob.runningTimePeriods;
                                ps.get(ps.size() - 1).end = jobScheduler.proccessorTime;
                                jobScheduler.backJob(rr.allocatedJob);
                                rr.allocate(j2);
                                rr.allocatedJob.runningTimePeriods.add(new Period(jobScheduler.proccessorTime, -1));
                            }
                        }
                    }
                }
            }
            jobScheduler.proccessorTime++;
        }
    }
}
