package edu.cursor.library.model;

public enum BookGenre {

    FICTION(1), FANTASY(2), HISTORY(3), MYSTERY(4), HORROR(5), ENCYCLOPEDIA(6), COMEDY(7), UNKNOWN(8);

    private int id;

    BookGenre( int id ) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public static BookGenre getGenre(int id){
        BookGenre genre = null ;
        for( BookGenre g : BookGenre.values() ){
            if(id == g.getId()){
                genre = g ;
                break;
            }
        }
        return genre ;
    }
}
