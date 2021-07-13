package core.y2020;

import common.Util;
import org.apache.commons.lang3.StringUtils;

import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;


public class Day11 {
    private static Logger logger = Logger.getLogger("Day11");
    static HashMap<Integer, HashMap> map = new HashMap<>();
    static int countOfOccupiedSeat = 0;

    public static void main(String[] args) {
        String inputs = Util.readFile("src/main/resources/y2020/day11.txt");
        Day11 day11 = new Day11();
        String[] input = inputs.split("\n");
        logger.log(Level.INFO, "the counts of seat1 is {0}", day11.getCountOfSeat1(input));
        Day11.detailData(input);
        logger.log(Level.INFO, "the counts of seat2 is {0}", getCountOfSeat2(input));
    }

    private int getCountOfSeat1(String[] inputs) {
        HashMap<Integer, ArrayList<Integer>> map1 = new HashMap<>();
        int count = 0;
        for (int i = 0; i < inputs.length; i++) {
            ArrayList<Integer> list = new ArrayList<>();
            String[] states = inputs[i].split("");
            for (int j = 0; j < states.length; j++) {
                if (states[j].equals(".")) continue;
                int countOfOccupiedSeat1 = 0;
                if (i > 0) {
                    String[] upRow = inputs[i - 1].split("");
                    countOfOccupiedSeat1 += j > 0 ? upRow[j - 1].equals("#") ? 1 : 0 : 0;
                    countOfOccupiedSeat1 += upRow[j].equals("#") ? 1 : 0;
                    countOfOccupiedSeat1 += j < upRow.length - 1 ? upRow[j + 1].equals("#") ? 1 : 0 : 0;
                }
                if (i < inputs.length - 1) {
                    String[] downRow = inputs[i + 1].split("");
                    countOfOccupiedSeat1 += j > 0 ? downRow[j - 1].equals("#") ? 1 : 0 : 0;
                    countOfOccupiedSeat1 += downRow[j].equals("#") ? 1 : 0;
                    countOfOccupiedSeat1 += j < downRow.length - 1 ? downRow[j + 1].equals("#") ? 1 : 0 : 0;
                }
                countOfOccupiedSeat1 += j > 0 ? states[j - 1].equals("#") ? 1 : 0 : 0;
                countOfOccupiedSeat1 += j < states.length - 1 ? states[j + 1].equals("#") ? 1 : 0 : 0;

                if ((states[j].equals("#") && countOfOccupiedSeat1 >= 4) || (states[j].equals("L") && countOfOccupiedSeat1 == 0)) {
                    list.add(j);
                }

                if (states[j].equals("#")) count++;
            }
            if (!list.isEmpty()) map1.put(i, list);
        }
        if (!map1.isEmpty()) {
            changeStates(map1, inputs);
            return getCountOfSeat1(inputs);
        }
        return count;

    }

