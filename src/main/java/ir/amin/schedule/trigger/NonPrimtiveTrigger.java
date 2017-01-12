package ir.amin.schedule.trigger;

import ir.amin.schedule.Job;

/**
 * Created by amin on 1/12/17.
 */
public class NonPrimtiveTrigger implements Trigger<Job> {

    @Override
    public Job trigger(int time) {
        return null;
    }
}
