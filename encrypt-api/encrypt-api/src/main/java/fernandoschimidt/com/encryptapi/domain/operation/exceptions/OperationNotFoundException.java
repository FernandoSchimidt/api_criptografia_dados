package fernandoschimidt.com.encryptapi.domain.operation.exceptions;

public class OperationNotFoundException extends RuntimeException {

    public OperationNotFoundException(Long id) {
        super("Operation not found with Id: " + id);
    }
}
