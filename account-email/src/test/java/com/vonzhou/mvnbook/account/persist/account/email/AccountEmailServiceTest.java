package com.vonzhou.mvnbook.account.persist.account.email;

import com.icegreen.greenmail.util.GreenMail;
import com.icegreen.greenmail.util.GreenMailUtil;
import com.icegreen.greenmail.util.ServerSetup;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import javax.mail.Message;

import static junit.framework.Assert.assertEquals;

/**
 * Created by vonzhou on 16/3/16.
 */
public class AccountEmailServiceTest {
    private GreenMail greenMail;

    @Before
    public void startMailServer() throws  Exception{
        ServerSetup setup = new ServerSetup(9999, "localhost", "smtp");
        greenMail = new GreenMail(setup);
        greenMail.setUser("test@vonzhou.com", "123456");
        greenMail.start();
    }

    @Test
    public void testSendEmail() throws  Exception{
        ApplicationContext context = new ClassPathXmlApplicationContext("account-email.xml");
        AccountEmailService accountEmailService = (AccountEmailService)context.getBean("accountEmailService");

        String subject = "Email Subject";
        String htmlText = "<h1>Test Test</h1>";

        accountEmailService.sendEmail("944671035@qq.com", subject, htmlText);
        greenMail.waitForIncomingEmail(2000, 1);

        Message[] msg = greenMail.getReceivedMessages();
        assertEquals(1, msg.length);
        assertEquals(subject, msg[0].getSubject());
        assertEquals(htmlText, GreenMailUtil.getBody(msg[0]).trim());

    }

    @After
    public void stopMailServer() throws Exception{
        greenMail.stop();
    }
}
