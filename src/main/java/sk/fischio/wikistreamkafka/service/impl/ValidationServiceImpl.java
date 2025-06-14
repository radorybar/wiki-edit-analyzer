package sk.fischio.wikistreamkafka.service.impl;

import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import sk.fischio.wikistreamkafka.service.api.ValidationService;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Implementation of the ValidationService interface. This service is responsible for validating
 * domain objects using the Jakarta Bean Validation API.
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class ValidationServiceImpl implements ValidationService {

  private final Validator validator;

  @Override
  public <T> boolean isValid(T object) {
    if (object == null) {
      log.warn("Validation failed: object is null");
      return false;
    }

    Set<ConstraintViolation<T>> violations = validator.validate(object);
    if (!violations.isEmpty()) {
      String errorMessage =
          violations.stream()
              .map(violation -> violation.getPropertyPath() + ": " + violation.getMessage())
              .collect(Collectors.joining(", "));
      log.warn("Validation failed: {}", errorMessage);
      return false;
    }

    return true;
  }

  @Override
  public <T> void validate(T object) {
    if (object == null) {
      throw new IllegalArgumentException("Object cannot be null");
    }

    Set<ConstraintViolation<T>> violations = validator.validate(object);
    if (!violations.isEmpty()) {
      String errorMessage =
          violations.stream()
              .map(violation -> violation.getPropertyPath() + ": " + violation.getMessage())
              .collect(Collectors.joining(", "));
      throw new IllegalArgumentException("Validation failed: " + errorMessage);
    }
  }
}
