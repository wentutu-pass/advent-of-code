package core.y2021;


import common.FileUtil;

import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Day13 {
    private static final Logger logger = Logger.getLogger("Day13");

    public static void main(String[] args) {
        String string = FileUtil.readFile("src/main/resources/y2021/day13.txt");
        String[] inputs = string.split("\n");
        Day13 day13 = new Day13();
        TreeMap<Integer, List<Integer>> map = day13.getData(inputs);
        logger.log(Level.INFO, "the ans1 is {0}", day13.getAns1(map));
        logger.log(Level.INFO, "the ans2 is {0}", day13.getAns2(map, day13.getRuleMap()));

    }

    private TreeMap<Integer, List<Integer>> getData(String[] inputs) {
        // 降序排序
        TreeMap<Integer, List<Integer>> map = new TreeMap<>(
                Integer::compareTo
        );
        for (String lines : inputs) {
            String[] split = lines.split(",");
            String start = split[0];
            String end = split[1];
            List<Integer> list = map.getOrDefault(Integer.parseInt(start), new ArrayList<>());
            list.add(Integer.parseInt(end));
            map.put(Integer.parseInt(start), list);
        }
        return map;
    }

    private TreeMap<Integer, List<Integer>> sort(TreeMap<Integer, List<Integer>> map) {
        TreeMap<Integer, List<Integer>> tmpMap = new TreeMap<>(
                Integer::compareTo
        );
        tmpMap.putAll(map);
        return tmpMap;
    }

    private List<Integer> mergeList(List<Integer> list1, List<Integer> list2) {
        if (list1.isEmpty()) {
            return list2;
        }

        if (list2.isEmpty()) {
            return list1;
        }

        for (int num : list2) {
            if (!list1.contains(num)) {
                list1.add(num);
            }
        }

        return list1;
    }

    private int getPointNum(TreeMap<Integer, List<Integer>> map) {
        int count = 0;
        Set<Map.Entry<Integer, List<Integer>>> entries = map.entrySet();
        for (Map.Entry<Integer, List<Integer>> entry : entries) {
            count += entry.getValue().size();
        }

        return count;
    }

    private TreeMap<Integer, List<Integer>> completingByX(TreeMap<Integer, List<Integer>> map, int rule) {
        Iterator<Map.Entry<Integer, List<Integer>>> it = map.entrySet().iterator();
        TreeMap<Integer, List<Integer>> tempMap = new TreeMap<>();
        while (it.hasNext()) {
            Map.Entry<Integer, List<Integer>> entry = it.next();
            int next = entry.getKey();
            if (next > rule) {
                int x = rule - (next - rule);
                List<Integer> list = map.getOrDefault(x, new ArrayList<>());
                List<Integer> list2 = mergeList(list, entry.getValue());
                it.remove();
                tempMap.put(x, list2);
            }
            if (next == rule) {
                it.remove();
            }

        }

        map.putAll(tempMap);
        return map;

    }

    private TreeMap<Integer, List<Integer>> completingByY(TreeMap<Integer, List<Integer>> map, int rule) {
        for (Map.Entry<Integer, List<Integer>> entry : map.entrySet()) {
            List<Integer> list = entry.getValue();
            Collections.sort(list);
            ListIterator<Integer> listIterator = list.listIterator();
            while (listIterator.hasNext()) {
                int next = listIterator.next();
                if (next > rule) {
                    listIterator.remove();
                    int y = rule - (next - rule);
                    if (!list.contains(y)) {
                        listIterator.add(y);
                    }

                }
                if (next == rule) {
                    listIterator.remove();
                }
            }
            map.put(entry.getKey(), list);
        }

        return map;

    }

    private int getAns1(TreeMap<Integer, List<Integer>> map) {
        TreeMap<Integer, List<Integer>> map1;
        map1 = completingByX(map, 655);

        return getPointNum(map1);

    }

    private int getAns2(TreeMap<Integer, List<Integer>> map, List<String> list) {
        TreeMap<Integer, List<Integer>> map1 = null;
        for (String rules : list) {
            String[] split = rules.split(",");
            String rule = split[0];
            int num = Integer.parseInt(split[1]);
            if (rule.equals("x")) {
                map1 = completingByX(map, num);
            } else {
                map1 = completingByY(map, num);
            }
            map1 = sort(map1);
        }
        assert map1 != null;
        String[][] arr = getStringArrByMap(map);
        for (int i = 0; i <= arr.length - 1; i++) {
            for (int j = 0; j <= arr[i].length - 1; j++) {
                System.out.print(arr[i][j]);

            }
            System.out.print("\n");
        }

        return getPointNum(map);

    }

    private ArrayList<String> getRuleMap() {
        ArrayList<String> list = new ArrayList<>();
        list.add("x" + "," + 655);
        list.add("y" + "," + 447);
        list.add("x" + "," + 1327);
        list.add("y" + "," + 2213);
        list.add("x" + "," + 1613);
        list.add("y" + "," + 111);
        list.add("x" + "," + 81);
        list.add("y" + "," + 55);
        list.add("x" + "," + 40);
        list.add("y" + "," + 27);
        list.add("y" + "," + 113);
        list.add("y" + "," + 6);
        return list;
    }

    private String[][] getStringArrByMap(TreeMap<Integer, List<Integer>> map) {
        List<Integer> keyList = new ArrayList<>();
        List<Integer> valueList = new ArrayList<>();
        for (Map.Entry<Integer, List<Integer>> entry : map.entrySet()) {
            keyList.add(entry.getKey());
            valueList.addAll(entry.getValue());
        }

        Collections.sort(keyList);
        Collections.sort(valueList);
        int minKey = keyList.get(0);
        int maxKey = keyList.get(keyList.size() - 1);
        int minValue = valueList.get(0);
        int maxValue = valueList.get(valueList.size() - 1);
        int addK = 0;
        int addV = 0;

        if (minKey < 0) {
            addK = Math.abs(minKey);
        }
        if (minValue < 0) {
            addV = Math.abs(minValue);
        }
        int x = maxValue + addV + 1;
        int y = maxKey + addK + 1;
        String[][] arr = new String[x][y];
        for (int i = 0; i < x; i++) {
            for (int j = 0; j < y; j++) {
                arr[i][j] = ".";
            }
        }

        for (Map.Entry<Integer, List<Integer>> entry : map.entrySet()) {
            for (int j : entry.getValue()) {
                arr[j + addV][entry.getKey() + addK] = "#";
            }
        }

        return arr;
    }


}