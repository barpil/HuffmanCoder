package huffmancode.structures;

import java.lang.reflect.Array;
import java.util.Arrays;


public class SimpleStack<T> {
    T[] array;
    int size;
    @SuppressWarnings("unchecked")
    SimpleStack(Class<T> clazz){
        this(clazz, 10);
    }
    @SuppressWarnings("unchecked")
    SimpleStack(Class<T> clazz, int initialSize){
        array = (T[]) Array.newInstance(clazz, initialSize);
        size=0;
    }

    public void push(T element){
        resizeIfNeeded();
        array[size]= element;
        size++;
    }

    public T pop(){
        if(size>0){
            size--;
            T tmp = array[size];
            array[size]=null;
            return tmp;
        }
        return null;

    }

    public int getSize(){
        return size;
    }
    private void resizeIfNeeded(){
        if(size==array.length){
            resize();
        }
    }

    private void resize(){
        array = Arrays.copyOf(array, array.length*2);
    }


}
