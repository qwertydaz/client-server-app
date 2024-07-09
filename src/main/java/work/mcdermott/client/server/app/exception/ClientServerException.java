package work.mcdermott.client.server.app.exception;

public class ClientServerException extends RuntimeException {
	public ClientServerException(String message, Throwable cause)
	{
		super(message, cause);
	}
}
