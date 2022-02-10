package core.y2021;

import common.FileUtil;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Day5 {
    private static final Logger logger = Logger.getLogger("Day5");

    public static void main(String[] args) {
        String string = FileUtil.readFile("src/main/resources/y2021/day5.txt");
        String[] inputs = string.split("\n");
        Day5 day5 = new Day5();
        HashMap<String, Integer> dataMap = day5.getDataMap(inputs);
        logger.log(Level.INFO, "the ans1 is is {0}", day5.getAns1(dataMap));
        HashMap<String, Integer> dataMap2 = day5.getDataMap2(inputs);
        logger.log(Level.INFO, "the ans2 is {0}", day5.getAns1(dataMap2));
    }

    //    R:B:
//    L:F:
    private HashMap<String, Integer> getDataMap(String[] inputs) {
        HashMap<String, Integer> map = new HashMap<>();
        for (String input : inputs) {
            int[] intArr = getIntArr(input);
            int x1 = intArr[0];
            int y1 = intArr[1];
            int x2 = intArr[2];
            int y2 = intArr[3];
            if (x1 != x2 && y1 != y2) {
                continue;
            }
            if (x1 == x2) {
                detailData(x1, y1, y2, map, true);
            }
            if (y1 == y2) {
                detailData(y1, x1, x2, map, false);
            }
        }

        return map;
    }

    private void detailData(int var1, int var2, int var3, HashMap<String, Integer> map, boolean isX) {
        int min = var2;
        int max = var3;
        if (var2 > var3) {
            max = var2;
            min = var3;
        }
        for (int j = min; j <= max; j++) {
            if (isX) {
                if (map.containsKey(var1 + "," + j)) {
                    int count = map.get(var1 + "," + j);
                    map.put(var1 + "," + j, ++count);
                } else {
                    map.put(var1 + "," + j, 1);
                }
            } else {
                if (map.containsKey(j + "," + var1)) {
                    int count = map.get(j + "," + var1);
                    map.put(j + "," + var1, ++count);
                } else {
                    map.put(j + "," + var1, 1);
                }
            }

        }
    }

    private int getAns1(HashMap<String, Integer> map) {
        int count = 0;
        for (Map.Entry<String, Integer> entry : map.entrySet()) {
            if (entry.getValue() >= 2) {
                count++;
            }

        }
        return count;

    }

    private int[] getIntArr(String input) {
        String[] path = input.split(" -> ");
        String[] start = path[0].split(",");
        int x1 = Integer.parseInt(start[0]);
        int y1 = Integer.parseInt(start[1]);
        String[] end = path[1].split(",");
        int x2 = Integer.parseInt(end[0]);
        int y2 = Integer.parseInt(end[1]);
        return new int[]{x1, y1, x2, y2};
    }

    private HashMap<String, Integer> getDataMap2(String[] inputs) {
        HashMap<String, Integer> map = new HashMap<>();
        for (String input : inputs) {
            int[] intArr = getIntArr(input);
            int x1 = intArr[0];
            int y1 = intArr[1];
            int x2 = intArr[2];
            int y2 = intArr[3];

            if (x1 == x2) {
                detailData(x1, y1, y2, map, true);
            } else if (y1 == y2) {
                detailData(y1, x1, x2, map, false);
            } else {
                //An entry like 1,1 -> 3,3 covers points 1,1, 2,2, and 3,3.
                //An entry like 9,7 -> 7,9 covers points 9,7, 8,8, and 7,9.
                while (x1 != x2 && y1 != y2) {
                    if (map.containsKey(x1 + "," + y1)) {
                        int count = map.get(x1 + "," + y1);
                        map.put(x1 + "," + y1, ++count);
                    } else {
                        map.put(x1 + "," + y1, 1);
                    }
                    if (x1 > x2) {
                        x1--;
                    } else {
                        x1++;
                    }

                    if (y1 > y2) {
                        y1--;
                    } else {
                        y1++;
                    }
                }
                if (map.containsKey(x2 + "," + y2)) {
                    int count = map.get(x2 + "," + y2);
                    map.put(x2 + "," + y2, ++count);
                } else {
                    map.put(x2 + "," + y2, 1);
                }
            }
        }

        return map;
    }

}