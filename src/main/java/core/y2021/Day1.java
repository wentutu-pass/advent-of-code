package core.y2021;

import common.FileUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Day1 {
    private static final Logger logger = Logger.getLogger("Day1");

    public static void main(String[] args) {
        String string = FileUtil.readFile("src/main/resources/y2021/day1.txt");
        String[] inputs = string.split("\n");
        Day1 day1 = new Day1();
        logger.log(Level.INFO, "the ans1 is {0}", day1.countOfIncrease(inputs));
        logger.log(Level.INFO, "the ans2 is {0}", day1.countOfIncrease1(inputs));
    }

    private int countOfIncrease(String[] inputs) {
        int[] dataStr = Arrays.stream(inputs).mapToInt(Integer::parseInt).toArray();
        int count = 0;
        for (int i = 0; i < dataStr.length - 1; i++) {

            if (dataStr[i + 1] > dataStr[i]) {
                count++;
            }
        }
        return count;
    }

    private int countOfIncrease1(String[] inputs) {
        int[] dataStr = Arrays.stream(inputs).mapToInt(Integer::parseInt).toArray();
        int count = 0;
        ArrayList<Integer> list = new ArrayList<>();

        for (int i = 0; i < dataStr.length - 2; i++) {
            int a = dataStr[i] + dataStr[i + 1] + dataStr[i + 2];
            list.add(a);
        }

        for (int i = 0; i < list.size() - 1; i++) {
            if (list.get(i + 1) > list.get(i)) {
                count++;
            }
        }

        return count;
    }

}
