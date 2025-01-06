package huffmancode.structures;

import huffmancode.structures.nodes.CharNode;
import pl.edu.pw.ee.aisd2024zex4.Node;

public class NodePriorityQueue {
    RedBlackTree<Integer, CharNode> rbt;
    private int size;
    public NodePriorityQueue(){
        rbt = new RedBlackTree<>();
    }

    public void push(CharNode node){
        rbt.put(node.getCounter(), node);
        size++;
    }

    public CharNode pop(){
        if(rbt.getRoot()==null) {
            return null;
        }
        Node<Integer, CharNode> n = rbt.getRoot();
        while(n.getLeft()!=null){
            n=n.getLeft();
        }
        rbt.deleteMin();
        size--;
        return n.getValue();
    }

    public int size(){
        return size;
    }
    public Node<Integer, CharNode> getRoot(){
        return rbt.getRoot();
    }


}
