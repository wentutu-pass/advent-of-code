package y2020;



import common.Util;

import java.util.*;

public class Day10 {
    private static HashMap<Integer, Integer> hashMap = new HashMap<>();
        private static Queue<Integer> queue = new LinkedList<>();
        private static HashMap<Integer, ArrayList<Integer>> map = new HashMap<>();

    public static void main(String[] args)  {
        String inputs = Util.readFile("src/main/resources/y2020/day10.txt");
        Day10 day6 = new Day10();
        String[] split = inputs.split("\n");
        int[] ints = Arrays.stream(split).mapToInt(Integer::parseInt).toArray();
        int[] ints1 = new int[ints.length + 1];
        System.arraycopy(ints,0,ints1,0,ints.length);
        Arrays.sort(ints1);
        int[] ints2 = new int[ints1.length + 1];
        System.arraycopy(ints1,0,ints2,0,ints1.length);
        ints2[ints2.length-1] = ints1[ints1.length-1]+3;
        Arrays.sort(ints2);

//        for (int i: ints) {
//            queue.add(i);
//        }

//        hashMap.put(1, 0);
//        hashMap.put(2, 0);
//        hashMap.put(3, 0);
//        HashMap<Integer, Integer> num = day6.getNum( 0);
//        System.out.println(num.get(1));
//        System.out.println(num.get(3)+1);
//        System.out.println(num.get(1) * (num.get(3)+1));
//        queue.add(0);
//        System.out.println(day6.getNums(ints));
//        day6.getMaps(ints);
//        System.out.println(day6.totalBags());

        System.out.println(day6.getPart2(ints2,0,new HashMap<>()));

    }

    private HashMap<Integer, Integer> getNum(int count){
        if(! queue.isEmpty()){
            Integer i = queue.poll();
            switch (i-count){
                case 1 :
                    int j = hashMap.get(1);
                    hashMap.put(1, j+1);
                    getNum(i);
                    break;
                case 2 :
                    int j1 = hashMap.get(2);
                    hashMap.put(2, j1+1);
                    getNum(i);
                    break;
                case 3 :
                    int j2 = hashMap.get(3);
                    hashMap.put(3, j2+1);
                    getNum(i);
                    break;
                default:break;
            }
        }
       return hashMap;
    }
//1     0
//4     1
//5     2
//6     3
//7     4
//10    5
//11    6
//12    7
//15    8
//16    9
//19    10

    //12    7
//13    8
//14    9
//15   10

    private Long getNums(int[] inputs ){
        long count = 0l;
        while(!queue.isEmpty()){
            int index = queue.poll();
            count++;
            for(int i=index; i<inputs.length;i++){ //<11
                if(i<inputs.length-2){ //<9   12 7
                    switch (inputs[i+1]-inputs[i]){//1
                        case 3: break;
                        case 1:
                        case 2: //     <9
                            if(i<inputs.length-2 && inputs[i+2]-inputs[i] <= 3){
                                queue.add(i+2);
                            }   // <8
                            if(i<inputs.length-3 && inputs[i+3]-inputs[i] <= 3){
                                queue.add(i+3);
                            }
                            break;
                    }
                }

            }
        }
        return count;
    }

    private void getMaps(int[] inputs){

        for(int i=0; i<inputs.length;i++){ //<11
            ArrayList<Integer> list = new ArrayList<>();
            if(i<inputs.length-1 && inputs[i+1]-inputs[i] <= 3){
                list.add(inputs[i+1]);
            }//<9   12 7
                if(i<inputs.length-2 && inputs[i+2]-inputs[i] <= 3){
                    list.add(inputs[i+2]);
                }   // <8
                if(i<inputs.length-3 && inputs[i+3]-inputs[i] <= 3){
                    list.add(inputs[i+3]);
                }
               map.put(inputs[i],list) ;

        }
    }

   /*
    if startIndex in cache:
        return cache[startIndex]

    if startIndex == len(adapters) - 1: # we're at the end
        cache[startIndex] = 1
        return 1

    jolts = adapters[startIndex]

    totalPaths = 0
    if jolts+1 in adapters:
        totalPaths += pathsToEnd(adapters, adapters.index(jolts+1), cache)
    if jolts+2 in adapters:
        totalPaths += pathsToEnd(adapters, adapters.index(jolts+2), cache)
    if jolts+3 in adapters:
        totalPaths += pathsToEnd(adapters, adapters.index(jolts+3), cache)

    cache[startIndex] = totalPaths
    return totalPaths */

    private long getPart2(int[] ints, int index, HashMap<Integer, Long> map){
        if(map.containsKey(index)){
            return map.get(index);
        }
        if(index == ints.length -1 ){
            map.put(index,1l);
            return 1l;
        }
        int value = ints[index];
        long totalPath = 0;
        if( -1 != binarySearch(ints, value+1)){
                    totalPath += getPart2(ints, binarySearch(ints, value+1), map);//1
         }
        if( -1 != binarySearch(ints, value+2)){
            totalPath += getPart2(ints, binarySearch(ints, value+2), map);
        }
        if( -1 != binarySearch(ints, value+3)){
            totalPath += getPart2(ints, binarySearch(ints, value+3), map);
        }

        map.put(index,totalPath);  //6(11),2
        return totalPath;
    }


        public  int binarySearch(int[] array,int target){
            int left=0;
            int right=array.length-1;
            int mid;
            while(left<=right){
                mid=left+(right-left)/2;//中间位置
                if(array[mid]==target){
                    return mid;
                }else if(target<array[mid]){//如果目标值小于当前Mid下标位置的值 则在前半区查找
                    right=mid-1;
                }else{//反之在后半区查找
                    left=mid+1;
                }
            }
            return -1;
        }

}
