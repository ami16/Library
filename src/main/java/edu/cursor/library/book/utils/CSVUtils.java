package edu.cursor.library.book.utils;


import edu.cursor.library.book.entity.TblBook;
import edu.cursor.library.book.enums.Genre;
import org.joda.time.LocalDate;

import java.io.*;
import java.util.List;

public class CSVUtils {

    private static final char DEFAULT_SEPARATOR = ',';


    public static void writeLine(List<TblBook> bookList, String path) {
        try (Writer w = new BufferedWriter(new FileWriter(path))) {
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
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static TblBook[] readFile(String path) {
        String line;
        int counter = 0;
        TblBook[] bookArray;
        File file = new File(path);
        try (BufferedReader r = new BufferedReader(new FileReader(file))) {
            while ((line = r.readLine()) != null) {
                counter++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            bookArray = new TblBook[counter - 1];
        }
        try (BufferedReader r = new BufferedReader(new FileReader(file))) {
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
        } catch (IOException e) {
            e.printStackTrace();
        }

        return bookArray;

    }
}
