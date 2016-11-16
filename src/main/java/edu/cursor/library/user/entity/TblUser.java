package edu.cursor.library.user.entity;

import lombok.*;
import org.joda.time.LocalDate;
import edu.cursor.library.user.enums.Role;


@Data
public class TblUser {
	private int id;
	private String firstName;
	private String lastName;
	private String eMail;
	private int mobileNum;
	private String address;
	private LocalDate dateOfRegistration;
	private Role role;
}
