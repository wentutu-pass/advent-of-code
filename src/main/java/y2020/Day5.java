package y2020;

import common.Util;

import java.util.ArrayList;

public class Day5 {
    public static void main(String[] args)  {
        String string = Util.readFile("src/main/resources/y2020/day5.txt");
        String[] inputs = string.split("\n");
        Day5 day1 = new Day5();
        //System.out.println(day1.getMaxSeatId(inputs));
        System.out.println(day1.getYoursSeatId(inputs));


    }
//    R:B:
//    L:F:
    private int getMaxSeatId(String[] inputs){
        int maxSeatId = 0;
        for (String input : inputs){
            String rows = input.substring(0, 7);
            String cols = input.substring(7);
            int row = getNumByLetter(0, 127, rows);
            int col = getNumByLetter(0, 7, cols);
            int i = row * 8 + col;
            System.out.println(row +" "+col+" "+i);
            maxSeatId = i > maxSeatId ? i : maxSeatId;
        }
        return maxSeatId;
    }

    private int getNumByLetter(int min, int max, String inputs){
        String letter = inputs.split("")[0];
        if(inputs.length() >1 ){
            inputs = inputs.substring(1);
        }

        if( letter.equals("R") || letter.equals("B")){
            min = max- (max-min)/2;
            max = max;
        }else if( letter.equals("L") || letter.equals("F") ){
            min = min;
            max = (max-min)/2 + min;
        }

        if( min == max) return min;
        return getNumByLetter(min, max, inputs);
    }

    private int getYoursSeatId(String[] inputs){
        ArrayList<Integer> list = new ArrayList<>();
        for (String input : inputs){
            String rows = input.substring(0, 7);
            String cols = input.substring(7);
            int row = getNumByLetter(0, 127, rows);
            int col = getNumByLetter(0, 7, cols);
            int i = row * 8 + col;
            list.add(i);
        }
        for (int i=80; i<=919; i++){
            if(!list.contains(i)){
                return i;
            }
        }
        list.sort(Integer::compareTo);
        for (Integer i: list) {
            System.out.println(i);
        }
       // System.out.println(list.size());
        return list.get(0);
    }

}
