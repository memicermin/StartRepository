package resources.validation_messages;

/**
 * Created by Enver on 12.12.2016.
 */
public class ValidationMessages {

    public static final String EMAIL_REQUIRED = "You must provide valid email address!";
    public static final String EMAIL_PATTERN = "Email is not valid!";

    public static final String PASSWORD_REQUIRED = "You must provide password!";
    public static final String PASSWORD_PATTERN = "Invalid password! Password must contain at least one digit, uppercase and lowercase letter, one of these (@#$%) special characters, and must be at least 6 characters long and maximum 20 characters long.";

    public static final String NAME_MIN = "Three (3) letters is minimum for name and surname.";
    public static final String NAME_MAX = "Forty (40) letters is maximum for name and surname.";

    public static final String USERNAME_MIN = "Three (3) letters is minimum for username.";
    public static final String USERNAME_MAX = "Forty (40) letters is maximum for username.";


}
