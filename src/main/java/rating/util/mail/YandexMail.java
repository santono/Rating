package rating.util.mail;


import java.util.Properties;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.Multipart;
import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;
import javax.mail.internet.*;

public class YandexMail {
    //kfu.2018@ya.ru pwd=ar1737
    //kfu.2018@yandex.ru pwd=ar1737

    public static void SendMail(String destAddress,String messageText) {
  /*
          final String yandexUser="kfu.2018";
          final String yandexPwd="ar1737";
          final String internetAddress="kfu.2018@yandex.ru";
          Properties props = new Properties();
          props.put("mail.smtp.host", "smtp.yandex.ru");
          props.put("mail.smtp.auth", "true");
          props.put("mail.smtp.port", "465");
          props.put("mail.smtp.socketFactory.port", "465");
          props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");

          Session session = Session.getDefaultInstance(props,
                 new javax.mail.Authenticator() {
                     @Override
                     protected PasswordAuthentication getPasswordAuthentication() {
                         return new PasswordAuthentication(yandexUser, yandexPwd);
                     }
                 });

          Message message = new MimeMessage(session);
          message.setFrom(new InternetAddress("yandex.user@ya.ru"));
//    message.setRecipients(Message.RecipientType.TO, InternetAddress.parse("a@grasoff.net"));
          message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(destAddress));
          message.setSubject("Регистрация в система рейтинга научных работ");
          message.setText(messageText);

          Transport.send(message);
   */
    }
}
