package simpledb;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class HeapFileIterator implements DbFileIterator {
    private  TransactionId tid;
    private int currentPage;
    private int tableid;
    private int numPages;
    private HeapPageId pid;
    private java.util.Iterator<Tuple> Iterator;

    public HeapFileIterator(TransactionId tid, int tableid, int numPages) {
        this.tid = tid;
        this.tableid = tableid;
        this.numPages = numPages;
    }

    private Iterator<Tuple> getTupleIterator(HeapPageId pid) throws TransactionAbortedException, DbException {
        HeapPage hp = (HeapPage) Database.getBufferPool().getPage(tid,pid,Permissions.READ_ONLY);
        return hp.iterator();
    }

    @Override
    public void open() throws DbException, TransactionAbortedException {
        currentPage = 0;
        pid = new HeapPageId(tableid,currentPage);
        Iterator = getTupleIterator(pid);
    }

    public Tuple next() throws DbException, TransactionAbortedException {
        if (!hasNext()) {
            throw new NoSuchElementException();
        }
        return Iterator.next();
    }

    @Override
    public boolean hasNext() throws DbException, TransactionAbortedException {
        if (Iterator == null) {
            return false;
        }
        if (Iterator.hasNext()) {
            return true;
        }
        if (currentPage < numPages - 1) {
            currentPage ++;
            HeapPageId pid = new HeapPageId(tableid,currentPage);
            Iterator = getTupleIterator(pid);
            return Iterator.hasNext();
        }
        return false;

    }

    @Override
    public void rewind() throws DbException, TransactionAbortedException {
        close();
        open();
    }

    public void close() {
        Iterator = null;
    }
}
