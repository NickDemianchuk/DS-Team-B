package com.kaniademianchuk.events;

import com.kaniademianchuk.api.IEventHandler;
import com.kaniademianchuk.api.ITogglable;
import com.kaniademianchuk.model.AbstractIdentifiable;

import java.util.Collection;
import java.util.Objects;

public class DefaultEventHandler extends AbstractIdentifiable implements IEventHandler {
    private final Collection<Condition> conditions;
    private final Action action;

    public DefaultEventHandler(String name, Collection<Condition> conditions, Action action) {
        super(AbstractIdentifiable.receiveAndIncrementLatestId(), name);
        this.conditions = conditions;
        this.action = action;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        DefaultEventHandler that = (DefaultEventHandler) o;
        return Objects.equals(conditions, that.conditions) &&
                Objects.equals(action, that.action);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), conditions, action);
    }

    @Override
    public String toString() {
        return "DefaultEventHandler{" +
                "id=" + this.getId() +
                ", name=" + this.getName() +
                '}';
    }

    @Override
    public void handle(ITogglable subject, EventHandlerType event) {
        for (Condition c : this.conditions) {
            if (!c.evaluate(subject, event)) {
                return;
            }
        }
        this.action.execute(subject);
    }

    public interface Condition {
        boolean evaluate(ITogglable subject, EventHandlerType event);
    }

    public interface Action {
        void execute(ITogglable subject);
    }
}
