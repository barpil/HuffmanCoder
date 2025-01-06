package huffmancode.structures;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import util.CharacterGenerator;

import static org.junit.jupiter.api.Assertions.*;

class MapTest {
    private static CharacterGenerator gen;
    private static final int SEED= 1337;
    Map<Integer,Character> map;
    @BeforeAll
    public static void setUp(){
        gen = new CharacterGenerator(SEED);
    }

    @Test
    public void shouldBeAbleTo_PutMorePairsThanItsInitialSize(){
        final int INITIAL_SIZE = 5;
        map= new Map<>(INITIAL_SIZE);
        for(int i=0;i<INITIAL_SIZE*2;i++){
            map.put(i,gen.generateRandomCharacter());
        }
    }

    @Test
    void shouldBeAbleTo_ReturnAllPutValuesByTheirKeys(){
        map=new Map<>();
        final int NUM_OF_PAIRS=10;
        char[] array = gen.generateRandomArrayOfCharacters(NUM_OF_PAIRS);
        for(int i=0;i<NUM_OF_PAIRS;i++){
            map.put(i, array[i]);
        }
        for(int i=0;i<NUM_OF_PAIRS;i++){
            if(map.get(i)!=array[i]){
                fail("Put value is not avaiable under its specified key!");
            }
        }
    }

}