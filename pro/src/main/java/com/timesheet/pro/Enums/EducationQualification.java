package com.timesheet.pro.Enums;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum EducationQualification {
    B_TECH, DEGREE;
    @JsonCreator
    public static EducationQualification fromString(String value) {
        return EducationQualification.valueOf(value.toUpperCase().replace(".", "_"));
    }
}