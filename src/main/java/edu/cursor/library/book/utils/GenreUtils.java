package edu.cursor.library.book.utils;


import edu.cursor.library.book.enums.Genre;

import java.util.*;
import java.util.stream.Collectors;

public class GenreUtils {
    private static final Scanner scan = new Scanner(System.in);

    public static Genre chooseGenre() {
        System.out.println("Pls enter genre for book.");
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
            if (scan.nextLine().toLowerCase().charAt(0) == 'y') {
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
            System.out.println("Genre: " + myGenre + " doesn't exist!");
            return chooseGenre();
        }
    }

    public static EnumSet<Genre> insertGenre(String st) {
        StringBuilder genre = new StringBuilder();
        genre.append(st);
        System.out.println("If you want add another genre press 'y'");
        while (scan.nextLine().toUpperCase().charAt(0) == 'Y') {
            genre.append(";");
            System.out.println("Pls enter genre for book.");
            genre.append(scan.nextLine());
            System.out.println("If you want add another genre press 'y'");
        }
        return IOUtils.converToEnumSet(genre.toString());
    }


}