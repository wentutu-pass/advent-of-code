package y2020;

import common.Util;
import org.apache.commons.lang3.StringUtils;
import java.util.*;


public class Day11 {
    private static HashMap<Integer, Integer> hashMap = new HashMap<>();
        private static Queue<Integer> queue = new LinkedList<>();
        HashMap<Integer, HashMap> map = new HashMap<>();;
        static int countOfOccupiedSeat = 0;

    public static void main(String[] args)  {
        String inputs = Util.readFile("src/main/resources/y2020/day11.txt");
        Day11 day11 = new Day11();
        String[] input = inputs.split("\n");
        //System.out.println(day11.getCountOfSeat1(input));
        day11.detailData(input);
        System.out.println(day11.getCountOfSeat2(input));
    }

    private int getCountOfSeat1(String[] inputs){
        HashMap<Integer, ArrayList<Integer>> map = new HashMap<>();
        int count=0;
        for(int i=0;i<inputs.length;i++){
            ArrayList<Integer> list = new ArrayList<>();
            String[] states = inputs[i].split("");
                for(int j = 0; j< states.length; j++){
                    if(states[j].equals(".")) continue;
                    int countOfOccupiedSeat = 0;
                    if(i>0){
                        String[] upRow = inputs[i-1].split("");
                        countOfOccupiedSeat += j>0 ? upRow[j-1].equals("#") ? 1 : 0 : 0;
                        countOfOccupiedSeat += upRow[j].equals("#") ? 1 : 0 ;
                        countOfOccupiedSeat += j<upRow.length-1 ? upRow[j+1].equals("#") ? 1 : 0 : 0;
                    }
                    if(i<inputs.length-1){
                        String[] downRow = inputs[i+1].split("");
                        countOfOccupiedSeat += j>0 ? downRow[j-1].equals("#") ? 1 : 0 : 0;
                        countOfOccupiedSeat += downRow[j].equals("#") ? 1 : 0 ;
                        countOfOccupiedSeat += j<downRow.length-1 ? downRow[j+1].equals("#") ? 1 : 0 : 0;
                    }
                    countOfOccupiedSeat += j>0 ? states[j-1].equals("#") ? 1 : 0 : 0;
                    countOfOccupiedSeat += j<states.length-1 ? states[j+1].equals("#") ? 1 : 0 : 0;

                    if((states[j].equals("#") && countOfOccupiedSeat>=4) || (states[j].equals("L") && countOfOccupiedSeat==0)){
                        list.add(j);
                    }

                    if(states[j].equals("#")) count++;
                }
                if(!list.isEmpty()) map.put(i,list);
        }
        if(!map.isEmpty()){
            changeStates(map,inputs);
            return getCountOfSeat1(inputs);
        }
        return count;

    }

    private  void changeStates(HashMap<Integer,ArrayList<Integer>> map, String[] inputs){
        Iterator<Map.Entry<Integer, ArrayList<Integer>>> iterator = map.entrySet().iterator();
        while(iterator.hasNext()){
            Map.Entry<Integer, ArrayList<Integer>> next = iterator.next();
            int row = next.getKey();
            ArrayList<Integer> value = next.getValue();
            for(int i=0;i<inputs.length;i++){
                if(i == row){
                    String[] states = inputs[i].split("");
                    for(int j = 0; j< states.length; j++){
                        if(value.contains(j)){
                            states[j] = states[j].equals("#") ? "L" : "#";
                            inputs[i] = StringUtils.join(states,"");
                            continue;
                        }
                    }
                    break;
                }
            }
        }
    }

