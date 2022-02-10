package core.y2020;

import common.FileUtil;

import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Day10 {
    private static final Logger logger = Logger.getLogger("Day10");
    private static final HashMap<Integer, Integer> hashMap = new HashMap<>();
    private static final Queue<Integer> queue = new LinkedList<>();

    public static void main(String[] args) {
        String inputs = FileUtil.readFile("src/main/resources/y2020/day10.txt");
        Day10 day10 = new Day10();
        String[] split = inputs.split("\n");
        int[] ints = Arrays.stream(split).mapToInt(Integer::parseInt).toArray();
        int[] ints1 = new int[ints.length + 1];
        System.arraycopy(ints, 0, ints1, 0, ints.length);
        Arrays.sort(ints1);
        int[] ints2 = new int[ints1.length + 1];
        System.arraycopy(ints1, 0, ints2, 0, ints1.length);
        ints2[ints2.length - 1] = ints1[ints1.length - 1] + 3;
        Arrays.sort(ints2);
        logger.log(Level.INFO, "the value of part2 is {0}", day10.getPart2(ints2, 0, new HashMap<>()));
    }

    private HashMap<Integer, Integer> getNum(int count) {
        if (!queue.isEmpty()) {
            Integer i = queue.poll();
            switch (i - count) {
                case 1:
                    int j = hashMap.get(1);
                    hashMap.put(1, j + 1);
                    getNum(i);
                    break;
                case 2:
                    int j1 = hashMap.get(2);
                    hashMap.put(2, j1 + 1);
                    getNum(i);
                    break;
                case 3:
                    int j2 = hashMap.get(3);
                    hashMap.put(3, j2 + 1);
                    getNum(i);
                    break;
                default:
                    break;
            }
        }
        return hashMap;
    }
//1     0
//4     1
//5     2
//6     3
//7     4
//10    5
//11    6
//12    7
//15    8
//16    9
//19    10

    //12    7
//13    8
//14    9
//15   10

    private Long getNums(int[] inputs) {
        long count = 0;
        while (!queue.isEmpty()) {
            int index = queue.poll();
            count++;
            for (int i = index; i < inputs.length; i++) { //<11
                if (i < inputs.length - 2) { //<9   12 7
                    switch (inputs[i + 1] - inputs[i]) {//1
                        case 1:
                        case 2: //     <9
                            if (i < inputs.length - 2 && inputs[i + 2] - inputs[i] <= 3) {
                                queue.add(i + 2);
                            }   // <8
                            if (i < inputs.length - 3 && inputs[i + 3] - inputs[i] <= 3) {
                                queue.add(i + 3);
                            }
                            break;
                        default:
                            break;
                    }
                }

            }
        }
        return count;
    }

    private long getPart2(int[] ints, int index, HashMap<Integer, Long> map) {
        if (map.containsKey(index)) {
            return map.get(index);
        }
        if (index == ints.length - 1) {
            map.put(index, 1L);
            return 1L;
        }
        int value = ints[index];
        long totalPath = 0;
        if (-1 != binarySearch(ints, value + 1)) {
            totalPath += getPart2(ints, binarySearch(ints, value + 1), map);//1
        }
        if (-1 != binarySearch(ints, value + 2)) {
            totalPath += getPart2(ints, binarySearch(ints, value + 2), map);
        }
        if (-1 != binarySearch(ints, value + 3)) {
            totalPath += getPart2(ints, binarySearch(ints, value + 3), map);
        }

        map.put(index, totalPath);  //6(11),2
        return totalPath;
    }


    public int binarySearch(int[] array, int target) {
        int left = 0;
        int right = array.length - 1;
        int mid;
        while (left <= right) {
            mid = left + (right - left) / 2;//中间位置
            if (array[mid] == target) {
                return mid;
            } else if (target < array[mid]) {//如果目标值小于当前Mid下标位置的值 则在前半区查找
                right = mid - 1;
            } else {//反之在后半区查找
                left = mid + 1;
            }
        }
        return -1;
    }

}
