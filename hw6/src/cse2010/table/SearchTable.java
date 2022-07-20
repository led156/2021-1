package cse2010.table;

import cse2010.orderedmap.Entry;
import cse2010.orderedmap.OrderedMap;

import java.util.Arrays;
import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

public class SearchTable<K extends Comparable<K>,V> implements OrderedMap<K, V> {
    private final Entry<K,V>[] entries;
    private int size;

    /**
     * Create a search table with capacity of {@code capacity}.
     * @param capacity maximum number of entries that can be stored in the table
     */
    @SuppressWarnings("unchecked")
    public SearchTable(int capacity) {
        entries = (Entry<K, V>[]) new Entry[capacity];
        size = 0;
    }

    /**
     * Check whether the table is empty or not.
     * @return true if empty, false otherwise
     */
    public boolean isEmpty() {
        if(size()==0) { return true; }
        return false;
    }

    /**
     * Check whether the table is full or not.
     * @return true if full, false otherwise
     */
    public boolean isFull() { 
        if(entries.length==size()) { return true; }
        return false;
    }

    /**
     * Returns the entry with smallest key value (or null, if the map is empty).
     * @return the entry with smallest key value (or null, if the map is empty)
     */
    @Override public Entry<K, V> firstEntry() {
        if(!isEmpty()){
            return entries[0];
        }
        return null;
    }

    /**
     * Returns the entry with largest key value(or null if the map is empty)
     * @return the entry with largest key value (or null, if the map is empty)
     */
    @Override public Entry<K, V> lastEntry() {
        if(!isEmpty()){
            return entries[size()-1];
        }
        return null;
    }

    /**
     * Returns the entry with the smallest key value greater than or equal to "key" (or null if no such entry exists).
     * @param key   search key
     * @return the entry with the smallest key value less than or equal to "key"
     * (or null, if no such entry exists)
     V
    V
    V
     * Returns the entry with the largest key value less than or equal to "key" (or null if no such entry exists).
     * @param key   search key
     * @return the entry with the largest key value less than or equal to "key"
     * (or null, if no such entry exists)
     */
    @Override public Entry<K, V> floorEntry(final K key) {
        int low = 0;
        int high = size()-1;
        int mid = 0;

        while(high >= low){
            mid = (high + low)/2;
            if(key.compareTo(entries[mid].getKey()) == 0) {
                return entries[mid];
            }
            else if(key.compareTo(entries[mid].getKey()) == 1){ //key > .getkey
                low = mid+1;
            }
            else if(key.compareTo(entries[mid].getKey()) == -1){
                high = mid-1;
            }
        }
        return key.compareTo(entries[0].getKey()) == -1 ? null : entries[high];
    }

    /**
     * Returns the entry with the largest key value less than or equal to "key" (or null if no such entry exists).
     * @param key   search key
     * @return the entry with the largest key value less than or equal to k (or null, if no such entry exists)
     V
    V
    V
     * Returns the entry with the least key value greater than or equal to "key" (or null if no such entry exists).
     * @param key   search key
     * @return the entry with the least key value greater than or equal to k (or null, if no such entry exists)
     */
    @Override public Entry<K, V> ceilingEntry(final K key) {
        int low = 0;
        int high = size()-1;
        int mid = 0;

        while(high >= low){
            mid = (high + low)/2;
            if(key.compareTo(entries[mid].getKey()) == 0) {
                return entries[mid];
            }
            else if(key.compareTo(entries[mid].getKey()) == 1){ //key > .getkey
                low = mid+1;
            }
            else if(key.compareTo(entries[mid].getKey()) == -1){
                high = mid-1;
            }
        }
        return key.compareTo(entries[size()-1].getKey()) == 1 ? null : entries[low];

    }

    /**
     * Returns the entry with the greatest key value strictly less than "key" (or null if no such entry exists).
     * @param key   search key
     * @return the entry with the greatest key value strictly less than k (or null, if no such entry exists).
     V
    V
    V
     * Returns the entry with the largest key value strictly less than "key" (or null if no such entry exists).
     * @param key   search key
     * @return the entry with the largest key value strictly less than k (or null, if no such entry exists).
     */
    @Override public Entry<K, V> lowerEntry(final K key) {
        int low = 0;
        int high = size()-1;
        int mid = 0;

        while(high >= low){
            mid = (high + low)/2;

            if(key.compareTo(entries[mid].getKey()) == 1){ //key > .getkey
                low = mid+1;
            }
            else if(key.compareTo(entries[mid].getKey()) >= -1){
                high = mid-1;
            }
        }
        return high < 0 || entries[high]==null ? null : entries[high];
    }

