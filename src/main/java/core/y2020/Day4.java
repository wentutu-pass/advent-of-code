package core.y2020;

import common.Util;

import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Stream;

public class Day4 {
    private static Logger logger = Logger.getLogger("Day4");

    public static void main(String[] args) {
        String inputs = Util.readFile("src/main/resources/y2020/day4.txt");
        Day4 day4 = new Day4();
        logger.log(Level.INFO, "counts of vaild passport1 is {0}", day4.getCountsOfValidPassport1(inputs));
        logger.log(Level.INFO, "counts of vaild passport2 is {0}", day4.getCountsOfValidPassport2(inputs));
    }

    private int getCountsOfValidPassport1(String inputs) {
        int count = 0;
        String[] string = inputs.split("\n\n");

        for (String input : string) {
            String[] datas = input.split(" |\n");
            if (datas.length == 8 || (datas.length == 7 && Stream.of(datas).noneMatch(s -> s.startsWith("cid")))) {
                count++;
            }
        }
        return count;
    }

    private int getCountsOfValidPassport2(String inputs) {
        int count = 0;
        String[] string = inputs.split("\n\n");

        a:
        for (String input : string) {
            String[] datas = input.split(" |\n");
            boolean noCid = Stream.of(datas).noneMatch(s -> s.startsWith("cid"));
            if (noCid ? datas.length != 7 : datas.length != 8) {
                continue;
            }
            for (String data : datas) {
                if (data.startsWith("byr")) {
                    int byr = Integer.parseInt(data.split(":")[1]);
                    if (byr < 1920 || byr > 2002) {
                        continue a;
                    }
                }
                if (data.startsWith("iyr")) {
                    int iyr = Integer.parseInt(data.split(":")[1]);
                    if (iyr < 2010 || iyr > 2020) {
                        continue a;
                    }
                }

                if (data.startsWith("eyr")) {
                    int eyr = Integer.parseInt(data.split(":")[1]);
                    if (eyr < 2020 || eyr > 2030) {
                        continue a;
                    }
                }

                if (data.startsWith("hgt")) {
                    String hgt = data.split(":")[1];
                    if (!hgt.endsWith("cm") && !hgt.endsWith("in")) {
                        continue a;
                    }

                    if (hgt.endsWith("cm")) {
                        int h = Integer.parseInt(hgt.split("cm")[0]);
                        if (h < 150 || h > 193) {
                            continue a;
                        }
                    }

                    if (hgt.endsWith("in")) {
                        int h = Integer.parseInt(hgt.split("in")[0]);
                        if (h < 59 || h > 76) {
                            continue a;
                        }
                    }
                }

                if (data.startsWith("hcl")) {
                    String hcl = data.split(":")[1];
                    if (!hcl.matches("^#[0-9a-f]{6}")) {
                        continue a;
                    }
                }

                if (data.startsWith("ecl")) {
                    String ecl = data.split(":")[1];
                    String[] array = {"amb", "blu", "brn", "gry", "grn", "hzl", "oth"};
                    if (!Arrays.asList(array).contains(ecl)) {
                        continue a;
                    }
                }

                if (data.startsWith("pid")) {
                    String pid = data.split(":")[1];
                    if (!pid.matches("[0-9]{9}")) {
                        continue a;
                    }
                }
            }
            count++;
        }
        return count;
    }


}
