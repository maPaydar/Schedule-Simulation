package ir.amin.schedule;

import ir.amin.schedule.algorithm.ScheduleAlgorithm;
import ir.amin.schedule.entities.Job;
import ir.amin.schedule.entities.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

/**
 * Created by amin on 12/25/16.
 */
public class JobScheduler {

    final Logger logger = LoggerFactory.getLogger(JobScheduler.class);
    public List<Job> readyQueue = new LinkedList<>();
    public List<Job> jobs = new ArrayList<>();
    public List<Resource> resources = new ArrayList<>();
    public List<Job> returnedJobs = new ArrayList<>();
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
        algorithm.run(this);
    }


    public void clearJobScheduler() {
        readyQueue.clear();
        jobs.clear();
        returnedJobs.clear();
        resources.clear();
        proccessorTime = 0;
        for (int i = 0; i < jobs.size(); i++) {
            jobs.get(i).runningTimePeriods.clear();
        }
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
            if (j.getRemainingTime() < m && j.getStartTime() == -1) {
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

    public int getFirstUnStrtedJob () {
        for (int i = 0; i < readyQueue.size(); i++) {
            if(readyQueue.get(i).getStartTime() == -1)
                return i;
        }
        return -1;
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


    public void sortReturnedJobs() {
        Collections.sort(this.returnedJobs, (j1, j2) -> {
            return (j2.getArrivalTime() > j1.getArrivalTime()) ? -1 : 1;
        });
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
        j.setStartTime(-1);
        this.readyQueue.add(j);
    }

    public void finishJob(Job j) {
        this.returnedJobs.add(j);
    }

    public void setJobs(List<Job> jobs) {
        this.jobs = jobs;
    }

    public void setResources(List<Resource> resources) {
        this.resources = resources;
    }

    public void setAlgorithm(ScheduleAlgorithm algorithm) {
        this.algorithm = algorithm;
    }

}
