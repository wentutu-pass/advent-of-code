package y2020;

import common.Util;

import java.util.*;

public class Day6 {

    public static void main(String[] args)  {
        String inputs = Util.readFile("src/main/resources/y2020/day6.txt");
        Day6 day6 = new Day6();
        //System.out.println(day6.getSumOfAnyYes(inputs));
        System.out.println(day6.getSumOfEveryYes(inputs));

    }

    private int getSumOfAnyYes(String inputs){
        int count = 0;
        String[] string = inputs.split("\n\n");
        for (String input : string) {  //一组人
            Set<String> set = new HashSet<>();
            String[] datas = input.split("\n"); // 每个人的回答
            for (String data: datas) {
                for (String yes: data.split("")) {
                    set.add(yes);
                }
            }
            //System.out.println(set.size());
            count += set.size();
        }
        return count;
    }

    private int getSumOfEveryYes(String inputs){
        int count = 0;
        String[] string = inputs.split("\n\n");
        for (String input : string) {  //一组人
            Set<String> set = new HashSet<>();
            String[] datas = input.split("\n"); // 每个人的回答
            if(datas.length ==1){
                count += datas[0].length();
                System.out.println(datas[0].length());
                continue;
            }
            List<String> firstAnws = new ArrayList<>(Arrays.asList(datas[0].split("")));
            for (int i=1;i<datas.length;i++){//第二个人的回答
                List<String> list = Arrays.asList(datas[i].split(""));
                firstAnws.removeIf(yes -> !list.contains(yes));
            }
            System.out.println(firstAnws.size());
            count += firstAnws.size();
        }
        return count;
    }
}
