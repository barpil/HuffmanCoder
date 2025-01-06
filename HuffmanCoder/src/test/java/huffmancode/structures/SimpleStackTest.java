package huffmancode.structures;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import util.CharacterGenerator;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class SimpleStackTest {
    private static CharacterGenerator gen;
    private final static int SEED = 1337;
    private SimpleStack<Character> stack;
    @BeforeAll
    public static void setUp(){
        gen = new CharacterGenerator(SEED);
    }

    @Test
    public void shouldBeAbleTo_PushMoreElementsThanItsInitialSize(){
        final int INITIAL_SIZE = 4;
        stack= new SimpleStack<>(Character.class, INITIAL_SIZE);
        for(int i=0;i<INITIAL_SIZE*2;i++){
            stack.push('a');
        }
    }

    @Test
    public void should_PopElementsInLIFO(){
        final int NUM_OF_CHARACTERS = 10;
        stack = new SimpleStack<>(Character.class);
        char[] array = gen.generateRandomArrayOfCharacters(NUM_OF_CHARACTERS);
        for(char c: array){
            stack.push(c);
        }

        for(int i=array.length-1;i>=0;i--){
            char c = stack.pop();
            if(array[i]!= c){
                fail("Stack does not return values in LIFO order!");
            }
        }


    }

}