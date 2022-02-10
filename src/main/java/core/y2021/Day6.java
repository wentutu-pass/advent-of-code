package core.y2021;

import common.FileUtil;

import java.math.BigInteger;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Day6 {
    private static final Logger logger = Logger.getLogger("Day6");

    public static void main(String[] args) {
        String inputs = FileUtil.readFile("src/main/resources/y2021/day6.txt");
        Day6 day6 = new Day6();
        Scanner scanner = new Scanner(System.in);
        int year = scanner.nextInt();
        logger.log(Level.INFO, "the ans1 is {0}", day6.getSumOfAnyYes(year, inputs));
        logger.log(Level.INFO, "the ans2 is {0}", day6.getSumOfAnyYes1(year, inputs));
    }

    private int getSumOfAnyYes(int year, String dataStr) {
        String[] data = dataStr.split(",");
        ArrayList<Integer> list = new ArrayList<>();
        for (String datum : data) {
            list.add(Integer.parseInt(datum));
        }

        int loop = 0;
        while (loop < year) {
            int count = 0;
            for (int i = 0; i < list.size(); i++) {
                if (list.get(i) == 0) {
                    list.set(i, 6);
                    count++;
                } else {
                    list.set(i, list.get(i) - 1);
                }


            }
            for (int j = 0; j < count; j++) {
                list.add(8);
            }
            loop++;
        }
        return list.size();
    }

    private BigInteger getSumOfAnyYes1(int year, String dataStr) {
        BigInteger fishCount = new BigInteger(String.valueOf(0));
        String[] data = dataStr.split(",");
        HashMap<Integer, BigInteger> map = new HashMap<>();
        for (String datum : data) {
            int key = Integer.parseInt(datum);
            if (map.containsKey(key)) {
                map.put(key, map.get(key).add(BigInteger.valueOf(1)));
            } else {
                map.put(key, BigInteger.valueOf(1));
            }
        }

        for (int i = 0; i < year; i++) {
            HashMap<Integer, BigInteger> map1 = new HashMap<>();
            if (map.containsKey(0)) {
                map1.put(8, map.get(0));
                map1.put(6, map.get(0));
                map1.put(0, map.get(0));
            }

            for (int j = 1; j <= 8; j++) {
                if (!map.containsKey(j)) {
                    continue;
                }
                BigInteger bigInteger = map.get(j);
                if (map.containsKey(j - 1)) {
                    map.put(j - 1, map.get(j - 1).add(bigInteger));
                } else {
                    map.put(j - 1, bigInteger);
                }
                map.put(j, BigInteger.valueOf(0));

            }
            if (map1.containsKey(0)) {
                BigInteger count0 = map.getOrDefault(0, BigInteger.valueOf(0));
                if (count0.compareTo(map1.get(0)) >= 0) {
                    map.put(0, count0.subtract(map1.get(0)));
                }
            }
            if (map1.containsKey(6)) {
                BigInteger count6 = map.getOrDefault(6, BigInteger.valueOf(0));
                map.put(6, count6.add(map1.get(6)));
            }
            if (map1.containsKey(8)) {
                BigInteger count8 = map.containsKey(8) ? map.get(8) : BigInteger.valueOf(0);
                map.put(8, count8.add(map1.get(8)));
            }
        }

        for (Map.Entry<Integer, BigInteger> entry : map.entrySet()) {
            fishCount = fishCount.add(entry.getValue());
        }
        return fishCount;
    }

}
