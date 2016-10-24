package edu.cursor.library.book.utils;


import edu.cursor.library.book.enums.Genre;

import java.util.Arrays;
import java.util.Scanner;
import java.util.stream.Collectors;

public class GenreUtils {
    private static Scanner scan = new Scanner(System.in);
    public static Genre chooseGenre() {
        System.out.println("Pls chose genre for book.");
        String tryGenre = scan.nextLine().toUpperCase();
        Boolean checkGenre = Arrays.stream(Genre.values())
                .map(Genre::name)
                .collect(Collectors.toList())
                .stream()
                .anyMatch(tryGenre::equals);
        if (checkGenre) {
            return Genre.valueOf(tryGenre);
        } else {
            System.out.println("Wrong genre!!!");
            System.out.println("Do you want write correct genre? Press 'y'");
            char recursion = scan.nextLine().toLowerCase().charAt(0);
            if (recursion == 'y') {
                return chooseGenre();
            } else return Genre.UNKNOWN;
        }
    }
    public static Genre chooseGenre(String myGenre) {
        Boolean checkGenre = Arrays.stream(Genre.values())
                .map(Genre::name)
                .collect(Collectors.toList())
                .stream()
                .anyMatch(myGenre::equals);
        if (checkGenre) {
            return Genre.valueOf(myGenre);
        } else {
            System.out.println("Genre:" + myGenre + " doesn't exist!");
            return chooseGenre();
        }
    }

}
