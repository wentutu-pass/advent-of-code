package common;

public class ArrayUtil {
    public static int[][] covertToTwoIntArr(String[] inputs, String splitWith) {
        int x = inputs.length;
        int y = inputs[0].split(splitWith).length;
        int[][] arr = new int[x][y];
        for (int i = 0; i < x; i++) {
            for (int j = 0; j < y; j++) {
                arr[i][j] = Integer.parseInt(inputs[i].split(splitWith)[j]);
            }
        }
        return arr;
    }

    public static void printTwoArr(int[][] inputs) {
        for (int[] input : inputs) {
            for (int j = 0; j < inputs[0].length; j++) {
                System.out.print(input[j]);
            }
            System.out.print("\n");
        }
    }

    public static void copyTwoArr(int[][] arr1, int[][] arr2) {
        for (int i = 0; i < arr1.length; i++) {
            System.arraycopy(arr1[i], 0, arr2[i], 0, arr1[0].length);
        }
    }

    public static int[][] getIntArr(String[] inputs) {
        int x = inputs.length;
        int y = inputs[0].split("").length;
        int[][] nums = new int[x][y];
        for (int i = 0; i < x; i++) {
            for (int j = 0; j < y; j++) {
                nums[i][j] = Integer.parseInt(inputs[i].split("")[j]);
            }

        }
        return nums;
    }
}
