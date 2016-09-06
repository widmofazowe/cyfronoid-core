package eu.cyfronoid.core.communication.email;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.google.common.base.Preconditions;

import eu.cyfronoid.core.communication.Recipient;

public class EmailRecipient implements Recipient {
    final private static String EMAIL_PATTERN_REGEXP =
        "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@" +
        "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
    final private static Pattern EMAIL_PATTERN = Pattern.compile(EMAIL_PATTERN_REGEXP);
    final private static Pattern NAME_PATTERN = Pattern.compile("[a-zA-Z]+");
    private String recipientName;
    private String recipientAddress;

    public EmailRecipient(String recipientAddress) {
        this(recipientAddress, "");
    }

    public EmailRecipient(String recipientAddress, String recipientName) {
        Preconditions.checkNotNull(recipientAddress);
        Preconditions.checkNotNull(recipientName);
        this.recipientAddress = recipientAddress;
        this.recipientName = recipientName;
    }

    @Override
    public boolean isValid() {
        Matcher email_matcher = EMAIL_PATTERN.matcher(recipientAddress);
        Matcher name_matcher = NAME_PATTERN.matcher(recipientName);
        return email_matcher.matches() && name_matcher.matches();
    }

    @Override
    public String toString() {
        return getStringRepresentation();
    }

    public String getStringRepresentation() {
        StringBuilder sb = new StringBuilder();
        if(!recipientName.isEmpty()) {
            sb.append(recipientName).append(" ");
        }
        sb.append("<").append(recipientAddress).append(">");
        return sb.toString();
    }

}