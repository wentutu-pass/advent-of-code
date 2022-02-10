package core.y2021;

import common.FileUtil;

import java.math.BigInteger;
import java.time.Duration;
import java.time.Instant;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Day14 {
    private static final Logger logger = Logger.getLogger("Day14");
    private static final HashMap<String, String> map = new HashMap<>();

    public static void main(String[] args) {
        String inputs = FileUtil.readFile("src/main/resources/y2021/day14.txt");
        String[] dataArr = inputs.split("\n");
        Day14 day14 = new Day14();
        logger.log(Level.INFO, "the ans1 is {0}", day14.getAns1(getData(dataArr)));
        getData(dataArr);
        logger.log(Level.INFO, "the ans2 is {0}", day14.getAns2(dataArr[0]));
    }

    private ArrayList<String> getList1(List<String> list, int count) {
        ArrayList<String> list1 = new ArrayList<>();
        list1.add(list.get(0));

        for (int i = 0; i < list.size() - 1; i++) {
            String key = list.get(i).concat(list.get(i + 1));
            if (map.containsKey(key)) {
                list1.add(map.get(key));
            }
            list1.add(list.get(i + 1));
        }
        count--;
        if (count == 0) {
            return list1;
        }
        return getList1(list1, count);

    }

    private int getAns1(List<String> list) {
        ArrayList<String> list1 = getList1(list, 10);
        ArrayList<Integer> list2 = new ArrayList<>();
        HashMap<String, Integer> map1 = new HashMap<>();
        for (String letter : list1) {
            int count = map1.getOrDefault(letter, 0);
            map1.put(letter, count + 1);
        }

        for (Map.Entry<String, Integer> entry : map1.entrySet()) {
            list2.add(entry.getValue());
        }
        Collections.sort(list2);

        return list2.get(list2.size() - 1) - list2.get(0);

    }

    private BigInteger getAns2(String str) {
        String end = str.substring(str.length() - 1);
        HashMap<String, BigInteger> map2 = getMap2(str);
        HashMap<String, BigInteger> dataMap = new HashMap<>();

        for (Map.Entry<String, BigInteger> entry : map2.entrySet()) {
            dataMap.put(entry.getKey().substring(0, 1), dataMap.getOrDefault(entry.getKey().substring(0, 1), BigInteger.ZERO).add(entry.getValue()));
            //dataMap.put(entry.getKey().substring(1, 2), dataMap.getOrDefault(entry.getKey().substring(1, 2), BigInteger.ZERO).add(entry.getValue()));
        }
        ArrayList<BigInteger> list2 = new ArrayList<>();
        for (Map.Entry<String, BigInteger> entry : dataMap.entrySet()) {
            BigInteger value = entry.getValue();
            if (entry.getKey().equals(end)) {
                value = value.add(BigInteger.ONE);
            }
            list2.add(value);
        }

        Collections.sort(list2);

        return list2.get(list2.size() - 1).subtract(list2.get(0));

    }

    public static List<String> getData(String[] inputs) {
        List<String> list = Arrays.asList(inputs[0].split(""));
        for (String data : inputs) {
            if (data.contains("->")) {
                String[] split = data.split(" -> ");
                map.put(split[0], split[1]);
            }
        }
        return list;
    }

    private HashMap<String, BigInteger> getMap2(String data) {
        HashMap<String, BigInteger> startMap = getStartMap(data);

        for (int j = 0; j < 40; j++) {
            Instant begin = Instant.now();
            HashMap<String, BigInteger> tmp = new HashMap<>();
            for (Map.Entry<String, BigInteger> next : startMap.entrySet()) {
                String key = next.getKey();
                BigInteger value = next.getValue();
                String mid = map.get(key);
                String s1 = key.substring(0, 1).concat(mid);
                String s2 = mid.concat(key.substring(1, 2));
                tmp.put(s1, tmp.getOrDefault(s1, BigInteger.ZERO).add(value));
                tmp.put(s2, tmp.getOrDefault(s2, BigInteger.ZERO).add(value));
            }

            startMap = tmp;
            Instant end = Instant.now();
            System.out.println("Step " + j + " = " + Duration.between(begin, end));
        }
        return startMap;

    }

    public static HashMap<String, BigInteger> getStartMap(String data) {
        HashMap<String, BigInteger> dataMap = new HashMap<>();
        for (int i = 0; i < data.length() - 1; i++) {
            String key = data.substring(i, i + 2);
            BigInteger value = dataMap.getOrDefault(key, BigInteger.ZERO);
            dataMap.put(key, value.add(BigInteger.ONE));
        }
        return dataMap;
    }
}
