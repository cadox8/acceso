package es.ivan.acceso.old.utils;

import java.util.Random;
import java.util.TreeSet;

public class RandomNumbers {

    // No pillo el posible StackOverflow porque el rango de nº aleatorios es de 0-100 y es dificil que coincidan
    public TreeSet<Integer> generateRandomNumbers() {
        final TreeSet<Integer> numbers = new TreeSet<>();
        new Random().ints(10, 0, 101 /* 101 porque el valor es exclusivo */).forEach(numbers::add);
        return numbers.size() != 10 ? this.generateRandomNumbers() : numbers;
    }
}
