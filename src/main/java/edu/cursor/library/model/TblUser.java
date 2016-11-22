package edu.cursor.library.model;

import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.builder.*;
import org.joda.time.LocalDate;
import lombok.*;
import org.springframework.data.annotation.Id;
import javax.persistence.*;

@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users")
public class TblUser {
	@Getter
	@Setter
	@Id
	@GeneratedValue(strategy= GenerationType.AUTO)
	@Column(name ="id",nullable = false)
	private int id;
	@Getter
	@Setter
	@Column(name = "first_name")
	private String firstName;
	@Getter
	@Setter
	@Column(name = "last_name")
	private  String lastName;
	@Getter
	@Setter
	@Column(name = "e_mail")
    private String email;
	@Getter
	@Setter
	@Column(name ="mobile_num")
	private int mobileNum;
	@Getter
	@Setter
	@Column(name ="address")
	private String address;
	@Getter
	@Setter
	@Column(name = "date_of_registration")
	private LocalDate dateOfRegistration;
	@Getter
	@Setter
	@Column(name = "role")
	@Enumerated(EnumType.STRING)
	private UserRole role;

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
	              .append(this, other.getEmail())
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
	          .append(this.getEmail())
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
	.append("e-mail",this.getEmail())
	.append("mobile number", this.getMobileNum())
	.append("address", this.getAddress())
	.append("date of registration", this.getDateOfRegistration())
	.append("role", this.getRole())
	.append("\n")
	.toString();
	}
}
