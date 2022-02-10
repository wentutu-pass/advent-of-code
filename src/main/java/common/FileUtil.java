package common;

import java.io.*;

public class FileUtil {

    public static String readFile(String fileName) {
        File file = new File(fileName);
        long fileLength = file.length();
        byte[] fileContent = new byte[(int) fileLength];
        try {
            FileInputStream in = new FileInputStream(file);
            int read = in.read(fileContent);
            if (read == 0) {
                throw new IOException();
            }
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new String(fileContent);
    }
}
