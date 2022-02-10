package core.y2020;

import common.FileUtil;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Day5 {
    private static final Logger logger = Logger.getLogger("Day5");

    public static void main(String[] args) {
        String string = FileUtil.readFile("src/main/resources/y2020/day5.txt");
        String[] inputs = string.split("\n");
        Day5 day5 = new Day5();
        logger.log(Level.INFO, "max seatId is {0}", day5.getMaxSeatId(inputs));
        logger.log(Level.INFO, "your seatId is {0}", day5.getYoursSeatId(inputs));
    }

    //    R:B:
//    L:F:
    private int getMaxSeatId(String[] inputs) {
        int maxSeatId = 0;
        for (String input : inputs) {
            String rows = input.substring(0, 7);
            String cols = input.substring(7);
            int row = getNumByLetter(0, 127, rows);
            int col = getNumByLetter(0, 7, cols);
            int i = row * 8 + col;
            maxSeatId = Math.max(i, maxSeatId);
        }
        return maxSeatId;
    }

    private int getNumByLetter(int min, int max, String inputs) {
        String letter = inputs.split("")[0];
        if (inputs.length() > 1) {
            inputs = inputs.substring(1);
        }

        if (letter.equals("R") || letter.equals("B")) {
            min = max - (max - min) / 2;
        } else if (letter.equals("L") || letter.equals("F")) {
            max = (max - min) / 2 + min;
        }

        if (min == max) return min;
        return getNumByLetter(min, max, inputs);
    }

    private int getYoursSeatId(String[] inputs) {
        ArrayList<Integer> list = new ArrayList<>();
        for (String input : inputs) {
            String rows = input.substring(0, 7);
            String cols = input.substring(7);
            int row = getNumByLetter(0, 127, rows);
            int col = getNumByLetter(0, 7, cols);
            int i = row * 8 + col;
            list.add(i);
        }
        for (int i = 80; i <= 919; i++) {
            if (!list.contains(i)) {
                return i;
            }
        }
        list.sort(Integer::compareTo);
        return list.get(0);
    }

}
