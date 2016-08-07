package de.cau.lps.debugger.channel.listenertest.mocks;

import de.cau.lps.debugger.messages.AbstractMessage;
import de.cau.lps.debugger.messages.handler.ViewMessageHandler;

/**
 * Mocks out a {@link ViewMessageHandler} for testing purposes.
 */
public class ViewMessageHandlerMock implements ViewMessageHandler {

    @Override
    public void handleIncomingViewMessage(AbstractMessage message) {
    }
}
