package subhasys.api.restful.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
public class Customer {
	
	@JsonProperty(value = "first")
	private String firstName;
	
	@JsonProperty(value = "middle")
	private String middleName;
	
	@JsonProperty(value = "last")
	private String lastName;
	
	private String gender;
	private String email;

    @Id
    @NotNull
    @Size(max = 64)
    @Column(name = "customerid", nullable = false, updatable = false)
    private String customerid;

	@NotNull
    @Size(max = 64)
    @Column(name = "password", nullable = false)
    private String password;

    Customer() {
    }

    public Customer(final String customerId, final String password, final String email) {
        this.customerid = customerId;
        this.password = password;
        this.email = email;
    }

    /**
	 * @return the firstName
	 */
	public String getFirstName() {
		return firstName;
	}

	/**
	 * @param firstName the firstName to set
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	/**
	 * @return the middleName
	 */
	public String getMiddleName() {
		return middleName;
	}

	/**
	 * @param middleName the middleName to set
	 */
	public void setMiddleName(String middleName) {
		this.middleName = middleName;
	}

	/**
	 * @return the lastName
	 */
	public String getLastName() {
		return lastName;
	}

	/**
	 * @param lastName the lastName to set
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	/**
	 * @return the gender
	 */
	public String getGender() {
		return gender;
	}

	/**
	 * @param gender the gender to set
	 */
	public void setGender(String gender) {
		this.gender = gender;
	}

	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * @param customerid the customerid to set
	 */
	public void setCustomerid(String customerid) {
		this.customerid = customerid;
	}

	public String getCustomerid() {
		return customerid;
	}

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return Objects.toStringHelper(this)
                .add("customerid", customerid)
                .add("password", password)
                .add("firstName", firstName)
                .add("middleName", middleName)
                .add("lastName", lastName)
                .add("gender", gender)
                .add("email", email)
                .toString();
    }
    
}
