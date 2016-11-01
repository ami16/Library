package edu.cursor.library.book.utils;


import edu.cursor.library.book.entity.TblBook;
import edu.cursor.library.book.enums.Genre;
import org.joda.time.LocalDate;

import java.io.*;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;

import static edu.cursor.library.infrastructure.Constans.DEFAULT_SEPARATOR;
import static edu.cursor.library.infrastructure.Constans.MULTI_PICKLIST_SEPARATOR;


public class IOUtils {




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
                w.append(converToString(b.getGenre()));
                w.append(DEFAULT_SEPARATOR);
                w.append("\n");
            }
            w.flush();
        } catch (IOException e) {
            // Logger code here
        }

    }

    private static String converToString(EnumSet genre) {
        StringBuilder sb = new StringBuilder();
       for (int i = 0; i < genre.toArray().length; i++){
               sb.append(genre.toArray()[i].toString());
           if (i < genre.toArray().length - 1) {
               sb.append(MULTI_PICKLIST_SEPARATOR);
           }
       }
       return sb.toString();
    }

    public static EnumSet<Genre> converToEnumSet (String myGenre) {
        String[] fields = myGenre.split(MULTI_PICKLIST_SEPARATOR);
        List <Genre> gen = new ArrayList<>();
        for (String st : fields) {
            gen.add(GenreUtils.chooseGenre(st));

        }
        return EnumSet.copyOf(gen);
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
                bookArray[index] = new TblBook(Integer.parseInt(fields[0]), fields[1], fields[2],
                        LocalDate.parse(fields[3]), LocalDate.parse(fields[4]),
                        converToEnumSet(fields[5].toUpperCase()));
                index++;
            }
        } catch (IOException | IllegalArgumentException e) {
            // Logger code here
        }

        return bookArray;

    }
}
