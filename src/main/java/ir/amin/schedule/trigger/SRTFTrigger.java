package ir.amin.schedule.trigger;

import ir.amin.schedule.entities.Job;
import ir.amin.schedule.entities.Resource;

/**
 * Created by amin on 1/12/17.
 */
public class SRTFTrigger implements Trigger<Job> {

    @Override
    public Job trigger(Resource r, int time) {
        return null;
    }
}
