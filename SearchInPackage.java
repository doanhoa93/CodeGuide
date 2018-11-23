package com.nitsoft;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.filefilter.TrueFileFilter;

import java.io.*;
import java.util.*;

public class SearchInPackage {

    public static void main(String[] args) throws IOException {
        System.out.println("START : " + new Date());

        File source = new File("/home/doan.quang.hoa/Project/speeda");
        List<File> files = (List<File>) FileUtils.listFiles(source, TrueFileFilter.INSTANCE, TrueFileFilter.INSTANCE);

        List<String> listKeys = getKeyNotExtract();

        List<String> listLines = new ArrayList<>();

        List<String> unExtractKeys = new ArrayList<>();
        unExtractKeys = getKeyUnExtract();

        for (String key : listKeys) {
            boolean isExistsKey = false;

            for (File file : files) {

                if (FilenameUtils.getExtension(file.getAbsolutePath()).equals("java")
                        || FilenameUtils.getExtension(file.getAbsolutePath()).equals("html")) {
                    FileReader fileReader = null;
                    String lineStr;
                    int linePosition = 0;

                    fileReader = new FileReader(file);
                    BufferedReader br = new BufferedReader(fileReader);
                    while ((lineStr = br.readLine()) != null) {
                        linePosition++;

                        int indexfound = lineStr.indexOf("\"" + key + "\"");
                        if (indexfound > -1) {
//                            listLines.add(key + "\t" + file.getPath() + ":" + linePosition);
                            isExistsKey = true;
                            break;
                        }
                    }

                    if (isExistsKey) {
                        listLines.add(key + "\t" + "\t" + file.getPath() + ":" + linePosition);
                        fileReader.close();
                        break;
                    }
                    fileReader.close();

                }
            }
            if (isExistsKey) {
                continue;
            } else {
                listLines.add(key + "\t" + "Unused");
            }
        }
        BufferedWriter bw = null;
        FileWriter fw = null;
        File file = new File("/home/doan.quang.hoa/Project/Java/Modern-Ecommerce/backend-rest-api/output.txt");

        for (String entry : listLines) {

            try {

                if (!file.exists()) {
                    file.createNewFile();
                }

                fw = new FileWriter(file.getAbsoluteFile(), true);
                bw = new BufferedWriter(fw);
                bw.write(entry+"\n");

            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    if (bw != null)
                        bw.close();
                    if (fw != null)
                        fw.close();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        }
        System.out.println("END : " + new Date());

    }

    public static List<String> getKeyNotExtract() {

        List<String> arr = new ArrayList<String>();
        try (BufferedReader br = new BufferedReader(new FileReader("/home/doan.quang.hoa/Project/Java/unExtract.txt"))) {

            String sCurrentLine;

            while ((sCurrentLine = br.readLine()) != null) {
                arr.add(sCurrentLine);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return arr;
    }

    public static List<String> getKeyUnExtract() {

        List<String> arr = new ArrayList<String>();
        try (BufferedReader br = new BufferedReader(new FileReader("/home/doan.quang.hoa/Desktop/unExtract"))) {

            String sCurrentLine;

            while ((sCurrentLine = br.readLine()) != null) {
                arr.add(sCurrentLine);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return arr;
    }
}
