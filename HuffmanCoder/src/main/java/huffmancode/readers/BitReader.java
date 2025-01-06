package huffmancode.readers;

import huffmancode.exceptions.ToFewBitsInFileException;

import java.io.FileInputStream;
import java.io.IOException;

public class BitReader {
    StringBuilder readBuffor;
    FileInputStream fIS;

    public BitReader(FileInputStream fIS){
        this.fIS=fIS;
        readBuffor= new StringBuilder();
    }


    public String readBits(int numOfBits) throws IOException {
        while(readBuffor.length()<numOfBits){
            int b;
            if ((b= fIS.read())!=-1) {
                readBuffor.append(String.format("%8s", Integer.toBinaryString(b & 0xFF)).replace(' ', '0'));
            }else{
                throw new ToFewBitsInFileException("Could not read "+numOfBits+"bits! (To few bits in file left)");
            }
        }
        String s= readBuffor.substring(0,numOfBits);
        readBuffor.delete(0,numOfBits);
        return s;
    }

    public int readBit() throws IOException {
        try{
            return readBits(1).charAt(0);
        }catch(ToFewBitsInFileException e){
            return -1;
        }
    }
}
