package common;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.*;
import java.util.ArrayList;

public class Util {
    public static File writeStringToFile(String str, String fileNmae) {
        File f = new File(fileNmae);
        try {
            if (!f.exists()) {
                f.createNewFile();
            }
            OutputStreamWriter write = new OutputStreamWriter(new FileOutputStream(f));
            BufferedWriter writer = new BufferedWriter(write);
            writer.write(str);
            writer.flush();
            write.close();
        }catch(Exception e){
            System.err.println(e.getMessage());
        }
        return f;
    }

    public static String readFile(String fileName){
            File file = new File(fileName);
            Long filelength = file.length();
            byte[] filecontent = new byte[filelength.intValue()];
            try {
                FileInputStream in = new FileInputStream(file);
                in.read(filecontent);
                in.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
        }
            return new String(filecontent);
    }
    public static String getResponeBodyByUrl(String url) throws IOException {
        CloseableHttpClient aDefault = HttpClients.createDefault();
        HttpClientContext httpClientContext = HttpClientContext.create();
        HttpGet httpGet = new HttpGet(url);
        CloseableHttpResponse response = aDefault.execute(httpGet, httpClientContext);
        return EntityUtils.toString(response.getEntity());
    }

    public static ArrayList<String> readFileByBlankLine(String fileName){
        File file = new File(fileName);
        Long filelength = file.length();
        Long readlength = 0l;
        ArrayList<String> list =null;
        try {
            BufferedReader fin=new BufferedReader(new FileReader(fileName));
            String read=null;//声明du一个字符串zhi变量存放读出的字符串

            list = new ArrayList<>();
            String string = "";
            while( read ==null || readlength< filelength){
                while((read=fin.readLine())!=null && !"".equals(read))//一直读dao （只要不是空行或者读完毕）
                {
                    string += read;
                    System.out.println(read+" read");
                    readlength += read.length();
                    System.out.println(readlength);
                }
                list.add(string);
                System.out.println(readlength+" "+filelength);
            }

            fin.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return list;
    }
}
