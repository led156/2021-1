//package cse2010.assignment2;
package hw2;

public class DLinkedList<T> {

    private Node<T> header;
    private Node<T> trailer;
    private int size = 0;

    /**
     * Create an empty doubly linked list
     */
    public DLinkedList() {
        /**/
        header = new Node<>(null,null,null);
        trailer = new Node<>(null,header,null);
        header.setNext(trailer);
    }

    /**
     * Set the header node item field with `info`.
     * @param info
     */
    public void setHeaderInfo(T info) {
        header.setItem(info);
    }

    /**
     * Set the trailer node item field with `info`.
     * @param info
     */
    public void setTrailerInfo(T info) {
        trailer.setItem(info);
    }

    /**
     * Check whether a list is empty or not.
     * @return true if list is empty, false otherwise
     */
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * Returns the number of nodes in a list.
     * @return  the number of nodes in a list
     */
    public int getSize() { return size; }

    /**
     * Returns the first node of a list if exists.
     * @return  the first node of a list, null if not exists
     */
    public Node<T> getFirst() {
        if (isEmpty()) return null;
        return header.getNext();
    }

    /**
     * Returns the last node of a list if exists.
     * @return  the last node of a list, null if not exists
     */
    public Node<T> getLast() {
        if (isEmpty()) return null;
        return trailer.getPrev();
    }

    /**
     * Insert a node with item `n` at the front of a list.
     * @param n a new item to insert
     */
    public void addFirst(T n) {
        /**/
    	addAfter(header,new Node<>(n,null,null));
    }

    /**
     * Insert a node with item `n` at the rear of a list.
     * @param  n a new item to insert
     */
    public void addLast(T n) {
        /**/
    	addBefore(trailer,new Node<>(n, null, null));
    }

    /**
     * Remove the first node of a list, and returns an item of the node, if exists.
     * @return  item of the removed node if exists, null otherwise
     */
    public T removeFirst() {
        /**/
    	return remove(header.getNext());
    }

    /**
     * Remove the last node of a list, and returns an item of the node, if exists.
     * @return  item of the removed node if exists, null otherwise
     */
    public T removeLast() {
        /**/
    	return remove(trailer.getPrev());
    }

    /**
     * Insert a new node `n` after node `p`.
     * @param p target node to insert a new node after
     * @param n node to insert
     */
    public void addAfter(Node<T> p, Node<T> n) {
       /**/
    	n.setNext(p.getNext());
    	n.setPrev(p);
    	p.getNext().setPrev(n);
    	p.setNext(n);
    	size = size+1;
    }

    /**
     * Insert a new node `n` before node `p`.
     * @param p target node to insert a new node before
     * @param n node to insert
     */
    public void addBefore(Node<T> p, Node<T> n) {
        /**/
    	n.setPrev(p.getPrev());
    	n.setNext(p);
    	p.getPrev().setNext(n);
    	p.setPrev(n);
    	size = size + 1;
    }

    /**
     * Remove a node.
     * @param n target node to remove
     * @return  a node
     */
    public T remove(Node<T> n) {
        /**/
    	n.getPrev().setNext(n.getNext());
    	n.getNext().setPrev(n.getPrev());
    	n.setPrev(null);
    	n.setNext(null);
    	size = size - 1;
    	return n.getItem();
    }

    /**
     * Returns a string representation of a list.
     * @return  a string representation of a list
     */
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder(
            "List: size = " + size + " [");
        Node<T> current = header.getNext();

        while (current != trailer) {
            builder.append(current.getItem().toString());
            current = current.getNext();
        }
        builder.append("]");

        return builder.toString();
    }
}
