package ir.amin.schedule.factories;

import ir.amin.schedule.Config;
import ir.amin.schedule.JobScheduler;
import ir.amin.schedule.entities.Job;
import ir.amin.schedule.entities.Resource;
import ir.amin.schedule.trigger.NonPrimtiveTrigger;
import ir.amin.schedule.trigger.RRTrigger;
import ir.amin.schedule.trigger.SRTFTrigger;
import ir.amin.schedule.trigger.Trigger;
import ir.amin.schedule.util.BasicRandomize;
import ir.amin.schedule.util.Randomize;

import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by amin on 1/12/17.
 */
public class ResourceFactory {

    private static ResourceFactory resourceFactory;
    private Randomize randomize;
    private List<Resource> generatedResources;
    private JobScheduler.ScheduleType scheduleType;
    private JobScheduler jobScheduler;

    public static ResourceFactory newInstance(JobScheduler.ScheduleType scheduleType) {

        if (resourceFactory == null) {
            resourceFactory = new ResourceFactory(scheduleType, new BasicRandomize());
        }
        return resourceFactory;
    }

    public static ResourceFactory newInstance(JobScheduler.ScheduleType scheduleType, JobScheduler jobScheduler) {

        if (resourceFactory == null) {
            resourceFactory = new ResourceFactory(scheduleType, new BasicRandomize(), jobScheduler);
        }
        return resourceFactory;
    }

    public static ResourceFactory newInstance(JobScheduler.ScheduleType scheduleType, Randomize randomize) {

        if (resourceFactory == null) {
            resourceFactory = new ResourceFactory(scheduleType, randomize);
        }
        return resourceFactory;
    }

    private ResourceFactory(JobScheduler.ScheduleType scheduleType, Randomize randomize, JobScheduler jobScheduler) {
        this.randomize = randomize;
        this.scheduleType = scheduleType;
        this.jobScheduler = jobScheduler;
        this.generatedResources = generatedResources();
    }

    private ResourceFactory(JobScheduler.ScheduleType scheduleType, Randomize randomize) {
        this.randomize = randomize;
        this.scheduleType = scheduleType;
        this.generatedResources = generatedResources();
    }

    public List<Resource> generatedResources() {
        List<Resource> rs = new ArrayList<>();
        Trigger trigger = null;
        switch (scheduleType) {
            case NonPrimptive:
                trigger = new NonPrimtiveTrigger();
                break;
            case RR:
                trigger = new RRTrigger();
                break;
            case SRTF:
                trigger = new SRTFTrigger();
                break;
        }
        for (int i = 0; i < Config.RESOURCES_NUMBER; i++) {
            Resource r = new Resource(this.jobScheduler, trigger, randomize.getRandomizedResourseSpeed());
            rs.add(r);
        }
        Collections.sort(rs, ((r1, r2) -> {
            return r2.getSpeed() < r1.getSpeed() ? -1 : 1;
        }));
        return rs;
    }

    public List<Resource> getGeneratedResources() {
        return cloneList(generatedResources);
    }

    public void setScheduleType(Trigger<Job> trigger) {
        this.scheduleType = scheduleType;
        for (int i = 0; i < Config.RESOURCES_NUMBER; i++) {
            generatedResources.get(i).setTriggerObject(trigger);
        }
    }

    public List<Resource> cloneList(List<Resource> list) {
        List<Resource> clone = new ArrayList<Resource>(list.size());
        for (Resource item : list) clone.add((Resource) item.clone());
        return clone;
    }

    @Override
    public String toString() {
        String str = "";
        for (Resource r : generatedResources) {
            str += r.toString();
        }
        return str;
    }
}