    private static void changeStates(HashMap<Integer, ArrayList<Integer>> map, String[] inputs) {
        Iterator<Map.Entry<Integer, ArrayList<Integer>>> iterator = map.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<Integer, ArrayList<Integer>> next = iterator.next();
            int row = next.getKey();
            ArrayList<Integer> value = next.getValue();
            for (int i = 0; i < inputs.length; i++) {
                if (i == row) {
                    String[] states = inputs[i].split("");
                    for (int j = 0; j < states.length; j++) {
                        if (value.contains(j)) {
                            states[j] = states[j].equals("#") ? "L" : "#";
                            inputs[i] = StringUtils.join(states, "");
                        }
                    }
                    break;
                }
            }
        }
    }

    private static void getCountOfOccupiedSeat(int row, int col, String[] input) {
        int rows = input.length - 1;
        int cols = input[0].length() - 1;
        for (int i = row - 1; i >= 0; i--) {
            String values = (String) ((HashMap) map.get(i).values()).get(col);
            if (!values.isEmpty() && values.equals("#")) {
                countOfOccupiedSeat++;
                break;
            }
        }

        for (int i = row + 1; i <= rows; i++) {
            if (input[i].split("")[col].equals("#")) {
                countOfOccupiedSeat++;
                break;
            }
        }

        for (int j = col - 1; j >= 0; j--) {
            if (input[row].split("")[j].equals("#")) {
                countOfOccupiedSeat++;
                break;
            }
        }

        for (int j = col + 1; j <= cols; j++) {
            if (input[row].split("")[j].equals("#")) {
                countOfOccupiedSeat++;
                break;
            }
        }
    }

    private static void getCountOfOccupiedSeat2(int row, int col, String[] input) {
        int rows = input.length - 1;
        int cols = input[0].length() - 1;
        int i = 0;
        while (i < rows) {
            if (i < row) {
                int j = col - (row - i);
                if (j <= cols && j >= 0) {//zuoshang
                    if (input[i].split("")[j].equals(".")) {
                        i++;
                    } else if (input[i].split("")[j].equals("#")) {
                        countOfOccupiedSeat++;
                    }
                }
                i++;
            } else
                break;
        }
        int j = 0;
        while (j < rows) {
            if (j < row) {
                int k = col + (row - i);
                if (k <= cols && k >= 0) {//zuoshang
                    if (input[j].split("")[k].equals(".")) {
                        j++;
                    } else if (input[j].split("")[k].equals("#")) {
                        countOfOccupiedSeat++;
                    }
                }
                j++;
            } else
                break;
        }
        int i1 = rows;
        while (i1 >= 0) {
            if (i1 > row) {
                int j1 = col + (row - i);
                if (j1 <= cols && j1 >= 0) {//zuoshang
                    if (input[i1].split("")[j1].equals(".")) {
                        i1--;
                    } else if (input[i1].split("")[j1].equals("#")) {
                        countOfOccupiedSeat++;
                    }
                }
                i1--;
            } else
                break;
        }
        int i2 = rows;
        while (i2 >= 0) {
            if (i2 > row) {
                int j2 = col - (row - i);
                if (j2 <= cols && j2 >= 0) {//zuoshang
                    if (input[i2].split("")[j2].equals(".")) {
                        i2--;
                    } else if (input[i2].split("")[j2].equals("#")) {
                        countOfOccupiedSeat++;
                    }
                }
                i2--;
            } else
                break;
        }
    }

    private static int getCountOfSeat2(String[] inputs) {
        HashMap<Integer, ArrayList<Integer>> map = new HashMap<>();
        int count = 0;
        for (int i = 0; i < inputs.length; i++) {  //row
            ArrayList<Integer> list = new ArrayList<>();
            String[] states = inputs[i].split("");
            for (int j = 0; j < states.length; j++) {   //j
                countOfOccupiedSeat = 0;
                if (states[j].equals(".")) continue;
                getCountOfOccupiedSeat(i, j, inputs);
                getCountOfOccupiedSeat2(i, j, inputs);
                if ((states[j].equals("#") && countOfOccupiedSeat >= 5) || (states[j].equals("L") && countOfOccupiedSeat == 0)) {
                    list.add(j);
                }

                if (states[j].equals("#")) count++;
            }
            if (!list.isEmpty()) map.put(i, list);
        }
        if (!map.isEmpty()) {
            changeStates(map, inputs);
            detailData(inputs);
            return getCountOfSeat2(inputs);
        }
        return count;

    }

    public static Map<Integer, HashMap> detailData(String[] inputs) {
        for (int i = 0; i < inputs.length; i++) {
            String[] split = inputs[i].split("");
            HashMap<Integer, String> map1 = new HashMap<>();
            for (int j = 0; i < split.length; j++) {
                if (!split[j].equals(".")) {
                    map1.put(j, split[j]);
                }
            }
            map.put(i, map1);
        }
        return map;
    }
}
