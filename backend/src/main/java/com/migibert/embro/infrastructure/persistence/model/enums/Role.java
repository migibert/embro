/*
 * This file is generated by jOOQ.
 */
package com.migibert.embro.infrastructure.persistence.model.enums;


import com.migibert.embro.infrastructure.persistence.model.Public;

import org.jooq.Catalog;
import org.jooq.EnumType;
import org.jooq.Schema;


/**
 * This class is generated by jOOQ.
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes", "this-escape" })
public enum Role implements EnumType {

    OWNER("OWNER"),

    EDITOR("EDITOR"),

    VIEWER("VIEWER");

    private final String literal;

    private Role(String literal) {
        this.literal = literal;
    }

    @Override
    public Catalog getCatalog() {
        return getSchema().getCatalog();
    }

    @Override
    public Schema getSchema() {
        return Public.PUBLIC;
    }

    @Override
    public String getName() {
        return "role";
    }

    @Override
    public String getLiteral() {
        return literal;
    }

    /**
     * Lookup a value of this EnumType by its literal. Returns
     * <code>null</code>, if no such value could be found, see {@link
     * EnumType#lookupLiteral(Class, String)}.
     */
    public static Role lookupLiteral(String literal) {
        return EnumType.lookupLiteral(Role.class, literal);
    }
}
