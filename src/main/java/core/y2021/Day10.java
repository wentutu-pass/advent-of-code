package core.y2021;

import common.FileUtil;

import java.math.BigInteger;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Day10 {
    private static final Logger logger = Logger.getLogger("Day10");

    public static void main(String[] args) {
        String inputs = FileUtil.readFile("src/main/resources/y2021/day10.txt");
        String[] split = inputs.split("\n");
        logger.log(Level.INFO, "the ans1 is {0}", getAns1(split));
        logger.log(Level.INFO, "the ans2 is {0}", getAns2(split));
    }

    public static String getEndStr(String letter) {
        String mapStr = "";
        switch (letter) {
            case "(":
                mapStr = ")";
                break;
            case "[":
                mapStr = "]";
                break;
            case "<":
                mapStr = ">";
                break;
            case "{":
                mapStr = "}";
                break;
            default:
                break;

        }
        return mapStr;
    }

    public static int getNumByStr(String letter) {
        int num = 0;
        switch (letter) {
            case ")":
                num = 3;
                break;
            case "]":
                num = 57;
                break;
            case "}":
                num = 1197;
                break;
            case ">":
                num = 25137;
                break;
            default:
                break;

        }
        return num;
    }

    public static boolean isStartStr(String letter) {
        return letter.equals("(") || letter.equals("[") || letter.equals("{") || letter.equals("<");
    }

    public static int getAns1(String[] dataArr) {
        ArrayList<Integer> list = new ArrayList<>();
        for (String line : dataArr) {
            Stack<String> stack = new Stack<>();
            for (String letter : line.split("")) {
                if (isStartStr(letter)) {
                    stack.push(letter);
                } else if (!stack.isEmpty() && !letter.equals(getEndStr(stack.pop()))) {
                    list.add(getNumByStr(letter));
                    break;
                }

            }
        }
        return list.stream().mapToInt(Integer::intValue).sum();
    }

    public static BigInteger getAns2(String[] dataArr) {
        ArrayList<BigInteger> list1 = new ArrayList<>();
        for (String line : dataArr) {
            ArrayList<Integer> list = new ArrayList<>();
            Stack<String> stack = new Stack<>();
            boolean corrupted = false;
            for (String letter : line.split("")) {
                if (isStartStr(letter)) {
                    stack.push(letter);
                } else if (!stack.isEmpty() && !letter.equals(getEndStr(stack.pop()))) {
                    corrupted = true;
                    list.add(getNumByStr(letter));
                    break;
                }

            }

            if (!corrupted) {
                while (!stack.isEmpty()) {
                    list.add(getPointsByStr2(stack.pop()));
                }
                BigInteger count = new BigInteger("0");
                for (int num : list) {
                    count = count.multiply(new BigInteger("5")).add(new BigInteger(String.valueOf(num)));
                }
                list1.add(count);

            }
        }
        Collections.sort(list1);

        return list1.get(list1.size() / 2);
    }

    public static int getPointsByStr2(String letter) {
        int num = 0;
        switch (letter) {
            case "(":
                num = 1;
                break;
            case "[":
                num = 2;
                break;
            case "{":
                num = 3;
                break;
            case "<":
                num = 4;
                break;
            default:
                break;

        }
        return num;
    }
}
