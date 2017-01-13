package ir.amin.schedule.algorithm;

import ir.amin.schedule.JobScheduler;

/**
 * Created by amin on 1/12/17.
 */
public class SrtfAlgorithm implements ScheduleAlgorithm {

    // Todo implement srtf
    @Override
    public void run(JobScheduler jobScheduler) {
        while (!jobScheduler.jobs.isEmpty() && !jobScheduler.readyQueue.isEmpty()) {

            jobScheduler.proccessorTime++;
            jobScheduler.trigger();
        }
    }
}
