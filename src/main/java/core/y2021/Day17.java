package core.y2021;

import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Day17 {
    private static final Logger logger = Logger.getLogger("Day17");
    public static ArrayList<Integer> list = new ArrayList<>();
    public static HashSet<String> set = new HashSet<>();

    public static int x0 = 281;
    public static int x1 = 311;
    public static int y0 = -74;
    public static int y1 = -54;
    //target area: x=281..311, y=-74..-54

    public static void main(String[] args) {
        logger.log(Level.INFO, "the ans1 is {0}", getANS1());
        logger.log(Level.INFO, "the ans2 is {0}", getANS2());
    }

    //xv: [1 -> x1 +1 )
//
//yv: [y0+1 -> abs(y0))
//
//打印速度。。。。。。
//得到 速度，初始位置，list -》递归
//
//知道x>x1 y<y0 -> return
//放list
//
//如果在范围厄内   x0<=x<=x1    yo<=y<=y1   取list里最高的 存放到global list里   打印落脚点和 y
    public static int getANS1() {
        //target area: x=20..30, y=-10..-5
        for (int xv = 1; xv < x1 + 1; xv++) {
            for (int yv = y0; yv < Math.abs(y0); yv++) {
                System.out.println("速度： " + xv + " " + yv);
                ArrayList<Integer> list1 = new ArrayList<>();
                calPoint(xv, yv, list1, 0, 0);
            }
        }
        OptionalInt max = list.stream().mapToInt(Integer::valueOf).max();
        if (max.isPresent()) {
            return max.getAsInt();
        }
        return 0;
    }

    public static int getANS2() {
        //target area: x=20..30, y=-10..-5
        for (int xv = 1; xv < x1 + 1; xv++) {
            for (int yv = y0; yv < Math.abs(y0); yv++) {
                calPoint(xv, yv, 0, 0, xv, yv);
            }
        }
        return set.size();
    }

    public static ArrayList<Integer> calPoint(int xv, int yv, ArrayList<Integer> list1, int x, int y) {
        if (x > x1 || y < y0) {
            return list;
        }
        list1.add(y);
        if (x0 <= x && x <= x1 && y0 <= y && y <= y1) {
            System.out.println("落点：" + x + " " + y);
            OptionalInt max = list1.stream().mapToInt(Integer::valueOf).max();
            if (max.isPresent()) {
                list.add(max.getAsInt());
            }
        }
        x = x + xv;
        y = y + yv;
        xv = xv == 0 ? xv : xv - 1;
        yv = yv - 1;
        return calPoint(xv, yv, list1, x, y);
    }

    public static HashSet<String> calPoint(int xv, int yv, int x, int y, int x0v, int y0v) {
        if (x > x1 || y < y0) {
            return set;
        }
        if (x0 <= x && y <= y1) {
            // System.out.println("落点："+x+" "+y);
            System.out.println("速度：" + x0v + " " + y0v);
            set.add(x0v + "," + y0v);
        }
        x = x + xv;
        y = y + yv;
        xv = xv == 0 ? xv : xv - 1;
        yv = yv - 1;
        return calPoint(xv, yv, x, y, x0v, y0v);
    }
}
