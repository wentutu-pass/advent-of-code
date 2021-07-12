package y2020;
import common.Util;

import java.util.stream.Stream;

public class Day2 {

    public static void main(String[] args)  {
        String string = Util.readFile("src/main/resources/y2020/day2.txt");
        String[] inputs = string.split("\n");
        Day2 day2 = new Day2();
        //System.out.println(day1.getSum1(inputs));
        System.out.println(day2.getCountsOfValidPW2(inputs));


    }
/*1-7 j: vrfjljjwbsv
1-10 j: jjjjjjjjjjjj
9-13 s: jfxssvtvssvsbx
10-12 d: ddvddnmdnlvdddqdcqph
11-12 b: bbbbbbbbbrbnb
7-9 q: qqqqlqmqqq
8-11 z: zzzzzzzpzzl*/
    private int getCountsOfValidPW1(String[] inputs){
        int count = 0;
        for (int i=0;i<inputs.length;i++){
            String[] datas = inputs[i].split(" ");
            String[] range= datas[0].split("-");
            String letter = datas[1].replace(":", "");
            String str = datas[2];
            long count1 = Stream.of(str.split("")).filter(s -> s.equals(letter)).count();
            int size = (int) count1;
            if(size >= Integer.parseInt(range[0]) && size <=Integer.parseInt(range[1])){
                System.out.println(inputs[i]);
                count++;
            }
        }
        return count;
    }
    /*1-7 j: vrfjljjwbsv
    1-10 j: jjjjjjjjjjjj
    9-13 s: jfxssvtvssvsbx
    10-12 d: ddvddnmdnlvdddqdcqph
    11-12 b: bbbbbbbbbrbnb
    7-9 q: qqqqlqmqqq
    8-11 z: zzzzzzzpzzl*/
    private int getCountsOfValidPW2(String[] inputs){
        int count = 0;
        for (int i=0;i<inputs.length;i++){
            String[] datas = inputs[i].split(" ");
            String[] range= datas[0].split("-");
            String letter = datas[1].replace(":", "");
            String str = datas[2];
            String str1 = str.substring(Integer.parseInt(range[0]) - 1,Integer.parseInt(range[0]));
            String str2 = str.substring(Integer.parseInt(range[1]) - 1,Integer.parseInt(range[1]));

            if((letter.equals(str1) && !letter.equals(str2) ) || (!letter.equals(str1) && letter.equals(str2) )){
                System.out.println(inputs[i]);
                count++;
            }
        }
        return count;
    }


}
