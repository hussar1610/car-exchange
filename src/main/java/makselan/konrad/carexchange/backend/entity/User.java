package makselan.konrad.carexchange.backend.entity;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.RandomStringUtils;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import java.util.LinkedList;
import java.util.List;

@Entity
public class User extends AbstractEntity{

    @NotEmpty
    @Column(unique = true)
    private String username;

    @NotEmpty
    private String passwordSalt;
    private String passwordHash;

    @Pattern(regexp = "^(\\d{3}[- ]?){3}",
            message = "Invalid phone number (Should be: XXX-XXX-XXX or XXX XXX XXX or XXXXXXXXX")
    private String phoneNumber;

    @OneToMany(mappedBy = "userWhoPosted", fetch = FetchType.EAGER)
    private List<Car> postedCars = new LinkedList<>();

    public User() {
    }

    public User(String username, String password, String phoneNumber) {
        this.username = username;
        this.passwordSalt = RandomStringUtils.random(32);
        this.passwordHash = DigestUtils.sha1Hex(password + passwordSalt);
        this.phoneNumber = phoneNumber;
    }

    public boolean isPasswordCorrect(String password){
        return  DigestUtils.sha1Hex(password + passwordSalt).equals(passwordHash);
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public List<Car> getPostedCars() {
        return postedCars;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
