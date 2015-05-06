/*
 * Copyright (C) 2014 UndeadScythes <udscythes@gmail.com>
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.undeadscythes.ceremail;

import java.io.IOException;
import java.util.Properties;
import javax.mail.Flags;
import javax.mail.Flags.Flag;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.search.FlagTerm;
import javax.mail.search.MessageIDTerm;
import javax.mail.search.SearchTerm;

/**
 *
 * @author UndeadScythes <udscythes@gmail.com>
 */
public class MailHandler {
    //private static final Logger logger = Logger.getLogger(MailBox.class.getName());

    private final Session imapSession = Session.getInstance(getImapProps(), null);
    private final String host;
    private String username;
    private String password;

    private Store store;
    private MailBox box;
    private Folder folder;
    private int index;
    private Message[] msgs;

    public MailHandler(String host, String username, String password, boolean debug) {
        this.host = host;
        this.username = username != null ? username : this.username;
        this.password = password != null ? password : this.password;
        imapSession.setDebug(debug);
    }

    public MailHandler(String host, String username, String password) {
        this(host, username, password, false);
    }
    
    public MailHandler(String host, boolean debug) {
        this(host, null, null, debug);
    }
    
    public MailHandler(String host) {
        this(host, null, null, false);
    }

    public void login(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public void openBox(String username, String password, MailBox box) throws MessagingException {
        this.box = box;
        store = imapSession.getStore();
        store.connect(host, username, password);
        folder = store.getFolder(box.toString());
        folder.open(Folder.READ_WRITE);
    }

    public void openBox(MailBox box) throws MessagingException {
        openBox(username, password, box);
        index = 0;
        msgs = folder.getMessages();
    }

    public int searchBox(MailTerm search) throws MessagingException {
        if (search != null) {
            msgs = folder.search(search.getSearchTerm());
        }
        return msgs.length;
    }

    public boolean hasNext() {
        return index < msgs.length;
    }

    public Mail getNextMail() throws MessagingException, IOException {
        Mail mail = new Mail(msgs[index], box);
        nextMail();
        return mail;
    }
    
    public void nextMail() {
        index++;
    }

    private Message getMsg(Mail mail) throws MessagingException {
        return folder.search(new MessageIDTerm(mail.getId()))[0];
    }

    public void setFlag(Mail mail, Flag flag, boolean value) throws MessagingException {
        openBox(username, password, mail.getBox());
        getMsg(mail).setFlag(flag, value);
        closeBox();
    }

    public void closeBox() throws MessagingException {
        folder.close(false);
        store.close();
    }

    private static Properties getImapProps() {
        Properties imap = new Properties();
        imap.setProperty("mail.store.protocol", "imaps");
        return imap;
    }
}
