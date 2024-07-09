package work.mcdermott.client.server.app.consts;

public class ClientServerConstants {

	private ClientServerConstants() {}

	public static final String DIVIDER = "----------------------------------------";

	// Port number
	public static final int PORT = 9876;

	// Error messages
	public static final String ERROR_TEMPLATE = "ERROR: %s";
	public static final String INVALID_REQUEST_TYPE = "Request is not a String";

	// Client
	public static final String EXIT = "exit";
	public static final String CONNECTED_TO_SERVER = "Connected to server: %s";
	public static final String REQUEST_SENT = "\nRequest sent: '%s'";
	public static final String RESPONSE_RECEIVED = "Response received: '%s'";
	public static final String SENDING_REQUESTS = "\nSending requests to server...";
	public static final String DISCONNECTED_FROM_SERVER = "Disconnected from server";

	// Server
	public static final String MESSAGE_TEMPLATE = "Hello %s!";
	public static final String SERVER_STARTED = "Server started";
	public static final String REQUEST_RECEIVED = "\nRequest received: '%s'";
	public static final String RESPONSE_SENT = "Response sent: '%s'";
	public static final String AWAITING_REQUEST = "\nAwaiting request from client...";
	public static final String SERVER_SHUTDOWN = "Server shutdown";
}
