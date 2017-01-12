package ir.amin.schedule.util;

import ir.amin.schedule.Config;

import java.util.Random;

/**
 * Created by amin on 1/12/17.
 */
public class BasicRandomize implements Randomize {

    private final Random random = new Random();

    @Override
    public int getRandomArrivalTime() {
        int x = random.nextInt(Config.RANDOM_UPPER_BOUND), y = 0;
        int u = Config.RANDOM_UPPER_BOUND / Config.RANDOM_JOBS_NUMBER;
        for (int i = 0; i < Config.RANDOM_JOBS_NUMBER; i++) {
            if (x >= i * u) y = i;
        }
        return y;
    }

    @Override
    public int getRandomBurstTime() {
        return random.nextInt(Config.MAX_TIME_BOUND) + 1;
    }

    @Override
    public int getRandomizedResourseSpeed() {
        return random.nextInt(Config.MAX_RESOURCE_SPEED) + 1;
    }
}
