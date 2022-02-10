package core.y2020;

import common.FileUtil;
import com.google.common.collect.EvictingQueue;

import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Day9 {
    static int size = 25;
    private final EvictingQueue<Long> queue = EvictingQueue.create(size);
    private static final Logger logger = Logger.getLogger("Day9");

    public static void main(String[] args) {
        String inputs = FileUtil.readFile("src/main/resources/y2020/day9.txt");
        Day9 day9 = new Day9();
        String[] split = inputs.split("\n");
        day9.setQueue(split);
        long inValidNum = day9.getNum(split);
        logger.log(Level.INFO, "invalid num is {0}", inValidNum);
        ArrayList<Long> nums = day9.getNums(split, inValidNum);
        nums.sort(Long::compareTo);
        logger.log(Level.INFO, "the value is {0}", nums.get(0) + nums.get(nums.size() - 1));
    }

    private long getNum(String[] inputs) {
        for (int i = size; i < inputs.length; i++) {
            long value = Long.parseLong(inputs[i]);
            boolean valid = isValid(value);
            if (valid) {
                queue.add(value);
            } else {
                return value;
            }

        }
        return 0;
    }

    private boolean isValid(long a) {
        for (long i : queue) {
            for (long j : queue) {
                if (i != j && a == i + j) return true;
            }
        }
        return false;
    }

    private void setQueue(String[] oldInp) {
        for (int i = 0; i < size; i++) {
            queue.add(Long.parseLong(oldInp[i]));
        }
    }

    private ArrayList<Long> getNums(String[] inputs, long inValidNum) {
        long count = 0;
        ArrayList<Long> longs = new ArrayList<>();
        for (String input : inputs) {
            long value = Long.parseLong(input);
            count += value;
            if (count == inValidNum) {
                return longs;
            }
            if (count > inValidNum) {
                String[] newInputs = new String[inputs.length - 1];
                System.arraycopy(inputs, 1, newInputs, 0, newInputs.length);
                return getNums(newInputs, inValidNum);
            }
            longs.add(value);
        }
        return longs;
    }

}
