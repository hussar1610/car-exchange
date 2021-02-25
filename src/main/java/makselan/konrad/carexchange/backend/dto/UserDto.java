package makselan.konrad.carexchange.backend.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;


public class UserDto {

    @NotEmpty
    @NotNull
    @Size(min = 5, max = 20)
    private String username;

    @NotEmpty
    @NotNull
    @Size(min = 5, max = 20)
    @Pattern(regexp = "^(\\S+)$",
            message = "white spaces don’t allowed")
    private String password;

    @NotEmpty
    @NotNull
    private String confirmPassword;

    @Pattern(regexp = "^(\\d{3}[- ]?){3}",
            message = "Invalid phone number (Should be: XXX-XXX-XXX or XXX XXX XXX or XXXXXXXXX")
    private String phoneNumber;

    public UserDto() {
    }

    public UserDto(String username, String password, String confirmPassword, String phoneNumber) {
        this.username = username;
        this.password = password;
        this.confirmPassword = confirmPassword;
        this.phoneNumber = phoneNumber;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    public String getPhoneNumber() { return phoneNumber; }

    public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }
}
