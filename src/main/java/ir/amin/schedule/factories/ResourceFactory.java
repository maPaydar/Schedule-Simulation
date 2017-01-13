package ir.amin.schedule.factories;

import ir.amin.schedule.Config;
import ir.amin.schedule.JobScheduler;
import ir.amin.schedule.entities.Resource;
import ir.amin.schedule.trigger.NonPrimtiveTrigger;
import ir.amin.schedule.trigger.RRTrigger;
import ir.amin.schedule.trigger.SRTFTrigger;
import ir.amin.schedule.trigger.Trigger;
import ir.amin.schedule.util.BasicRandomize;
import ir.amin.schedule.util.Randomize;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by amin on 1/12/17.
 */
public class ResourceFactory {

    private static ResourceFactory resourceFactory;
    private Randomize randomize;
    private List<Resource> generatedResources;
    private JobScheduler.ScheduleType scheduleType;

    public static ResourceFactory newInstance(JobScheduler.ScheduleType scheduleType) {

        if (resourceFactory == null) {
            resourceFactory = new ResourceFactory(scheduleType, new BasicRandomize());
        }
        return resourceFactory;
    }

    public static ResourceFactory newInstance(JobScheduler.ScheduleType scheduleType, Randomize randomize) {

        if (resourceFactory == null) {
            resourceFactory = new ResourceFactory(scheduleType, randomize);
        }
        return resourceFactory;
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
            Resource r = new Resource(trigger, randomize.getRandomizedResourseSpeed());
            rs.add(r);
        }
        return rs;
    }

    public List<Resource> getGeneratedResources() {
        return generatedResources;
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
