package com.example.wikistreamkafka.service;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

/**
 * Service responsible for validating domain objects.
 * This service uses the Jakarta Bean Validation API to validate objects
 * against their defined constraints.
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class ValidationService {

    private final Validator validator;

    /**
     * Validates an object against its defined constraints.
     *
     * @param object The object to validate
     * @param <T> The type of the object
     * @return true if the object is valid, false otherwise
     */
    public <T> boolean isValid(T object) {
        if (object == null) {
            log.warn("Validation failed: object is null");
            return false;
        }

        Set<ConstraintViolation<T>> violations = validator.validate(object);
        if (!violations.isEmpty()) {
            String errorMessage = violations.stream()
                    .map(violation -> violation.getPropertyPath() + ": " + violation.getMessage())
                    .collect(Collectors.joining(", "));
            log.warn("Validation failed: {}", errorMessage);
            return false;
        }

        return true;
    }

    /**
     * Validates an object and throws an exception if it's invalid.
     *
     * @param object The object to validate
     * @param <T> The type of the object
     * @throws IllegalArgumentException if the object is invalid
     */
    public <T> void validate(T object) {
        if (object == null) {
            throw new IllegalArgumentException("Object cannot be null");
        }

        Set<ConstraintViolation<T>> violations = validator.validate(object);
        if (!violations.isEmpty()) {
            String errorMessage = violations.stream()
                    .map(violation -> violation.getPropertyPath() + ": " + violation.getMessage())
                    .collect(Collectors.joining(", "));
            throw new IllegalArgumentException("Validation failed: " + errorMessage);
        }
    }
}