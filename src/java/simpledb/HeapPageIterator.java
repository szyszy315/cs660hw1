package simpledb;

import java.util.ArrayList;
import java.util.Iterator;

public class HeapPageIterator implements Iterator<Tuple> {
    ArrayList<Tuple> tuples;
    Iterator<Tuple> iterator;
    public HeapPageIterator(ArrayList<Tuple> tuples) {
        this.tuples = tuples;
        iterator = tuples.iterator();
    }

    @Override
    public boolean hasNext() {
        return iterator.hasNext();
    }

    @Override
    public Tuple next() {
        return iterator.next();
    }

    @Override
    public void remove() {

    }
}