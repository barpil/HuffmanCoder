package huffmancode.structures.nodes;

public class CharNode implements Comparable<CharNode>{
    private static Object nil;
    protected char character;
    protected int counter;



    private CharNode left;
    private CharNode right;
    public CharNode(Integer counter){
        this.counter=counter;
        character= (char)-1;
        left=null;
        right=null;
    }

    public void setLeft(CharNode left) {
        this.left = left;
    }

    public void setRight(CharNode right) {
        this.right = right;
    }

    @Override
    public int compareTo(CharNode charNode) {
        if(this.counter< charNode.counter) return -1;
        if(this.counter== charNode.counter) return 0;
        return 1;
    }

    public void setCounter(int value){
        counter=value;
    }
    public void incrementCounter(){
        counter++;
    }

    public int getCounter(){
        return counter;
    }

    public char getCharacter() {
        return character;
    }
    public CharNode getRight() {
        return right;
    }

    public CharNode getLeft() {
        return left;
    }
}
