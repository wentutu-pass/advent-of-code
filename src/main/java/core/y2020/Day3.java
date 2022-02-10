package core.y2020;

import common.FileUtil;

import java.math.BigDecimal;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Day3 {
    private static final Logger logger = Logger.getLogger("Day3");

    public static void main(String[] args) {
        String string = FileUtil.readFile("src/main/resources/y2020/day3.txt");
        String[] inputs = string.split("\n");
        Day3 day3 = new Day3();
        logger.log(Level.INFO, "counts of tree1 is {0}", day3.getCountsOfTrees1(inputs));
        BigDecimal i1 = day3.getCountsOfTrees2(inputs, 1, 1);
        BigDecimal i2 = day3.getCountsOfTrees2(inputs, 1, 3);
        BigDecimal i3 = day3.getCountsOfTrees2(inputs, 1, 5);
        BigDecimal i4 = day3.getCountsOfTrees2(inputs, 1, 7);
        BigDecimal i5 = day3.getCountsOfTrees2(inputs, 2, 1);
        BigDecimal decimal = i1.multiply(i2).multiply(i3).multiply(i4).multiply(i5);
        logger.log(Level.INFO, "counts of tree2 is {0}", decimal);
    }

    /*
    .#..........#...#...#..#.......
    .###...#.#.##..###..#...#...#..
    #.....#................#...#.#.
    #.....#..###.............#....#
    ......#.....#....#...##.....###
    ....#........#.#......##....#.#
    ..#.......##..#.#.#............
    #.............#..#...#.#...#...
    .#...........#.#....#..##......*/
    private int getCountsOfTrees1(String[] inputs) {
        int count = 0;
        for (int i = 1; i < inputs.length; i++) {  //列
            if (inputs[i].length() < i * 3) {
                for (int j = i; j < inputs.length; j++) {
                    inputs[j] += inputs[j];
                }
            }
            if (inputs[i].split("")[i * 3].equals("#")) {
                count++;
            }

        }
        return count;
    }

    private BigDecimal getCountsOfTrees2(String[] inputs, int i1, int i2) {
        BigDecimal count = new BigDecimal(0);
        for (int i = i1; i < inputs.length; i += i1) {  //列
            if (inputs[i].length() / 2 < i * i2) {
                for (int j = i - 1; j < inputs.length; j++) {
                    inputs[j] += inputs[j];
                }
            }
            String[] strings = inputs[i].split("");
            if (i1 < i2) {
                if (strings[i * i2].equals("#")) {
                    count = count.add(new BigDecimal(1));
                }
            } else {
                if (strings[i / i1].equals("#")) {
                    count = count.add(new BigDecimal(1));
                }
            }

        }
        return count;
    }


}
