package com.epam.rd.autocode.threads.maxbystreams;

import java.util.Arrays;

public class MaxOfMatrix {

    private static void pause(int pause) {
        try {
            Thread.sleep(pause);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    public static String oneThreadSearch(int[][] matrix, int pause) {
        long before = System.currentTimeMillis();
        return Arrays.stream(matrix)
                .flatMapToInt(Arrays::stream)
                .peek(i -> pause(pause))
                .max()
                .getAsInt() + " " + (System.currentTimeMillis() - before);
    }

    public static String multipleThreadSearch(int[][] matrix, int pause) {
        long before = System.currentTimeMillis();
        return Arrays.stream(matrix)
                .parallel()
                .mapToInt(row -> Arrays.stream(row)
                        .parallel()
//                        .peek(i -> pause(pause))
//                        .max()
//                        .getAsInt())
                .reduce(Integer.MIN_VALUE, (a, b) -> {
                    pause(pause);
                    return Math.max(a, b);
                }))
                .max()
                .getAsInt() + " " + (System.currentTimeMillis() - before);
    }
}
