package ir.amin.schedule;
/**
 * Created by amin on 12/25/16.
 */

import ir.amin.schedule.algorithm.*;
import ir.amin.schedule.entities.Job;
import ir.amin.schedule.entities.Resource;
import ir.amin.schedule.factories.JobFactory;
import ir.amin.schedule.factories.ResourceFactory;
import ir.amin.schedule.trigger.NonPrimtiveTrigger;
import ir.amin.schedule.trigger.RRTrigger;
import ir.amin.schedule.trigger.SRTFTrigger;
import ir.amin.schedule.util.TimeUtils;

import java.util.List;

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
        JobScheduler jobScheduler = new JobScheduler();
        ResourceFactory resourceFactory = ResourceFactory.newInstance(scheduleType, jobScheduler);
        JobFactory jobFactory = JobFactory.newInstance();
        List<Job> jobs = jobFactory.getGeneratedJobs();
        List<Resource> resources = resourceFactory.getGeneratedResources();
        //System.out.println(jobs);

        System.out.format("%20s%20s%20s%20s%20s%20s%20s%20s%20s%20s", "fcfs turn time", "fcfs waiting time","sjf turn time", "sjf waiting time","maxmin turn time", "maxmin waiting time","rr turn time", "rr waiting time","srtf turn time", "srtf waiting time");
        System.out.println();

        resourceFactory.setScheduleType(new NonPrimtiveTrigger());
        jobScheduler.restart();
        jobScheduler.setJobs(jobFactory.getGeneratedJobs());
        jobScheduler.setResources(resourceFactory.getGeneratedResources());
        jobScheduler.setAlgorithm(new FcfsAlgorithm());
        jobScheduler.run();
        jobScheduler.sortReturnedJobs();
        System.out.format("%20f", TimeUtils.computeTurnAroundTime(jobScheduler.returnedJobs));
        System.out.format("%20f", TimeUtils.computeNonPrimetiveAverageWatingTime(jobScheduler.returnedJobs));

        resourceFactory.setScheduleType(new NonPrimtiveTrigger());
        jobScheduler.restart();
        jobScheduler.setJobs(jobFactory.getGeneratedJobs());
        jobScheduler.setResources(resourceFactory.getGeneratedResources());
        jobScheduler.setAlgorithm(new SjfAlgorithm());
        jobScheduler.run();
        jobScheduler.sortReturnedJobs();
        System.out.format("%20f", TimeUtils.computeTurnAroundTime(jobScheduler.returnedJobs));
        System.out.format("%20f", TimeUtils.computeNonPrimetiveAverageWatingTime(jobScheduler.returnedJobs));

        resourceFactory.setScheduleType(new NonPrimtiveTrigger());
        jobScheduler.restart();
        jobScheduler.setJobs(jobFactory.getGeneratedJobs());
        jobScheduler.setResources(resourceFactory.getGeneratedResources());
        jobScheduler.setAlgorithm(new MaxMinAlgorithm());
        jobScheduler.run();
        jobScheduler.sortReturnedJobs();
        System.out.format("%20f", TimeUtils.computeTurnAroundTime(jobScheduler.returnedJobs));
        System.out.format("%20f", TimeUtils.computeNonPrimetiveAverageWatingTime(jobScheduler.returnedJobs));

        Config.RR_QUANTUM = 2;
        resourceFactory.setScheduleType(new RRTrigger());
        jobScheduler.restart();
        jobScheduler.setJobs(jobFactory.getGeneratedJobs());
        jobScheduler.setResources(resourceFactory.getGeneratedResources());
        jobScheduler.setAlgorithm(new RRAlgorithm());
        for (int i = 0; i < jobs.size(); i++) {
            jobs.get(i).runningTimePeriods.clear();
        }
        jobScheduler.run();
        jobScheduler.sortReturnedJobs();
        System.out.format("%20f", TimeUtils.computeTurnAroundTime(jobScheduler.returnedJobs));
        System.out.format("%20f", TimeUtils.computePrimetiveAverageWatingTime(jobScheduler.returnedJobs));
        /*
        Config.RR_QUANTUM = 4;
        resourceFactory.setScheduleType(new RRTrigger());
        jobScheduler.restart();
        jobScheduler.setJobs(jobFactory.getGeneratedJobs());
        jobScheduler.setResources(resourceFactory.getGeneratedResources());
        jobScheduler.setAlgorithm(new RRAlgorithm());
        for (int i = 0; i < jobs.size(); i++) {
            jobs.get(i).runningTimePeriods.clear();
        }
        jobScheduler.run();
        jobScheduler.sortReturnedJobs();
        System.out.println("RR FINISHED");
        System.out.println("Turn around time = " + TimeUtils.computeTurnAroundTime(jobScheduler.returnedJobs));
        System.out.println("Wating time = " + TimeUtils.computePrimetiveAverageWatingTime(jobScheduler.returnedJobs));

        Config.RR_QUANTUM = 6;
        resourceFactory.setScheduleType(new RRTrigger());
        jobScheduler.restart();
        jobScheduler.setJobs(jobFactory.getGeneratedJobs());
        jobScheduler.setResources(resourceFactory.getGeneratedResources());
        jobScheduler.setAlgorithm(new RRAlgorithm());
        for (int i = 0; i < jobs.size(); i++) {
            jobs.get(i).runningTimePeriods.clear();
        }
        jobScheduler.run();
        jobScheduler.sortReturnedJobs();
        System.out.println("RR FINISHED");
        System.out.println("Turn around time = " + TimeUtils.computeTurnAroundTime(jobScheduler.returnedJobs));
        System.out.println("Wating time = " + TimeUtils.computePrimetiveAverageWatingTime(jobScheduler.returnedJobs));

        Config.RR_QUANTUM = 8;
        resourceFactory.setScheduleType(new RRTrigger());
        jobScheduler.restart();
        jobScheduler.setJobs(jobFactory.getGeneratedJobs());
        jobScheduler.setResources(resourceFactory.getGeneratedResources());
        jobScheduler.setAlgorithm(new RRAlgorithm());
        for (int i = 0; i < jobs.size(); i++) {
            jobs.get(i).runningTimePeriods.clear();
        }
        jobScheduler.run();
        jobScheduler.sortReturnedJobs();
        System.out.println("RR FINISHED");
        System.out.println("Turn around time = " + TimeUtils.computeTurnAroundTime(jobScheduler.returnedJobs));
        System.out.println("Wating time = " + TimeUtils.computePrimetiveAverageWatingTime(jobScheduler.returnedJobs));
        Config.RR_QUANTUM = 10;
        resourceFactory.setScheduleType(new RRTrigger());
        jobScheduler.restart();
        jobScheduler.setJobs(jobFactory.getGeneratedJobs());
        jobScheduler.setResources(resourceFactory.getGeneratedResources());
        jobScheduler.setAlgorithm(new RRAlgorithm());
        for (int i = 0; i < jobs.size(); i++) {
            jobs.get(i).runningTimePeriods.clear();
        }
        jobScheduler.run();
        jobScheduler.sortReturnedJobs();
        System.out.println("RR FINISHED");
        System.out.println("Turn around time = " + TimeUtils.computeTurnAroundTime(jobScheduler.returnedJobs));
        System.out.println("Wating time = " + TimeUtils.computePrimetiveAverageWatingTime(jobScheduler.returnedJobs));
        */

        resourceFactory.setScheduleType(new SRTFTrigger());
        jobScheduler.restart();
        jobScheduler.setJobs(jobFactory.getGeneratedJobs());
        jobScheduler.setResources(resourceFactory.getGeneratedResources());
        jobScheduler.setAlgorithm(new SrtfAlgorithm());
        for (int i = 0; i < jobs.size(); i++) {
            jobs.get(i).runningTimePeriods.clear();
        }
        jobScheduler.run();
        jobScheduler.sortReturnedJobs();
        System.out.format("%20f", TimeUtils.computeTurnAroundTime(jobScheduler.returnedJobs));
        System.out.format("%20f", TimeUtils.computePrimetiveAverageWatingTime(jobScheduler.returnedJobs));
    }
}
