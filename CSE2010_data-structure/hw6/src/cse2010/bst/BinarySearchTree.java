package cse2010.bst;

import cse2010.orderedmap.Entry;
import cse2010.orderedmap.OrderedMap;
import cse2010.trees.Position;
import cse2010.trees.binary.linked.LinkedBinaryTree;

import java.awt.*;
import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Link-based implementation of binary search tree
 *
 * @param <K> the key type
 * @param <V> the value type
 */
public class BinarySearchTree<K extends Comparable<K>, V> extends LinkedBinaryTree<Entry<K, V>>
        implements OrderedMap<K, V> {

    /**
     * Removes the entry having key "key" (if any) and returns its associated value.
     *
     * @param key search key
     */
    @Override
    public V remove(K key) {
        V result = null;

        Position<Entry<K, V>> n = root();
        if(n==null){
            return null;
        }

        while(isInternal(n) && n.getElement().getKey().compareTo(key) != 0){
            K k = n.getElement().getKey();
            if(k.compareTo(key)==1){ // k > key, to left
                n = left(n);
            }

            else{ // k < key, to right
                n = right(n);
            }
        }

        //
        if(isExternal(n)) return null;

        //case 1
        if(isExternal(left(n)) | isExternal(right(n))){
            // (1)
            if(isExternal(left(n))){
                this.remove(left(n)); size = size + 1;
            }
            else if(isExternal(right(n))){
                this.remove(right(n)); size = size + 1;
            }

            // (2)
            result = n.getElement().getValue();
            remove(n);
        }


        //case 2
        else{
            // (1)
            Position<Entry<K, V>> w = root();
            if(w==null){
                result = null;
            }
            else {
                while (isInternal(w) && w.getElement().getKey().compareTo(key) != 0) {
                    K k = w.getElement().getKey();
                    if (k.compareTo(key) == 1) { // k > key, to left
                        w = left(w);
                    } else { // k < key, to right
                        w = right(w);
                    }
                }

                if (isRoot(w) || (hasRight(w) && right(w).getElement() != null)) {
                    w = right(w);
                    while (hasLeft(w) && left(w).getElement() != null) {
                        w = left(w);
                    }
                    result = w.getElement().getValue();
                }

                else if (isLeftChild(w)) {
                    result = parent(w).getElement().getValue();
                }

                else if (isRightChild(w)) {
                    while (isRightChild(w)) {
                        w = parent(w);
                        if (isRoot(w)) result = null; break;
                    }
                    result = parent(w).getElement().getValue();
                }
            }


            // (2)
            if(w==null) return result;
            result = n.getElement().getValue();
            replace(n, new Entry<>(w.getElement().getKey(), w.getElement().getValue()));

            // (3)
            remove(left(w)); size = size + 1;
            remove(w);

        }

        return result;
    }

    /**
     * Returns the set of keys in the map sorted in ascending order.
     * 
     * @return the set of keys in the map sorted in ascending order
     */
    @Override
    public Set<K> keys() {
        return inOrder().stream().filter(p -> p.getElement() != null).map(p -> p.getElement().getKey())
                .collect(Collectors.toSet());
    }

    /**
     * Returns the collection of values in the map.
     * 
     * @return the collection of values in the map
     */
    @Override
    public Collection<V> values() {
        return inOrder().stream().filter(p -> p.getElement() != null).map(p -> p.getElement().getValue())
                .collect(Collectors.toSet());
    }

    /**
     * Returns the collection of entries in the map.
     * 
     * @return the collection of entries in the map
     */
    @Override
    public Collection<Entry<K, V>> entries() {
        return inOrder().stream().filter(p -> p.getElement() != null).map(p -> p.getElement())
                .collect(Collectors.toSet());
    }

    /**
     * Returns the entry with smallest key value (or null, if the tree is empty).
     *
     * @return the entry with smallest key value (or null, if the tree is empty)
     */
    @Override
    public Entry<K, V> firstEntry() {
        if(this.isEmpty()) return null;
        Position n = root();
        while(isInternal(left(n))){
            if(hasLeft(n)) n = left(n);
        }
        return (Entry<K, V>) n.getElement();
    }

    /**
     * Returns the entry with largest key value(or null if the tree is empty)
     *
     * @return the entry with largest key value (or null, if the tree is empty)
     */
    @Override
    public Entry<K, V> lastEntry() {
        if(this.isEmpty()) return null;
        Position n = root();
        while(isInternal(right(n))){
            if(hasRight(n)) n = right(n);
        }
        return (Entry<K, V>) n.getElement();
    }


    /**
     * Returns the entry with the largest key value less than or equal to "key" (or
     * null if no such entry exists).
     *
     * @param key search key
     * @return the entry with the largest key value less than or equal to "key" (or
     *         null, if no such entry exists)
     */
    @Override
    public Entry<K, V> floorEntry(K key) {
        Position<Entry<K, V>> n = root();
        if(n==null){
            return null;
        }

        while(isInternal(n) && n.getElement().getKey().compareTo(key) != 0){
            K k = n.getElement().getKey();
            if(k.compareTo(key)==1){ // k > key, to left
                n = left(n);
            }

            else{ // k < key, to right
                n = right(n);
            }
        }

        if(isRoot(n)) return n.getElement();

        if(n.getElement()!=null && n.getElement().getKey().compareTo(key) == 0){
            return n.getElement();
        }

        if(isLeftChild(n)) {
            while(isLeftChild(n)){
                n = parent(n);
                if(isRoot(n)) return null;
            }
            return n.getElement();
        }

        if(isRightChild(n)) {
            return parent(n).getElement();
        }

        return null;
    }



    /**
     * Returns the entry with the largest key value strictly less than "key" (or
     * null if no such entry exists).
     *
     * @param key search key
     * @return the entry with the largest key value strictly less than k (or null,
     *         if no such entry exists).
     */
    @Override
    public Entry<K, V> lowerEntry(K key) {
        Position<Entry<K, V>> n = root();
        if(n==null){
            return null;
        }
        while(isInternal(n) && n.getElement().getKey().compareTo(key) != 0){
            K k = n.getElement().getKey();
            if(k.compareTo(key)==1){ // k > key, to left
                n = left(n);
            }

            else{ // k < key, to right
                n = right(n);
            }
        }

        if(isRoot(n) || (hasLeft(n) && left(n).getElement()!=null)){
            n = left(n);
            while(hasRight(n) && right(n).getElement()!=null){
                n = right(n);
            }
            return n.getElement();
        }

        if(isRightChild(n)){
            return parent(n).getElement();
        }

        if(isLeftChild(n)) {
            while(isLeftChild(n)){
                n = parent(n);
                if(isRoot(n)) return null;
            }
            return parent(n).getElement();
        }

        return null;
    }



    /**
     * Returns the entry with the least key value greater than or equal to "key"
     * (or null if no such entry exists).
     *
     * @param key search key
     * @return the entry with the least key value greater than or equal to k (or
     *         null, if no such entry exists)
     */
    @Override
    public Entry<K, V> ceilingEntry(K key) {
        Position<Entry<K, V>> n = root();
        if(n==null){
            return null;
        }
        while(isInternal(n) && n.getElement().getKey().compareTo(key) != 0){
            K k = n.getElement().getKey();
            if(k.compareTo(key)==1){ // k > key, to left
                n = left(n);
            }
            else{ // k < key, to right
                n = right(n);
            }
        }

        if(isRoot(n)) return n.getElement();

        if(n.getElement()!=null && n.getElement().getKey().compareTo(key) == 0){
            return n.getElement();
        }

        if(isRightChild(n)) {
            while(isRightChild(n)){
                n = parent(n);
                if(isRoot(n)) return null;
            }
            return parent(n).getElement();
        }

        if(isLeftChild(n)) {
            return parent(n).getElement();
        }


        return null;
    }



    /**
     * Returns the entry with the least key value strictly greater than "key" (or
     * null if no such entry exists).
     *
     * @param key search key
     * @return the entry with the least key value strictly greater than "key" (or
     *         null if no such entry exists)
     */
    @Override
    public Entry<K, V> higherEntry(K key) {
        Position<Entry<K, V>> n = root();
        if(n==null){
            return null;
        }
        while(isInternal(n) && n.getElement().getKey().compareTo(key) != 0){
            K k = n.getElement().getKey();
            if(k.compareTo(key)==1){ // k > key, to left
                n = left(n);
            }

            else{ // k < key, to right
                n = right(n);
            }
        }

        if(isRoot(n) || (hasRight(n) && right(n).getElement()!=null)){
            n = right(n);
            while(hasLeft(n) && left(n).getElement()!=null){
                n = left(n);
            }
            return n.getElement();
        }

        if(isLeftChild(n)){
            return parent(n).getElement();
        }

        if(isRightChild(n)) {
            while(isRightChild(n)){
                n = parent(n);
                if(isRoot(n)) return null;
            }
            return parent(n).getElement();
        }

        return null;
    }

    /**
     * Returns the value associated with the specified key (or else null).
     *
     * @param key search key
     * @return the value associated with the specified key (or else null)
     */
    @Override
    public V get(K key) {
        Position<Entry<K, V>> n = root();
        if(n==null){
            return null;
        }

        while(isInternal(n) && n.getElement().getKey().compareTo(key) != 0){
            K k = n.getElement().getKey();
            if(k.compareTo(key)==1){ // k > key, to left
                n = left(n);
            }

            else{ // k < key, to right
                n = right(n);
            }
        }

        if(isExternal(n)) return null;

        return n.getElement().getValue();
    }

    /**
     * Associates the given value with the given key, returning any overridden
     * value.
     * 
     * @param key   search key
     * @param value value of entry
     * @return old value associated with the key, if already exists, or null
     *         otherwise
     */
    @Override
    public V put(K key, V value) {

        V result = null;
        if(root()==null) {
            this.addRoot(new Entry<>(key, value));
            this.addLeft(root(), null);
            this.addRight(root(), null);
            size = size-2;
            return null;
        }

        Position<Entry<K, V>> n = root();
        while(isInternal(n)){
            Comparable<K> nk = n.getElement().getKey();
            if(nk.compareTo(key) == -1) { // right
                n = right(n);
            }

            else if(nk.compareTo(key) == 1) { // left
                n = left(n);
            }

            else if(nk.compareTo(key) == 0){
                result = ((Entry<K, V>) n.getElement()).getValue();
                break;
            }
        }

        this.replace(n, new Entry<>(key, value));
        this.addLeft(n, null);
        this.addRight(n, null);

        size = size - 1;

        return result;
    }

}
