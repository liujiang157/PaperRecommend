package com.example.demo2.utils;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

/**
 * @Author: Liujiang
 * @Date: 2020/4/9 15:26
 */


public class SendEmail {

    public static boolean sendemail(String theme, String messages,String email){
        Properties prop = new Properties();
        prop.put("mail.transport.protocol", "smtp");
        prop.put("mail.host", "smtp.qq.com");
        prop.put("mail.smtp.auth", "true");
        Session session = Session.getInstance(prop);
        session.setDebug(true);
        Message message;
        try {
            message = createSimpleMail(session, theme, messages,email);
            Transport ts = session.getTransport();
            ts.connect("liujiang157@qq.com", "xgcpltqsfnncbdda");
            ts.sendMessage(message, message.getAllRecipients());
            ts.close();
            return true;
        } catch (Exception e) {
            return false;
        }

    }

    private static MimeMessage createSimpleMail(Session session, String theme, String messages,String email) throws Exception {
        MimeMessage message = new MimeMessage(session);
        message.setFrom(new InternetAddress("liujiang157@qq.com"));
        message.addRecipients(Message.RecipientType.TO, email);
        message.setSubject(theme);
        message.setText(messages);
        message.saveChanges();
        return message;
    }

}

