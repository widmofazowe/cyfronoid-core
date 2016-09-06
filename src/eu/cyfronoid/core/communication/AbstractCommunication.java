package eu.cyfronoid.core.communication;

import java.util.Map;

import com.google.common.base.Preconditions;
import com.google.common.collect.Maps;

public abstract class AbstractCommunication {
    protected Map<String, Recipient> recipientCollection = Maps.newHashMap();
    private String text;

    abstract public boolean send();

    public AbstractCommunication() {
        text = "";
    }

    public void addRecipient(Recipient recipient) {
        recipientCollection.put(recipient.toString(), recipient);
    }

    public void addRecipient(Recipient recipient, String identifier) {
        Preconditions.checkNotNull(recipient);
        if(identifier == null) {
            identifier = recipient.toString();
        }

        recipientCollection.put(identifier, recipient);
    }

    public void removeRecipient(String identifier) {
        recipientCollection.remove(identifier);
    }

    public void setMessage(String text) {
        this.text = text;
    }

    public String getMessage() {
        return text;
    }

}
