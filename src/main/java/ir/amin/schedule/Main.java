package ir.amin.schedule; /**
 * Created by amin on 12/25/16.
 */

import ir.amin.schedule.algorithm.ScheduleAlgorithm;
import ir.amin.schedule.algorithm.SrtfAlgorithm;

public class Main {

    public static void main(String[] args) throws InterruptedException {

        JobScheduler.ScheduleType scheduleType;
        ScheduleAlgorithm algorithm = new SrtfAlgorithm();

        if (args.length > 1) {
            scheduleType = JobScheduler.ScheduleType.valueOf(args[1]);
        } else {
            scheduleType = JobScheduler.ScheduleType.NonPrimptive;
        }

        ResourceFactory resourceFactory = ResourceFactory.newInstance(scheduleType);
        JobFactory jobFactory = JobFactory.newInstance();
        JobScheduler jobScheduler = new JobScheduler(resourceFactory.getGeneratedResources(), jobFactory.getGeneratedJobs());
        jobScheduler.run();


        /*int delay[] = new int[]{0, 1000, 2000, 3000};
        JobScheduler jobScheduler = new JobScheduler();
        new Thread(jobScheduler).start();
        Random r = new Random(20);
        for (int i = 0; i < 5; i++) {
            Job j1 = new Job();
            int btime = r.nextInt(10) + 1;
            j1.setBurstTime(btime);
            j1.setRemainingTime(btime);
            Thread.sleep(r.nextInt(4));
            jobScheduler.addJob(j1);
        }
        *//*for (int i = 0; i < 20; i++) {
            Thread.sleep(i*300);
            System.out.println("asdf");
            jobScheduler.addJob(new Job());
            jobScheduler.addJob(new Job());
            jobScheduler.addJob(new Job());
        }*/
    }
}
