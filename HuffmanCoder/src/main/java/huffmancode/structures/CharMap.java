package huffmancode.structures;

import huffmancode.structures.nodes.KVPair;
import huffmancode.structures.nodes.LeafCharNode;

import java.util.ArrayList;
import java.util.List;

public class CharMap extends Map<Character, LeafCharNode> {
    private List<Character> savedCharacters;
    private int totalNumberOfCharacters;
    public CharMap() {
        this(127);
    }

    public CharMap(int size) {
        super(size);
        this.savedCharacters = new ArrayList<>();

    }

    public void putChar(Character character){
        put(character, null);
    }

    @Override
    public void put(Character character, LeafCharNode node) {
        validateInputElem(character);
        resizeIfNeeded();

        int key = character;
        int i = 0;
        int hashId = hashFunc(key, i);


        while (hashElems[hashId] != null) {
            if(hashElems[hashId].getValue().getCharacter()==character){
                break;
            }
            if (i + 1 == size) {
                doubleResize();
                i = -1;
            }
            i = (i + 1) % size;
            hashId = hashFunc(key, i);
        }
        totalNumberOfCharacters++;
        if(hashElems[hashId]!=null){
            hashElems[hashId].getValue().incrementCounter();
            return;
        }
        hashElems[hashId] = new KVPair<>(character,new LeafCharNode(character, 1));
        savedCharacters.add(character);

        nElems++;
    }

    @Override
    public LeafCharNode get(Character character) {
        int i=0;
        int j;
        int key= character;
        do{
            j=hashFunc(key,i);
            if(hashElems[j]!=null && hashElems[j].getValue().getCharacter() == character){
                return hashElems[j].getValue();
            }
            i++;
        }while(i<this.size && hashElems[j]!=null);
        return null;
    }


    public char[] getArrayOfSavedCharacters(){
        char[] savedCharacters = new char[this.savedCharacters.size()];
        int i=0;
        for(Character c : this.savedCharacters){
            savedCharacters[i]=c;
            i++;
        }
        return savedCharacters;
    }


}
