package ir.amin.schedule.trigger;

/**
 * Created by amin on 1/12/17.
 */
public interface Trigger<T> {

    public T trigger(int time);
}
