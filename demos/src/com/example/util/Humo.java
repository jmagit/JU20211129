package com.example.util;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import org.junit.jupiter.api.Tag;

@Target({ TYPE, METHOD })
@Retention(RUNTIME)
@Tag("Smoke")
public @interface Humo { }
