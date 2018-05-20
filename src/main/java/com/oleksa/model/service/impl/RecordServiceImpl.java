package com.oleksa.model.service.impl;

import java.util.List;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import com.oleksa.model.dao.RecordDao;
import com.oleksa.model.entity.Record;
import com.oleksa.model.entity.User;
import com.oleksa.model.exception.RecordOccupiedException;
import com.oleksa.model.service.RecordService;

public class RecordServiceImpl implements RecordService {

	private static enum BeautySalonMail {
		MESSAGE ("Please, leave a comment on your record."),
		SUBJECT ("Beauty Salon record."),
		PASSWORD ("4e8df24ba7ad6caa"),
		EMAIL ("beautysalon9689aac76739245d@gmail.com");
		
		private static final Properties PROPERTIES = new Properties();
		static {
			BeautySalonMail.PROPERTIES.put("mail.smtp.auth", "true");
			BeautySalonMail.PROPERTIES.put("mail.smtp.starttls.enable", "true");
			BeautySalonMail.PROPERTIES.put("mail.smtp.host", "smtp.gmail.com");
			BeautySalonMail.PROPERTIES.put("mail.smtp.port", "587");
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
	
	private RecordDao recordDao;

	public RecordServiceImpl(RecordDao recordDao) {
		this.recordDao = recordDao;
	}

	@Override
	public Record create(Record record) throws RecordOccupiedException {
		return recordDao.create(record);
	}

	@Override
	public List<Record> findAll() {
		return recordDao.findAll();
	}

	@Override
	public List<Record> findAllByClient(User client) {
		return recordDao.findAllByClientIdWithMaster(client.getId());
	}

	@Override
	public void update(Record record) {
		try {
			recordDao.update(record);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public void delete(Record record) {
		recordDao.deleteById(record.getId());
	}

	@Override
	public List<Record> findAllWithComments() {
		return recordDao.findAllWithComments();
	}

	@Override
	public void sendEmail(User user) {
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
