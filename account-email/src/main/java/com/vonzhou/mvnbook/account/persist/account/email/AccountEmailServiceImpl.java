package com.vonzhou.mvnbook.account.persist.account.email;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;


/**
 * Created by vonzhou on 16/3/16.
 */
public class AccountEmailServiceImpl implements  AccountEmailService{
    private JavaMailSender javaMailSender;
    private String systemEmail;
    public void sendEmail(String to, String subject, String htmlText) throws AccountEmailException {

        try{
            MimeMessage msg = javaMailSender.createMimeMessage();
            MimeMessageHelper helper= new MimeMessageHelper(msg);

            helper.setFrom(systemEmail);
            helper.setTo(to);
            helper.setSubject(subject);
            // true 表示邮件是HTML格式
            helper.setText(htmlText, true);

            javaMailSender.send(msg);

        }catch (MessagingException e){
            throw new AccountEmailException("Failed to send email....",e);
        }
    }

    public JavaMailSender getJavaMailSender() {
        return javaMailSender;
    }

    public void setJavaMailSender(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    public String getSystemEmail() {
        return systemEmail;
    }

    public void setSystemEmail(String systemEmail) {
        this.systemEmail = systemEmail;
    }
}
