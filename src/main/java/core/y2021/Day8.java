package core.y2021;

import common.FileUtil;

import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Day8 {
    private static final Logger logger = Logger.getLogger("Day8");

    public static void main(String[] args) {
        String inputs = FileUtil.readFile("src/main/resources/y2021/day8.txt");
        String[] dataArr = inputs.split("\n");
        logger.log(Level.INFO, "the ans1 is {0}", getAns1(dataArr));
        logger.log(Level.INFO, "the ans2 is {0}", getAns2(dataArr));
    }

    public static HashMap<String, Integer> getData2(String data) {
        String[] strArr = data.split(" ");
        HashMap<Integer, String> map1 = new HashMap<>();
        HashMap<String, Integer> map2 = new HashMap<>();
        ArrayList<String> list5 = new ArrayList<>();
        ArrayList<String> list6 = new ArrayList<>();
        for (String str : strArr) {
            String[] split = str.split("");
            Arrays.sort(split);
            str = String.join("", split);
            switch (str.length()) {
                case 2:
                    map1.put(1, str);
                    map2.put(str, 1);
                    break;
                case 4:
                    map1.put(4, str);
                    map2.put(str, 4);
                    break;
                case 3:
                    map1.put(7, str);
                    map2.put(str, 7);
                    break;
                case 7:
                    map1.put(8, str);
                    map2.put(str, 8);
                    break;
                case 5:
                    list5.add(str);
                    break;
                case 6:
                    list6.add(str);
                    break;
                default:
                    break;
            }
        }

        String a = getDiff(map1.get(1), map1.get(7));
        String bd = getDiff(map1.get(1), map1.get(4));
        String abd = a.concat(bd);
        String fg = "";
        Iterator<String> it5 = list5.iterator();
        while (it5.hasNext()) {
            String next = it5.next();
            if (isContain(bd, next)) {
                map1.put(5, next);
                map2.put(next, 5);
                fg = getDiff(next, abd);
                it5.remove();
                break;
            }
        }
        Iterator<String> it51 = list5.iterator();
        while (it51.hasNext()) {
            String next = it51.next();
            if (isContain(fg, next)) {
                map1.put(3, next);
                map2.put(next, 3);
                it51.remove();
                break;
            }
        }

        Optional<String> first = list5.stream().findFirst();
        if (first.isPresent()) {
            String str2 = first.get();
            map1.put(2, str2);
            map2.put(str2, 2);
        }

        Iterator<String> it6 = list6.iterator();
        while (it6.hasNext()) {
            String next = it6.next();
            if (isContain(map1.get(3), next)) {
                map1.put(9, next);
                map2.put(next, 9);
                it6.remove();
            } else if (isContain(map1.get(5), next)) {
                map1.put(6, next);
                map2.put(next, 6);
                it6.remove();
            }
        }

        Optional<String> first1 = list6.stream().findFirst();
        if (first1.isPresent()) {
            String str0 = first1.get();
            map1.put(0, str0);
            map2.put(str0, 0);
        }

        return map2;
    }

    public static int getAns1(String[] dataArr) {
        int count = 0;
        for (String data : dataArr) {
            String[] split = data.split(" \\| ");
            String[] signal = split[1].split(" ");
            count += Arrays.stream(signal).filter(l -> l.length() == 2 || l.length() == 4 || l.length() == 3 || l.length() == 7).count();
        }
        return count;
    }

    public static int getAns2(String[] dataArr) {
        int[] nums = new int[dataArr.length];
        for (int i = 0; i < dataArr.length; i++) {
            String[] split = dataArr[i].split(" \\| ");
            HashMap<String, Integer> data2 = getData2(split[0]);
            String[] signal = split[1].split(" ");
            StringBuilder a = new StringBuilder();
            for (String s : signal) {
                String[] split1 = s.split("");
                Arrays.sort(split1);
                a.append(data2.get(String.join("", split1)));
            }
            if (!a.toString().equals("")) {
                nums[i] = Integer.parseInt(a.toString());
            }

        }
        return Arrays.stream(nums).sum();
    }

    public static String getDiff(String str1, String str2) {
        Set<String> set1 = new HashSet<>(Arrays.asList(str1.split("")));
        Set<String> set2 = new HashSet<>(Arrays.asList(str2.split("")));
        Set<String> set3 = new HashSet<>(set2);

        set3.addAll(set1);
        set1.retainAll(set2);
        set3.removeAll(set1);
        return String.join("", set3);
    }

    public static boolean isContain(String str1, String str2) {
        if (str1.equals(str2)) {
            return false;
        }

        boolean b = str1.length() > str2.length();
        byte[] bytes;
        if (b) {
            bytes = Arrays.stream(str2.split("")).filter(l -> !str1.contains(l)).findFirst().orElse("").getBytes();
        } else {
            bytes = Arrays.stream(str1.split("")).filter(l -> !str2.contains(l)).findFirst().orElse("").getBytes();
        }
        return bytes.length == 0;
    }


}