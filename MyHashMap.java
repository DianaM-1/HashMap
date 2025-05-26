package org.example;
import java.util.Objects;

class MyHashMap<K, V> {
    private static final int DEFAULT_CAPACITY = 16;
    private static final float LOAD_FACTOR = 0.75f;

    private Node<K, V>[] table;
    private int size;

    @SuppressWarnings("unchecked")
    public MyHashMap() {
        table = (Node<K, V>[]) new Node[DEFAULT_CAPACITY];
        size = 0;
    }

    private static class Node<K, V> {
        final K key;
        V value;
        Node<K, V> next;

        Node(K key, V value, Node<K, V> next) {
            this.key = key;
            this.value = value;
            this.next = next;
        }
    }

    public void put(K key, V value) {
        if (key == null) {
            throw new NullPointerException("Key cannot be null");
        }
        if (size >= table.length * LOAD_FACTOR) {
            resize();
        }

        int index = getIndex(key);
        Node<K, V> node = table[index];

        while (node != null) {
            if (node.key.equals(key)) {
                node.value = value; // Обновляем значение
                return;
            }
            node = node.next;
        }

        table[index] = new Node<>(key, value, table[index]);
        size++;
    }

    public V get(K key) {
        if (key == null) {
            throw new NullPointerException("Key cannot be null");
        }

        int index = getIndex(key);
        Node<K, V> node = table[index];

        while (node != null) {
            if (node.key.equals(key)) {
                return node.value;
            }
            node = node.next;
        }
        return null;
    }

    public boolean containsKey(K key) {
        return get(key) != null;
    }

    public boolean containsValue(V value) {
        for (Node<K, V> node : table) {
            Node<K, V> current = node;
            while (current != null) {
                if (Objects.equals(current.value, value)) {
                    return true;
                }
                current = current.next;
            }
        }
        return false;
    }

    public V remove(K key) {
        if (key == null) {
            throw new NullPointerException("Key cannot be null");
        }

        int index = getIndex(key);
        Node<K, V> node = table[index];
        Node<K, V> prev = null;

        while (node != null) {
            if (node.key.equals(key)) {
                if (prev == null) {
                    table[index] = node.next;
                } else {
                    prev.next = node.next;
                }
                size--;
                return node.value;
            }
            prev = node;
            node = node.next;
        }
        return null;
    }

    private int getIndex(K key) {
        return (key.hashCode() & 0x7fffffff) % table.length;
    }

    @SuppressWarnings("unchecked")
    private void resize() {
        Node<K, V>[] oldTable = table;
        table = (Node<K, V>[]) new Node[oldTable.length * 2];
        size = 0;

        for (Node<K, V> node : oldTable) {
            while (node != null) {
                put(node.key, node.value);
                node = node.next;
            }
        }
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (Node<K, V> node : table) {
            while (node != null) {
                sb.append(" ").append(node.key).append(" - ").append(node.value).append("\n");
                node = node.next;
            }
        }
        return sb.toString();
    }
}