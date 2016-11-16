package edu.cursor.library.model.DAO;


import edu.cursor.library.model.TblUserBooksRegistry;
import java.util.List;


public interface UserBooksRegistryDAO {

   List<TblUserBooksRegistry> findAll();

}