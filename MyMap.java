package org.example;
import java.util.Objects;

public class MyMap {
    public static void main(String[] args) {
        MyHashMap<Movie, Double> hashMap = new MyHashMap<>();
        Movie m1 = new Movie("1+1", 2011);
        Movie m2 = new Movie("Субстанция", 2024);
        Movie m3 = new Movie("Волк с Уолл-стрит", 2013);
        Movie m4 = new Movie("Зеленая миля", 1999);
        Movie m5 = new Movie("Веном", 2018);

        hashMap.put(m1, 8.4);
        hashMap.put(m2, 6.3);
        hashMap.put(m3, 7.7);
        hashMap.put(m4, 8.1);
        hashMap.put(m5, 6.9);

        System.out.println("Список фильмов" + '\n' + hashMap);
        System.out.println("Количество фильмов в списке: " + hashMap.size());

        hashMap.remove(m2);
        System.out.println("\nУбираем фильм с минимальным рейтингом:" + '\n' + hashMap);
        System.out.println("В списке есть фильм 'Субстанция'? - " + hashMap.containsKey(m2));
        System.out.println("В списке есть фильм 'Зеленая миля'? - " + hashMap.containsKey(m4));

        System.out.println("\nДополняем список фильмов");

        Movie m6 = new Movie("Я иду искать", 2019);
        Movie m7 = new Movie("Назад в будущее", 1985);
        Movie m8 = new Movie("Ущелье", 2024);

        hashMap.put(m6, 6.9);
        hashMap.put(m7, 7.7);
        hashMap.put(m8, 7.0);

        System.out.println("Новый список фильмов:" + '\n' + hashMap);
        System.out.println("Количество фильмов в списке: " + hashMap.size());

        System.out.println("\nРейтинг фильма 'Ущелье' - " + hashMap.get(m8));
        System.out.println("Рейтинг фильма 'Волк с Уолл-стрит' - " + hashMap.get(m3));
    }
}

class Movie {
    private final String name;
    private final int year;

    public Movie(String name, int year) {
        this.name = Objects.requireNonNull(name, "Name cannot be null");
        this.year = year;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return name + ", " + year + "г.";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Movie movie = (Movie) o;
        return year == movie.year && Objects.equals(name, movie.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, year);
    }
}

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
        return Math.abs(key.hashCode()) % table.length;
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