package huffmancode.structures;

import getters.AdvancedGetters;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pl.edu.pw.ee.aisd2024zex4.Node;
import pl.edu.pw.ee.aisd2024zex4.RedBlackTree;
import util.CharacterGenerator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

import static org.junit.jupiter.api.Assertions.*;

class RedBlackTreeTest {
    pl.edu.pw.ee.aisd2024zex4.RedBlackTree<Integer, String> rbt;
    static CharacterGenerator dataGenerator;
    final int TEST_DATA_SIZE=10000;
    @BeforeAll
    public static void setUpGenerator(){
        dataGenerator=new CharacterGenerator(1234);
    }



    @BeforeEach
    public void setUpClass(){
        rbt = new RedBlackTree<>();
    }


    @Test
    void put_Elements_Should_Be_Avaible_To_Get(){
        String[] values = dataGenerator.generateRandStringArray(TEST_DATA_SIZE);
        Function<Object, Integer> getKey = key -> key.hashCode();
        for(String v: values){
            rbt.put(getKey.apply(v), v);
        }
        for(String v :values){
            if(rbt.get(getKey.apply(v))==null){
                fail("Key value pair <"+getKey.apply(v)+", "+v+"> could not be found in tree!");
            }
            if(!rbt.get(getKey.apply(v)).equals(v)){
                fail("Get method returned value not reletated to given key!");
            }
        }

    }

    @Test
    void should_HaveBlackRoot_Property(){
        String[] values = dataGenerator.generateRandStringArray(TEST_DATA_SIZE);

        for(String v: values){
            rbt.put(v.hashCode(), v);
            if(AdvancedGetters.getRootNode(rbt).isRed()) fail("Root note cannot be red!");
        }


    }

    @Test
    void redNodesChildren_Should_AlwaysBeBlack_Property(){
        String[] values = {"S","O","C","Z","E","W","K","I"};

        List<String> putValues= new ArrayList<>();
        for(String v: values){
            rbt.put(v.hashCode(), v);
            putValues.add(v);

            Integer key = v.hashCode();
            Node<Integer, String> testNode = AdvancedGetters.getNodeByKey(rbt, key);
            if(testNode==null){
                fail("A node which should be present was missing!");
            }
            if(testNode.isRed()){
                if(isNodeRed(testNode.getLeft()) || isNodeRed(testNode.getRight())){
                    fail("At least one child of a red node is not black!");
                }
            }

        }


    }

    @Test
    void allBranchesFromParticularNode_Should_HaveEqualNumberOfBlackNodes(){
        String[] values = {"S","O","C","Z","E","W","K","I"};

        for(String v: values){
            rbt.put(v.hashCode(), v);
            isNumberOfBlackNodesEqualInEachPathCheck();
        }


    }

    @Test
    void redNodes_shouldNot_BeLeftChildren(){
        String[] values = {"S","O","C","Z","E","W","K","I"};

        for(String v: values){
            rbt.put(v.hashCode(), v);
            areAllRedNodesNotRightChildrenCheck();
        }


    }
    @Test
    void tree_Should_HaveCorrectBalancePropertyAtAllTimes(){
        String[] values = {"S","O","C","Z","E","W","K","I"};

        for(String v: values){
            rbt.put(v.hashCode(), v);
            treeBalancePropertyCheck(AdvancedGetters.getRootNode(rbt), 1);
        }
    }


    @Test
    void should_haveAllRedBlackTreeProperties_With_ASC_DataInserted(){
        String[] values = dataGenerator.generateAscStringArray(TEST_DATA_SIZE);
        java.util.Map<Integer, String> putValues= new HashMap<>();
        for(String v: values){
            rbt.put(v.hashCode(), v);
            putValues.put(v.hashCode(), v);
            checkRBTProperties(putValues.values().toArray(new String[0]));
        }
    }

    @Test
    void should_haveAllRedBlackTreeProperties_With_DSC_DataInserted(){
        String[] values = dataGenerator.generateDescStringArray(TEST_DATA_SIZE);
        java.util.Map<Integer,String> putValues= new HashMap<>();
        for(String v: values){
            rbt.put(v.hashCode(), v);
            putValues.put(v.hashCode(), v);
            checkRBTProperties(putValues.values().toArray(new String[0]));
        }
    }

    @Test
    void should_ReplaceNode_IfKeyAlreadyExistsInTree(){
        Node<Integer, String> shouldNotBePresentNode = new Node<>(4,"SHOULD_NOT_BE_PRESENT");
        List<Node> nodeList = new ArrayList<>(List.of(new Node[]{new Node(1,"A"),shouldNotBePresentNode ,new Node(14, "L"), new Node(shouldNotBePresentNode.getKey(),"B"), new Node(7,"C"),new Node(8,"G"), new Node(2,"D")}));

        for(Node n:  nodeList){
            rbt.put((Integer) n.getKey(), (String) n.getValue());
        }
        if(rbt.get(shouldNotBePresentNode.getKey()).equals(shouldNotBePresentNode.getValue())){
            fail("Value was not replaced after putting node with same key!");
        }
    }
    @Test
    void should_DeleteMinElement_And_RetainRedBlackTreePropertiesAfter(){
        String[] values = dataGenerator.generateRandStringArray(TEST_DATA_SIZE);
        String minValue = getMinStringKey(values);
        if(minValue==null){
            return;
        }
        for(String s: values){
            rbt.put(s.hashCode(), s);
        }
        rbt.deleteMin();
        Map<Integer, String> mapOfValues = new HashMap<>();
        for(String s: values){
            mapOfValues.put(s.hashCode(),s);
        }
        mapOfValues.remove(minValue.hashCode());
        if(rbt.get(minValue.hashCode())!=null){
            fail("Minimal value is still present in tree after deletion method! (minKey="+ minValue +", tree.get(minKey)="+rbt.get(minValue.hashCode())+")");
        }


        checkRBTProperties(mapOfValues.values().toArray(new String[0]));


    }
    private String getMinStringKey(String[] values){
        if(values.length<1){
            return null;
        }
        Integer minKey = values[0].hashCode();
        String minValue= values[0];
        for(int i=1;i<values.length;i++){
            if(minKey.compareTo(values[i].hashCode())>0){
                minKey =values[i].hashCode();
                minValue=values[i];
            }
        }
        return minValue;
    }

