package com.amrit.util;

import org.springframework.context.annotation.Configuration;

@Configuration
public class Constants {

    public static final String EMAIL_REGEX = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";

    public static final String MOBILE_REGEX = "^(\\+?\\d{1,3})?[-.\\s]?(\\d{10})$";
}
