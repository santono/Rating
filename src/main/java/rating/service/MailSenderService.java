package rating.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import java.util.Random;

@Service
public class MailSenderService {
    @Autowired
    private MailSender mailSender;
    @Autowired
    private SimpleMailMessage templateMessage;
    public void sendMail(String toAddress,String subject,String body) {
//        System.out.println("-> inside mailSend "+toAddress+" "+subject+" "+body);
//      String from="kfu.2018@yandex.ru";
//      Создаём потокобезопасную копию шаблона.
        SimpleMailMessage email = new SimpleMailMessage(templateMessage);
        email.setTo(toAddress);
        email.setText(body);
        email.setSubject(subject);
        try {
            mailSender.send(email);
//            System.out.println("Mail sended");
        } catch (MailException mailException) {
            System.out.println("Mail send failed.");
            System.out.println(mailException.getMessage());
        } catch (Exception exception) {
            System.out.println("Mail send failed ending.");
            System.out.println(exception.getMessage());
        }
//        email.setTo(toAddress);
//        email.setSubject(subject);
//        email.setText(body);
/*
        try {
             mailSender.send(email);
        } catch (Exception e) {
             System.out.println(" error="+e.getMessage());
        }
*/
    }
    public String sendCodeByMail(String toAddress) {
//        System.out.println(" inside sending code for "+toAddress);
        String retval;
        int min=10000;
        int max=100000;
        int r= min+(int)(Math.random()*(max-min));
        retval=""+r;
        String subject="Код для регистрации в системе учета НТР";
        String body="Код для продолжения регистрации "+r;
        sendMail(toAddress,subject,body);
        return retval;
    }
    public boolean sendPasswordByMail(String toAddress,String pwd, String login, String address) {
        if (toAddress==null)
            return false;
        if (toAddress.trim().length()<3)
            return false;
        if (!isValidEmailAddress(toAddress))
            return false;
//        System.out.println(" inside sending Password By Email for "+toAddress);
        String subject="Реквизиты для входа в систему учета НПР";
        StringBuilder sb=new StringBuilder();
        sb.append("Ваша регистрация в информационной системе учета научных работ подтверждена \r\n");
        sb.append("Реквизиты для входа: \r\n");
        sb.append("-------------------- \r\n");
        sb.append("адрес:  ");
        sb.append(address);
        sb.append("\r\n");
        sb.append("логин:  ");
        sb.append(login);
        sb.append("\r\n");
        sb.append("пароль: ");
        sb.append(pwd);
        sb.append("\r\n");
        sb.append(" ");
        sb.append("\r\n");
        sb.append("Рекомендуется сменить пароль в личном кабинете. ");
        sb.append("\r\n");
        String body=sb.toString();
        sendMail(toAddress,subject,body);
        return true;
    }
    public boolean isValidEmailAddress(String email) {
        boolean result = true;
        try {
            InternetAddress emailAddr = new InternetAddress(email);
            emailAddr.validate();
        } catch (AddressException ex) {
            result = false;
        }
        return result;
    }

}
