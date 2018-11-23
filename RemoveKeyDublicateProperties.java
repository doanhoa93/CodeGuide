package com.nitsoft;


import java.io.*;
import java.util.*;

public class RemoveKeyDublicateProperties {
    ;

    public static void main(String[] args) {
        RemoveKeyDublicateProperties app = new RemoveKeyDublicateProperties();
        app.printAll();
    }

    private void printAll() {
        Properties prop = new Properties();
        InputStream input = null;
        Map<String, String> keyValues = new HashMap<>();
        List<String> unExtractKeys = new ArrayList<>();

        try {
            String filename = "SpeedaCoreApplication_en.properties";
            input = getClass().getClassLoader().getResourceAsStream(filename);
            if (input == null) {
                return;
            }
            prop.load(input);

            Enumeration<?> e = prop.propertyNames();
            while (e.hasMoreElements()) {
                String key = (String) e.nextElement();
                String value = prop.getProperty(key);
                keyValues.put(key, value);
            }

            unExtractKeys = getKeyUnExtract();

//            for (String key : unExtractKeys) {
//                if (keyValues.containsKey(key)) {
//                    //key exists
//                    keyValues.remove(key);
//                }
//            }

            BufferedWriter bw = null;
            FileWriter fw = null;
            File file = new File("/home/doan.quang.hoa/Project/Java/Modern-Ecommerce/backend-rest-api/unextract.properties");

            for (String key : unExtractKeys) {
                try {

                    if (!file.exists()) {
                        file.createNewFile();
                    }

                    fw = new FileWriter(file.getAbsoluteFile(), true);
                    bw = new BufferedWriter(fw);

                    if (keyValues.containsKey(key)) {
//                        bw.write(key + "\t" + keyValues.get(key).replaceAll("\r\n","\\r\\n").replaceAll("\\n","\\\n").replaceAll("\n", "\\n") + "\n");
                        bw.write(key + "\t");
                        if (keyValues.get(key) !=null && keyValues.get(key) != "") {
                            bw.write(keyValues.get(key)
                                    .replaceAll("\\\\","\\\\\\\\")
                                    .replaceAll("\u3000","\\u3000")
                                    .replaceAll("\r\n","\\r\\n")
                                    .replaceAll("\\n","\\\n")
                                    .replaceAll("\n", "\\n") + "\n");
                        } else {
                            bw.write("");
                        }

                    }




                } catch (IOException exs) {
                    exs.printStackTrace();
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

        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            if (input != null) {
                try {
                    input.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public List<String> getKeyUnExtract() {

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