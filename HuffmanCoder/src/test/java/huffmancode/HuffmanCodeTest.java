package huffmancode;

import exceptions.DifferentFileException;
import getters.AdvancedGetters;
import huffmancode.structures.CharMap;
import huffmancode.structures.CharacterTree;
import huffmancode.structures.NodePriorityQueue;
import huffmancode.structures.nodes.CharNode;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.*;

import static org.junit.jupiter.api.Assertions.*;

class HuffmanCodeTest {


    @Test
    void asciiCharactersFile_BeforeCodingAndAfterDecoding_shouldBeEqual(){
        final String UNCODED_FILE="src/test/resources/data/Pan_Tadeusz_Bez_Polskich_Znakow.txt";
        final String CODED_FILE="src/test/resources/tmp/PanTadeuszBezPolskichCoded";
        final String DECODED_FILE = "src/test/resources/tmp/PanTadeuszBezPolskichDecoded";
        File uncodedFile = new File(UNCODED_FILE);
        File codedFile = new File(CODED_FILE);
        File decodedFile = new File(DECODED_FILE);
        HuffmanCode.encode(uncodedFile,codedFile);
        HuffmanCode.decode(codedFile, decodedFile);


        try{
            fileEqualityTest(uncodedFile, decodedFile);
        }catch(DifferentFileException e) {
            fail(e);
        }
        finally {
            codedFile.delete();
            decodedFile.delete();
        }
    }

    @Test
    void UTF8CharactersFile_BeforeCodingAndAfterDecoding_shouldBeEqual(){
        final String UNCODED_FILE="src/test/resources/data/Pan_Tadeusz.txt";
        final String CODED_FILE="src/test/resources/tmp/PanTadeuszCoded";
        final String DECODED_FILE = "src/test/resources/tmp/PanTadeuszDecoded";
        File uncodedFile = new File(UNCODED_FILE);
        File codedFile = new File(CODED_FILE);
        File decodedFile = new File(DECODED_FILE);
        HuffmanCode.encode(uncodedFile, codedFile);
        HuffmanCode.decode(codedFile, decodedFile);
        try{
            fileEqualityTest(uncodedFile, decodedFile);
        }catch(DifferentFileException e) {
            fail(e);
        }
        finally {
            codedFile.delete();
            decodedFile.delete();
        }



    }

    @Test
    void should_CorrectlyEncodeAndDecode_EveryPolishCharacter(){
        final String UNCODED_FILE="src/test/resources/data/predefinedTestFile.txt";
        final String CODED_FILE="src/test/resources/tmp/predefinedCoded";
        final String DECODED_FILE = "src/test/resources/tmp/predefinedDecoded";
        File uncodedFile = new File(UNCODED_FILE);
        File codedFile = new File(CODED_FILE);
        File decodedFile = new File(DECODED_FILE);
        HuffmanCode.encode(uncodedFile, codedFile);
        HuffmanCode.decode(codedFile, decodedFile);
        try{
            fileEqualityTest(uncodedFile, decodedFile);
        }catch(DifferentFileException e) {
            fail(e);
        }
        finally {
            codedFile.delete();
            decodedFile.delete();
        }
    }




    private void fileEqualityTest(File file1, File file2) throws DifferentFileException{
        try(InputStreamReader iSR1 = new InputStreamReader(new FileInputStream(file1));
            InputStreamReader iSR2 = new InputStreamReader(new FileInputStream(file2))){
            int c1=-2;
            int c2=-2;
            while((c1= iSR1.read())!=-1 && (c2=iSR2.read())!=-1){
                if(c1!=c2){
                    throw new DifferentFileException("Compared files have different content inside!");
                }
            }
            if(c1!=iSR2.read()){
                throw new DifferentFileException("Compared files have different length!");
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    
}