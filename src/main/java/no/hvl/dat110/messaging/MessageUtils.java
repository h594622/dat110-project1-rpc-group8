package no.hvl.dat110.messaging;

import java.util.Arrays;

import no.hvl.dat110.TODO;

public class MessageUtils {

	public static final int SEGMENTSIZE = 128;

	public static int MESSAGINGPORT = 8080;
	public static String MESSAGINGHOST = "localhost";

	public static byte[] encapsulate(Message message) {
		
		byte[] segment = null;
		byte[] data;
		
		// TODO - START
		data = message.getData();
		// encapulate/encode the payload data of the message and form a segment
		// according to the segment format for the messaging layer

		if (data == null || data.length > SEGMENTSIZE - 1)
			throw new UnsupportedOperationException("Ugyldig");

		segment = new byte[SEGMENTSIZE];
		segment[0] = (byte) data.length;

		for(int i = 1; i < data.length; i++){
			segment[i+1] = data[i];
		}

		// TODO - END
		return segment;
		
	}

	public static Message decapsulate(byte[] segment) {

		Message message = null;
		
		// TODO - START
		// decapsulate segment and put received payload data into a message

		if (segment == null || segment.length > SEGMENTSIZE)
			throw new UnsupportedOperationException("Ugyldig");

		byte[] data = new byte[SEGMENTSIZE - 1];

		for(int i = 0; i < data.length; i++){
			data[i] = segment[i+1];
		}

		message = new Message(data);

		// TODO - END
		
		return message;
		
	}
	
}
