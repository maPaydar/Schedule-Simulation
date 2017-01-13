package ir.amin.schedule.trigger;

import ir.amin.schedule.entities.Resource;

/**
 * Created by amin on 1/12/17.
 */
public interface Trigger<T> {

    public T trigger(Resource r, int time);
}
