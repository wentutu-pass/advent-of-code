package core.y2020;

import common.Util;

import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Day6 {
    private static Logger logger = Logger.getLogger("Day6");

    public static void main(String[] args) {
        String inputs = Util.readFile("src/main/resources/y2020/day6.txt");
        Day6 day6 = new Day6();
        logger.log(Level.INFO, "sum of any yes is {0}", day6.getSumOfAnyYes(inputs));
        logger.log(Level.INFO, "sum of every yes is {0}", day6.getSumOfEveryYes(inputs));
    }

    private int getSumOfAnyYes(String inputs) {
        int count = 0;
        String[] string = inputs.split("\n\n");
        for (String input : string) {  //一组人
            String[] datas = input.split("\n"); // 每个人的回答
            Set<String> set = new HashSet<>(Arrays.asList(datas));
            count += set.size();
        }
        return count;
    }

    private int getSumOfEveryYes(String inputs) {
        int count = 0;
        String[] string = inputs.split("\n\n");
        for (String input : string) {  //一组人
            String[] datas = input.split("\n"); // 每个人的回答
            if (datas.length == 1) {
                count += datas[0].length();
                continue;
            }
            List<String> firstAnws = new ArrayList<>(Arrays.asList(datas[0].split("")));
            for (int i = 1; i < datas.length; i++) {//第二个人的回答
                List<String> list = Arrays.asList(datas[i].split(""));
                firstAnws.removeIf(yes -> !list.contains(yes));
            }
            count += firstAnws.size();
        }
        return count;
    }
}
