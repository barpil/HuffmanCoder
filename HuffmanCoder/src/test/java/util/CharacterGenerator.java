package util;

import huffmancode.structures.nodes.CharNode;
import huffmancode.structures.nodes.LeafCharNode;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

public class CharacterGenerator {
    private final String DEFAULT_FILE_NAME= "generatedCharactersFile";
    private final int NUMBER_OF_CHARACTERS= 58;
    private final int CHARACTER_OFFSET=65;
    Random rand;
    public CharacterGenerator(){
        rand = new Random(1337);
    }

    public CharacterGenerator(int seed){
        rand = new Random(seed);
    }

    public char[] generateRandomArrayOfCharacters(int size){
        char[] arrayOfCharacters = new char[size];
        for(int i=0;i<size;i++){
            arrayOfCharacters[i]= generateRandomCharacter();
        }
        return arrayOfCharacters;
    }

    public char[] generateRandomArrayOfDifferentCharacters(int size){
        if(size>58) throw new IllegalArgumentException("Cannot generate array of more than 58 different characters");
        char[] arrayOfCharacters = new char[size];
        int start_number = rand.nextInt(0,NUMBER_OF_CHARACTERS+1);
        for(int i=0;i<size;i++){
            arrayOfCharacters[i]=(char)((start_number+i)%(NUMBER_OF_CHARACTERS+1) + CHARACTER_OFFSET);
        }
        return arrayOfCharacters;

    }

    public char generateRandomCharacter(){
        return (char)(rand.nextInt(0,NUMBER_OF_CHARACTERS+1)+CHARACTER_OFFSET);
    }

    public CharNode[] generateArrayOfLeafCharNodes(int size, int counterOrigin, int counterBound){
        CharNode[] charNodeArray = new CharNode[size];
        for(int i=0;i<size;i++){
            charNodeArray[i]= new LeafCharNode(generateRandomCharacter(), rand.nextInt(counterOrigin, counterBound));
        }
        return charNodeArray;
    }


    public File generateFileWithRandomCharacters(int numOfCharacters) {
        File file = new File("src/test/resources/tmp/"+DEFAULT_FILE_NAME);
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            for (int i = 0; i < numOfCharacters; i++) {
                writer.append(generateRandomCharacter());
            }
        } catch (IOException e) {
            System.err.println("An error has occured while creating randomCharacterFile");
        }

        return file;
    }

    public String[] generateRandStringArray(int size){
        String[] data = new String[size];

        int stringSize = 20;
        for(int i=0;i<size;i++){
            data[i]= ((Integer)i).toString();
        }
        int k;
        for(int i=0;i<size;i++){
            k= rand.nextInt(0,size);
            String tmp = data[k];
            data[k]=data[i];
            data[i]=tmp;
        }
        return data;
    }

    public String[] generateAscStringArray(int size){
        String[] data = new String[size];
        for(int i=0;i<size;i++){
            Integer integer = i;
            data[i] = integer.toString();
        }
        return data;
    }

    public String[] generateDescStringArray(int size){
        String[] data = new String[size];
        for(int i=size-1;i>=0;i--){
            Integer integer = i;
            data[i] = integer.toString();
        }
        return data;
    }
}