    private void getCountOfOccupiedSeat(int row, int col, String[] input){
        int rows = input.length-1;
        int cols = input[0].length()-1;
        for(int i = row-1;i>=0;i--){
            String values = (String)((HashMap) map.get(i).values()).get(col);
            if(values.isEmpty()) continue;
            if(values.equals("#") )countOfOccupiedSeat++;
            break;
        }

        for(int i = row+1;i<=rows;i++){
            if(input[i].split("")[col].equals(".")) continue;
            if(input[i].split("")[col].equals("#")) countOfOccupiedSeat++;
            break;
        }

        for(int j = col-1;j>=0;j--){
            if(input[row].split("")[j].equals(".")) continue;
            if(input[row].split("")[j].equals("#")) countOfOccupiedSeat++;
            break;
        }

        for(int j = col+1;j<=cols;j++){
            if(input[row].split("")[j].equals(".")) continue;
            if(input[row].split("")[j].equals("#")) countOfOccupiedSeat++;
            break;
        }
    }

    private void getCountOfOccupiedSeat2(int row, int col, String[] input){
        int rows = input.length-1;
        int cols = input[0].length()-1;
        for(int i =0; i<rows;){
            if(i<row){
                int j = col-(row-i);
                if(j<=cols && j>=0){//zuoshang
                    if(input[i].split("")[j].equals(".")){
                        i++;
                        continue;
                    }
                    if(input[i].split("")[j].equals("#")){
                       countOfOccupiedSeat++;
                       break;
                    }
                    break;
                }
                i++;
            }else
                break;
        }
        for(int i =0; i<rows;){
            if(i<row){
                int j = col+(row-i);
                if(j<=cols && j>=0){//zuoshang
                    if(input[i].split("")[j].equals(".")){
                        i++;
                        continue;
                    }
                    if(input[i].split("")[j].equals("#")){
                        countOfOccupiedSeat++;
                        break;
                    }
                    break;
                }
                i++;
            }else
                break;
        }
        for(int i =rows; i>=0;){
            if(i>row){
                int j = col+(row-i);
                if(j<=cols && j>=0){//zuoshang
                    if(input[i].split("")[j].equals(".")){
                        i--;
                        continue;
                    }
                    if(input[i].split("")[j].equals("#")){
                        countOfOccupiedSeat++;
                        break;
                    }
                    break;
                }
                i--;
            }else
                break;
        }

        for(int i =rows; i>=0;){
            if(i>row){
                int j = col-(row-i);
                if(j<=cols && j>=0){//zuoshang
                    if(input[i].split("")[j].equals(".")){
                        i--;
                        continue;
                    }
                    if(input[i].split("")[j].equals("#")){
                        countOfOccupiedSeat++;
                        break;
                    }
                    break;
                }
                i--;
            }else
                break;
        }
    }

    private int getCountOfSeat2(String[] inputs){
        HashMap<Integer, ArrayList<Integer>> map = new HashMap<>();
        int count=0;
        for(int i=0;i<inputs.length;i++){  //row
            ArrayList<Integer> list = new ArrayList<>();
            String[] states = inputs[i].split("");
            for(int j = 0; j< states.length; j++){   //j
                countOfOccupiedSeat = 0;
                if(states[j].equals(".")) continue;
                getCountOfOccupiedSeat(i, j,inputs);
                getCountOfOccupiedSeat2(i,j,inputs);
                if((states[j].equals("#") && countOfOccupiedSeat>=5) || (states[j].equals("L") && countOfOccupiedSeat==0)){
                    list.add(j);
                }

                if(states[j].equals("#")) count++;
            }
            if(!list.isEmpty()) map.put(i,list);
        }
        if(!map.isEmpty()){
            changeStates(map,inputs);
            detailData(inputs);
            return getCountOfSeat2(inputs);
        }
        return count;

    }

    public HashMap<Integer, HashMap> detailData(String[] inputs){
        for (int i=0;i<inputs.length;i++){
            String[] split = inputs[i].split("");
            HashMap<Integer, String> map1 = new HashMap<Integer, String>();
            for(int j =0;i<split.length;j++){
                if(!split[j].equals(".")){
                    map1.put(j,split[j]);
                }
            }
            map.put(i,map1);
        }
        return map;
    }
}
