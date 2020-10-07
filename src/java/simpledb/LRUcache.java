package simpledb;

import java.util.Hashtable;
import java.util.NoSuchElementException;
import java.util.Set;


public class LRUcache {
    class DLinkedNode {
        PageId key;
        Page value;
        DLinkedNode pre;
        DLinkedNode post;
    }

    /**
     * Always add the new node right after head;
     */
    private void addNode(DLinkedNode node) {

        node.pre = head;
        node.post = head.post;

        head.post.pre = node;
        head.post = node;
    }

    /**
     * Remove an existing node from the linked list.
     */
    private void removeNode(DLinkedNode node){
        DLinkedNode pre = node.pre;
        DLinkedNode post = node.post;

        pre.post = post;
        post.pre = pre;
    }

    /**
     * Move certain node in between to the head.
     */
    private void moveToHead(DLinkedNode node){
        this.removeNode(node);
        this.addNode(node);
    }

    // pop the current tail.
    private DLinkedNode popTail(){
        DLinkedNode res = tail.pre;
        this.removeNode(res);
        return res;
    }

    private Hashtable<PageId, DLinkedNode> cache = new Hashtable<PageId, DLinkedNode>();
    private int count;
    private int capacity;
    private DLinkedNode head, tail;

    public LRUcache(int capacity) {
        this.count = 0;
        this.capacity = capacity;

        head = new DLinkedNode();
        head.pre = null;

        tail = new DLinkedNode();
        tail.post = null;

        head.post = tail;
        tail.pre = head;
    }

    public Page get(PageId key) {
        DLinkedNode node = cache.get(key);
        if(node == null){
            throw new NoSuchElementException();
        }

        // move the accessed node to the head;
        this.moveToHead(node);

        return node.value;
    }

    public void remove(PageId key) {
        this.cache.remove(key);
        --count;
        DLinkedNode cur = head;
        while (cur.post !=tail) {
            cur = cur.post;
            if (cur.key.equals(key)) {
                this.removeNode(cur);
            }
        }
    }

    public Set<PageId> KeySet() {
        return this.cache.keySet();
    }

    public boolean Containkey(PageId p) {
        return cache.containsKey(p);
    }

    public Page Simpleget(PageId pid) {
        return cache.get(pid).value;
    }

    public Page put(PageId key, Page value) {
        DLinkedNode node = cache.get(key);
        Page res = null;
        if(node == null){

            DLinkedNode newNode = new DLinkedNode();
            newNode.key = key;
            newNode.value = value;

            this.cache.put(key, newNode);
            this.addNode(newNode);

            ++count;

            if(count > capacity){
                // pop the tail
                DLinkedNode tail = this.popTail();
                res = tail.value;
            }
        }else{
            // update the value.
            node.value = value;
            this.moveToHead(node);
        }
        return res;
    }

}
