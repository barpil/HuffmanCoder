package huffmancode.structures;

import huffmancode.structures.nodes.KVPair;

import java.lang.reflect.Array;

public class Map<K extends Comparable<K>, V> {
    protected final V nil = null;
    protected final KVPair<K, V> DELETED= new KVPair<>(null, null);

    protected int size;
    protected int nElems;
    protected KVPair<K, V>[] hashElems;
    private final double correctLoadFactor;


    public Map() {
        this(2039); // initial size as random prime number
    }

    public Map(int size) {
        validateMapInitSize(size);

        this.size = size;
        this.hashElems = createTable(this.size);
        this.correctLoadFactor = 0.75;
    }


    public void put(K key,V value) {
        validateInputElem(key);
        resizeIfNeeded();

        int k = key.hashCode();
        int i = 0;
        int hashId = hashFunc(k, i);

        while (hashElems[hashId] != nil && hashElems[hashId] != DELETED) {
            if (i + 1 == size) {
                doubleResize();
                i = -1;
            }
            i = (i + 1) % size;
            hashId = hashFunc(k, i);
        }

        hashElems[hashId] = new KVPair<>(key,value);
        nElems++;
    }


    public V get(K key) {
        int i=0;
        int j;
        int k=key.hashCode();
        do{
            j=hashFunc(k,i);
            if(hashElems[j]!=null && hashElems[j].getKey().compareTo(key)==0){
                return hashElems[j].getValue();
            }
            i++;
        }while(i<this.size && hashElems[j]!=null);
        return null;
    }


    public void remove(K key) {
        int i=0;
        int j;
        int k=key.hashCode();
        do{
            j=hashFunc(k,i);
            if(hashElems[j].getKey().compareTo(key)==0){
                hashElems[j]=DELETED;
                return;
            }
            i++;
        }while(i<this.size && hashElems[j]!=null);

    }

    protected void validateMapInitSize(int initialSize) {
        if (initialSize < 1) {
            throw new IllegalArgumentException("Initial size of map cannot be lower than 1!");
        }
    }

    protected void validateInputElem(K key) {
        if (key == null) {
            throw new IllegalArgumentException("Key cannot be null!");
        }
    }

    protected int hashFunc(int key, int i){
        int m = getSize();
        key =key & Integer.MAX_VALUE;
        int h1=key%m;
        if(m==1){
            return h1;
        }
        int h2=1+(key%(m-1));
        int result = (h1+(h2*i))%m;
        return (result+m)%m;
    }

    int getSize() {
        return size;
    }

    @SuppressWarnings("unchecked")
    private KVPair<K,V>[] createTable(int size) {
        return (KVPair<K,V>[]) Array.newInstance(KVPair.class, size);

    }

    protected void resizeIfNeeded() {
        double loadFactor = countLoadFactor();
        if (loadFactor >= correctLoadFactor) {
            doubleResize();
        }
    }

    protected double countLoadFactor() {
        return (double) nElems / size;
    }

    protected void doubleResize() {
        this.size *= 2;
        KVPair<K,V>[] oldElems;
        oldElems = hashElems;
        hashElems = createTable(this.size);
        nElems = 0;

        KVPair<K,V> currentElem;
        for (int i = 0; i < oldElems.length; i++) {
            currentElem = oldElems[i];

            if (currentElem != nil && currentElem != DELETED) {
                put(currentElem.getKey(), currentElem.getValue());
            }
        }
    }

    public int getnElems(){
        return nElems;
    }
}


