/*
 * This file is generated by jOOQ.
*/
package com.speaker.db.jooq.generated;


import com.speaker.db.jooq.generated.tables.Account;
import com.speaker.db.jooq.generated.tables.City;
import com.speaker.db.jooq.generated.tables.Country;
import com.speaker.db.jooq.generated.tables.Friends;
import com.speaker.db.jooq.generated.tables.Message;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.annotation.Generated;

import org.jooq.Catalog;
import org.jooq.Table;
import org.jooq.impl.SchemaImpl;


/**
 * This class is generated by jOOQ.
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.9.1"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class Speaker extends SchemaImpl {

    private static final long serialVersionUID = -1786459021;

    /**
     * The reference instance of <code>speaker</code>
     */
    public static final Speaker SPEAKER = new Speaker();

    /**
     * The table <code>speaker.account</code>.
     */
    public final Account ACCOUNT = com.speaker.db.jooq.generated.tables.Account.ACCOUNT;

    /**
     * The table <code>speaker.city</code>.
     */
    public final City CITY = com.speaker.db.jooq.generated.tables.City.CITY;

    /**
     * The table <code>speaker.country</code>.
     */
    public final Country COUNTRY = com.speaker.db.jooq.generated.tables.Country.COUNTRY;

    /**
     * The table <code>speaker.friends</code>.
     */
    public final Friends FRIENDS = com.speaker.db.jooq.generated.tables.Friends.FRIENDS;

    /**
     * The table <code>speaker.message</code>.
     */
    public final Message MESSAGE = com.speaker.db.jooq.generated.tables.Message.MESSAGE;

    /**
     * No further instances allowed
     */
    private Speaker() {
        super("speaker", null);
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public Catalog getCatalog() {
        return DefaultCatalog.DEFAULT_CATALOG;
    }

    @Override
    public final List<Table<?>> getTables() {
        List result = new ArrayList();
        result.addAll(getTables0());
        return result;
    }

    private final List<Table<?>> getTables0() {
        return Arrays.<Table<?>>asList(
            Account.ACCOUNT,
            City.CITY,
            Country.COUNTRY,
            Friends.FRIENDS,
            Message.MESSAGE);
    }
}
