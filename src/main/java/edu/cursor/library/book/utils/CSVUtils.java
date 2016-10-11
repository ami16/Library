package edu.cursor.library.book.utils;


import edu.cursor.Genre;
import edu.cursor.library.book.entity.TblBook;
import org.joda.time.LocalDate;

import java.io.*;
import java.util.List;

public class CSVUtils {

    private static final char DEFAULT_SEPARATOR = ',';

    private static File file;


    public static void writeLine(Writer w, List<TblBook> bookList, String patch) throws IOException {
        w = new BufferedWriter(new FileWriter(patch));
        w.write("ISBN,Author,Title,publYear,writYear,Genre,\n");
        for (TblBook b : bookList) {
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


    public static TblBook[] readFile(BufferedReader r, String patch) throws IOException {
        String line;
        int counter = 0;
        TblBook[] bookArray;
        try {
            file = new File(patch);
            r = new BufferedReader(new FileReader(file));
            while ((line = r.readLine()) != null) {
                counter++;
            }
        } finally {
            bookArray = new TblBook[counter - 1];
            r.close();
        }
        try {
            file = new File(patch);
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
                TblBook book = new TblBook(ISBN, author, title, publYear, writYear, GenreUtils.chooseGenre(StrGenre));
                bookArray[index] = book;
                index++;
            }
        } finally {
            r.close();
        }

        return bookArray;

    }
}
