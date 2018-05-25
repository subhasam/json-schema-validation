package subhasys.api.restful.service.exception;

public class CustomerExistsException extends RuntimeException {

	private static final long serialVersionUID = 4833930191528899280L;

	public CustomerExistsException(final String message) {
        super(message);
    }
}
