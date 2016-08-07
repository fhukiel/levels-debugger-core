package de.cau.lps.debugger.messages.outgoing;

/**
 * Lists the types of known outgoing messages.
 * 
 * @author Thomas Ulrich
 *
 */
public enum OutgoingMessageType {

    /**
     * Message signals the view that step commandos are sent automatically to the runtime.
     */
    AUTOSTEPPINGENABLED,

    /**
     * Message signals the view that step commandos are no longer sent automatically to the runtime.
     */
    AUTOSTEPPINGDISABLED,

    /**
     * Message signals the view that the runtime replay has finished.
     */
    ENDOFREPLAYTAPE,

    /**
     * Message contains an updated variable table to be sent to the view.
     */
    TABLEUPDATED,

    /**
     * Message contains an updated position to be sent to the view.
     */
    POSITIONUPDATED,

    /**
     * Message contains an updated call stack to be sent to the view.
     */
    CALLSTACKUPDATED,

    /**
     * Message signals the runtime to run to the next statement.
     */
    NEXTSTEPREQUESTED,

    /**
     * Message signals the view that both parties are now connected.
     */
    READY,

    /**
     * Message signals the receiver to terminate communication with the sender.
     */
    TERMINATECOMMUNICATION
}
