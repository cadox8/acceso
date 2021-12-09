package es.ivan.acceso.utils;

import java.util.Random;
import java.util.TreeSet;

public class RandomNumbers {

    private final Random r = new Random();

    public TreeSet<Integer> generateRandomNumbers(int amount, int max) {
        final TreeSet<Integer> numbers = new TreeSet<>();
        for (int i = 0; i < amount; i++) numbers.add(r.nextInt(max) + 1);
        return numbers;
    }
}