    @Test
    void shouldNot_ThrowError_When_DeletingMinimalValue_If_TreeIsEmpty(){
        rbt.deleteMin();
    }

    @Test
    void should_ReturnNull_IfKeyNotPresentInTree(){
        rbt = new RedBlackTree<>();
        rbt.put(1,"A");
        if(rbt.get(2)!=null){
            fail("Get method returns value of non existant key in tree!");
        }
    }

    @Test
    void should_ReturnCorrectValue_IfKeyPresentInTree(){
        rbt= new RedBlackTree<>();
        String[] stringArray = dataGenerator.generateRandStringArray(100);
        for(String s: stringArray){
            rbt.put(s.hashCode(), s);
        }
        assertEquals(stringArray[0], rbt.get(stringArray[0].hashCode()));
    }

    @Test
    void should_DeleteMinimalValueCorretly_If_RootIsMinimal(){
        rbt.put(1,"A");
        rbt.put(2,"B");
        rbt.deleteMin();
        if(rbt.get(1)!=null){
            fail("Minimal value is still present in tree after deletion!");
        }
        if(rbt.get(2)==null || !rbt.get(2).equals("B")){
            fail("While trying to delete minimal value, other values have been modified!");
        }
    }

    private void checkRBTProperties(String[] putValues){


        //Korzeń musi być czarny
        if(AdvancedGetters.getRootNode(rbt).isRed()) fail("Root note cannot be red!");

        //Dzieci czerwonego wezla sa zawsze czarne
        for(String v: putValues){
            Integer key = v.hashCode();
            Node<Integer, String> testNode = AdvancedGetters.getNodeByKey(rbt, key);
            if(testNode==null){
                fail("A node which should be present was missing!");
            }
            if(testNode.isRed()){
                if(isNodeRed(testNode.getLeft()) || isNodeRed(testNode.getRight())){
                    fail("At least one child of a red node is not black!");
                }
            }
        }

        //Kazda sciezka od okreslonego wezla do liscia ma tyle samo czarnych wezlow
        isNumberOfBlackNodesEqualInEachPathCheck();

        //Czerwone wezly nie moga byc prawym dzieckiem innego wezla
        areAllRedNodesNotRightChildrenCheck();

        //Najdluzsza galaź może być maksymalnie 2 razy dłuższa od najkrótszej gałęzi
        treeBalancePropertyCheck(AdvancedGetters.getRootNode(rbt), 1);
    }


    private int blackValue;
    private <K extends Comparable<K>, V> void isNumberOfBlackNodesEqualInEachPathCheck(){
        blackValue=-1;
        arePathsEquallyBlack(AdvancedGetters.getRootNode(rbt), 0);

    }

    private <K extends Comparable<K>, V> void arePathsEquallyBlack(Node<K,V> node, int numberOfBlackNodes){
        int x=numberOfBlackNodes;
        if(isNodeBlack(node)){
            x++;
        }
        if(node.getLeft()!=null){
            arePathsEquallyBlack(node.getLeft(), x);
        }
        if(node.getRight()!=null){
            arePathsEquallyBlack(node.getRight(), x);
        }
        if(node.getLeft()==null & node.getRight()==null){
            if(blackValue<0){
                blackValue=x;
            }else{
                if(blackValue!=x){
                    fail("Number of black nodes in branches counted from particular node are not equal!");
                }
            }
        }
    }

    private <K extends Comparable<K>, V> void areAllRedNodesNotRightChildrenCheck(){
        redNotOnRightCheck(AdvancedGetters.getRootNode(rbt));
    }

    private <K extends Comparable<K>, V> void redNotOnRightCheck(Node<K,V> node){
        if(isNodeRed(node.getRight())){
            fail("Right nodes cannot be red!");
        }
        if(node.getLeft()!=null){
            redNotOnRightCheck(node.getLeft());
        }
        if(node.getRight()!=null){
            redNotOnRightCheck(node.getRight());
        }
    }



    int maxPath=-1;
    int minPath;
    private <K extends Comparable<K>, V> void treeBalancePropertyCheck(Node<K,V> node, int pathCount){

        if(node.getLeft()!=null){
            treeBalancePropertyCheck(node.getLeft(),pathCount+1);
        }
        if(node.getRight()!=null){
            treeBalancePropertyCheck(node.getRight(), pathCount+1);
        }
        if(node.getLeft()==null & node.getRight()==null){
            if(maxPath<0) {
                maxPath = pathCount;
                minPath = maxPath;
            }else{
                if(maxPath<pathCount){
                    maxPath=pathCount;
                } else if (minPath>pathCount) {
                    minPath=pathCount;
                }

            }
        }
        if(pathCount==0){
            if(maxPath>minPath*2){
                fail("Longest path is to long! Longest path should be at most 2 times longer than shortest path. (maxPath="+maxPath+", minPath="+minPath+")");
            }
        }
    }


    private <K extends Comparable<K>, V> boolean isNodeBlack(Node<K, V> node) {
        return !isNodeRed(node);
    }

    private <K extends Comparable<K>, V> boolean isNodeRed(Node<K, V> node) {
        return node != null && node.isRed();
    }



}