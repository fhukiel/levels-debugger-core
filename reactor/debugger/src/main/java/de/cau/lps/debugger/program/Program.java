package de.cau.lps.debugger.program;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.cau.lps.debugger.channel.CommunicationChannel;
import de.cau.lps.debugger.channel.CommunicationChannelTarget;
import de.cau.lps.debugger.channel.SocketChannel;
import de.cau.lps.debugger.languagespecific.initialization.DependencyInitializer;
import de.cau.lps.debugger.messages.outgoing.OutgoingMessageType;
import de.cau.lps.debugger.messages.outgoing.TerminateCommunicationMessage;

/**
 * Main entry class for this program.
 * 
 * @author Thomas Ulrich
 *
 */
public class Program {

    private static Logger logger = LoggerFactory.getLogger(Program.class);
    private static CommunicationChannel viewChannel;
    private static CommunicationChannel runtimeChannel;

    /**
     * Starts the program.
     * 
     * @param args
     *            The program arguments.
     */
    public static void main(String[] args) {
        try {
            logger.info("Debugger is starting.");
            DependencyInitializer.initialize();
            setupChannels();
            Thread debuggerThread = new Thread(new DebuggerImpl(viewChannel, runtimeChannel));
            try {
                debuggerThread.start();
                debuggerThread.join();
            } catch (InterruptedException e) {
                logger.error("Error during execution, exception was " + e.getMessage(), e);
                throw e;
            }
            closeChannels();
            logger.info("Debugger is done.");
        } catch (Throwable e) {
            logger.error("A runtime error occurred: " + e.getMessage(), e);
            e.printStackTrace();
        } finally {
        }
    }

    /**
     * Creates the required view / runtime channel.
     */
    private static void setupChannels() {
        viewChannel = new SocketChannel(59599, CommunicationChannelTarget.VIEW);
        runtimeChannel = new SocketChannel(59598, CommunicationChannelTarget.RUNTIME);
    }

    /**
     * Closes the used {@link CommunicationChannel}s.
     */
    private static void closeChannels() {
        try {
            logger.debug("Closing view channel...");
            viewChannel
                .sendMessage(new TerminateCommunicationMessage(OutgoingMessageType.TERMINATECOMMUNICATION.toString()));
            viewChannel.close();
            logger.debug("Closing runtime channel...");
            runtimeChannel
                .sendMessage(new TerminateCommunicationMessage(OutgoingMessageType.TERMINATECOMMUNICATION.toString()));
            runtimeChannel.close();
        } catch (Exception e) {
            logger.error("Error while attempting to close communication channels, exception was " + e.getMessage(), e);
        }
    }
}
