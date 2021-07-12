package y2020;

import common.Util;

import java.util.Arrays;

public class Day1 {
    public static void main(String[] args)  {
        String string = Util.readFile("src/main/resources/y2020/day1.txt");
        String[] inputs = string.split("\n");
        Day1 day1 = new Day1();
        //System.out.println(day1.getSum1(inputs));
        System.out.println(day1.getSum2(inputs));
    }

    private int getSum1(String[] inputs){
        int[] datas = Arrays.stream(inputs).mapToInt(Integer::parseInt).toArray();
        for (int i=0;i<datas.length;i++){
            for (int j=i+1;j<datas.length;j++){
                if( datas[i] + datas[j] == 2020){
                    return datas[i]*datas[j];
                }
            }
        }
        return 0;
    }

    private int getSum2(String[] inputs){
        int[] datas = Arrays.stream(inputs).mapToInt(Integer::parseInt).toArray();
        for (int i=0;i<datas.length;i++){
            for (int j=i+1;j<datas.length;j++){
                for (int k=j+1;k<datas.length;k++){
                    if( datas[i] + datas[j]+datas[k] == 2020){
                        System.out.println(datas[i]);
                        System.out.println(datas[j]);
                        System.out.println(datas[k]);
                        return datas[i]*datas[j]*datas[k];
                    }
                }
            }
        }
        return 0;
    }

}
