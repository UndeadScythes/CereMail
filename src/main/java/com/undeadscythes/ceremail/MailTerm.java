/*
 * Copyright (C) 2014 UndeadScythes
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

import javax.mail.Flags;
import javax.mail.search.FlagTerm;
import javax.mail.search.SearchTerm;

/**
 *
 * @author UndeadScythes
 */
public enum MailTerm {
    NEW(new FlagTerm(new Flags(Flags.Flag.SEEN), false));
    
    private final SearchTerm searchTerm;

    private MailTerm(SearchTerm searchTerm) {
        this.searchTerm = searchTerm;
    }

    public SearchTerm getSearchTerm() {
        return searchTerm;
    }
}
