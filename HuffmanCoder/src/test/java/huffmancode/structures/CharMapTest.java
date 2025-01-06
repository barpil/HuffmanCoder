package huffmancode.structures;

import getters.AdvancedGetters;
import huffmancode.EntryFileMaper;
import huffmancode.structures.nodes.LeafCharNode;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import util.CharacterGenerator;

import java.io.File;
import java.lang.reflect.Field;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

class CharMapTest {
    CharMap charMap;
    int DEFAULT_SIZE = 127;
    CharacterGenerator generator;
    static Random rand;
    static int seed = 1337;
    @BeforeAll
    static void setUp(){
        rand = new Random(seed);

    }

    @BeforeEach
    public void createCharMap(){
        charMap = new CharMap(127);
        generator=new CharacterGenerator(seed);

    }

    @Test
    public void shouldInsertCorrectCharacter(){
        charMap.putChar('a');
        assertEquals('a', charMap.get('a').getCharacter());
    }

    @Test
    public void shouldCountPutElementsCorrectly(){
        int numerOfCharacters= 26;
        int characterOffset= 97;
        int[] testCharacters = new int[numerOfCharacters];
        for(int i=0;i<numerOfCharacters;i++){
            testCharacters[i] = rand.nextInt(0,100);
        }

        for(int i=0;i<numerOfCharacters;i++){
            for(int j=0;j<testCharacters[i];j++){
                charMap.putChar((char) (i+characterOffset));
            }
        }
        for(int i=0;i<numerOfCharacters;i++){
            if(charMap.get((char)(i+characterOffset)).getCounter()!=testCharacters[i]){
                fail("Character counters in charMap does not equal the number of put characters!");
            }
        }

    }

    @Test
    public void shouldReturn_ListOfAllInsertedCharacters(){
        int numOfCharacters = 1000;
        char[] arrayOfCharacters = generator.generateRandomArrayOfCharacters(numOfCharacters);
        for(int i = 0; i< numOfCharacters; i++){
            charMap.putChar(arrayOfCharacters[i]);
        }
        char[] charsInMap = charMap.getArrayOfSavedCharacters();
        e:
        for(int i=0;i<numOfCharacters;i++){
            for(int j=0;j<charsInMap.length;j++){
                if(arrayOfCharacters[i]==charsInMap[j]) continue e;
            }

            fail("An incomplete array of inserted characters has been returned");
        }

    }





}