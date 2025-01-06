package huffmancode;

import huffmancode.readers.BitReader;
import huffmancode.structures.CharMap;
import huffmancode.structures.CharacterTree;
import huffmancode.structures.nodes.CharNode;
import huffmancode.writers.BinaryValuesWriter;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;


public class HuffmanCode {
    private static BinaryValuesWriter bW;
    public static void encode(File entryFile){
        try {
            Path pathFile = Paths.get("results/result");
            if(!pathFile.getParent().toFile().exists()){
                Files.createDirectories(pathFile.getParent());
            }
            Path file;
            if(!pathFile.toFile().exists()){
                file = Files.createFile(pathFile);
            }else{
                file = pathFile;
            }
            encode(entryFile, file.toFile());
        } catch (IOException e) {
            System.err.println("\033[31mAn error occurred while trying to create result file!\033[0m");
        }
    }

    public static void encode(File entryFile, File outFile){
        encodeFile(entryFile, outFile);
        System.out.println("\n\033[32mEncoded "+entryFile.length()+"B into "+outFile.length()+"B\nResult saved at:\n"+outFile.getAbsolutePath()+"\033[0m");
    }

    private static StringBuilder byteCode;
    private static void encodeFile(File entryFile, File outFile) {
        CharMap charMap = EntryFileMaper.createCharMap(entryFile);
        CharacterTree characterTree = new CharacterTree(charMap);
        byteCode = new StringBuilder();
        codeCharacters(entryFile, outFile, charMap, characterTree);

    }
    private static int numberOfBitsToFillToFullByte;
    private static void createTreeReconstructionCode(File entryFile, File outFile, CharMap charMap, CharacterTree characterTree) throws IOException {
        //Pierwsze 3 bity oznaczaja ilosc dodanych dodatkowych zer
        //dalej opisana jest rekonstrukcja drzewa
        //0 oznacza, że node jest CharNodem
        //1 oznacza, że node jest LeafCharNodem
        //Po dodaniu tego znowu nie bedzie mi sie zgadzac liczba bitow do dopelnienia bajtu
        StringBuilder characterCodes = new StringBuilder();
        cTRC(characterCodes, characterTree.getRoot());
        int tmp=(calculateNumberOfExtraBits(charMap,characterTree)+3+characterCodes.length())%8;
        numberOfBitsToFillToFullByte=(tmp==0)? 0:8-tmp; // Źle oblicza ilość brakujących bitów

        bW.writeBinaryToFile(numberOfBitsToFillToFullByte,3);// Zapis 3 bitów mówiących o liczbie dopisanych na początek zakodowanego tesktu 0
        bW.writeBinaryToFile(characterCodes.toString()); // Zapis drzewa kodow

    }
    //Plan jest taki, przy kodowaniu znaku rozbijam go na sume liczb mniejszych od 256 (takich co da sie zapisac na 8 bitach)
    //Na koncu kazdego takiego fragmentu zapisuje 0 albo 1. 0 oznacza ze to koniec sumy, 1 oznacza ze dalsze 8 bitow nalezy dodac do sumy.
    //Po natknieciu sie na 0 czyli osiagnieciu sumy znak bedzie zapisany w mapie znakow i nastepnie mozna dalej odkodowywac
    //budowe drzewa. W ten sposób jedynie znaki, które potrzebują tego będą zapisywane na wiecej niz 8 bitach.


    //2 Plan (lepszy)
    //Po wstawieniu 1 oznaczajacej ze jest to node ze znakiem zapisuje znak kontrolny 0 albo 1 oznaczajacy czy znak ma wartosc int >255.
    //1 oznacza ze do wartosci int znaku wyjsciowego nalezy dodac 255. Jest tak aż do momentu napotkania 0.
    //Po 0 jest 8 bitow liczby mniejszej niz 255 oznaczajacej reszte z dzielenia wartosci int znaku przez 255.
    //Ergo przy odczycie powinny byc takie kroki:
    //Wykryto 1 - (node drzewa bedzie posiadal znak, wiec musimy go odczytac)
    //Iterujemy po jedynkach az natkniemy sie na 0. Po napotkaniu każdej jedynki dodajemy 255 do sumy.
    //Po natknieciu sie na 0 odczytujemy nastepne 8 bitow reszty, ktore przeksztalcone na dziesietny dodajemy do sumy
    //odkodowany znak zapisujemy w drzewie dekodujacym.

