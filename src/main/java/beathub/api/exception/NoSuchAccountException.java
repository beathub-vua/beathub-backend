package beathub.api.exception;

public class NoSuchAccountException extends RuntimeException {
    public NoSuchAccountException(Long accountId) {
        super("No account with id " + accountId);
    }
}
