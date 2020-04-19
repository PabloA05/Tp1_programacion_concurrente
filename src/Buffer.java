import java.util.LinkedList;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Buffer {

    private LinkedList<Integer> store_list=new LinkedList<Integer>();
    private Lock listLock;
    private int num=0;

    public Buffer(boolean fairMode) {
        listLock = new ReentrantLock(fairMode);
    }
    private void add_toStore(int e){
        store_list.add(e);
    }
    private void remove_fromStore(){
        store_list.remove();
    }

    public void consume() {
    }

    public void reposition(Producto product) {
    }
}


