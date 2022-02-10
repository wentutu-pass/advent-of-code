package core.y2021;

import common.ArrayUtil;
import common.FileUtil;

import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Day15 {
    private static final Logger logger = Logger.getLogger("Day15");
    private static final List<Integer> gList = new ArrayList<>();

    public static void main(String[] args) {
        String string = FileUtil.readFile("src/main/resources/y2021/day15.txt");
        String[] inputs = string.split("\n");
        int[][] intArr = ArrayUtil.covertToTwoIntArr(inputs, "");
        Day15 day15 = new Day15();
        int[][] data = day15.getData(intArr);
        logger.log(Level.INFO, "the ans is {0}", day15.getAns1(data));
    }

    private void getList(String start, int[][] intArr, String end,int count) {
        int xLength = intArr.length - 1;
        int yLength = intArr[0].length - 1;
        String x = start.substring(0, 1);
        String y = start.substring(1, 2);
        if(!start.equals(end)){
            String[] stringArr = new String[2];
            stringArr[0]= Integer.parseInt(y)<yLength ? x.concat(String.valueOf(Integer.parseInt(y)+1)) : ""; //right
            stringArr[1]=Integer.parseInt(x)<xLength ? String.valueOf(Integer.parseInt(x)+1).concat(y) : ""; //down
            for(String adr : stringArr){
                if(adr.isEmpty()) continue;
                int value = intArr[Integer.parseInt(adr.substring(0, 1))][Integer.parseInt(adr.substring(1, 2))];
                getList(adr,intArr,end,count+value);
            }
        }else{
            gList.add(count);
        }
    }

    private int getAns1(int[][] intArr) {
        HashMap<String, Integer> map = new HashMap<>();
        map.put("0,0", 0);
        LinkedList<String> queue = new LinkedList<>();
        queue.add("0,0");
        int[] xArr = {0,1,-1,0};
        int[] yArr = {1,0,0,-1};
        while(!queue.isEmpty()){
            String poll = queue.poll();
            String x = poll.split(",")[0];
            int ix = Integer.parseInt(x);
            String y = poll.split(",")[1];
            int iy = Integer.parseInt(y);
            int value = map.getOrDefault(poll, 0);
            for(int i = 0; i<4 ; i++){
                int dx = ix+xArr[i];
                int dy = iy+yArr[i];
                String newStr = String.valueOf(dx).concat(",").concat(String.valueOf(dy));
                if(dx>=0 && dx<intArr.length && dy>=0 && dy<intArr[0].length){
                    int count = map.getOrDefault(newStr, 0);
                    if(count ==0 || count > value + intArr[dx][dy]){
                        map.put(newStr,value + intArr[dx][dy]);
                        queue.add(newStr);
                    }
                }
            }
        }
        String end = String.valueOf(intArr.length-1).concat(",").concat( String.valueOf(intArr[0].length-1));
        return map.get(end);
    }

    private int[][] getData(int[][] intArr) {
        int length = intArr.length;
        int yLength = intArr[0].length;
        int[][] ints = new int[length][yLength*15];
        ArrayUtil.copyTwoArr(intArr,ints);
        ArrayUtil.printTwoArr(ints);
        for(int j = 0; j<length; j++){
            for(int k = yLength; k<yLength*15; k++){
                ints[j][k] = ints[j][k-yLength]==9? 1 :  ints[j][k-yLength]+1;
            }
        }

        //x
        int length1 = ints.length;
        int yLength2 = ints[0].length;
        int[][] ints2 = new int[length1*15][yLength2];
        ArrayUtil.copyTwoArr(ints,ints2);
        ArrayUtil.printTwoArr(ints2);
        for(int j = length1; j<length1*15; j++){
            for(int k = 0; k<yLength2; k++){
                ints2[j][k] = ints2[j-length1][k]==9? 1 :  ints2[j-length1][k]+1;
            }
        }


        ArrayUtil.printTwoArr(ints2);
        return ints2;
    }


}
