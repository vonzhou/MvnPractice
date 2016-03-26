package com.vonzhou.mvnbook.account.persist;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentFactory;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

import java.io.*;
import java.util.Iterator;
import java.util.List;

/**
 * Created by vonzhou on 16/3/17.
 */
public class AccountPersistServiceImpl implements AccountPersistService{
    private String file;

    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file;
    }

    private SAXReader reader = new SAXReader();
    private static final String ELEMENT_ROOT = "account-persist";
    private static final String ELEMENT_ACCOUNTS = "accounts";
    private static final String ELEMENT_ACCOUNT = "account";
    private static final String ELEMENT_ACCOUNT_ID = "id";
    private static final String ELEMENT_ACCOUNT_NAME = "name";
    private static final String ELEMENT_ACCOUNT_PASSWORD = "password";
    private static final String ELEMENT_ACCOUNT_EMAIL = "email";
    private static final String ELEMENT_ACCOUNT_ACTIVATED = "activated";

    private Document readDocument() throws AccountPersistException{
        File dataFile = new File(file);
        if(!dataFile.exists()){
            dataFile.getParentFile().mkdirs();
            Document doc = DocumentFactory.getInstance().createDocument();
            Element rootElement = doc.addElement(ELEMENT_ROOT);
            rootElement.addElement(ELEMENT_ACCOUNTS);
            writeDocument(doc);

        }

        try{
            return reader.read(dataFile);
        }catch (DocumentException e){
            throw new AccountPersistException("Unable to read persist data xml", e);
        }
    }

    private void writeDocument(Document doc) throws AccountPersistException{
        Writer out = null;
        try{
            out = new OutputStreamWriter(new FileOutputStream(file), "utf-8");
            XMLWriter writer = new XMLWriter(out, OutputFormat.createPrettyPrint());
            writer.write(doc);
        }catch (IOException e){
            throw new AccountPersistException("Unable to write persist data xml", e);
        }finally {
            try{
                if(out != null){
                    out.close();
                }
            }catch (IOException e){
                throw new AccountPersistException("Unable to close persist data xml", e);
            }

        }
    }


    public Account createAccount(Account account) throws AccountPersistException {
        Document doc = readDocument();
        Element accountsElement = doc.getRootElement().element(ELEMENT_ACCOUNTS);
        Element accountElement = accountsElement.addElement(ELEMENT_ACCOUNT);
        accountElement.addAttribute(ELEMENT_ACCOUNT_ID, account.getId());
        accountElement.addAttribute(ELEMENT_ACCOUNT_NAME, account.getName());
        accountElement.addAttribute(ELEMENT_ACCOUNT_EMAIL, account.getEmail());
        accountElement.addAttribute(ELEMENT_ACCOUNT_PASSWORD, account.getPassword());
        accountElement.addAttribute(ELEMENT_ACCOUNT_ACTIVATED, account.isActivted() ? "true" : "false");

        writeDocument(doc);
        return account;
    }

    public Account readAccount(String id) throws AccountPersistException {
        Document doc = readDocument();
        Element accountsElement = doc.getRootElement().element(ELEMENT_ACCOUNTS);
//        Iterator<Element> it = accountsElement.elementIterator(ELEMENT_ACCOUNT);
        for(Iterator iter = accountsElement.elementIterator(ELEMENT_ACCOUNT); iter.hasNext();){
            Element account = (Element)iter.next();
            System.out.println("in for loop" + ":" + account.attributeValue(ELEMENT_ACCOUNT_ID) + ":" + account.elementText(ELEMENT_ACCOUNT_ID));
            if(account.attributeValue(ELEMENT_ACCOUNT_ID).equals(id)){
                return buildAccount(account);
            }
        }
        return null;
    }

    private Account buildAccount(Element accountElement) throws AccountPersistException{
        Account account = new Account();
        account.setId(accountElement.attributeValue(ELEMENT_ACCOUNT_ID));
        account.setName(accountElement.attributeValue(ELEMENT_ACCOUNT_NAME));
        account.setPassword(accountElement.attributeValue(ELEMENT_ACCOUNT_PASSWORD));
        account.setEmail(accountElement.attributeValue(ELEMENT_ACCOUNT_EMAIL));
        account.setActivted("true".equals(accountElement.attributeValue(ELEMENT_ACCOUNT_ACTIVATED)) ? true : false);

        return account;
    }

    public Account updateAccount(Account account) throws AccountPersistException {
        return null;
    }

    public void deleteAccount(Account account) throws AccountPersistException {

    }
}
