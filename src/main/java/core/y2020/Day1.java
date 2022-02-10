package core.y2020;

import common.FileUtil;

import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Day1 {
    private static final Logger logger = Logger.getLogger("Day1");

    public static void main(String[] args) {
        String string = FileUtil.readFile("src/main/resources/y2020/day1.txt");
        String[] inputs = string.split("\n");
        Day1 day1 = new Day1();
        logger.log(Level.INFO, "sum1 is {0}", day1.getSum1(inputs));
        logger.log(Level.INFO, "sum2 is {0}", day1.getSum2(inputs));
    }

    private int getSum1(String[] inputs) {
        int[] dataArr = Arrays.stream(inputs).mapToInt(Integer::parseInt).toArray();
        for (int i = 0; i < dataArr.length; i++) {
            for (int j = i + 1; j < dataArr.length; j++) {
                if (dataArr[i] + dataArr[j] == 2020) {
                    return dataArr[i] * dataArr[j];
                }
            }
        }
        return 0;
    }

    private int getSum2(String[] inputs) {
        int[] dataArr = Arrays.stream(inputs).mapToInt(Integer::parseInt).toArray();
        for (int i = 0; i < dataArr.length; i++) {
            for (int j = i + 1; j < dataArr.length; j++) {
                for (int k = j + 1; k < dataArr.length; k++) {
                    if (dataArr[i] + dataArr[j] + dataArr[k] == 2020) {
                        return dataArr[i] * dataArr[j] * dataArr[k];
                    }
                }
            }
        }
        return 0;
    }

}
