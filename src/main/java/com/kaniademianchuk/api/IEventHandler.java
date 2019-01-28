package com.kaniademianchuk.api;

import com.kaniademianchuk.events.EventHandlerType;

public interface IEventHandler {

    public void handle(ITogglable subject, EventHandlerType event);
}
