package de.digitalfrontiers.springboot.mina.autoconfigure;

import org.springframework.beans.factory.annotation.Value;

import java.lang.annotation.*;

/**
 * Annotation at the field or method/constructor parameter level that injects the port of the SSHD.
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.METHOD,
    ElementType.PARAMETER, ElementType.ANNOTATION_TYPE})
@Value("${mina.sshd.port}")
public @interface SshdPort {

}
