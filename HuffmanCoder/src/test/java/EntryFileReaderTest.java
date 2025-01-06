import huffmancode.EntryFileMaper;
import huffmancode.structures.CharMap;
import org.junit.jupiter.api.Test;
import util.CharacterGenerator;

import java.io.File;

import static org.junit.jupiter.api.Assertions.*;

class EntryFileReaderTest {

    @Test
    public void returnedChar_ShouldHaveCountedAllCharactersFromFile(){
        final int NUMBER_OF_CHARACTERS = 1000;
        CharacterGenerator cG = new CharacterGenerator();
        File file = cG.generateFileWithRandomCharacters(NUMBER_OF_CHARACTERS);
        CharMap charMap = EntryFileMaper.createCharMap(file);

        char[] characterArray = charMap.getArrayOfSavedCharacters();
        int sum=0;
        for(char c : characterArray){
           sum+=charMap.get(c).getCounter();
        }
        assertEquals(NUMBER_OF_CHARACTERS, sum);
        file.delete();
    }

}