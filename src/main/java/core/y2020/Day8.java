package core.y2020;

import common.FileUtil;

import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Day8 {
    private final Queue<Integer> searchQ = new LinkedList<>();
    private static final Logger logger = Logger.getLogger("Day8");

    public static void main(String[] args) {
        String inputs = FileUtil.readFile("src/main/resources/y2020/day8.txt");
        Day8 day8 = new Day8();
        logger.log(Level.INFO, "count1 {0}", day8.getAdd(inputs));
        day8.setSearchQ(inputs);
        logger.log(Level.INFO, "count2 {0}", day8.getAdd1(inputs, 0, false));
    }

    //nop +0
//acc +1
//jmp +4
//acc +3
//jmp -3
//acc -99
//acc +1
//jmp -4
//acc +6
    private int getAdd(String inputs) {
        HashSet<String> set = new HashSet<>();
        int count = 0;
        String[] string = inputs.split("\n");
        int i = 0;
        while (i >= 0 && i < string.length) {  //一个操作
            boolean add = set.add(i + " " + string[i]);
            if (!add) break;
            int[] intArr = getCount(string[i].split(" "), count, i, "");
            count = intArr[0];
            i = intArr[1];
        }
        return count;
    }

    private int getAdd1(String oldInp, int index, boolean replace) {
        HashSet<String> set = new HashSet<>();
        int count = 0;
        String[] string = oldInp.split("\n");
        int i = 0;
        while (i >= 0 && i < string.length) {  //一个操作
            String[] data = string[i].split(" ");
            String caoZ = data[0];

            if (replace && index == i) {
                caoZ = caoZ.equals("nop") ? "jmp" : "nop";
            }
            boolean add = set.add(i + " " + string[i]);
            if (!add) {
                if (!searchQ.isEmpty()) {
                    return getAdd1(oldInp, searchQ.poll(), true);
                } else {
                    return 0;
                }
            }

            int[] intArr = getCount(string[i].split(" "), count, i, caoZ);
            count = intArr[0];
            i = intArr[1];
        }
        return count;
    }

    private void setSearchQ(String oldInp) {
        String[] string = oldInp.split("\n");
        for (int i = 0; i < string.length; i++) {  //一个操作
            String[] data = string[i].split(" ");
            String caoZ = data[0];
            if (caoZ.equals("nop") || caoZ.equals("jmp")) {
                searchQ.add(i);
            }
        }
    }

    private int[] getCount(String[] data, int count, int i, String caoZ) {
        String caoZuo = caoZ.isEmpty() ? data[0] : caoZ;
        String suaSu = data[1].substring(0, 1);
        int suzi = Integer.parseInt(data[1].substring(1));
        if (caoZuo.equals("nop")) {
            i++;
        }
        if (suaSu.equals("+")) {
            if (caoZuo.equals("acc")) {
                count += suzi;
                i++;
            } else {
                i += suzi;
            }
        } else if (suaSu.equals("-")) {
            if (caoZuo.equals("acc")) {
                count -= suzi;
                i++;
            } else {
                i -= suzi;
            }
        }
        return new int[]{count, i};
    }
}
