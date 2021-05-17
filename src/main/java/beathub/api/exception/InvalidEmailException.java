package beathub.api.exception;

public class InvalidEmailException extends RuntimeException {

    public InvalidEmailException() {
        super("Invalid email address. Valid e-mail can contain only latin letters, numbers, '@' and '.'. And should " +
                "look like 'name@company.com'.");
    }
}
