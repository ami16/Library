package edu.cursor.library.user;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import edu.cursor.library.user.entity.TblUser;

public interface UserRepository extends CrudRepository<TblUser, Long> {
	List<TblUser> findByName(TblUser user);

}
