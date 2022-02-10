package core.y2020;

import common.FileUtil;

import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Day6 {
    private static final Logger logger = Logger.getLogger("Day6");

    public static void main(String[] args) {
        String inputs = FileUtil.readFile("src/main/resources/y2020/day6.txt");
        Day6 day6 = new Day6();
        logger.log(Level.INFO, "sum of any yes is {0}", day6.getSumOfAnyYes(inputs));
        logger.log(Level.INFO, "sum of every yes is {0}", day6.getSumOfEveryYes(inputs));
    }

    private int getSumOfAnyYes(String inputs) {
        int count = 0;
        String[] string = inputs.split("\n\n");
        for (String input : string) {  //一组人
            String[] dataStr = input.split("\n"); // 每个人的回答
            Set<String> set = new HashSet<>(Arrays.asList(dataStr));
            count += set.size();
        }
        return count;
    }

    private int getSumOfEveryYes(String inputs) {
        int count = 0;
        String[] string = inputs.split("\n\n");
        for (String input : string) {  //一组人
            String[] dataStr = input.split("\n"); // 每个人的回答
            if (dataStr.length == 1) {
                count += dataStr[0].length();
                continue;
            }
            List<String> firstAns = new ArrayList<>(Arrays.asList(dataStr[0].split("")));
            for (int i = 1; i < dataStr.length; i++) {//第二个人的回答
                List<String> list = Arrays.asList(dataStr[i].split(""));
                firstAns.removeIf(yes -> !list.contains(yes));
            }
            count += firstAns.size();
        }
        return count;
    }
}
