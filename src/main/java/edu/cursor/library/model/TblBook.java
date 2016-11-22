package edu.cursor.library.model;



import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.*;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.joda.time.LocalDate;

import javax.persistence.*;
import java.util.EnumSet;

@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "books")
public class TblBook implements Comparable{

    @Getter
    @Setter
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    //private Integer id;
    @Column(name = "isbn", nullable = false)
    private int ISBN;
    @Getter
    @Setter
    @Column(name = "author")
    private String author;
    @Getter
    @Setter
    @Column(name = "title")
    private String title;
    @Getter
    @Setter
    @Column(name = "publ_year")
    private LocalDate publYear;
    @Getter
    @Setter
    @Column(name = "writ_year")
    private LocalDate writYear;
    @Getter
    @Setter
    @Column(name = "genre")
    @Enumerated(EnumType.STRING)
    private EnumSet<BookGenre> genre;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        TblBook tblBook = (TblBook) o;

        return new EqualsBuilder()
                .append(getISBN(), tblBook.getISBN())
                .append(getAuthor(), tblBook.getAuthor())
                .append(getTitle(), tblBook.getTitle())
                .append(getPublYear(), tblBook.getPublYear())
                .append(getWritYear(), tblBook.getWritYear())
                .append(getGenre(), tblBook.getGenre())
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(getISBN())
                .append(getAuthor())
                .append(getTitle())
                .append(getPublYear())
                .append(getWritYear())
                .append(getGenre())
                .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,
                ToStringStyle.SIMPLE_STYLE)
                .append("ISBN", ISBN)
                .append("Author", author)
                .append("Title", title)
                .append("Published", publYear)
                .append("Written", writYear)
                .append("Genre", genre)
                .toString();
    }


    @Override
    public int compareTo(Object o) {
        return 0;
    }
}
