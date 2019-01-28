package com.kaniademianchuk.events;

import com.kaniademianchuk.api.IEventHandler;
import com.kaniademianchuk.api.IIdentifiable;
import com.kaniademianchuk.api.ITogglable;
import com.kaniademianchuk.model.Manager;

public class EventBroker<T extends IIdentifiable & IEventHandler> extends Manager<T> implements IEventHandler {

    public void handle(ITogglable subject, EventHandlerType type) {
        for (IEventHandler handler : this.getAllDevices()) {
            handler.handle(subject, type);
        }
    }

}
