package huffmancode.structures;

import getters.AdvancedGetters;
import huffmancode.EntryFileMaper;
import huffmancode.HuffmanCode;
import huffmancode.readers.BitReader;
import huffmancode.structures.nodes.CharNode;
import huffmancode.structures.nodes.LeafCharNode;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import util.CharacterGenerator;

import java.io.*;

import static org.junit.Assert.fail;

class CharacterTreeTest {
    private CharacterTree cT;
    private static CharacterGenerator gen;
    @BeforeAll
    public static void setUp(){
        gen = new CharacterGenerator(1337);
    }



    @Test
    public void shouldBeAbleToGetCode_ForEveryCharacter_FromCharMap(){
        final int DEFAULT_SIZE=30;
        CharMap cM = new CharMap();
        char[] array = gen.generateRandomArrayOfDifferentCharacters(DEFAULT_SIZE);
        for(char c: array){
            cM.putChar(c);
        }
        cT = new CharacterTree(cM);
        for(char c: array){
            if(cT.getCode(c)==null){
                Assertions.fail("Not all pushed characters are present in result CharacterTree!");
            }
        }
    }

    @Test
    public void shouldBeAbleToGetCode_ForEveryCharacter_FromNodePriorityQueue(){
        final int DEFAULT_SIZE=5;
        NodePriorityQueue pQ = new NodePriorityQueue();
        char[] array = gen.generateRandomArrayOfDifferentCharacters(DEFAULT_SIZE);
        for(char c: array){
            pQ.push(new LeafCharNode(c, gen.generateRandomCharacter()));
        }
        cT = new CharacterTree(pQ);
        for(char c: array){
            if(cT.getCode(c)==null){
                Assertions.fail("Not all pushed characters are present in result CharacterTree!");
            }
        }
        printTree(cT.getRoot());
    }
    @Test void should_BuildCorrectCharacterTree_WhenEncoding(){
        CharMap charMap = new CharMap();
        try(FileInputStream fileInputStream = new FileInputStream("src/test/resources/data/predefinedTestFile.txt")){
            int c;
            while((c=fileInputStream.read())!=-1){
                charMap.putChar((char)c);
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        cT = new CharacterTree(charMap);
        printTree(cT.getRoot());

    }

    @Test
    public void should_BuildCorrect_TreeOfValuesDescendingFromRootToLeafs_ForPredefinedValues() {
        NodePriorityQueue pQ = new NodePriorityQueue();
        pQ.push(new LeafCharNode('a', 45));
        pQ.push(new LeafCharNode('b', 13));
        pQ.push(new LeafCharNode('c', 12));
        pQ.push(new LeafCharNode('d', 16));
        pQ.push(new LeafCharNode('e', 9));
        pQ.push(new LeafCharNode('f', 5));
        cT= new CharacterTree(pQ);
        printTree(AdvancedGetters.getRootOfCharacterTree(cT));
        testEachTreeNode(AdvancedGetters.getRootOfCharacterTree(cT));
    }




    @Test
    public void should_BuildCorrect_TreeOfValuesDescendingFromRootToLeafs_ForRandomValues(){
        int DEFAULT_SIZE = 20;
        NodePriorityQueue pQ = new NodePriorityQueue();
        CharNode[] charNodeArray = gen.generateArrayOfLeafCharNodes(DEFAULT_SIZE,1,101);
        for(CharNode c : charNodeArray){
            pQ.push(c);
        }
        cT = new CharacterTree(pQ);
        printTree(AdvancedGetters.getRootOfCharacterTree(cT));


        testEachTreeNode(AdvancedGetters.getRootOfCharacterTree(cT));
    }

    private void testEachTreeNode(CharNode root){
        gTN(root);
    }

    private void gTN(CharNode node){
        if(node == null) return;
        if(node.getLeft()!=null && (node.getLeft().getCounter() > node.getCounter())){
            fail("Counter numbers of nodes are not descending from root to leafs (["+node.getLeft().getCharacter()+":"+node.getLeft().getCounter()+"]"+">["+node.getCharacter()+":"+node.getCounter()+"])");
        }
        if(node.getRight()!=null && (node.getRight().getCounter()>node.getCounter())) {
            fail("Counter numbers of nodes are not descending from root to leafs (["+node.getRight().getCharacter()+":"+node.getRight().getCounter()+"]"+">["+node.getCharacter()+":"+node.getCounter()+"])");
        }
        gTN(node.getLeft());
        gTN(node.getRight());
    }


    @Test
    void should_BeAbleTo_ReconstructCodedCharacterTree(){
        final String DEFAULT_TEST_FILE_PATH ="src/test/resources/data/predefinedCoded";
        final String DEFAULT_CONTROL_FILE_PATH= "src/test/resources/data/predefinedTestFile.txt";
        try(FileInputStream fIS = new FileInputStream(new File(DEFAULT_TEST_FILE_PATH))){
            BitReader bitReader = new BitReader(fIS);
            bitReader.readBits(3); //Pominiecie 3 pierwszych bitow
            cT = new CharacterTree(bitReader);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        CharacterTree cTTest = new CharacterTree(EntryFileMaper.createCharMap(new File(DEFAULT_CONTROL_FILE_PATH)));
        printTree(cT.getRoot());
        treeEqualityTest(cT, cTTest);

    }

    private void treeEqualityTest(CharacterTree tree1, CharacterTree tree2){
        tET(tree1.getRoot(), tree2.getRoot());
    }

    private void tET(CharNode node1, CharNode node2){
        if(node1==null && node2==null){
            return;
        }
        if(node1==null || node2==null || node1.getCharacter()!=node2.getCharacter()){
            fail("Tree nodes are not equal!");
        }
        tET(node1.getLeft(), node2.getLeft());
        tET(node1.getRight(), node2.getRight());
    }


    public void printTree(CharNode root){
        System.out.println("----------------------------------");
        pt(root, 0);
        System.out.println("----------------------------------");
    }

    private void pt(CharNode node, int x)
    {
        int i;
        if(node != null)
        {
            x = x+5;
            pt(node.getLeft(), x);
            System.out.println();
            for ( i = 5; i < x; i++) {
                System.out.print(" ");
            }
            System.out.print("[" +node.getCharacter()+":"+node.getCounter()+"]");

            System.out.println();
            pt(node.getRight(), x);
        }
    }



}