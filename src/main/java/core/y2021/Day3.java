package core.y2021;

import common.FileUtil;

import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Day3 {
    private static final Logger logger = Logger.getLogger("Day3");

    public static void main(String[] args) {
        String string = FileUtil.readFile("src/main/resources/y2021/day3.txt");
        String[] inputs = string.split("\n");
        Day3 day3 = new Day3();

        logger.log(Level.INFO, "the ans1 is {0}", day3.getCountsOfTrees1(inputs));
        logger.log(Level.INFO, "the ans2 is {0}", day3.getCountsOfTrees2(inputs));
    }

    private int getCountsOfTrees1(String[] inputs) {
        HashMap<Integer, HashMap<Integer, Integer>> map = new HashMap<>();
        for (String input : inputs) {  //列
            String[] strings = input.split("");
            for (int j = 0; j < strings.length; j++) {
                HashMap<Integer, Integer> map1 = map.get(j);
                if (null == map1) {
                    map1 = new HashMap<>();
                }
                int key = Integer.parseInt(strings[j]);
                if (map1.containsKey(key)) {
                    int nums = map1.get(key);
                    map1.put(key, ++nums);
                } else {
                    map1.put(key, 1);
                }
                map.put(j, map1);
            }
        }

        Iterator<Map.Entry<Integer, HashMap<Integer, Integer>>> iterator = map.entrySet().iterator();
        StringBuilder a = new StringBuilder();
        StringBuilder a1 = new StringBuilder();
        while (iterator.hasNext()) {
            Map.Entry<Integer, HashMap<Integer, Integer>> next = iterator.next();
            if (next.getValue().get(0) >= next.getValue().get(1)) {
                a.append("0");
                a1.append("1");
            } else {
                a.append("1");
                a1.append("0");
            }
        }
        int max = Integer.parseInt(a.toString(), 2);
        int min = Integer.parseInt(a1.toString(), 2);

        return max * min;
    }

    private String getCountsOf(List<String> list, int count, int startWith, boolean max) {

        HashMap<Integer, Integer> map = new HashMap<>();

        Iterator<String> it = list.iterator();
        while (it.hasNext()) {
            String x = it.next();
            String[] strings = x.split("");
            if (count > strings.length) {
                break;
            }

            if (count != 0) {
                int key0 = Integer.parseInt(strings[count - 1]);
                if (key0 != startWith) {
                    it.remove();
                    continue;
                } else if (count == strings.length - 1) {
                    int key = Integer.parseInt(strings[count]);
                    if (max && key != 1) {
                        it.remove();
                        continue;
                    } else if (!max && key != 0) {
                        it.remove();
                        continue;
                    }
                }
            }
            int key = Integer.parseInt(strings[count]);

            if (map.containsKey(key)) {
                int nums = map.get(key);
                map.put(key, ++nums);
            } else {
                map.put(key, 1);
            }

        }
        if (list.size() == 1) {
            return list.get(0);
        }
        Integer num0 = map.get(0);
        Integer num1 = map.get(1);
        if (max) {
            if (num0 != null && num1 != null && num0 > num1) {
                startWith = 0;
            } else {
                startWith = 1;
            }
        } else {
            if (num0 > num1) {
                startWith = 1;
            } else {
                startWith = 0;
            }
        }

        return getCountsOf(list, count + 1, startWith, max);
    }

    private int getCountsOfTrees2(String[] inputs) {
        List<String> list = Arrays.asList(inputs);
        ArrayList<String> list1 = new ArrayList<>(list);
        String max = getCountsOf(list1, 0, 0, true);

        ArrayList<String> list2 = new ArrayList<>(list);
        String min = getCountsOf(list2, 0, 0, false);

        return Integer.parseInt(max, 2) * Integer.parseInt(min, 2);
    }


}
