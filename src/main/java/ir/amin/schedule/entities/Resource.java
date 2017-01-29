package ir.amin.schedule.entities;

import ir.amin.schedule.JobScheduler;
import ir.amin.schedule.trigger.Trigger;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by amin on 12/25/16.
 */
public class Resource implements Cloneable {

    private JobScheduler jobScheduler;
    private final Logger logger = LoggerFactory.getLogger(Resource.class);
    private int speed;
    private boolean isFree = true;
    public Job allocatedJob;
    private Trigger<Job> triggerObject;

    public Resource(Trigger triggerObject, int speed) {
        this.triggerObject = triggerObject;
        this.speed = speed;
    }

    public Resource(JobScheduler jobScheduler, int speed) {
        this.jobScheduler = jobScheduler;
        this.speed = speed;
    }

    public Resource(JobScheduler jobScheduler, Trigger triggerObject, int speed) {
        this.jobScheduler = jobScheduler;
        this.triggerObject = triggerObject;
        this.speed = speed;
    }

    public void setFree(boolean free) {
        isFree = free;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public int getSpeed() {
        return speed;
    }

    public boolean isFree() {
        return isFree;
    }

    public void allocate(Job j) {
        this.setFree(false);
        this.allocatedJob = j;
    }

    public void unallocate() {
        setFree(true);
        logger.info("\nJob {} back to ready queue"
                , allocatedJob);
        jobScheduler.backJob(allocatedJob);
    }

    public void finishJob() {
        jobScheduler.finishJob(allocatedJob);
    }

    public Job trigger(int time) {
        return triggerObject.trigger(this, time);
    }

    public void setTriggerObject(Trigger<Job> triggerObject) {
        this.triggerObject = triggerObject;
    }

    public Job getAllocatedJob() {
        return allocatedJob;
    }

    public int mapBurstTime(int burstTime) {
        return speed / burstTime + 1;
    }

    public Job reallocate(Job j) {
        Job jt = allocatedJob;
        this.setFree(false);
        allocatedJob = j;
        return jt;
    }

    @Override
    public String toString() {
        String str = "Resource speed " + speed + "\n";
        return str;
    }

    public Object clone() {
        try {
            return super.clone();
        } catch (CloneNotSupportedException var2) {
            throw new InternalError(var2);
        }
    }
}
