package simpledb;

import java.util.HashMap;

public class LRUCache {

    private class Node {
        int key, val;
        Node prev, next;
        public Node(int key, int val) {
            this.key = key;
            this.val = val;
        }
        public void removeSelf() {
            if (this.prev != null) this.prev.next = this.next;
            if (this.next != null) this.next.prev = this.prev;
            this.prev = null;
            this.next = null;
        }
        public Node addFirst(Node node) {
            node.prev = null;
            node.next = this;
            this.prev = node;
            return node;
        }
    }

    private HashMap<Integer, Node> map;
    private Node first, last;
    private int capacity;

    private void reorder(Node node) {
        if (this.first == this.last || this.first == node) return;
        if (node.next == null) this.last = node.prev; // Update Last Node
        node.removeSelf(); // Remove Connections to Node
        this.first = this.first.addFirst(node); // Move Node to First Node
    }

    public LRUCache(int capacity) {
        this.map = new HashMap<>(capacity);
        this.capacity = capacity;
    }

    public int get(int key) {
        if (!this.map.containsKey(key)) return -1;
        Node node = this.map.get(key);
        reorder(node);
        return node.val;
    }

    public void put(int key, int value) {
        // Update Value if Contains
        if (this.map.containsKey(key)) {
            Node node = this.map.get(key);
            node.val = value;
            reorder(node);
            return;
        }

        // Reuse Last Node
        if (this.map.size() == capacity) {
            Node lastNode = this.last;
            this.map.remove(lastNode.key);
            lastNode.key = key;
            lastNode.val = value;
            reorder(lastNode);
            this.map.put(key, lastNode);
            return;
        }

        // Add New Node to First Node
        Node newNode = new Node(key, value);
        if (this.first == null) {
            this.first = newNode;
            this.last = newNode;
        } else {
            this.first = this.first.addFirst(newNode);
        }
        this.map.put(key, newNode);
    }
}

