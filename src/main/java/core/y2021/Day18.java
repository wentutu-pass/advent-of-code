package core.y2021;

import common.FileUtil;

import java.math.BigInteger;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;


public class Day18 {
    private static final Logger logger = Logger.getLogger("Day18");
    private static final String NUM_Patterns = "(?<=\\D)(?=\\d)|(?<=\\d)(?=\\D)|(?<=\\D)(?=\\D)";

    public static void main(String[] args) {
        String inputs = FileUtil.readFile("src/main/resources/y2021/day18.txt");
        logger.log(Level.INFO, "the ans1 is {0}", getANS1(inputs.split("\n")));
        logger.log(Level.INFO, "the ans2 is {0}", getANS2(inputs.split("\n")));
    }

    ////站变成字符串的方法    String   栈（string）
    public static String getStrFromStack(Stack<String> sta) {
        StringBuilder str = new StringBuilder();
        while (!sta.isEmpty()) {
            str.append(sta.pop());
        }
        return str.toString();
    }

    ////字符串变数字的方法   BIGINT  string
    public static BigInteger getNum(String str) {
        ArrayList<Integer> list = new ArrayList<>();
        String[] strArr = str.split(NUM_Patterns);
        for (String s : strArr) {
            if (s.matches("\\d+")) {
                list.add(Integer.parseInt(s));
            }
        }
        int leftNum = list.get(0);
        int rightNum = list.get(1);
        BigInteger left = BigInteger.valueOf(leftNum).multiply(BigInteger.valueOf(3));
        BigInteger right = BigInteger.valueOf(rightNum).multiply(BigInteger.valueOf(2));
        return left.add(right);
    }

    ////   ->取值放到字符串里
//1. 求BigInteger getMagnitudeNum{String}
//
//定义栈
//
//[[[[0,7],4],[[7,8],[6,0]]],[8,1]]
//
//遍历字符串放入栈中
// [[[[0,7   =》 [[[14
//遇到】
//另一个栈放入】 从栈中取值放入
//】7,0【
//直到遇到【  放入栈  -》 成新栈取值字符串【0,7】  -》计算值 14放入炸中
//
//-》遍历下一个字符循环
//
    public static BigInteger getMagnitudeNum(String str) {
        String[] strArr = str.split(NUM_Patterns);
        Stack<String> stack = new Stack<>();
        BigInteger num = BigInteger.ZERO;
        for (String s : strArr) {
            if (!s.equals("]")) {
                stack.add(s);
            } else {
                Stack<String> stack1 = new Stack<>();
                stack1.add(s);
                while (!stack.isEmpty()) {
                    if (stack.peek().equals("[")) {
                        stack1.add(stack.pop());
                        break;
                    }
                    stack1.add(stack.pop());
                }
                num = getNum(getStrFromStack(stack1));
                stack.add(num.toString());
            }

        }
        return num;

    }

    //2.加法   newSTR = 【 + A【0】，A【1】】
    public static String add(String str1, String str2) {
        return "[" + str1 + "," + str2 + "]";
    }

    //替换第一个数字
    public static String repFirsNum(String str, int num) {
        String[] strArr = str.split(NUM_Patterns);
        String str1 = "";
        for (String value : strArr) {
            if (value.matches("\\d+")) {
                str1 = value;
                break;
            }
        }
        if (!str1.equals("")) {
            str = str.replaceFirst(str1, String.valueOf(num + Integer.parseInt(str1)));
        }
        return str;
    }

    //替换最后一个数字
    public static String repLastNum(String str, int num) {
        String[] strArr = str.split(NUM_Patterns);
        String str1 = "";
        for (int i = strArr.length - 1; i >= 0; i--) {
            if (strArr[i].matches("\\d+")) {
                str1 = strArr[i];
                break;
            }
        }
        if (!str1.equals("")) {
            int i = str.lastIndexOf(str1);
            String newStr = String.valueOf(num + Integer.parseInt(str1));
            str = str.substring(0, i) + newStr + str.substring(i + str1.length());
        }
        return str;
    }

    public static String getStrFormArrByIndex(String[] arr, int index, boolean isBefore) {
        StringBuilder str = new StringBuilder();
        if (isBefore) {
            for (int i = 0; i < index; i++) {
                str.append(arr[i]);
            }
        } else {
            for (int i = index + 1; i < arr.length; i++) {
                str.append(arr[i]);
            }
        }
        return str.toString();
    }

