package no.hvl.dat110.messaging;


import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

import no.hvl.dat110.TODO;

public class MessageConnection {

	private DataOutputStream outStream; // for writing bytes to the underlying TCP connection
	private DataInputStream inStream; // for reading bytes from the underlying TCP connection
	private Socket socket; // socket for the underlying TCP connection
	
	public MessageConnection(Socket socket) {

		try {

			this.socket = socket;

			outStream = new DataOutputStream(socket.getOutputStream());

			inStream = new DataInputStream (socket.getInputStream());

		} catch (IOException ex) {

			System.out.println("Connection: " + ex.getMessage());
			ex.printStackTrace();
		}
	}

	public void send(Message message) throws IOException {
		System.out.println("Sendar...");
		byte[] data;

		// TODO - START
		// encapsulate the data contained in the Message and write to the output stream
		data = message.getData();
		if (data == null ||  data.length > 127)
			throw new UnsupportedOperationException("Ugyldig");

		byte[] segment = MessageUtils.encapsulate(message);

        try {
            outStream.write(segment);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
		outStream.flush();

        // TODO - END

	}

	public Message receive() {
		System.out.println("Tek imot...");
		Message message = null;
		byte[] data;
		
		// TODO - START
		// read a segment from the input stream and decapsulate data into a Message

		try {
			// lag array med fast storleik SEGMENTSIZE
			byte[] segment = new byte[MessageUtils.SEGMENTSIZE];

			// les éin heil segment frå straumen
			inStream.read(segment);

			// hent Message med decapsulate
			message = MessageUtils.decapsulate(segment);

		} catch (IOException e) {
			throw new RuntimeException(e);
		}

		// TODO - END
		
		return message;
		
	}

	// close the connection by closing streams and the underlying socket	
	public void close() {

		try {
			
			outStream.close();
			inStream.close();

			socket.close();
			
		} catch (IOException ex) {
			System.out.println("Connection: " + ex.getMessage());
			ex.printStackTrace();
		}
	}
}