package core.y2020;

import common.Util;

import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Day8 {
    private Queue<Integer> searchQ = new LinkedList<>();
    private static Logger logger = Logger.getLogger("Day8");

    public static void main(String[] args) {
        String inputs = Util.readFile("src/main/resources/y2020/day8.txt");
        Day8 day8 = new Day8();
        logger.log(Level.INFO, "count1 {0}", day8.getAdd(inputs));
        day8.getSearchQ(inputs);
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

            String[] data = string[i].split(" ");
            String caoz = data[0];
            String suansu = data[1].substring(0, 1);

            int suzi = Integer.parseInt(data[1].substring(1));
            if (caoz.equals("nop")) {
                i++;
            }
            if (suansu.equals("+")) {
                if (caoz.equals("acc")) {
                    count += suzi;
                    i++;
                } else {
                    i += suzi;
                }
            } else if (suansu.equals("-")) {
                if (caoz.equals("acc")) {
                    count -= suzi;
                    i++;
                } else {
                    i -= suzi;
                }
            }
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
            String caoz = data[0];
            String suansu = data[1].substring(0, 1);

            if (replace && index == i) {
                caoz = caoz.equals("nop") ? "jmp" : "nop";
            }
            boolean add = set.add(i + " " + string[i]);
            if (!add) {
                if (!searchQ.isEmpty()) {
                    return getAdd1(oldInp, searchQ.poll(), true);
                } else {
                    return 0;
                }
            }
            int suzi = Integer.parseInt(data[1].substring(1));
            if (caoz.equals("nop")) {
                i++;
            }
            if (suansu.equals("+")) {
                if (caoz.equals("acc")) {
                    count += suzi;
                    i++;
                } else {
                    i += suzi;

                }
            } else if (suansu.equals("-")) {
                if (caoz.equals("acc")) {
                    count -= suzi;
                    i++;
                } else {
                    i -= suzi;
                }
            }
        }
        return count;
    }

    private Queue<Integer> getSearchQ(String oldInp) {
        String[] string = oldInp.split("\n");
        for (int i = 0; i < string.length; i++) {  //一个操作
            String[] data = string[i].split(" ");
            String caoz = data[0];
            if (caoz.equals("nop") || caoz.equals("jmp")) {
                searchQ.add(i);
            }
        }
        return searchQ;
    }
}
