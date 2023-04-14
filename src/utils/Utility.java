package utils;

import domain.CacheNode;

/**
 * A helper class for methods like
 * add node to list
 * remove node from list
 * move a node to head
 *
 * **/
public class Utility<K, V> {
    public void moveToHead(CacheNode<K, V> node, CacheNode<K,V> head, CacheNode<K,V> tail) {
        if (node == head) {
            return;
        }

        if (node == tail) {
            tail = node.getPrev();
            tail.setNext(null);
        } else {
            node.getPrev().setNext(node.getNext());
            node.getNext().setPrev(node.getPrev());
        }

        node.setPrev(null);
        node.setNext(head);
        head.setPrev(node);
        head = node;
    }
    public void removeFromList(CacheNode<K, V> entry, CacheNode<K,V> head, CacheNode<K,V> tail) {
        if (entry.getPrev() != null) {
            entry.getPrev().setNext(entry.getNext());
        } else {
            head = entry.getNext() ;
        }
        if (entry.getNext()  != null) {
            entry.getNext().setPrev(entry.getPrev());
        } else {
            tail = entry.getPrev();
        }
    }

    public void addNode(CacheNode<K, V> node, CacheNode<K,V> head, CacheNode<K,V> tail) {
        if (head == null) {
            head = node;
            tail = node;
        } else {
            node.setNext(head);
            head.setPrev(node);
            head = node;
        }
    }

}
