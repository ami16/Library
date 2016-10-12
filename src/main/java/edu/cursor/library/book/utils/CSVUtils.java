package edu.cursor.library.book.utils;


import edu.cursor.library.book.entity.Book2;
import edu.cursor.library.book.enums.Genre;
import org.joda.time.LocalDate;

import java.io.*;
import java.util.List;

public class CSVUtils {

    private static final char DEFAULT_SEPARATOR = ',';

    private static File file;


    public static void writeLine(Writer w, List<Book2> bookList, String path) throws IOException {
        w = new BufferedWriter(new FileWriter(path));
        w.write("ISBN,Author,Title,publYear,writYear,Genre,\n");
        for (Book2 b : bookList) {
            w.append(b.getISBN().toString());
            w.append(DEFAULT_SEPARATOR);
            w.append(b.getAuthor());
            w.append(DEFAULT_SEPARATOR);
            w.append(b.getTitle());
            w.append(DEFAULT_SEPARATOR);
            w.append(b.getPublYear().toString());
            w.append(DEFAULT_SEPARATOR);
            w.append(b.getWritYear().toString());
            w.append(DEFAULT_SEPARATOR);
            w.append(b.getGenre().toString());
            w.append(DEFAULT_SEPARATOR);
            w.append("\n");
        }
        w.flush();
        w.close();

    }


    public static Book2[] readFile(BufferedReader r, String path) throws IOException {
        String line;
        int counter = 0;
        Book2[] bookArray;
        try {
            file = new File(path);
            r = new BufferedReader(new FileReader(file));
            while ((line = r.readLine()) != null) {
                counter++;
            }
        } finally {
            bookArray = new Book2[counter - 1];
            r.close();
        }
        try {
            file = new File(path);
            r = new BufferedReader(new FileReader(file));
            line = r.readLine();
            int index = 0;
            while ((line = r.readLine()) != null) {
                String[] fields = line.split(",");
                Integer ISBN = Integer.parseInt(fields[0]);
                String author = fields[1];
                String title = fields[2];
                LocalDate publYear = LocalDate.parse(fields[3]);
                LocalDate writYear = LocalDate.parse(fields[4]);
                String StrGenre = fields[5].toUpperCase();
                Genre genre = null;
                Book2 book = new Book2(ISBN, author, title, publYear, writYear, GenreUtils.chooseGenre(StrGenre));
                bookArray[index] = book;
                index++;
            }
        } finally {
            r.close();
        }

        return bookArray;

    }
}
