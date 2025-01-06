package huffmancode.writers;

import java.io.FileOutputStream;
import java.io.IOException;

public class BinaryValuesWriter {
    private StringBuilder byteContent;
    private FileOutputStream fOS;
    public BinaryValuesWriter(FileOutputStream fOS){
        byteContent=new StringBuilder("");
        this.fOS = fOS;
    }
    public void writeBinaryToFile(String binaryValue) throws IOException {
        byteContent.append(binaryValue);
        while(byteContent.length()>=8){
            fOS.write((byte) Integer.parseInt(byteContent.substring(0,8),2));
            byteContent.delete(0,8);
        }
    }

    public void writeBinaryToFile(int intToConvert) throws IOException {
        writeBinaryToFile(BinaryValuesWriter.convertIntToBinaryString(intToConvert));
    }
    public void writeBinaryToFile(int intToConvert, int numOfBitesToWriteOn) throws IOException {
        writeBinaryToFile(BinaryValuesWriter.convertIntToBinaryString(intToConvert,numOfBitesToWriteOn));
    }

    public static String convertIntToBinaryString(int intToConvert){
        return Integer.toBinaryString(intToConvert);
    }

    public static String convertIntToBinaryString(int intToConvert, int numOfBitesToWriteOn){
        String s=BinaryValuesWriter.convertIntToBinaryString(intToConvert);

        StringBuilder result  = new StringBuilder();
        if(s.length()>numOfBitesToWriteOn){
            System.err.println("\033[31mAn error occured while decoding file!\nBinaryValuesWriter error:\nCannot write "+intToConvert+" on "+ numOfBitesToWriteOn+" bits\033[0m");
            throw new RuntimeException();
        }
        for(int i=0;i<numOfBitesToWriteOn-s.length();i++){
            result.append('0');
        }

        return result.append(s).toString();
    }
}
