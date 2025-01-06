package huffmancode.structures;

import huffmancode.readers.BitReader;
import huffmancode.structures.nodes.CharNode;
import huffmancode.structures.nodes.LeafCharNode;

import java.io.IOException;


public class CharacterTree {
    private CharNode root;
    Map<Character, String> codeMap;
    Map<String, Character > characterMap;
    public CharacterTree(CharMap charMap){
        NodePriorityQueue priorityQueue = new NodePriorityQueue();
        for(char c: charMap.getArrayOfSavedCharacters()){
            priorityQueue.push(charMap.get(c));
        }
        buildCharacterTree(priorityQueue);
        setCodingOfCharacters();
    }
    public CharacterTree(NodePriorityQueue priorityQueue){
        buildCharacterTree(priorityQueue);
        setCodingOfCharacters();

    }

    public CharacterTree(BitReader bitReader){
        try {
            buildCharacterTreeFromFile(bitReader);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    private void buildCharacterTreeFromFile(BitReader bitReader) throws IOException {
        root = bCTFF(bitReader, root);
        setCodingOfCharacters();
    }


    private CharNode bCTFF(BitReader bitReader, CharNode node) throws IOException {
        int c;
        if((c= bitReader.readBit())!=-1){
            switch((char) c){
                case '0':
                    node = new CharNode(0);
                    node.setLeft(bCTFF(bitReader, node.getLeft()));
                    node.setRight(bCTFF(bitReader, node.getRight()));
                    return node;
                case '1':
                    int sum=0;
                    while(((char) bitReader.readBit())!='0'){ //dodawanie 255 do sumy dopoki nie napotkam 0
                        sum+=255;
                    }
                    sum+=Integer.parseInt(bitReader.readBits(8),2);
                    char character = (char)sum;
                    node = new LeafCharNode(character,0);
                    return node;
                default:
                    throw new IOException("Unknown character: "+(char)c+" encountered while decoding!");
            }
        }
        return null;
    }

    private void buildCharacterTree(NodePriorityQueue priorityQueue){
        if(priorityQueue.size()<1) throw new IllegalArgumentException("Cannot build characterTree from empty queue");
        while(priorityQueue.size()>1){
            CharNode nodeA = priorityQueue.pop();
            CharNode nodeB = priorityQueue.pop();
            CharNode sumNode = new CharNode(nodeA.getCounter()+nodeB.getCounter());
            sumNode.setLeft(nodeA);
            sumNode.setRight(nodeB);
            priorityQueue.push(sumNode);
        }
        root = priorityQueue.pop();


    }

    private void setCodingOfCharacters() {
        codeMap = new Map<>();
        characterMap = new Map<>();
        if(root.getLeft()==null && root.getRight()==null){
            codeMap.put(root.getCharacter(),"1");
            characterMap.put("1",root.getCharacter());
            return;
        }
        sCOC(root, "");
    }

    private void sCOC(CharNode node, String code){
        if(node==null) return;
        if(node.getLeft()==null && node.getRight()==null){
            codeMap.put(node.getCharacter(), code);
            characterMap.put(code, node.getCharacter());
            return;
        }
        sCOC(node.getLeft(), code.concat("0"));
        sCOC(node.getRight(),code.concat("1"));
    }




    public String getCode(char character){
        return codeMap.get(character);
    }

    public char getCharacter(String code){
        Character c = characterMap.get(code);
        if(c!=null){
            return c;
        }
        return '\u0000';

    }

    public CharNode getRoot(){
        return root;
    }
}
