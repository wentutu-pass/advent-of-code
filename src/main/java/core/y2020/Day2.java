package core.y2020;

import common.FileUtil;

import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Stream;

public class Day2 {
    private static final Logger logger = Logger.getLogger("Day2");

    public static void main(String[] args) {
        String string = FileUtil.readFile("src/main/resources/y2020/day2.txt");
        String[] inputs = string.split("\n");
        Day2 day2 = new Day2();
        logger.log(Level.INFO, "counts of valid pw1 is {0}", day2.getCountsOfValidPW1(inputs));
        logger.log(Level.INFO, "counts of valid pw2 is {0}", day2.getCountsOfValidPW2(inputs));
    }

    private int getCountsOfValidPW1(String[] inputs) {
        int count = 0;
        for (String input : inputs) {
            String[] dataStr = input.split(" ");
            String[] range = dataStr[0].split("-");
            String letter = dataStr[1].replace(":", "");
            String str = dataStr[2];
            long count1 = Stream.of(str.split("")).filter(s -> s.equals(letter)).count();
            int size = (int) count1;
            if (size >= Integer.parseInt(range[0]) && size <= Integer.parseInt(range[1])) {
                count++;
            }
        }
        return count;
    }

    private int getCountsOfValidPW2(String[] inputs) {
        int count = 0;
        for (String input : inputs) {
            String[] dataStr = input.split(" ");
            String[] range = dataStr[0].split("-");
            String letter = dataStr[1].replace(":", "");
            String str = dataStr[2];
            String str1 = str.substring(Integer.parseInt(range[0]) - 1, Integer.parseInt(range[0]));
            String str2 = str.substring(Integer.parseInt(range[1]) - 1, Integer.parseInt(range[1]));

            if ((letter.equals(str1) && !letter.equals(str2)) || (!letter.equals(str1) && letter.equals(str2))) {
                count++;
            }
        }
        return count;
    }
}
