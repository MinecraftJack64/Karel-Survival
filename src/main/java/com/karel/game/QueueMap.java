package com.karel.game;
import java.util.ArrayList;

/**
 * Write a description of class ArrayMap here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class QueueMap<T extends Comparable, E>  
{
    ArrayList<T> keys;
    ArrayList<E> values;
    public QueueMap(){
        keys = new ArrayList<T>();
        values = new ArrayList<E>();
    }
    public void add(T t, E e){
        // TODO: compare binary
        keys.add(t);
        values.add(e);
    }
    public Entry peek(){
        if(keys.isEmpty() || values.isEmpty()) return null;
        return new Entry(keys.get(0), values.get(0));
    }
    public Entry remove(){
        Entry res = peek();
        if(res == null) return null;
        keys.remove(0);
        values.remove(0);
        return res;
    }
    public int size(){
        return keys.size();
    }
    public class Entry{
        public T key;
        public E value;
        public Entry(T key, E value){
            this.key = key;
            this.value = value;
        }
        public T getKey(){
            return key;
        }
        public E getValue(){
            return value;
        }
    }
}
