package edu.cursor.library.model;

import edu.cursor.library.model.UserRole;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.joda.time.LocalDate;

public class TblUser {
	private int id;
	private String firstName;
	private String lastName;
	private String eMail;
	private int mobileNum;
	private String address;
	private LocalDate dateOfRegistration;
	private UserRole role;

	public TblUser() {

	}

	public TblUser(int id, String firstName, String lastName, String eMail, int mobileNum, String address,
			LocalDate dateOfRegistration, UserRole role) {
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.eMail = eMail;
		this.mobileNum = mobileNum;
		this.address = address;
		this.dateOfRegistration = dateOfRegistration;
		this.role = role;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String geteMail() {
		return eMail;
	}

	public void seteMail(String eMail) {
		this.eMail = eMail;
	}

	public Integer getMobileNum() {
		return mobileNum;
	}

	public void setMobileNum(Integer mobileNum) {
		this.mobileNum = mobileNum;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public LocalDate getDateOfRegistration() {
		return dateOfRegistration;
	}

	public void setDateOfRegistration(LocalDate dateOfRegistration) {
		this.dateOfRegistration = dateOfRegistration;
	}

	public UserRole getRole() {
		return role;
	}

	public void setRole(UserRole role) {
		this.role = role;
	}

	@Override    
	public boolean equals(Object obj) {
	  if (this == obj)
	         return true;
	     if (obj == null)
	         return false;
	     if (getClass() != obj.getClass())
	         return false;
	     TblUser other = (TblUser) obj;
	     return new EqualsBuilder().append(this.getId(), other.getId())
	              .append(this.getFirstName(), other.getFirstName())
	              .append(this, other.getLastName())
	              .append(this, other.geteMail())
	              .append(this, other.getMobileNum())
	              .append(this, other.getAddress())
	              .append(this, other.getDateOfRegistration())
	              .append(this, other.getRole())
	              .isEquals();    
	}
	@Override
	public int hashCode() {
	return new HashCodeBuilder().append(this.getId())
	          .append(this.getFirstName())
	          .append(this.getLastName())
	          .append(this.geteMail())
	          .append(this.getMobileNum())
	          .append(this.getAddress())
	          .append(this.getDateOfRegistration())
	          .append(this.getRole())
	          .toHashCode();    
	}
	@Override 
	public String toString() { 
	return new ToStringBuilder(this, ToStringStyle.SIMPLE_STYLE) 
	.append("id ", this.getId()) 
	.append("name", this.getFirstName()) 
	.append("surname", this.getLastName()) 
	.append("e-mail",this.geteMail()) 
	.append("mobile number", this.getMobileNum()) 
	.append("address", this.getAddress()) 
	.append("date of registration", this.getDateOfRegistration()) 
	.append("role", this.getRole()) 
	.append("\n")
	.toString();
	} 
}
