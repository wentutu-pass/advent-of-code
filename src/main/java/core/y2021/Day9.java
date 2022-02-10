package core.y2021;

import common.ArrayUtil;
import common.FileUtil;

import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Day9 {
    private static final Logger logger = Logger.getLogger("Day9");

    public static void main(String[] args) {
        String inputs = FileUtil.readFile("src/main/resources/y2021/day9.txt");
        Day9 day9 = new Day9();
        String[] dataArr = inputs.split("\n");
        logger.log(Level.INFO, "the ans1 is {0}", day9.getAns1(dataArr));
        logger.log(Level.INFO, "the ans2 is {0}", day9.getAns2(dataArr));
    }

    private long getAns1(String[] inputs) {
        ArrayList<String> list = new ArrayList<>();
        for (int i = 0; i < inputs.length; i++) {
            String line = inputs[i];
            String[] yData = line.split("");
            for (int j = 0; j < yData.length; j++) {
                boolean[] boolArr = detailData(i, j, inputs, yData);
                if (!boolArr[0] && !boolArr[1] && !boolArr[2] && !boolArr[3]) {
                    list.add(yData[j]);
                }

            }

        }
        return list.stream().mapToInt(Integer::parseInt).sum() + list.size();
    }

    private boolean[] detailData(int i, int j, String[] inputs, String[] yData) {
        int i1 = Integer.parseInt(yData[j]);
        boolean bigThanUp = i > 0 && i1 >= Integer.parseInt(inputs[i - 1].split("")[j]);
        boolean bigThanLeft = j > 0 && i1 >= Integer.parseInt(inputs[i].split("")[j - 1]);
        boolean bigThanDown = i < inputs.length - 1 && i1 >= Integer.parseInt(inputs[i + 1].split("")[j]);
        boolean bigThanRight = j < yData.length - 1 && i1 >= Integer.parseInt(inputs[i].split("")[j + 1]);
        return new boolean[]{bigThanUp, bigThanLeft, bigThanDown, bigThanRight};
    }

    private long getAns2(String[] inputs) {
        HashSet<String> set = getMap2(inputs);
        int[][] arr = ArrayUtil.getIntArr(inputs);
        ArrayList<Integer> list = new ArrayList<>();
        for (String index : set) {
            int count = getData2(arr, index);
            list.add(count);
        }
        int size = list.size();
        Collections.sort(list);
        return (long) list.get(size - 1) * list.get(size - 2) * list.get(size - 3);

    }

    private HashSet<String> getMap2(String[] inputs) {
        HashSet<String> set = new HashSet<>();
        for (int i = 0; i < inputs.length; i++) {
            String line = inputs[i];
            String[] yData = line.split("");
            for (int j = 0; j < yData.length; j++) {
                boolean[] boolArr = detailData(i, j, inputs, yData);
                if (!boolArr[0] && !boolArr[1] && !boolArr[2] && !boolArr[3]) {
                    set.add(i + "," + j);
                }

            }

        }
        return set;
    }

    private int getData2(int[][] arr, String index) {
        HashSet<String> set = new HashSet<>();
        set.add(index);
        ArrayList<String> list = new ArrayList<>();
        list.add(index);
        while (!list.isEmpty()) {
            ListIterator<String> it = list.listIterator();
            while (it.hasNext()) {
                String next = it.next();
                it.remove();
                String[] split = next.split(",");
                int x = Integer.parseInt(split[0]);
                int y = Integer.parseInt(split[1]);
                int y1 = y - 1;
                if (y > 0 && arr[x][y1] > arr[x][y] && !set.contains(x + "," + y1) && arr[x][y1] != 9) {
                    it.add(x + "," + y1);
                    set.add(x + "," + y1);
                }

                int x1 = x - 1;
                if (x > 0 && arr[x1][y] > arr[x][y] && !set.contains(x1 + "," + y) && arr[x1][y] != 9) {
                    it.add(x1 + "," + y);
                    set.add(x1 + "," + y);
                }

                int x2 = x + 1;
                if (x < arr.length - 1 && arr[x2][y] > arr[x][y] && !set.contains(x2 + "," + y) && arr[x2][y] != 9) {
                    it.add(x2 + "," + y);
                    set.add(x2 + "," + y);
                }

                int y2 = y + 1;
                if (y < arr[0].length - 1 && arr[x][y2] > arr[x][y] && !set.contains(x + "," + y2) && arr[x][y2] != 9) {
                    it.add(x + "," + y2);
                    set.add(x + "," + y2);
                }
            }
        }


        return set.size();
    }

}