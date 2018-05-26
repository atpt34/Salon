package com.oleksa.model.service.impl;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import com.oleksa.model.entity.User;

/**
 * Class sends mail to users.
 * 
 * @author atpt34
 *
 */
public class MailRunner implements Runnable {

    private static enum BeautySalonMail {
        MESSAGE ("Please, leave a comment on your record."),
        SUBJECT ("Beauty Salon record."),
        PASSWORD ("4e8df24ba7ad6caa"),
        EMAIL ("beautysalon9689aac76739245d@gmail.com");

        private static final Properties PROPERTIES = new Properties();
        static {
            PROPERTIES.setProperty("mail.smtp.auth", "true");
            PROPERTIES.setProperty("mail.smtp.starttls.enable", "true");
            PROPERTIES.setProperty("mail.smtp.host", "smtp.gmail.com");
            PROPERTIES.setProperty("mail.smtp.port", "587");
        }

        private final String value;

        private BeautySalonMail(String value) {
            this.value = value;
        }

        private String getValue() {
            return value;
        }

        private static Properties getProperties() {
            return PROPERTIES;
        }
    }

    private User user;

    public MailRunner(User user) {
        this.user = user;
    }

    @Override
    public void run() {
        try {
            Message message = new MimeMessage(getMailSession());
            message.setFrom(new InternetAddress(BeautySalonMail.EMAIL.getValue()));
            message.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse(user.getEmail()));
            message.setSubject(BeautySalonMail.SUBJECT.getValue());
            message.setText(BeautySalonMail.MESSAGE.getValue());
            Transport.send(message);
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }

    private Session getMailSession() {
        return Session.getInstance(BeautySalonMail.getProperties(),
                new javax.mail.Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(BeautySalonMail.EMAIL.getValue(), BeautySalonMail.PASSWORD.getValue());
            }
        });
    }

}
