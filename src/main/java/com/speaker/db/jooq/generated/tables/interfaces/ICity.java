/*
 * This file is generated by jOOQ.
*/
package com.speaker.db.jooq.generated.tables.interfaces;


import java.io.Serializable;

import javax.annotation.Generated;


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
public interface ICity extends Serializable {

    /**
     * Setter for <code>speaker.city.id</code>.
     */
    public ICity setId(Integer value);

    /**
     * Getter for <code>speaker.city.id</code>.
     */
    public Integer getId();

    /**
     * Setter for <code>speaker.city.name</code>.
     */
    public ICity setName(String value);

    /**
     * Getter for <code>speaker.city.name</code>.
     */
    public String getName();

    /**
     * Setter for <code>speaker.city.country_id</code>.
     */
    public ICity setCountryId(Integer value);

    /**
     * Getter for <code>speaker.city.country_id</code>.
     */
    public Integer getCountryId();

    // -------------------------------------------------------------------------
    // FROM and INTO
    // -------------------------------------------------------------------------

    /**
     * Load data from another generated Record/POJO implementing the common interface ICity
     */
    public void from(com.speaker.db.jooq.generated.tables.interfaces.ICity from);

    /**
     * Copy data into another generated Record/POJO implementing the common interface ICity
     */
    public <E extends com.speaker.db.jooq.generated.tables.interfaces.ICity> E into(E into);
}