package core.y2021;

import common.FileUtil;

import java.util.logging.Level;
import java.util.logging.Logger;

public class Day2 {
    private static final Logger logger = Logger.getLogger("Day2");

    public static void main(String[] args) {
        String string = FileUtil.readFile("src/main/resources/y2021/day2.txt");
        String[] inputs = string.split("\n");
        Day2 day2 = new Day2();
        logger.log(Level.INFO, "the ans1 is {0}", day2.getCountsOfValidPW1(inputs));
        logger.log(Level.INFO, "tha ans2 is {0}", day2.getCountsOfValidPW2(inputs));
    }

    /*forward 5
down 5
forward 8
up 3
down 8
forward 2*/



    private int getCountsOfValidPW1(String[] inputs) {
        int num1 = 0;
        int num2 = 0;
        for (String input : inputs) {
            String jiSu = input.split(" ")[0];
            int num = Integer.parseInt(input.split(" ")[1]);
            if (jiSu.equals("forward")) {
                num1 += num;
            } else if (jiSu.equals("up")) {
                num2 -= num;
            } else {
                num2 += num;
            }
        }
        return num1 * num2;
    }

    /*down X increases your aim by X units.
up X decreases your aim by X units.
forward X does two things:
It increases your horizontal position by X units.
It increases your depth by your aim multiplied by X.*/
    private int getCountsOfValidPW2(String[] inputs) {
        int horizontal = 0;
        int depth = 0;
        int aim = 0;
        for (String input : inputs) {
            String jiSu = input.split(" ")[0];
            int num = Integer.parseInt(input.split(" ")[1]);
            if (jiSu.equals("forward")) {
                horizontal += num;
                depth += aim * num;
            } else if (jiSu.equals("up")) {
                aim -= num;
            } else {
                aim += num;
            }
        }
        return horizontal * depth;
    }


}
