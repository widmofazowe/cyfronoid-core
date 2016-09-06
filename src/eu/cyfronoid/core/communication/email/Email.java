package eu.cyfronoid.core.communication.email;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.apache.log4j.Logger;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.inject.Inject;

import eu.cyfronoid.core.communication.AbstractCommunication;
import eu.cyfronoid.core.communication.Recipient;
import eu.cyfronoid.core.configuration.ConfigProperties;

public class Email extends AbstractCommunication {
    private static final Logger logger = Logger.getLogger(Email.class);
    private static final String MAIL_SMTP_USER = "mail.smtp.user";
    private static final String MAIL_SMTP_PASSWORD = "mail.smtp.password";

    private final Map<String, EmailRecipient> apparentSecondaryRecipients = Maps.newHashMap();
    private final List<String> attachments = Lists.newArrayList();
    private final ConfigProperties configuration;

    private EmailRecipient sender;
    private String subject;
    private EmailRecipient primaryRecipient;

    @Inject
    public Email(ConfigProperties configuration) {
        this.configuration = Preconditions.checkNotNull(configuration);
    }

    public void setPrimaryRecipient(EmailRecipient recipient) {
        if(!recipientCollection.containsKey(recipient.toString())) {
            addRecipient(recipient);
        }
        primaryRecipient = recipient;
    }

    public void addCarbonRecipient(EmailRecipient recipient) {
        if(!recipientCollection.containsKey(recipient.toString())) {
            addRecipient(recipient);
        }
        if(!apparentSecondaryRecipients.containsKey(recipient.toString())) {
            apparentSecondaryRecipients.put(recipient.toString(), recipient);
        }
    }

    public void removeCarbonRecipient(EmailRecipient recipient) {
        if(!recipientCollection.containsKey(recipient.toString())) {
            removeRecipient(recipient.toString());
        }
        if(!apparentSecondaryRecipients.containsKey(recipient.toString())) {
            apparentSecondaryRecipients.remove(recipient.toString());
        }
    }

    public void addBlindRecipient(EmailRecipient recipient) {
        if(!recipientCollection.containsKey(recipient.toString())) {
            addRecipient(recipient);
        }
    }

    public void removeBlindRecipient(EmailRecipient recipient) {
        if(!recipientCollection.containsKey(recipient.toString())) {
            removeRecipient(recipient.toString());
        }
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public void setMessageBody(String messageBody) {
        setMessage(messageBody);
    }

    public void setSender(EmailRecipient sender) {
        Preconditions.checkNotNull(sender);
        this.sender = sender;
    }

    public void attachFile(String pathToFile) {
        attachments.add(pathToFile);
    }

    private void fillMessageRecipients(MimeMessage message) throws AddressException, MessagingException {
        message.addRecipient(Message.RecipientType.TO,
                                 new InternetAddress(primaryRecipient.toString()));

        for(Map.Entry<String, Recipient> entry : recipientCollection.entrySet()) {
            Recipient recipient = entry.getValue();
            if(apparentSecondaryRecipients.containsKey(recipient.toString())) {
                message.addRecipient(Message.RecipientType.CC,
                        new InternetAddress(recipient.toString()));
            } else {
                message.addRecipient(Message.RecipientType.BCC,
                        new InternetAddress(recipient.toString()));
            }
        }
    }

    private void fillMessageContent(MimeMessage message) throws MessagingException {
        if(!attachments.isEmpty()) {
            BodyPart messageBodyPart = new MimeBodyPart();
            messageBodyPart.setContent(getMessage(), "text/html");
            Multipart multipart = new MimeMultipart();
            multipart.addBodyPart(messageBodyPart);
            Iterator<String> iterator = attachments.iterator();
            while(iterator.hasNext()) {
                messageBodyPart = new MimeBodyPart();
                String filename = iterator.next();
                messageBodyPart.setDataHandler(new DataHandler(new FileDataSource(filename)));
                messageBodyPart.setFileName(filename);
                multipart.addBodyPart(messageBodyPart);
            }
            message.setContent(multipart);
       } else {
           message.setContent(getMessage(), "text/html");
       }
    }

    @Override
    public boolean send() {
        final Properties properties = new Properties();
        properties.putAll(configuration.getConfigProperties());
        //Security.addProvider(new com.sun.net.ssl.internal.ssl.Provider());
        try {
            Session session = Session.getInstance(properties, new javax.mail.Authenticator() {
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(properties.getProperty(MAIL_SMTP_USER),
                            properties.getProperty(MAIL_SMTP_PASSWORD));
                }
            });
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(sender.toString()));
            message.setSubject(subject);
            fillMessageRecipients(message);
            fillMessageContent(message);
            Transport.send(message);
            logger.info("Email sent to " + primaryRecipient.toString() + " from " + sender.toString() + ".\n");
            return true;
        } catch(MessagingException e) {
            logger.warn("Cannot send an email to " + primaryRecipient.toString() + " from " + sender.toString() + ".\n" + e.getMessage());
            return false;
        }
    }
}


