package core.y2021;

import common.FileUtil;

import java.math.BigInteger;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day16 {
    private static final Logger logger = Logger.getLogger("Day16");
    private static String gStr = "";
    private static final HashMap<String, String> map = new HashMap<>();
    private static final String Literal_Patterns = "(\\d{3}\\d{3}(1\\d{4})*0\\d{4})?";

    public static void main(String[] args) {
        Day16 day16 = new Day16();
        day16.prepareMap();
        String inputs = FileUtil.readFile("src/main/resources/y2021/day16.txt");
        String data = day16.getDataByInputs(inputs);
        logger.log(Level.INFO, "tha ans1 is {0}", day16.doData(data, 0));
        logger.log(Level.INFO, "the ans2 is {0}", day16.doData2(new ArrayList<>(), data, 0));
    }

    private boolean isSubPLength(String inputs) {
        String version = inputs.substring(6, 7);
        return "0".equals(version);
    }

    private int getVersion(String inputs) {
        String version = inputs.substring(0, 3);
        return Integer.parseInt(version, 2);
    }

    private boolean isLiteral(String inputs) {
        String id = inputs.substring(3, 6);
        int num = Integer.parseInt(id, 2);
        return num == 4;
    }

    public void prepareMap() {
        String data = "0 = 0000\n" +
                "1 = 0001\n" +
                "2 = 0010\n" +
                "3 = 0011\n" +
                "4 = 0100\n" +
                "5 = 0101\n" +
                "6 = 0110\n" +
                "7 = 0111\n" +
                "8 = 1000\n" +
                "9 = 1001\n" +
                "A = 1010\n" +
                "B = 1011\n" +
                "C = 1100\n" +
                "D = 1101\n" +
                "E = 1110\n" +
                "F = 1111";
        String[] dataAttr = data.split("\n");
        for (String line : dataAttr) {
            String[] values = line.split(" = ");
            map.put(values[0], values[1]);
        }


    }

    public String getDataByInputs(String input) {
        String newStr = "";
        for (String letter : input.split("")) {
            newStr = newStr.concat(map.get(letter));
        }
        return newStr;
    }

    public int doData(String str, int count) {
        if (str.length() < 10) {
            return count;
        }

        count += getVersion(str);
        if (isLiteral(str)) {
            Pattern pattern = Pattern.compile(Literal_Patterns);
            Matcher matcher = pattern.matcher(str);
            if (matcher.find()) {
                return doData(str.substring(matcher.group(0).length()), count); //新数据

            }
        } else {
            if (isSubPLength(str)) {
                String sLength = str.substring(7, 22);
                int length = Integer.parseInt(sLength, 2);
                String[] strArr = new String[]{str.substring(22, 22 + length), str.substring(22 + length)};
                for (String string : strArr) {
                    count = doData(string, count); //新数据
                }
            } else {
                //往后面找count个包
                return doData(str.substring(18), count);
            }
        }

        return count;
    }

    public ArrayList<BigInteger> doData2(ArrayList<BigInteger> list, String str, int count) {
        if (str.length() < 10) {
            return list;
        }

        String id = str.substring(3, 6);
        int num = Integer.parseInt(id, 2);
        //count += getVersion(str);
        if (num == 4) {
            Pattern pattern = Pattern.compile(Literal_Patterns);
            Matcher matcher = pattern.matcher(str);
            if (matcher.find()) {
                String str2 = matcher.group(0);

                BigInteger i = getLValue(str2.substring(6));
                list.add(i);
                if (count != 0 && list.size() == count) {
                    gStr = str.substring(str2.length());
                    return list;
                } else {
                    return doData2(list, str.substring(str2.length()), count); //新数据
                }
            }
        } else {
            if (isSubPLength(str)) {
                String sLength = str.substring(7, 22);
                BigInteger length = new BigInteger(sLength, 2);
                BigInteger add = BigInteger.valueOf(22).add(length);
                ArrayList<BigInteger> list2 = doData2(new ArrayList<>(), str.substring(22, add.intValue()), 0);
                if (!list2.isEmpty()) {
                    BigInteger value = getValue(list2, num);
                    list.add(value);
                    // list.add(value);
                }

                if (count != 0 && list.size() == count) {
                    gStr = str.substring(add.intValue());
                    return list;
                }
                //ArrayList<Long> list3 = doData2(new ArrayList<>(), str.substring(22 + (int)length),count);
                if (str.substring(add.intValue()).length() < 10) {
                    return list;
                }
                //不太对了
                doData2(list, str.substring(add.intValue()), count);

                if (count != 0 && list.size() == count) {
                    return list;
                }

                if (gStr.length() >= 10) {
                    //if(!gStr.equals("")){
                    String aStr = gStr;
                    gStr = "";
                    doData2(list, aStr, count);
                }

            } else {
                String sCount = str.substring(7, 18);
                int count1 = Integer.parseInt(sCount, 2);
                ArrayList<BigInteger> list4 = doData2(new ArrayList<>(), str.substring(18), count1);
                if (!list4.isEmpty()) {
                    list.add(getValue(list4, num));
                }
                if (count != 0 && list.size() == count) {
                    return list;
                }

                if (gStr.length() >= 10) {
                    //if(!gStr.equals("")){
                    String aStr = gStr;
                    gStr = "";
                    doData2(list, aStr, count);
                }

            }
        }

        return list;
    }

    //       0 => 值相加
//1 =》 相乘
//2=》 min
//3=》 max
//4 =》 值
//5 =》 大于 1 其余0
//6 =》 等于 1 其余0
    //
    public BigInteger getValue(ArrayList<BigInteger> list, int num) {
        BigInteger count = BigInteger.ZERO;
        switch (num) {
            case 0:
                for (BigInteger n : list) {
                    count = count.add(n);
                }
                break;
            case 1:
                count = BigInteger.ONE;
                for (BigInteger n : list) {
                    count = count.multiply(n);
                }
                break;
            case 2:
                count = list.get(0);
                for (BigInteger n : list) {
                    count = count.compareTo(n) > 0 ? n : count;
                }
                break;
            case 3:
                count = list.get(0);
                for (BigInteger n : list) {
                    count = count.compareTo(n) > 0 ? count : n;
                }
                break;
            case 5:
                count = list.get(0).compareTo(list.get(1)) > 0 ? BigInteger.ONE : BigInteger.ZERO;
                break;
            case 6:
                count = list.get(0).compareTo(list.get(1)) < 0 ? BigInteger.ONE : BigInteger.ZERO;
                break;
            case 7:
                count = list.get(0).compareTo(list.get(1)) == 0 ? BigInteger.ONE : BigInteger.ZERO;
                break;
        }
        return count;

    }

    public BigInteger getLValue(String s) {
        StringBuilder nStr = new StringBuilder();
        for (int i = 0; i < s.length(); i++) {
            if (i % 5 == 0) {
                continue;
            }
            nStr.append(s.charAt(i));
        }
        return new BigInteger(nStr.toString(), 2);
    }
}
//85683891814  低
//43655379800
//42841945907
//824517912710080
//12883091136180
//43655379800
//