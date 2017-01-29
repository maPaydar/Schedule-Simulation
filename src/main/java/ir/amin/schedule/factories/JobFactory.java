package ir.amin.schedule.factories;

import ir.amin.schedule.Config;
import ir.amin.schedule.entities.Job;
import ir.amin.schedule.util.BasicRandomize;
import ir.amin.schedule.util.Randomize;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by amin on 12/25/16.
 */
public class JobFactory {

    private static JobFactory jobFactory;
    private Randomize randomize;
    private List<Job> generatedJobs;

    public static JobFactory newInstance() {

        if (jobFactory == null) {
            jobFactory = new JobFactory(new BasicRandomize());
        }
        return jobFactory;
    }

    public static JobFactory newInstance(Randomize randomize) {

        if (jobFactory == null) {
            jobFactory = new JobFactory(randomize);
        }
        return jobFactory;
    }

    private JobFactory(Randomize randomize) {
        this.randomize = randomize;
        this.generatedJobs = generatedJobs();
    }

    public List<Job> generatedJobs() {
        List<Job> jobs = new ArrayList<>();
        for (int i = 0; i < Config.JOBS_NUMBER; i++) {
            Job j = new Job();
            j.setArrivalTime(randomize.getRandomArrivalTime());
            j.setBurstTime(randomize.getRandomBurstTime());
            j.setRemainingTime(j.getBurstTime());
            jobs.add(j);
        }
        Collections.sort(jobs, ((job1, job2) -> {
            return job2.getArrivalTime() > job1.getArrivalTime() ? -1 : 1;
        }));
        return jobs;
    }

    public List<Job> getGeneratedJobs() {
        return cloneList(generatedJobs);
    }


    public List<Job> cloneList(List<Job> list) {
        List<Job> clone = new ArrayList<Job>(list.size());
        for (Job item : list) clone.add((Job) item.clone());
        return clone;
    }


    @Override
    public String toString() {
        String str = "";
        for (Job j : generatedJobs) {
            str += j.toString();
        }
        return str;
    }
}
