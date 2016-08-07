package de.cau.lps.debugger.channel;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Opens up a given {@link CommunicationChannel}. Blocks until channel is open.
 * 
 * @author Thomas Ulrich
 *
 */
public class ChannelInitializer implements Runnable {
    private static Logger logger = LoggerFactory.getLogger(ChannelInitializer.class);

    private CommunicationChannel channel;

    /**
     * Initializes a new instance of the {@link ChannelInitializer} class.
     * 
     * @param channel
     *            The {@link ChannelInitializer} to initialize.
     */
    public ChannelInitializer(CommunicationChannel channel) {
        this.channel = channel;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Runnable#run()
     */
    @Override
    public void run() {
        try {
            this.channel.open();
        } catch (Exception e) {
            logger.error("Error during channel initialization, exception was " + e.getMessage(), e);
        }
    }
}
