package sk.fischio.wikistreamkafka.service.api;

/**
 * Service interface for validating domain objects. This interface defines methods for validating
 * objects against their defined constraints.
 */
public interface ValidationService {

  /**
   * Validates an object against its defined constraints.
   *
   * @param object The object to validate
   * @param <T> The type of the object
   * @return true if the object is valid, false otherwise
   */
  <T> boolean isValid(T object);

  /**
   * Validates an object and throws an exception if it's invalid.
   *
   * @param object The object to validate
   * @param <T> The type of the object
   * @throws IllegalArgumentException if the object is invalid
   */
  <T> void validate(T object);
}
