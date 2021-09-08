package beathub.api.exception;

public class NoSuchProjectException extends RuntimeException{

    public NoSuchProjectException(Long projectId) {
        super("No project with id " + projectId);
    }

}