    /**
     * Returns the entry with the least key value strictly greater than "key" (or null if no such entry exists).
     * @param key search key
     * @return the entry with the least key value strictly greater than "key" (or null if no such entry exists)
     *
     V
     V
     * Returns the entry with the least key value strictly greater than "key" (or null if no such entry exists).
     * @param key search key
     * @return the entry with the least key value strictly greater than "key" (or null if no such entry exists)
     */
    @Override public Entry<K, V> higherEntry(final K key) {
        int low = 0;
        int high = size()-1;
        int mid = 0;

        while(high >= low){
            mid = (high + low)/2;

            if(key.compareTo(entries[mid].getKey()) >= 0){ //key > .getkey
                low = mid+1;
            }
            else if(key.compareTo(entries[mid].getKey()) == -1){
                high = mid-1;
            }
        }
        return low > size() || entries[low]==null ? null : entries[low];
    }

    /**
     * Returns the value associated with the specified key (or else null).
     * @param key search key
     * @return the value associated with the specified key (or else null)
     */
    @Override public V get(final K key) {
        if (isEmpty()) throw new IllegalStateException("Empty table");
        int low = 0;
        int high = size()-1;
        int mid = 0;

        while(high >= low && high <= size()){
            mid = (high + low)/2;
            if(key.compareTo(entries[mid].getKey()) == 0) {
                return entries[mid].getValue();
            }
            else if(key.compareTo(entries[mid].getKey()) == 1){ //key > .getkey
                low = mid+1;
            }
            else if(key.compareTo(entries[mid].getKey()) == -1){
                high = mid-1;
            }
        }
        return null;
    }

    /**
     * Associates the given value with the given key, returning any overridden value.
     * @param key search key
     * @param value value of entry
     * @return old value associated with the key, if already exists, or null otherwise
     * @throws IllegalStateException if full
     */
    @Override public V put(final K key, final V value) {
        if (isFull()) throw new IllegalStateException("table full");

        //entries[size()] = new Entry<>(key, value);

        int low = 0;
        int high = size();
        int mid = 0;


        while(high > low){
            mid = (high + low)/2;
            if(key.compareTo(entries[mid].getKey()) == 1){ //key > .getkey
                low = mid+1;
            }
            else if(key.compareTo(entries[mid].getKey()) == -1){
                high = mid-1;
            }
        }

        if(high <= low){
            int i;
            for(i = size(); i != 0 && (key.compareTo(entries[i-1].getKey()) <= 0); i--){
                entries[i] = entries[i-1];
            }
            size++;
            entries[i] = new Entry(key, value);
            return value;
        }


        return null;
    }

    /**
     * Removes the entry having key "key" (if any) and returns its associated value.
     * @param key search key
     * @return value associated with the key
     */
    @Override public V remove(final K key) {
        if (isEmpty()) throw new IllegalArgumentException("table empty");

        int low = 0;
        int high = size();
        int mid = 0;

        while(high >= low){
            mid = (high + low)/2;
            if(key.compareTo(entries[mid].getKey()) == 0) {
                V value = entries[mid].getValue();
                for(int i = mid; i<size(); i++){
                    entries[i] = entries[i+1];
                }
                size--;
                return value;
            }
            else if(key.compareTo(entries[mid].getKey()) == 1){ //key > .getkey
                low = mid+1;
            }
            else if(key.compareTo(entries[mid].getKey()) == -1){
                high = mid-1;
            }
        }

        return null;
    }

    /**
     * Returns the number of entries in the map.
     * @return the number of entries in the map
     */
    @Override public int size() {
        return size;
    }

    /**
     * Returns the set of keys in the map sorted in ascending order.
     * @return the set of keys in the map sorted in ascending order
     */
    @Override public Set<K> keys() {
        return Arrays.stream(entries).limit(size).map(Entry::getKey).collect(Collectors.toSet());
    }

    /**
     * Returns the collection of values in the map.
     * @return the collection of values in the map
     */
    @Override public Collection<V> values() {
        return Arrays.stream(entries).limit(size).map(Entry::getValue).collect(Collectors.toList());
    }

    /**
     * Returns the collection of entries in the map.
     * @return the collection of entries in the map
     */
    @Override public Collection<Entry<K, V>> entries() {
        return Arrays.stream(entries).limit(size).collect(Collectors.toList());
    }

}
