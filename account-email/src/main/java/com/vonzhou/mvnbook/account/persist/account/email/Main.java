package com.vonzhou.mvnbook.account.persist.account.email;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by vonzhou on 16/3/16.
 */
public class Main {
    public static void main(String args[]) throws  Exception{
        ApplicationContext context = new ClassPathXmlApplicationContext("account-email.xml");
        AccountEmailService accountEmailService = (AccountEmailService)context.getBean("accountEmailService");

        String subject = "Email Subject";
        String htmlText = "<h1>Test Test</h1>";

        accountEmailService.sendEmail("944671035@qq.com", subject, htmlText);
    }
}
