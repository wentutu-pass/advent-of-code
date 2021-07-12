package y2020;

import java.io.File;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Day7 {
    public static void main(String[] args) {
        File inputFile = new File("src/main/resources/y2020/day7.txt");
        Pattern parentPattern = Pattern.compile("^\\w+ \\w+");
        Pattern childPattern = Pattern.compile("\\d+ \\w+ \\w+");
        Pattern childNumberPattern = Pattern.compile("\\d");
        Pattern childColorPattern = Pattern.compile("[a-zA-Z]+ [a-zA-Z]+");
        HashMap<String,HashMap<String, Integer>> bagContainers = new HashMap<>();


        try{
            Scanner fileScanner = new Scanner(inputFile);
            while(fileScanner.hasNext()){
                String nextLine = fileScanner.nextLine();
                Matcher parentMatch = parentPattern.matcher(nextLine);
                parentMatch.find();
                String parent = parentMatch.group();

                Matcher childMatch = childPattern.matcher(nextLine);
                HashMap<String, Integer> child = new HashMap<>();
                while(childMatch.find()){
                    String group = childMatch.group();
                    Matcher childNumberMatcher = childNumberPattern.matcher(group);
                    int childNUmber = 0;
                    while(childNumberMatcher.find()){
                        childNUmber = Integer.parseInt(childNumberMatcher.group());
                    }
                    String childColor="";
                    Matcher childColorMatcher = childColorPattern.matcher(group);
                    while(childColorMatcher.find()){
                        childColor = childColorMatcher.group();
                    }
                    if(!childColor.isEmpty())
                    child.put(childColor,childNUmber);
                }
                if(!child.isEmpty())
                bagContainers.put(parent,child);
            }

        }catch (Exception e){
            e.printStackTrace();
        }finally{
            //System.out.println(totalBags(bagContainers));
            System.out.println(totalBags2(bagContainers));
        }
    }

    public static int totalBags(HashMap<String, List<String>> myBags){
        HashSet<String> hashSet = new HashSet<>();//存放找出来的颜色 不重复
        Queue<String> searchQ = new LinkedList<>();//存放要寻找的颜色  找完就删除
        searchQ.add("shiny gold");
        while(!searchQ.isEmpty()){
            String nextSearch = searchQ.poll();
            Set<String> nextSearchSet = myBags.entrySet()
                    .stream()
                    .filter(kvEntry -> kvEntry.getValue().stream().anyMatch(s1 -> s1.contains(nextSearch)))
                    .map(Map.Entry::getKey)
                    .collect(Collectors.toSet());
            for(String child : nextSearchSet){
                searchQ.add(child);
                hashSet.add(child);
            }
        }

        return hashSet.size();
    }

    private static int totalBags2(HashMap<String,HashMap<String, Integer>> myBags){
        int count = 0;
        Queue<String> searchQ = new LinkedList<>();//存放要寻找的颜色  找完就删除
        searchQ.add("shiny gold");
        while(!searchQ.isEmpty()) {
            String nextSearch = searchQ.poll();
            List<HashMap<String, Integer>> shiny_gold = myBags.entrySet()
                .stream()
                .filter(kvEntry -> kvEntry.getKey().equals(nextSearch))
                .map(Map.Entry::getValue).collect(Collectors.toList());// 找到subBag
        for (HashMap<String, Integer> map : shiny_gold){
            Iterator it = map.entrySet().iterator();
            while (it.hasNext()) {
                Map.Entry entry = (Map.Entry)it.next();
                String bagName = (String)entry.getKey();
                int value = (Integer)entry.getValue();
                for (int i=0;i<value;i++) {
                    searchQ.add(bagName);
                    count++;
                }
            }
        }
        }
        return count;
    }
}
