package com.borisjerev.leadconsult.enums;

public enum CourseTypes {
    Main, Secondary;

    public static CourseTypes findByName(String name) {
        for (CourseTypes courseTypes : values()) {
            if (courseTypes.name().equals(name)) {
                return courseTypes;
            }
        }
        return null;
    }
}
