package huffmancode.structures.nodes;

public class KVPair<K extends Comparable<K>, V> {
    K key;
    V value;
    public KVPair(K key, V value){
        this.key= key;
        this.value=value;
    }

    public K getKey() {
        return key;
    }

    public V getValue() {
        return value;
    }
}
