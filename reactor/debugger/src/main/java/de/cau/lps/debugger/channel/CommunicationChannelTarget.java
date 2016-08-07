package de.cau.lps.debugger.channel;

/**
 * Lists the possible targets of a channel, i.e. which component a {@link CommunicationChannel} is connected with.
 * 
 * @author Thomas Ulrich
 *
 */
public enum CommunicationChannelTarget {
    /**
     * Target is the view.
     */
    VIEW,

    /**
     * Target is the runtime.
     */
    RUNTIME
}
