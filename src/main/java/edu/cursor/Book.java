package edu.cursor;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Book implements Comparable<Book> {

    private Integer ISBN;
    private String Title;
    private String Author;
    private int PublYear;
    private int WritYear;
    private Integer quantity;
    private int cat;

    public static List<Book> bList = new ArrayList<>();

    public Book(Integer ISBN, String title, String author, int cat, int publYear, int writYear, Integer quantity) {
        this.ISBN = ISBN; Title = title; Author = author; PublYear = publYear; WritYear = writYear; this.quantity = quantity; this.cat = cat ;
    }

    public Book(String title, String author, int cat, int publYear, int writYear, Integer quantity) {
        Title = title; Author = author; PublYear = publYear; WritYear = writYear; this.quantity = quantity; this.cat = cat ;
    }

    public Integer getISBN() { return ISBN; }
    public void setISBN(Integer ISBN) { this.ISBN = ISBN; }
    public String getTitle() { return Title; }
    public void setTitle(String title) { Title = title; }
    public String getAuthor() { return Author; }
    public void setAuthor(String author) { Author = author; }
    public int getPublYear() { return PublYear; }
    public void setPublYear(int publYear) { PublYear = publYear; }
    public int getWritYear() { return WritYear; }
    public void setWritYear(int writYear) { WritYear = writYear; }
    public Integer getQuantity() { return quantity; }
    public void setQuantity(Integer quantity) { this.quantity = quantity; }
    public int getCat() { return cat; }
    public void setCat(int cat) { this.cat = cat; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Book)) return false;

        Book book = (Book) o;

        if (getPublYear() != book.getPublYear()) return false;
        if (getWritYear() != book.getWritYear()) return false;
        if (getCat() != book.getCat()) return false;
        if (!getISBN().equals(book.getISBN())) return false;
        if (!getAuthor().equals(book.getAuthor())) return false;
        if (!getTitle().equals(book.getTitle())) return false;
        return getQuantity().equals(book.getQuantity());
    }

    @Override
    public int hashCode() {
        int result = getISBN().hashCode();
        result = 31 * result + getAuthor().hashCode();
        result = 31 * result + getTitle().hashCode();
        result = 31 * result + getPublYear();
        result = 31 * result + getWritYear();
        result = 31 * result + getQuantity().hashCode();
        result = 31 * result + getCat();
        return result;
    }

    @Override
    public String toString() {
        return ISBN + "\t'" + Title + "' -- " + Author + " ("+ BookCat.getCat(cat) + ") " + PublYear + "/" + WritYear + " (" + quantity + ")\n" ;
    }


    // ----- COMPARES -----
    @Override
    public int compareTo(Book b) {
        return Comparators.TITLE.compare(this, b) ;
    }

    public static class Comparators{

        public static final Comparator<Book> TITLE = ( Book b1, Book b2 ) -> b1.getTitle().compareTo(b2.getTitle()) ;
        public static final Comparator<Book> AUTHOR = ( Book b1, Book b2 ) -> b1.getAuthor().compareTo(b2.getAuthor()) ;
        public static final Comparator<Book> WRITTEN = ( Book b1, Book b2 ) -> ( b1.getWritYear() - b2.getWritYear() ) ;
        public static final Comparator<Book> PUBLISHED = ( Book b1, Book b2 ) -> ( b1.getPublYear() - b2.getPublYear() ) ;

        public static final Comparator<Book> TITLE_ = ( Book b1, Book b2 ) -> TITLE.thenComparing(AUTHOR.thenComparing(WRITTEN.thenComparing(PUBLISHED))).compare(b1, b2) ;
        public static final Comparator<Book> AUTHOR_ = ( Book b1, Book b2 ) -> AUTHOR.thenComparing(TITLE.thenComparing(WRITTEN.thenComparing(PUBLISHED))).compare(b1, b2);
        public static final Comparator<Book> WRITTEN_ = ( Book b1, Book b2 ) -> WRITTEN.thenComparing(TITLE.thenComparing(AUTHOR.thenComparing(PUBLISHED))).compare(b1, b2) ;
        public static final Comparator<Book> PUBLISHED_ = ( Book b1, Book b2 ) -> PUBLISHED.thenComparing(TITLE.thenComparing(AUTHOR.thenComparing(WRITTEN))).compare(b1, b2) ;
    }



    public static List<Book> createBookList() {

        bList.add(new Book(1000000005, "Mothers, Tell Your Daughters", "Bonnie Jo Campbell", 1, 2015, 1980, 1));
        bList.add(new Book(1000000009, "Elon Musk: Tesla, SpaceX & Fantastic Future", "Ashlee Vance", 1, 2015, 2014, 1));
        bList.add(new Book(1000000015, "The Jemima Code", "Toni Tipton-Martin", 1, 2000, 1992, 1));
        bList.add(new Book(1000000016, "The Wright Brothers", "David McCullough", 1, 2016, 1897, 1));
        bList.add(new Book(1000000021, "Fortune Smiles", "Adam Johnson", 1, 2007, 1943, 1));
        bList.add(new Book(1000000031, "The Fifth Season", "N.K. Jemisin", 1, 2015, 2000, 1));
        bList.add(new Book(1000000032, "Nimona", "Noelle Stevenson", 1, 2011, 1993, 1));
        bList.add(new Book(1000000037, "Real Men Don't Sing", "Allison McCracken", 1, 2016, 2011, 1));

        bList.add(new Book(1000000002, "Accidental Saints", "Nadia Bolz-Weber", 2, 2015,1914, 1));
        bList.add(new Book(1000000007, "Undermajordomo Minor", "Patrick deWitt", 2, 2000,1943,  1));
        bList.add(new Book(1000000010, "Adventures Of Lovelace And Babbage", "Sydney Padua", 2, 2001, 1951,	 1));
        bList.add(new Book(1000000019, "The Bear Ate Your Sandwich", "Julia Sarcone-Roach", 2, 2001, 1898,  1));
        bList.add(new Book(1000000020, "Among The Wild Mulattos", "Tom Williams", 2, 2013, 1991,  1));
        bList.add(new Book(1000000033, "The Sellout", "Paul Beatty", 2, 2004, 1897, 1));
        bList.add(new Book(1000000036, "Fates And Furies", "Lauren Groff", 2, 1997, 1880, 1));

        bList.add(new Book(1000000003, "Slade House", "David Mitchell", 3, 2010, 1950, 1));
        bList.add(new Book(1000000004, "Spy Games", "Adam Brookes", 3, 2000, 1949, 1));
        bList.add(new Book(1000000012, "Strangers Drowning", "Larissa MacFarquhar", 3, 2003, 1939, 1));
        bList.add(new Book(1000000013, "Honeydew", "Edith Pearlman", 3, 2010, 1977, 1));
        bList.add(new Book(1000000008, "All The Old Knives", "Olen Steinhauer", 3, 2016, 1913, 1));
        bList.add(new Book(1000000014, "The Unauthorised Life", "Jonathan Bate", 3, 2000, 1965, 1));
        bList.add(new Book(1000000023, "Habitat", "Lauren Liess", 3, 2010, 1997, 1));
        bList.add(new Book(1000000026, "Death And Mr. Pickwick", "Stephen Jarvis", 3, 1999, 1954, 1));

        bList.add(new Book(1000000011, "Small Plates To Share", "Ghillie Başan", 4, 2014, 1898, 1));
        bList.add(new Book(1000000001, "Peter Pan", "Author The First", 4, 2003, 1918, 1));
        bList.add(new Book(1000000018, "The Turnip Princess", "Franz-Xaver von Schönwerth", 4, 2014, 1886, 1));
        bList.add(new Book(1000000022, "A History of Japan", "Shigeru Mizuki", 4, 2007, 2005, 1));
        bList.add(new Book(1000000024, "A Spool Of Blue Thread", "Anne Tyler", 4, 1999, 1931, 1));
        bList.add(new Book(1000000034, "Hotels of North America", "Rick Moody", 4, 2010, 1995, 1));
        bList.add(new Book(1000000035, "Kitchen Hacks", "America's Test Kitchen", 4, 1997, 1905, 1));

        bList.add(new Book(1000000006, "Ancillary Mercy", "Ann Leckie", 5, 2001, 1919, 1));
        bList.add(new Book(1000000017, "Secrets Of State", "Matthew Palmer", 5, 2004, 1880, 1));
        bList.add(new Book(1000000025, "March", "Andrew Aydin", 5, 2014, 1995, 1));
        bList.add(new Book(1000000027, "Home", "Carson Ellis", 5, 2012, 1971, 1));
        bList.add(new Book(1000000028, "Listen Slowly", "Thanhhà Lại", 5, 2015, 1881, 1));
        bList.add(new Book(1000000029, "Murder On Steep Street", "Heda Margolius Kovály", 5, 1999, 1962, 1));
        bList.add(new Book(1000000030, "The Shepherd's Crown", "Terry Pratchett", 5, 2011, 1934, 1));

        return bList;
    }
}