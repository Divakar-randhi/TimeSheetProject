package com.timesheet.pro.Enums;
import com.fasterxml.jackson.annotation.JsonCreator;

public enum Relationship {
    FATHER, MOTHER, SISTER, BROTHER, SPOUSE;

    @JsonCreator
    public static Relationship fromString(String value) {
        return Relationship.valueOf(value.toUpperCase());
    }
}