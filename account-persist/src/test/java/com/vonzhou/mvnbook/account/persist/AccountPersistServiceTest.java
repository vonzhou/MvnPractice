package com.vonzhou.mvnbook.account.persist;

import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.File;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Created by vonzhou on 16/3/18.
 */
public class AccountPersistServiceTest {
    private AccountPersistService accountPersistService;

    @Before
    public void prepare()throws Exception{
        File persistFile = new File("target/test-classes/persist-data.xml");

        if(persistFile.exists()){
            persistFile.delete();
        }
        System.out.println("===========" + persistFile.getPath());
        ApplicationContext context = new ClassPathXmlApplicationContext("account-persist.xml");
        accountPersistService = (AccountPersistService)context.getBean("accountPersistService");
        Account account = new Account();
        account.setId("vonzhou");
        account.setName("FengX");
        account.setEmail("232423424@qq.com");
        account.setPassword("2323");
        account.setActivted(true);

        accountPersistService.createAccount(account);
    }

    @Test
    public void testReadAccount() throws  AccountPersistException{
        Account account = accountPersistService.readAccount("vonzhou");
        assertNotNull(account);
        System.out.println("ACCOUNT NOT NULL" + ":" + account.getName());
        assertEquals("FengX", account.getName());
    }
}
