package huffmancode;

import huffmancode.structures.CharMap;

import java.io.*;

public class EntryFileMaper {
    static CharMap charMap;

    private EntryFileMaper() {}

    public static CharMap createCharMap(File entryFile) {
        charMap = new CharMap();
        int read;
        try(InputStreamReader iFR = new InputStreamReader(new FileInputStream(entryFile), "UTF-8")) {
            while((read=iFR.read())!=-1){
                charMap.putChar((char)read);
            }

        }catch (IOException e) {
            throw new RuntimeException(e);
        }
        return charMap;
    }


}
