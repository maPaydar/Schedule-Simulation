package ir.amin.schedule.entities;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by amin on 12/25/16.
 */
public class Job implements Cloneable {

    final Logger logger = LoggerFactory.getLogger(Job.class);
    private int arrivalTime;
    private int startTime = -1;
    private int burstTime;
    private int remainingTime;
    private int returnedTime;
    public List<Period> runningTimePeriods = new ArrayList<>();

    public void setArrivalTime(int arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public void setBurstTime(int burstTime) {
        this.burstTime = burstTime;
    }

    public void setRemainingTime(int remainingTime) {
        this.remainingTime = remainingTime;
    }

    public void setStartTime(int startTime) {
        this.startTime = startTime;
    }

    public int getArrivalTime() {
        return arrivalTime;
    }

    public int getBurstTime() {
        return burstTime;
    }

    public int getRemainingTime() {
        return remainingTime;
    }

    public int getStartTime() {
        return startTime;
    }

    public void setReturnedTime(int returnedTime) {
        this.returnedTime = returnedTime;
    }

    public int getReturnedTime() {
        return returnedTime;
    }

    @Override
    public String toString() {
        String str = "\nJob " + super.toString() +
                "\nArrivalTime " + getArrivalTime() +
                "\nStartTime " + getStartTime() +
                "\nBurstTime " + getBurstTime() +
                "\nRemainingTime " + getRemainingTime() +
                "\nReturnTime " + getReturnedTime();
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

