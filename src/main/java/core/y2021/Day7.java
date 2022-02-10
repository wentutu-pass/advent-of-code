package core.y2021;

import common.FileUtil;

import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Day7 {
    private static final Logger logger = Logger.getLogger("Day7");

    public static void main(String[] args) {
        String inputs = FileUtil.readFile("src/main/resources/y2021/day7.txt");
        String[] dataArr = inputs.split(",");
        int[] nums = new int[dataArr.length];
        for (int i = 0; i < dataArr.length; i++) {
            nums[i] = Integer.parseInt(dataArr[i]);
        }
        Arrays.sort(nums);
        logger.log(Level.INFO, "the ans1 is {0}", getAns1(nums));
        logger.log(Level.INFO, "the ans2 is {0}", getAns2(nums));
    }

    public static int getMiddleNum(int[] nums) {
        int i = nums.length / 2;
        return nums[i];
    }

    public static int getAns1(int[] nums) {
        int count = 0;
        int middleNum = getMiddleNum(nums);
        for (int num : nums) {
            count += Math.abs(num - middleNum);

        }
        return count;

    }

    public static int getAns2(int[] nums) {
        int count1 = 0;
        int count2 = 0;
        int midNum = Arrays.stream(nums).sum() / nums.length;
        for (int num : nums) {
            count1 += getSums(Math.abs(num - midNum));
            count2 += getSums(Math.abs(num - (midNum + 1)));

        }
        return Math.min(count1, count2);

    }

    public static int getSums(int num) {
        int count = 0;
        for (int i = 1; i <= num; i++) {
            count += i;
        }
        return count;

    }

}
