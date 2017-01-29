package ir.amin.schedule.util;


import ir.amin.chart.WriteExcel;
import ir.amin.schedule.entities.Job;
import ir.amin.schedule.entities.Period;
import jxl.write.WriteException;

import java.io.IOException;
import java.util.List;

/**
 * Created by amin on 1/28/17.
 */
public class TimeUtils {

    static int in1 = 1;

    public static double getNonPrimetiveAverageWatingTime(List<Job> jobs) {
        try {
            writeExcel.write("nonpr" + in1 + ".xls");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (WriteException e) {
            e.printStackTrace();
        }
        double sum = 0;
        for (int i = 0; i < jobs.size(); i++) {
            try {
                writeExcel.addNumber(0, i, jobs.get(i).getStartTime() - jobs.get(i).getArrivalTime());
                writeExcel.addNumber(1, i, jobs.get(i).getBurstTime());
            } catch (WriteException e) {
                e.printStackTrace();
            }
            sum += jobs.get(i).getStartTime() - jobs.get(i).getArrivalTime();
        }
        writeExcel.save();
        in1++;
        return sum / jobs.size();
    }

    static WriteExcel writeExcel = new WriteExcel();
    static int in2 = 1;

    public static double getPrimetiveAverageWatingTime(List<Job> jobs) {
        try {
            writeExcel.write("pr" + in2 + ".xls");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (WriteException e) {
            e.printStackTrace();
        }
        int sum = 0;
        for (int i = 0; i < jobs.size(); i++) {
            int k = 0;
            List<Period> periods = jobs.get(i).runningTimePeriods;
            for (int j = 0; j < periods.size(); j++) {
                if (j == 0) {
                    try {
                        writeExcel.addNumber(k, i, periods.get(j).start - jobs.get(i).getArrivalTime());
                        writeExcel.addNumber(++k, i, periods.get(j).end - periods.get(j).start);
                    } catch (WriteException e) {
                        e.printStackTrace();
                    }
                    ++k;
                    sum += periods.get(j).start - jobs.get(i).getArrivalTime();
                } else {
                    sum += periods.get(j).start - periods.get(j - 1).end;
                    try {
                        writeExcel.addNumber(k, i, periods.get(j).start - periods.get(j - 1).end);
                        writeExcel.addNumber(++k, i, periods.get(j).end - periods.get(j).start);
                    } catch (WriteException e) {
                        e.printStackTrace();
                    }
                    ++k;
                }
            }
        }
        writeExcel.save();
        in2++;
        return sum / jobs.size();
    }

    public static double getTurnAroundTime(List<Job> jobs) {
        int sum = 0;
        for (int i = 0; i < jobs.size(); i++) {
            sum += jobs.get(i).getReturnedTime() - jobs.get(i).getArrivalTime();
        }
        return sum / jobs.size();
    }
}
