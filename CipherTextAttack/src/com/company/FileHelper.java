package com.company;

import java.io.*;

/**
 * Created by User on 30-03-2015.
 */
public class FileHelper {

    public static byte[] GetBinaryData(String path) {
        File file = new File(path);
        double size1 = (double)file.length();
        double size2 = Math.ceil(size1/10.0);
        int size  = (int)size2*10;
        byte[] result = new byte[size];
        if(file.exists() && !file.isDirectory()) {
            try {
                InputStream input = null;
                int totalBytesRead = 0;
                input = new BufferedInputStream(new FileInputStream(file));
                input.read(result, totalBytesRead, size);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    public static String GetTextData(String path) {
        String iv="";
        File file = new File(path);
        if(file.exists() && !file.isDirectory()) {
            try {
                FileReader fr = new FileReader(file);
                BufferedReader br = new BufferedReader(fr);
                String sCurrentLine;
                while ((sCurrentLine = br.readLine()) != null) {
                    iv += sCurrentLine+'\n';
                }
                br.close();
                fr.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return iv;
    }

    public static void WriteToFileAsText(String data,String fileName) {
        try {
            String s = new String(data);
            File file = new File(fileName);
            BufferedWriter output = new BufferedWriter(new FileWriter(file));
            output.write(new String(data));
            output.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
