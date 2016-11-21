package edu.cursor.library.model;

import org.apache.commons.lang3.builder.*;
import org.joda.time.LocalDate;
import lombok.*;


@NoArgsConstructor
@RequiredArgsConstructor
@AllArgsConstructor
public class TblUser {
	@Getter @Setter private int id;
	@Getter @Setter private String firstName;
	@Getter @Setter private String lastName;
	@Getter @Setter private String eMail;
	@Getter @Setter private int mobileNum;
	@Getter @Setter private String address;
	@Getter @Setter private LocalDate dateOfRegistration;
	@Getter @Setter private UserRole role;

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
	              .append(this, other.getEMail())
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
	          .append(this.getEMail())
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
	.append("e-mail",this.getEMail())
	.append("mobile number", this.getMobileNum()) 
	.append("address", this.getAddress()) 
	.append("date of registration", this.getDateOfRegistration()) 
	.append("role", this.getRole()) 
	.append("\n")
	.toString();
	}
}
