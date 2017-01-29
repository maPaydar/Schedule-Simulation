package ir.amin.schedule.entities;

/**
 * Created by amin on 1/28/17.
 */
public class Period {

    public int start;
    public int end;

    public Period(int start, int end) {
        this.start = start;
        this.end = end;
    }

    @Override
    public String toString() {
        return "{" + start + ", " + end + "}";
    }
}
