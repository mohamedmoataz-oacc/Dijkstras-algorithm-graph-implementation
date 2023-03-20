import java.util.HashMap;

public class PriorityQueue {
    Heap heap;

    PriorityQueue() {heap = new Heap();}

    public void insert(String key, int value) {heap.insert(key, value);}
    public HashMap<String, Integer> get() {return heap.get();}
    public boolean isEmpty() {return heap.isEmpty();}
}
