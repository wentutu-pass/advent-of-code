package core.y2020;

import common.Util;
import com.google.common.collect.EvictingQueue;

import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Day9 {
    static int size = 25;
    private EvictingQueue<Long> queue = EvictingQueue.create(size);
    private static Logger logger = Logger.getLogger("Day9");

    public static void main(String[] args) {
        String inputs = Util.readFile("src/main/resources/y2020/day9.txt");
        Day9 day9 = new Day9();
        String[] split = inputs.split("\n");
        day9.getQueue(split);
        long inValidNum = day9.getNum(split);
        logger.log(Level.INFO, "invalid num is {0}", inValidNum);
        ArrayList<Long> nums = day9.getNums(split, inValidNum);
        nums.sort(Long::compareTo);
        logger.log(Level.INFO, "the value is {0}", nums.get(0) + nums.get(nums.size() - 1));
    }

    private long getNum(String[] inputs) {
        for (int i = size; i < inputs.length; i++) {
            long value = Long.parseLong(inputs[i]);
            boolean vaild = isVaild(value);
            if (vaild) {
                queue.add(value);
            } else {
                return value;
            }

        }
        return 0;
    }

    private boolean isVaild(long a) {
        for (long i : queue) {
            for (long j : queue) {
                if (i != j && a == i + j) return true;
            }
        }
        return false;
    }

    private Queue<Long> getQueue(String[] oldInp) {
        for (int i = 0; i < size; i++) {
            queue.add(Long.parseLong(oldInp[i]));
        }
        return queue;
    }

    private ArrayList<Long> getNums(String[] inputs, long inValidNum) {
        long count = 0l;
        ArrayList<Long> longs = new ArrayList<>();
        for (int i = 0; i < inputs.length; i++) {
            long value = Long.parseLong(inputs[i]);
            count += value;
            if (count == inValidNum) {
                return longs;
            }
            if (count > inValidNum) {
                String[] newInps = new String[inputs.length - 1];
                System.arraycopy(inputs, 1, newInps, 0, newInps.length);
                return getNums(newInps, inValidNum);
            }
            longs.add(value);
        }
        return longs;
    }

}
