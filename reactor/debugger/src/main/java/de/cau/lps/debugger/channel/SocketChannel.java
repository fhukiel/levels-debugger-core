package de.cau.lps.debugger.channel;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.cau.lps.debugger.messages.AbstractMessage;

/**
 * Handles communication via a TCP socket.
 * 
 * @author Thomas Ulrich
 *
 */
public class SocketChannel implements CommunicationChannel {

    private static Logger logger = LoggerFactory.getLogger(SocketChannel.class);
    private static final String END_SYMBOL = "/!/";

    private boolean available;
    private int port;
    private ServerSocket serverSocket;
    private Socket socket;
    private BufferedReader reader;
    private BufferedWriter writer;
    private CommunicationChannelTarget target;

    /**
     * Initializes a new instance of the {@link SocketChannel} class.
     * 
     * @param port
     *            The port number on which to create the port.
     * @param target
     *            The {@link CommunicationChannelTarget} associated with this channel.
     */
    public SocketChannel(int port, CommunicationChannelTarget target) {
        this.port = port;
        this.target = target;
        this.available = false;
    }

    /**
     * Opens the socket and waits for a connection
     * 
     * @throws Exception
     *             Thrown if socket cannot be successfully opened.
     */
    @Override
    public void open() throws Exception {
        try {
            this.serverSocket = new ServerSocket(port);
            logger.debug("SocketChannel is waiting for client on port " + port);
            this.sendChannelOpenSignal();
            this.socket = serverSocket.accept();
            logger.debug("SocketChannel has accepted client on port " + port);
            this.reader = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
            this.writer = new BufferedWriter(new OutputStreamWriter(this.socket.getOutputStream()));
            logger.debug("SocketChannel is ready.");
            this.available = true;
        } catch (Exception e) {
            logger.error("Error while opening SocketChannel, exception was " + e.getMessage(), e);
            e.printStackTrace();
            throw e;
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see de.cau.lps.debugger.channel.CommunicationChannel#sendMessage(de.cau.lps.debugger.messages.AbstractMessage)
     */
    @Override
    public void sendMessage(AbstractMessage message) throws IOException {

        try {
            if (this.isAvailable()) {
                this.writer.write(message.toString() + END_SYMBOL);
                this.writer.newLine();
                this.writer.flush();
            } else {
                logger.error("Cannot send message, channel is no longer available!");
            }
        } catch (IOException e) {
            logger.error("Could not write message, exception was " + e.getMessage(), e);
            e.printStackTrace();
            throw e;
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see de.cau.lps.debugger.communication.CommunicationChannel#receiveMessage()
     */
    @Override
    public Optional<String> receiveMessage() {
        try {
            String read = this.reader.readLine();
            if (read == null) {
                logger.info("Socket was closed by peer.");
                return Optional.empty();
            }
            return Optional.of(read);
        } catch (IOException e) {
            // Swallow exceptions during closing
            if (!this.available) {
                logger.error("Could not receive message, exception was " + e.getMessage(), e);
            }
            return Optional.empty();
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.io.Closeable#close()
     */
    @Override
    public void close() throws IOException {
        logger.debug("SocketChannel is closing.");
        try {
            this.available = false;

            // Forcefully close input stream to stop blocking wait
            if (this.socket != null) {
                this.socket.getInputStream().close();
            }
            // Clean up remaining resources
            if (this.reader != null) {
                this.reader.close();
                this.reader = null;
            }
            if (this.writer != null) {
                this.writer.close();
                this.writer = null;
            }
            if (this.socket != null) {
                this.socket.close();
                this.socket = null;
            }
            if (this.serverSocket != null) {
                this.serverSocket.close();
                this.serverSocket = null;
            }
            logger.debug("SocketChannel successfully closed.");
        } catch (Exception e) {
            logger.error("Could not close associated resources, exception was " + e.getMessage(), e);
            e.printStackTrace();
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see de.cau.lps.debugger.communication.channel.CommunicationChannel#getTarget( )
     */
    @Override
    public CommunicationChannelTarget getTarget() {
        return target;
    }

    @Override
    public boolean isAvailable() {
        return available;
    }

    private void sendChannelOpenSignal() {
        logger.info("!!!" + this.target + "CHANNELREADY!!!");
    }
}
