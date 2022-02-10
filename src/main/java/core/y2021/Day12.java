package core.y2021;

import common.FileUtil;
import common.StringUtil;

import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Day12 {
    private static final Logger logger = Logger.getLogger("Day12");

    public static void main(String[] args) {
        String string = FileUtil.readFile("src/main/resources/y2021/day12.txt");
        String[] inputs = string.split("\n");
        Day12 day12 = new Day12();
        HashMap<String, List<String>> map = day12.getData(inputs);
        ArrayList<String> list = new ArrayList<>();
        list.add("start");
        logger.log(Level.INFO, "the ans1 is {0}", day12.getAns1(list, map, 0));
        logger.log(Level.INFO, "the ans2 is {0}", day12.getAns2(list, map, 0));
    }

    private HashMap<String, List<String>> getData(String[] inputs) {
        HashMap<String, List<String>> map = new HashMap<>();
        for (String lines : inputs) {
            String[] split = lines.split("-");
            String start = split[0];
            String end = split[1];
            List<String> list = map.getOrDefault(start, new ArrayList<>());
            list.add(end);
            map.put(start, list);

            if (!start.equals("start") && !end.equals("end")) {
                List<String> list2 = map.getOrDefault(end, new ArrayList<>());
                list2.add(start);
                map.put(end, list2);
            }
        }
        return map;
    }

    private int getAns1(ArrayList<String> list, HashMap<String, List<String>> map, int count) {
        //start
        String key = list.get(list.size() - 1);
        List<String> list1 = map.getOrDefault(key, new ArrayList<>());
        for (String str : list1) {
            if (str.equals("end")) {
                count = count + 1;
                continue;
            }

            if (StringUtil.issLowerStr(str) && StringUtil.issLowerStr(key) && (!map.containsKey(str) || map.get(str).size() == 1)) {
                continue;
            }
            if (StringUtil.issLowerStr(str) && list.contains(str)) {
                continue;
            }

            ArrayList<String> list2 = new ArrayList<>(list);
            list2.add(str);

            count = getAns1(list2, map, count);
        }
        return count;
    }

    private int getAns2(ArrayList<String> list, HashMap<String, List<String>> map, int count) {
        //start
        String key = list.get(list.size() - 1);
        List<String> list1 = map.getOrDefault(key, new ArrayList<>());
        for (String str : list1) {
            if (str.equals("end")) {
                count = count + 1;
                continue;
            } else if (str.equals("start")) {
                continue;
            }

            if (list.contains(str) && StringUtil.issLowerStr(str) && StringUtil.issLowerStr(key) && (!map.containsKey(str) || map.get(str).size() == 1)) {
                continue; //d

            }
            if (StringUtil.issLowerStr(str) && list.contains(str) && checkMoreTwice(list)) {
                continue;
            }

            ArrayList<String> list2 = new ArrayList<>(list);
            list2.add(str);

            count = getAns2(list2, map, count);
        }
        return count;
    }

    private boolean checkMoreTwice(ArrayList<String> list) {
        HashMap<String, Integer> map = new HashMap<>();
        for (String s : list) {
            if (StringUtil.issLowerStr(s)) {
                map.put(s, map.getOrDefault(s, 0) + 1);
                if (map.getOrDefault(s, 0) == 2) {
                    return true;
                }
            }
        }
        return false;
    }


}
