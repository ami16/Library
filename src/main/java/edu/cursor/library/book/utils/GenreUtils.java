package edu.cursor.library.book.utils;


import edu.cursor.library.book.enums.Genre;

import java.util.Arrays;
import java.util.EnumSet;
import java.util.Scanner;

import static edu.cursor.library.infrastructure.Constans.POSITIVE_ANSWER;

public class GenreUtils {
    private static final Scanner scan = new Scanner(System.in);

    private static Genre chooseGenre() {
        System.out.println("Pls enter genre for book.");
        String tryGenre = scan.nextLine().toUpperCase();
        if (Arrays.stream(Genre.values())
                .map(Genre::name)
                .anyMatch(tryGenre::equals)) {
            return Genre.valueOf(tryGenre);
        } else {
            System.out.println("Wrong genre!!!");
            System.out.println("Do you want write correct genre? Press 'y'");
            if (scan.nextLine().toLowerCase().charAt(0) == POSITIVE_ANSWER) {
                return chooseGenre();
            } else return Genre.UNKNOWN;
        }
    }


    public static Genre chooseGenre(String myGenre) {
        if (Arrays.stream(Genre.values())
                .map(Genre::name)
                .anyMatch(myGenre::equals)) {
            return Genre.valueOf(myGenre);
        } else {
            System.out.println("Genre: " + myGenre + " doesn't exist!");
            return chooseGenre();
        }
    }

    public static EnumSet<Genre> insertGenre(String st) {
        StringBuilder genre = new StringBuilder();
        genre.append(st.toUpperCase());
        System.out.println("If you want add another genre press 'y'");
        while (scan.nextLine().toUpperCase().charAt(0) == POSITIVE_ANSWER) {
            genre.append(";");
            System.out.println("Pls enter genre for book.");
            genre.append(scan.nextLine().toUpperCase());
            System.out.println("If you want add another genre press 'y'");
        }
        return IOUtils.converToEnumSet(genre.toString());
    }


}
