package huffmancode.structures;

import huffmancode.structures.nodes.CharNode;
import huffmancode.structures.nodes.LeafCharNode;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pl.edu.pw.ee.aisd2024zex4.Node;
import util.CharacterGenerator;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

class NodePriorityQueueTest {
    NodePriorityQueue pq;
    @BeforeEach
    public void createPriorityQueue(){
        pq = new NodePriorityQueue();
    }

    @Test
    public void shouldReturn_MinNodeFromQueue(){
        pq.push(new LeafCharNode('a',4));
        pq.push(new LeafCharNode('b',3));
        pq.push(new LeafCharNode('c',2));
        pq.push(new LeafCharNode('d',7));
        printTree(pq.getRoot());
        assertEquals('c',pq.pop().getCharacter());
    }

    @Test
    public void shouldBeAbleTo_PopTheSameNumberOfTimesAsHasBeenPushedTo(){
        Random rand = new Random(1337);
        CharacterGenerator cG = new CharacterGenerator();
        int DEFAULT_SIZE = 9;
        NodePriorityQueue pQ = new NodePriorityQueue();
        char[] arrayOfCharacters = cG.generateRandomArrayOfDifferentCharacters(DEFAULT_SIZE);
        for(char c : arrayOfCharacters){
            pQ.push(new LeafCharNode(c, rand.nextInt(1,3)));
        }
        printTree(pQ.getRoot());
        for(int i=0;i<arrayOfCharacters.length;i++){
            System.out.print(pQ.pop().getCharacter()+" ");
        }
        if(pQ.size()!=0){
            fail("Queue had more characters than passed!");
        }
    }
    @Test
    public void popingRandomValues_ShouldResultInSortedArray(){
        CharacterGenerator cG = new CharacterGenerator();
        int arraySize = 100;
        List<CharNode> charNodeArray = new ArrayList<>();

        for(int i=0;i<arraySize;i++){
            pq.push(new LeafCharNode(cG.generateRandomCharacter(), cG.generateRandomCharacter()));
        }
        CharNode l;
        while((l = pq.pop()) != null){
            charNodeArray.add(l);
        }
        for(int i=1;i<charNodeArray.size();i++){
            if(charNodeArray.get(i).getCounter()<charNodeArray.get(i-1).getCounter()) fail("pop() of huffmancode.structures.NodePriorityQueue does not return smallest Nodes");
        }


    }

    public void printTree(Node<Integer, CharNode> root){
        System.out.println("\n----------------------------------");
        pt(root, 0);
        System.out.println("----------------------------------");
    }

    private void pt(Node<Integer, CharNode> node, int x)
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
            System.out.print("[" +node.getValue().getCharacter()+":"+node.getValue().getCounter()+"]");

            System.out.println();
            pt(node.getRight(), x);
        }
    }


}