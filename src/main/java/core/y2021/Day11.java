package core.y2021;

import common.ArrayUtil;
import common.FileUtil;

import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;


public class Day11 {
    private static final Logger logger = Logger.getLogger("Day11");

    public static void main(String[] args) {
        String inputs = FileUtil.readFile("src/main/resources/y2021/day11.txt");
        Day11 day11 = new Day11();
        String[] input = inputs.split("\n");

        logger.log(Level.INFO, "the ans1 is {0}", day11.getAns1(ArrayUtil.getIntArr(input)));
        logger.log(Level.INFO, "the ans2 is {0}", day11.getAns2(ArrayUtil.getIntArr(input)));
    }

    private int getAns1(int[][] inputs) {
        int count = 0;
        for (int k = 0; k < 100; k++) {  // year
            ArrayList<String> list = detailData(inputs);
            while (!list.isEmpty()) {
                ListIterator<String> it = list.listIterator();
                while (it.hasNext()) {
                    String[] split = it.next().split("");
                    it.remove();
                    int x = Integer.parseInt(split[0]);
                    int y = Integer.parseInt(split[1]);

                    if (inputs[x][y] != 0) {
                        inputs[x][y] = 0;
                        count++;
                        detailValue(inputs, list, it, x, y);
                    }

                }
            }

        }
        return count;
    }

    private void detailValue(int[][] inputs, ArrayList<String> list, ListIterator<String> it, int x, int y) {
        if (x > 0) {
            setValue(x - 1, y, inputs, list, it);
            if (y > 0) {
                setValue(x - 1, y - 1, inputs, list, it);
            }
            if (y < inputs[0].length - 1) {
                setValue(x - 1, y + 1, inputs, list, it);
            }
        }
        if (y > 0) {
            setValue(x, y - 1, inputs, list, it);
            if (x < inputs.length - 1) {
                setValue(x + 1, y - 1, inputs, list, it);
            }
        }
        if (y < inputs[0].length - 1) {
            setValue(x, y + 1, inputs, list, it);
        }
        if (x < inputs.length - 1) {
            setValue(x + 1, y, inputs, list, it);
            if (y < inputs[0].length - 1) {
                setValue(x + 1, y + 1, inputs, list, it);
            }
        }
    }

    private ArrayList<String> detailData(int[][] inputs) {
        ArrayList<String> list = new ArrayList<>();
        for (int i = 0; i < inputs.length; i++) {
            for (int j = 0; j < inputs[i].length; j++) {
                inputs[i][j] = inputs[i][j] + 1;
                if (inputs[i][j] == 10) {
                    list.add(i + "" + j);
                }

            }
        }
        return list;
    }

    private int getAns2(int[][] inputs) {
        int num = 0;
        boolean loop;
        do {  // year
            num++;
            loop = false;
            ArrayList<String> list = detailData(inputs);
            while (!list.isEmpty()) {
                ListIterator<String> it = list.listIterator();
                while (it.hasNext()) {
                    String[] split = it.next().split("");
                    it.remove();
                    int x = Integer.parseInt(split[0]);
                    int y = Integer.parseInt(split[1]);

                    if (inputs[x][y] != 0) {
                        inputs[x][y] = 0;
                        detailValue(inputs, list, it, x, y);
                    }

                }
            }

            for (int[] input : inputs) {
                for (int i : input) {
                    if (i != 0) {
                        loop = true;
                        break;
                    }
                }
            }

        } while (loop);
        return num;
    }

    public static void setValue(int x, int y, int[][] inputs, ArrayList<String> list, ListIterator<String> it) {
        int num = inputs[x][y];
        if (num != 10 && num != 0) {
            inputs[x][y] = inputs[x][y] + 1;
            if (inputs[x][y] == 10 && !list.contains(x + "" + y)) {
                it.add(x + "" + y);
            }
        }
    }
}