    private static void cTRC(StringBuilder sB, CharNode node){
        if(node==null){
            return;
        }
        if(node.getLeft()==null && node.getRight()==null){
            sB.append('1'); //Informacja o tym ze bedzie podawany kod znaku!
            for(int i=0;i<((int)node.getCharacter()/255);i++){
                sB.append('1'); //Dodanie dodatkowych jedynek oznaczajacych dodanie 255 do sumy znaku
            }
            sB.append('0'); //Informacja o tym ze nastepne 8 bitow to reszta z sumy znaku
            sB.append(BinaryValuesWriter.convertIntToBinaryString(node.getCharacter()%255,8)); //Zapisanie reszty znaku
            return;
        }
        sB.append('0');
        cTRC(sB, node.getLeft());
        cTRC(sB, node.getRight());
    }

    private static void codeCharacters(File entryFile, File outFile, CharMap charMap, CharacterTree characterTree){

        try(InputStreamReader iSR = new InputStreamReader(new FileInputStream(entryFile), "UTF-8");
            FileOutputStream fOS = new FileOutputStream(outFile)) {
            bW = new BinaryValuesWriter(fOS);
            createTreeReconstructionCode(entryFile, outFile, charMap, characterTree);
            for(int i=0;i<numberOfBitsToFillToFullByte;i++){
                bW.writeBinaryToFile("0");
            }
            int c;
            while((c= iSR.read())!=-1){
                bW.writeBinaryToFile(characterTree.getCode((char) c));
            }
        }catch (FileNotFoundException e) {
        System.err.println("\033[31mError: Could not find specified entry file!\n(Passed path: "+entryFile.getAbsolutePath()+"\033[0m");
        System.exit(1);
    }catch (IOException e) {
        System.err.println("\033[31mError: An IOException occured while trying to encode data!\n(Passed path: "+entryFile.getAbsolutePath()+"\033[0m");
        System.exit(1);
        }
    }




    private static int calculateNumberOfExtraBits(CharMap charMap, CharacterTree tree){
            int sum=0;
            char[] array = charMap.getArrayOfSavedCharacters();
            for(char c: array){
                int n= charMap.get(c).getCounter();
                int x= tree.getCode(c).length();
                sum+=n*x;
                sum=sum%8;
            }
            return sum;
    }

    public static void decode(File entryFile){
        try {
            Path pathFile = Paths.get("results/result");
            if(!pathFile.getParent().toFile().exists()){
                Files.createDirectories(pathFile.getParent());
            }
            Path file;
            if(!pathFile.toFile().exists()){
                file = Files.createFile(pathFile);
            }else{
                file = pathFile;
            }
            decode(entryFile, file.toFile());
        } catch (IOException e) {
            System.err.println("\033[31mAn error occurred while trying to create result file!\033[0m");
        }
    }

    public static void decode(File entryFile, File outputFile){
        decodeFile(entryFile, outputFile);
        System.out.println("\n\033[32mDecoded "+entryFile.length()+"B into "+outputFile.length()+"B\nResult saved at:\n"+outputFile.getAbsolutePath()+"\033[0m");
    }

    private static void decodeFile(File entryFile, File outputFile){
        try(FileInputStream fIS = new FileInputStream(entryFile); OutputStreamWriter oSW = new OutputStreamWriter(new FileOutputStream(outputFile),"UTF-8")){
            BitReader bitReader = new BitReader(fIS);
            StringBuilder stringBuilder = new StringBuilder();
            int numberOfExtra0 = Integer.parseInt(bitReader.readBits(3),2);
            CharacterTree characterTree = new CharacterTree(bitReader);
            bitReader.readBits(numberOfExtra0);
            int c;
            int character;
            while((c=bitReader.readBit())!=-1){
                stringBuilder.append((char)c);
                if((character=characterTree.getCharacter(stringBuilder.toString()))!='\u0000'){
                    oSW.write(character);
                    stringBuilder.setLength(0);
                }
            }

        } catch (FileNotFoundException e) {
            System.err.println("\033[31mError: Could not find specified entry file!\n(Passed path: "+entryFile.getAbsolutePath()+"\033[0m");
            System.exit(1);
        } catch (IOException e) {
            System.err.println("\033[31mError: An IOException occured while trying to decode data!\n(Passed path: "+entryFile.getAbsolutePath()+"\033[0m");
            System.exit(1);
        }
    }



}
