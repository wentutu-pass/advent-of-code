package core.y2021;

import common.FileUtil;

import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Day4 {
    private static final Logger logger = Logger.getLogger("Day4");

    public static void main(String[] args) {
        String inputs = FileUtil.readFile("src/main/resources/y2021/Day4.txt");
        String[] string = inputs.split("\n\n");
        Day4 Day4 = new Day4();
        List<HashMap<String, List<String>>> dataList = Day4.getDataList(string);
        String randNum = string[0];
        int[] orders = Day4.getOrders(randNum, dataList);
        logger.log(Level.INFO, "the ans1 is {0}", Day4.getAns1(orders, dataList));

        int[] order2s = Day4.getOrders2(randNum, dataList);
        logger.log(Level.INFO, "the ans2 is {0}", Day4.getAns1(order2s, dataList));
    }

    private List<HashMap<String, List<String>>> getDataList(String[] string) {


        List<HashMap<String, List<String>>> bList = new ArrayList<>();
        for (int i = 1; i < string.length; i++) {  //一个系列
            HashMap<String, List<String>> map = new HashMap<>();
            String[] lines = string[i].split("\n");
            for (int j = 0; j < lines.length; j++) { //行
                String[] nums = lines[j].trim().split(" {1,2}");
                map.put("x" + j, new ArrayList<>(Arrays.asList(nums)));
                for (int k = 0; k < nums.length; k++) { //列
                    List<String> list = map.get("y" + k);
                    if (null == list) {
                        list = new ArrayList<>();
                    }
                    list.add(nums[k]);
                    map.put("y" + k, list);
                }
            }
            bList.add(map);

        }
        return bList;
    }

    private int[] getOrders(String randNum, List<HashMap<String, List<String>>> dataList) {
        for (String num : randNum.split(",")) {
            for (int i = 0; i < dataList.size(); i++) {  //list
                boolean remove = detailMap(dataList.get(i), num);
                if (remove) {
                    return new int[]{Integer.parseInt(num), i};
                }
            }

        }
        return new int[]{};
    }

    private boolean detailMap(HashMap<String, List<String>> map, String num) {
        boolean remove = false;
        for (Map.Entry<String, List<String>> stringListEntry : map.entrySet()) {  //map
            List<String> var = stringListEntry.getValue();
            Iterator<String> it = var.iterator();
            while (it.hasNext()) {
                boolean isSame = it.next().equals(num);
                if (isSame && var.size() == 1) {
                    remove = true;
                }
                if (isSame) {
                    it.remove();
                }

            }
        }
        return remove;
    }

    private int getAns1(int[] orders, List<HashMap<String, List<String>>> dataList) {
        int num = orders[0];
        int key = orders[1];
        HashSet<String> strings = new HashSet<>();
        HashMap<String, List<String>> map = dataList.get(key);
        for (Map.Entry<String, List<String>> stringListEntry : map.entrySet()) {
            List<String> value = stringListEntry.getValue();
            strings.addAll(value);
        }
        int sum = strings.stream().mapToInt(Integer::parseInt).sum();
        return num * sum;

    }

    private int[] getOrders2(String randNum, List<HashMap<String, List<String>>> dataList) {
        for (String num : randNum.split(",")) {
            for (int i = 0; i < dataList.size(); i++) {  //list
                boolean remove = detailMap(dataList.get(i), num);
                if (remove) {
                    if (dataList.size() == 1) {
                        return new int[]{Integer.parseInt(num), i};
                    }
                    dataList.remove(i);
                    return getOrders2(randNum, dataList);
                }
            }

        }
        return new int[]{};
    }
}
