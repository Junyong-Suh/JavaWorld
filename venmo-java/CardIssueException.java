/**
 * CardIssueException Class
 *
 * any card issue exceptions
 */
public class CardIssueException extends Exception {
	public CardIssueException() {
		super();
	}

	public CardIssueException(String message) {
		super(message);
	}

	public CardIssueException(String message, Throwable cause) {
		super(message, cause);
	}

	public CardIssueException(Throwable cause) {
		super(cause);
	}
}