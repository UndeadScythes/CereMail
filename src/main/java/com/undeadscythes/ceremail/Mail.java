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

import com.undeadscythes.ceremail.MailBox;
import java.io.IOException;
import java.io.Serializable;
import java.util.Date;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

/**
 *
 * @author UndeadScythes <udscythes@gmail.com>
 */
public class Mail implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private final MailBox box;
    private final String id;
    private final Date date;
    private final String sender;
    private final String recipient;
    private final String subject;
    private final String content;
    
    public Mail(Message msg) throws MessagingException, IOException {
        this(msg, MailBox.UNKNOWN);
    }
    
    public Mail(Message msg, MailBox box) throws IOException, MessagingException {
        this.box = box;
        id = ((MimeMessage) msg).getMessageID();
        date = msg.getSentDate();
        sender = msg.getFrom()[0].toString();
        recipient = msg.getAllRecipients()[0].toString();
        subject = msg.getSubject();
        content = msg.getContent().toString();
    }
    
    public MailBox getBox() {
        return box;
    }
    
    public String getId() {
        return id;
    }
    
    public Date getDate() {
        return date;
    }
    
    public String getSender() {
        return sender;
    }
    
    public String getSenderName() {
        return sender.contains("<") ? sender.substring(0, sender.indexOf('<')) : sender;
    }
    
    public String getRecipient() {
        return recipient;
    }
    
    public String getRecipientName() {
        return recipient.contains("<") ? recipient.substring(0, recipient.indexOf('<')) : recipient;
    }
    
    public String getSubject() {
        return subject;
    }
    
    public String getContent() {
        return content;
    }
}
