package getters;

import huffmancode.structures.nodes.CharNode;
import huffmancode.structures.CharacterTree;
import pl.edu.pw.ee.aisd2024zex4.Node;
import pl.edu.pw.ee.aisd2024zex4.RedBlackTree;

import java.lang.reflect.Field;

public class AdvancedGetters {
    public static <K extends Comparable<K>, V> Node<K,V> getNodeByKey(RedBlackTree<K,V> redBlackTree, K key){

        Node<K,V> node = AdvancedGetters.getRootNode(redBlackTree);
        while (node != null) {

            if (shouldCheckOnTheLeft(key, node)) {
                node =node.getLeft();

            } else if (shouldCheckOnTheRight(key, node)) {
                node = node.getRight();

            } else {
                return node;
            }
        }

        return null;
    }
    public static <K extends Comparable<K>, V> Node<K,V> getRootNode(RedBlackTree<K,V> redBlackTree){
        String fieldRoot = "root";

        try {
            Field field = redBlackTree.getClass().getDeclaredField(fieldRoot);
            field.setAccessible(true);

            return ((Node<K,V>) field.get(redBlackTree));
        } catch (NoSuchFieldException | IllegalArgumentException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }
    private static <K extends Comparable<K>, V> boolean shouldCheckOnTheLeft(K key, Node<K, V> node) {
        return key.compareTo(node.getKey()) < 0;
    }

    private static <K extends Comparable<K>, V> boolean shouldCheckOnTheRight(K key, Node<K, V> node) {
        return key.compareTo(node.getKey()) > 0;
    }

    public static CharNode getRootOfCharacterTree(CharacterTree cT){
        Field field = null;
        CharNode root=null;
        try {
            field = cT.getClass().getDeclaredField("root");
            field.setAccessible(true);
            root= (CharNode) field.get(cT);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }


        return root;
    }
}
