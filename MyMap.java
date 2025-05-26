package org.example;

public class MyMap {
    public static void main(String[] args) {
        MyHashMap<String, Double> hashMap = new MyHashMap<>();
        hashMap.put("1+1", 8.4);
        hashMap.put("Субстанция", 6.3);
        hashMap.put("Волк с Уолл-стрит", 7.7);
        hashMap.put("Зеленая миля", 8.1);
        hashMap.put("Веном", 6.9);

        System.out.println("Список фильмов" + '\n' + hashMap);
        System.out.println("Количество фильмов в списке: " + hashMap.size());

        hashMap.remove("Субстанция");
        System.out.println("\nУбираем фильм с минимальным рейтингом:" + '\n' + hashMap);
        System.out.println("В списке есть фильм 'Субстанция'? - " + hashMap.containsKey("Субстанция"));
        System.out.println("В списке есть фильм 'Зеленая миля'? - " + hashMap.containsKey("Зеленая миля"));

        System.out.println("\nДополняем список фильмов");

        hashMap.put("Я иду искать", 6.9);
        hashMap.put("Назад в будущее", 7.7);
        hashMap.put("Ущелье", 7.0);

        System.out.println("Новый список фильмов:" + '\n' + hashMap);
        System.out.println("Количество фильмов в списке: " + hashMap.size());

        System.out.println("\nРейтинг фильма 'Ущелье' - " + hashMap.get("Ущелье"));
        System.out.println("Рейтинг фильма 'Волк с Уолл-стрит' - " + hashMap.get("Волк с Уолл-стрит"));
    }
}