package com.kaniademianchuk.util;

import com.kaniademianchuk.api.IEventHandler;
import com.kaniademianchuk.api.ITogglable;
import com.kaniademianchuk.events.EventHandlerType;

public class MockEventHandler implements IEventHandler {
    @Override
    public void handle(ITogglable subject, EventHandlerType event) {
    }
}
