package ir.amin.schedule; /**
 * Created by amin on 12/25/16.
 */

import ir.amin.schedule.algorithm.FcfsAlgorithm;
import ir.amin.schedule.algorithm.ScheduleAlgorithm;
import ir.amin.schedule.algorithm.SrtfAlgorithm;
import ir.amin.schedule.factories.JobFactory;
import ir.amin.schedule.factories.ResourceFactory;

public class Main {

    public static void main(String[] args) throws InterruptedException {
        JobScheduler.ScheduleType scheduleType;
        ScheduleAlgorithm algorithm = new SrtfAlgorithm();

        if (args.length > 1) {
            scheduleType = JobScheduler.ScheduleType.valueOf(args[1]);
        } else {
            scheduleType = JobScheduler.ScheduleType.NonPrimptive;
            algorithm = new FcfsAlgorithm();
        }

        ResourceFactory resourceFactory = ResourceFactory.newInstance(scheduleType);
        JobFactory jobFactory = JobFactory.newInstance();
        JobScheduler jobScheduler = new JobScheduler(resourceFactory.getGeneratedResources(), jobFactory.getGeneratedJobs(), algorithm);
        jobScheduler.run();

    }
}
