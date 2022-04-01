/*
 * This file is generated by jOOQ.
*/
package com.speaker.db.jooq.generated;


import com.speaker.db.jooq.generated.tables.Account;
import com.speaker.db.jooq.generated.tables.City;
import com.speaker.db.jooq.generated.tables.Country;
import com.speaker.db.jooq.generated.tables.Friends;
import com.speaker.db.jooq.generated.tables.Message;

import javax.annotation.Generated;

import org.jooq.ForeignKey;
import org.jooq.Identity;
import org.jooq.Record;
import org.jooq.UniqueKey;
import org.jooq.impl.AbstractKeys;


/**
 * A class modelling foreign key relationships between tables of the <code>speaker</code> 
 * schema
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.9.1"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class Keys {

    // -------------------------------------------------------------------------
    // IDENTITY definitions
    // -------------------------------------------------------------------------

    public static final Identity<Record, Integer> IDENTITY_ACCOUNT = Identities0.IDENTITY_ACCOUNT;
    public static final Identity<Record, Integer> IDENTITY_CITY = Identities0.IDENTITY_CITY;
    public static final Identity<Record, Integer> IDENTITY_COUNTRY = Identities0.IDENTITY_COUNTRY;
    public static final Identity<Record, Integer> IDENTITY_FRIENDS = Identities0.IDENTITY_FRIENDS;
    public static final Identity<Record, Integer> IDENTITY_MESSAGE = Identities0.IDENTITY_MESSAGE;

    // -------------------------------------------------------------------------
    // UNIQUE and PRIMARY KEY definitions
    // -------------------------------------------------------------------------

    public static final UniqueKey<Record> KEY_ACCOUNT_PRIMARY = UniqueKeys0.KEY_ACCOUNT_PRIMARY;
    public static final UniqueKey<Record> KEY_CITY_PRIMARY = UniqueKeys0.KEY_CITY_PRIMARY;
    public static final UniqueKey<Record> KEY_COUNTRY_PRIMARY = UniqueKeys0.KEY_COUNTRY_PRIMARY;
    public static final UniqueKey<Record> KEY_FRIENDS_PRIMARY = UniqueKeys0.KEY_FRIENDS_PRIMARY;
    public static final UniqueKey<Record> KEY_MESSAGE_PRIMARY = UniqueKeys0.KEY_MESSAGE_PRIMARY;

    // -------------------------------------------------------------------------
    // FOREIGN KEY definitions
    // -------------------------------------------------------------------------

    public static final ForeignKey<Record, Record> FK_CITY_TO_COUNTRY = ForeignKeys0.FK_CITY_TO_COUNTRY;
    public static final ForeignKey<Record, Record> FK_FRIENDS_TO_ACCOUNT = ForeignKeys0.FK_FRIENDS_TO_ACCOUNT;

    // -------------------------------------------------------------------------
    // [#1459] distribute members to avoid static initialisers > 64kb
    // -------------------------------------------------------------------------

    private static class Identities0 extends AbstractKeys {
        public static Identity<Record, Integer> IDENTITY_ACCOUNT = createIdentity(Account.ACCOUNT, Account.ACCOUNT.ID);
        public static Identity<Record, Integer> IDENTITY_CITY = createIdentity(City.CITY, City.CITY.ID);
        public static Identity<Record, Integer> IDENTITY_COUNTRY = createIdentity(Country.COUNTRY, Country.COUNTRY.ID);
        public static Identity<Record, Integer> IDENTITY_FRIENDS = createIdentity(Friends.FRIENDS, Friends.FRIENDS.ID);
        public static Identity<Record, Integer> IDENTITY_MESSAGE = createIdentity(Message.MESSAGE, Message.MESSAGE.ID);
    }

    private static class UniqueKeys0 extends AbstractKeys {
        public static final UniqueKey<Record> KEY_ACCOUNT_PRIMARY = createUniqueKey(Account.ACCOUNT, "KEY_account_PRIMARY", Account.ACCOUNT.ID);
        public static final UniqueKey<Record> KEY_CITY_PRIMARY = createUniqueKey(City.CITY, "KEY_city_PRIMARY", City.CITY.ID);
        public static final UniqueKey<Record> KEY_COUNTRY_PRIMARY = createUniqueKey(Country.COUNTRY, "KEY_country_PRIMARY", Country.COUNTRY.ID);
        public static final UniqueKey<Record> KEY_FRIENDS_PRIMARY = createUniqueKey(Friends.FRIENDS, "KEY_friends_PRIMARY", Friends.FRIENDS.ID);
        public static final UniqueKey<Record> KEY_MESSAGE_PRIMARY = createUniqueKey(Message.MESSAGE, "KEY_message_PRIMARY", Message.MESSAGE.ID);
    }

    private static class ForeignKeys0 extends AbstractKeys {
        public static final ForeignKey<Record, Record> FK_CITY_TO_COUNTRY = createForeignKey(com.speaker.db.jooq.generated.Keys.KEY_COUNTRY_PRIMARY, City.CITY, "fk_city_to_country", City.CITY.COUNTRY_ID);
        public static final ForeignKey<Record, Record> FK_FRIENDS_TO_ACCOUNT = createForeignKey(com.speaker.db.jooq.generated.Keys.KEY_ACCOUNT_PRIMARY, Friends.FRIENDS, "fk_friends_to_account", Friends.FRIENDS.ACCOUNT_ID);
    }
}
