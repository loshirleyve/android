package com.yun9.jupiter.model;

import java.io.Serializable;
import java.util.Map;

/**
 * Created by huangbinglong on 15/6/8.
 */
public class SerialableEntry<K,V> implements Map.Entry<K,V>,Serializable{

    private K key;
    private V value;

    public SerialableEntry(K key, V value) {
        this.key = key;
        this.value = value;
    }

    public void setKey(K key) {
        this.key = key;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SerialableEntry<?, ?> that = (SerialableEntry<?, ?>) o;

        if (!key.equals(that.key)) return false;
        return value.equals(that.value);

    }

    @Override
    public K getKey() {
        return key;
    }

    @Override
    public V getValue() {
        return value;
    }

    @Override
    public int hashCode() {
        int result = key.hashCode();
        result = 31 * result + value.hashCode();
        return result;
    }

    @Override
    public V setValue(V object) {
        return this.value = object;
    }
}
