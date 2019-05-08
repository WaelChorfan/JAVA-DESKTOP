package api;

import entities.FosUser;
import entities.Ticket;
import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;

import java.util.Properties;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class SimpleMail {

    final public String _pass;
    public String _to;
    public String _from;

    
       

    
    
    public SimpleMail(Ticket t, FosUser fu) {
//    public SimpleMail() {

        this._pass = fu.getPassword();
        this._to = t.getRecepteurId().getEmail();
        this._from = t.getEmeteurId().getEmail();
//        this._pass = "pass";
//        this._to = "@gmail.com";
//        this._from ="@gmail.com";

    }

    public String SMTP_HOST_NAME = "smtp.gmail.com";
    public String SMTP_AUTH_USER = _from;

    public  void send() throws Exception {
        Properties props = new Properties();
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtp.host", SMTP_HOST_NAME);
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.required", "true");

        Authenticator auth = new SMTPAuthenticator();
        Session mailSession = Session.getDefaultInstance(props, auth);
        // uncomment for debugging infos to stdout
//        mailSession.setDebug(true);
        Transport transport = mailSession.getTransport();

        MimeMessage message = new MimeMessage(mailSession);
        message.setContent("This is a test", "text/plain");
        message.setFrom(new InternetAddress(_from));
        message.addRecipient(Message.RecipientType.TO,
                new InternetAddress(_to));

        transport.connect();
        transport.sendMessage(message,
                message.getRecipients(Message.RecipientType.TO));
        transport.close();
    }

    public class SMTPAuthenticator extends javax.mail.Authenticator {

        @Override
        public PasswordAuthentication getPasswordAuthentication() {
          
            return new PasswordAuthentication(_from, _to);
        }
    }
}
