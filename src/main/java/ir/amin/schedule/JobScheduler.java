package ir.amin.schedule;

import ir.amin.schedule.algorithm.ScheduleAlgorithm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by amin on 12/25/16.
 */
public class JobScheduler {

    final Logger logger = LoggerFactory.getLogger(JobScheduler.class);
    public List<Job> readyQueue = new LinkedList<>();
    public List<Job> jobs = new ArrayList<>();
    public List<Resource> resources = new ArrayList<>();
    public List<Resource> returnedJobs = new ArrayList<>();
    public int proccessorTime = 0;
    private ScheduleAlgorithm algorithm;

    public static enum ScheduleType {
        NonPrimptive,
        RR,
        SRTF
    }

    public JobScheduler() {
        // ToDo implement defualt constructor
    }

    public JobScheduler(List<Resource> resources, List<Job> jobs) {
        this.resources = resources;
        this.jobs = jobs;
    }

    public JobScheduler(List<Resource> resources, List<Job> jobs, ScheduleAlgorithm algorithm) {
        this.resources = resources;
        this.jobs = jobs;
        this.algorithm = algorithm;
    }

    public void run() {
        logger.debug("Scheduling start");
        trigger();
        algorithm.run(this);
    }

    public int getShortestJob() {
        int m = Integer.MAX_VALUE;
        int sj = -1;
        for (int i = 0; i < readyQueue.size(); i++) {
            Job j = readyQueue.get(i);
            if (j.getBurstTime() < m) {
                m = j.getBurstTime();
                sj = i;
            }
        }
        return sj;
    }

    public int getLongestJob() {
        int m = Integer.MIN_VALUE;
        int sj = -1;
        for (int i = 0; i < readyQueue.size(); i++) {
            Job j = readyQueue.get(i);
            if (j.getBurstTime() > m) {
                m = j.getBurstTime();
                sj = i;
            }
        }
        return sj;
    }

    public int getShortestRemainJob() {
        int m = Integer.MAX_VALUE;
        int sj = -1;
        for (int i = 0; i < readyQueue.size(); i++) {
            Job j = readyQueue.get(i);
            if (j.getRemainingTime() < m) {
                m = j.getRemainingTime();
                sj = i;
            }
        }
        return sj;
    }

    public void trigger() {
        List<Job> js = new ArrayList<>();
        for (int i = 0; i < jobs.size(); i++) {
            if (jobs.get(i).getArrivalTime() <= proccessorTime) {
                js.add(jobs.get(i));
            }
        }
        readyQueue.addAll(js);
        jobs.removeAll(js);
        for (Resource resource : resources) {
            resource.trigger(proccessorTime);
        }
    }

    public Resource getFastestFreeResource() {
        Resource maxResource = null;
        int maxSpeed = -1;
        for (Resource resource : resources) {
            if (resource.isFree() && resource.getSpeed() >= maxSpeed) {
                maxSpeed = resource.getSpeed();
                maxResource = resource;
            }
        }
        return maxResource;
    }

    public Resource getFastestBusyResource() {
        Resource maxResource = null;
        int maxSpeed = -1;
        for (Resource resource : resources) {
            if (!resource.isFree() && resource.getSpeed() >= maxSpeed) {
                maxSpeed = resource.getSpeed();
                maxResource = resource;
            }
        }
        return maxResource;
    }


    public List<Job> getReadyQueue() {
        return readyQueue;
    }

    public void setReadyQueue(List<Job> readyQueue) {
        this.readyQueue = readyQueue;
    }


    public void addJob(Job j) {
        this.readyQueue.add(j);
    }

    public void backJob(Job j) {
        this.readyQueue.add(j);
    }
}
