package com.karel.lang;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Scanner;

public class Lang {
    public static HashMap<String, String> phrases = new HashMap<String, String>();
    static{
        loadLanguage("en_us.lang");
    }
    public static void loadLanguage(String url){
        phrases.clear();
        try {
            Scanner s = new Scanner(new File("src/main/java/com/karel/lang/"+url));
            while(s.hasNext()){
                String[] toks = s.nextLine().split("=");
                String key = toks[0];
                String phrase = toks[1];
                phrases.put(key, phrase);
            }
            s.close();
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    public static String get(String key){
        if(phrases.containsKey(key)){
            return phrases.get(key);
        }else{
            return "???";
        }
    }
}