    //
//3. 爆炸  [[[[0,7],4],[7,[[8,4],9]]],[1,1]]   -》 [[[[0,7],4],[15,[0,13]]],[1,1]]  返回String 看看有没有变化 没变法true boolean = 有变化变化
//
//定义栈
//遍历字符放栈
//
//[[,[7,[[8,4],9]]],[1,1]]
//1个站   2个取值
//[0,7][,4]   ->
//
//遇到【 同时已经有4个【  -》[a[i+1], a[i+3] ]->0  字符前面  字符获取第一个值  +对应值
//
////遍历字符0，a【i】 取位数-》替换    后面替换啊a[i+4]  -》凭借新字符0
////替换位数 和字符
    public static String explode(String str) {
        String[] strAttr = str.split(NUM_Patterns);
        Stack<String> stack = new Stack<>();
        int count = 0;
        int index = -1;
        for (int i = 0; i < strAttr.length; i++) {
            stack.add(strAttr[i]);
            if (strAttr[i].equals("[")) {
                if (count == 4) {
                    index = i;
                    break;
                }
                count++;
            } else if (strAttr[i].equals("]")) {
                while (!stack.isEmpty()) {
                    String peek = stack.peek();
                    stack.pop();
                    if (peek.equals("[") && count != 0) {
                        count--;
                        break;
                    }
                }
            }
        }

        if (index != -1) {
            int left = Integer.parseInt(strAttr[index + 1]);
            int right = Integer.parseInt(strAttr[index + 3]);
            String str1 = repLastNum(getStrFormArrByIndex(strAttr, index, true), left);
            String str2 = repFirsNum(getStrFormArrByIndex(strAttr, index + 4, false), right);
            return str1 + "0" + str2;
        }
        return str;
    }

    //4分裂[[[[0,7],4],[15,[0,13]]],[1,1]] =》[[[[0,7],4],[[7,8],[0,13]]],[1,1]]
//遍历字符遇到 大于10的 取前面字符0，a【i】  +【i/2.0 Math.floor(); ,i/2.0 Math.ceil(11.4)】|和面字符a[i+1]
//
//
//=>新字符
    public static String split(String str) {
        int index = -1;
        String[] strArr = str.split(NUM_Patterns);
        for (int i = 0; i < strArr.length; i++) {
            if (strArr[i].matches("\\d+") && Integer.parseInt(strArr[i]) >= 10) {
                index = i;
                break;
            }
        }
        if (index != -1) {
            String newStr = add(String.valueOf((int) Math.floor(Integer.parseInt(strArr[index]) / 2.0)), String.valueOf((int) Math.ceil(Integer.parseInt(strArr[index]) / 2.0)));
            str = getStrFormArrByIndex(strArr, index, true) + newStr + getStrFormArrByIndex(strArr, index, false);
        }
        return str;
    }

    //先 newSTR = 【 + A【0】，A【1】】 =》 daAction => str =>str
//两个都为false借宿 遍历   true 代表有变化 || true  -》瞒住一个false    再次开头设为true
//[[,[7,[[8,4],9]]],[1,1]]
//遍历字符
    public static BigInteger getANS1(String[] arr) {
        for (int i = 0; i < arr.length - 1; i++) {
            String str = add(arr[i], arr[i + 1]);
            str = doAction(str);
            arr[i + 1] = str;
            System.out.println("after cal: " + str);
        }
        return getMagnitudeNum(arr[arr.length - 1]);
    }

    private static String doAction(String str) {
        boolean isExplode = true;
        boolean isSplit = true;
        while (isExplode || isSplit) {
            String explode = explode(str);
            isExplode = !str.equals(explode);
            if (!isExplode) {
                String split = split(explode);
                isSplit = !split.equals(explode);
                str = split;
            } else {
                str = explode;
            }

        }
        return str;
    }

    //遍历 i j  0 -》length  得到两数  循环两个数  球最大的max
    public static BigInteger getANS2(String[] arr) {
        BigInteger max = BigInteger.ZERO;
        for (int i = 0; i < arr.length; i++) {
            for (int j = i + 1; j < arr.length - 1; j++) {
                String[] strArr = {add(arr[i], arr[j]), add(arr[j], arr[i])};
                for (String str1 : strArr) {
                    System.out.println("before str1 is : " + str1);
                    str1 = doAction(str1);
                    BigInteger magnitudeNum = getMagnitudeNum(str1);
                    max = max.compareTo(magnitudeNum) > 0 ? max : magnitudeNum;
                }
            }

        }
        return max;
    }

}
