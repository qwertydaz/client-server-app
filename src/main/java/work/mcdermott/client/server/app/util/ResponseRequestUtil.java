package work.mcdermott.client.server.app.util;

import work.mcdermott.client.server.app.exception.ClientServerException;

import static work.mcdermott.client.server.app.consts.ClientServerConstants.INVALID_REQUEST_TYPE;

public class ResponseRequestUtil {

	private ResponseRequestUtil() {}

	public static String handleMessage(Object message) {
		try {
			return (String) message;
		}
		catch (ClassCastException cce) {
			throw new ClientServerException(INVALID_REQUEST_TYPE, cce.getCause());
		}
	}
}
