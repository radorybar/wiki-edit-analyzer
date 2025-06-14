package com.example.wikistreamkafka.service;

import com.example.wikistreamkafka.model.WikiEvent;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import jakarta.validation.Validation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ValidationServiceTest {

    @Mock
    private Validator validator;

    @InjectMocks
    private ValidationService validationService;

    private WikiEvent validWikiEvent;
    private WikiEvent invalidWikiEvent;

    @BeforeEach
    void setUp() {
        // Create a valid WikiEvent
        validWikiEvent = new WikiEvent();
        validWikiEvent.setId("123");
        validWikiEvent.setType("edit");
        validWikiEvent.setTitle("Test Page");
        validWikiEvent.setTimestamp(System.currentTimeMillis());
        validWikiEvent.setWiki("enwiki");
        
        WikiEvent.Meta meta = new WikiEvent.Meta();
        meta.setUri("https://en.wikipedia.org/wiki/Test_Page");
        meta.setDomain("en.wikipedia.org");
        meta.setStream("mediawiki.recentchange");
        meta.setTopic("eqiad.mediawiki.recentchange");
        validWikiEvent.setMeta(meta);
        
        // Create an invalid WikiEvent (missing required fields)
        invalidWikiEvent = new WikiEvent();
        invalidWikiEvent.setId("123");
        // Missing type, title, timestamp, wiki, meta
    }

    @Test
    void isValid_withValidObject_returnsTrue() {
        // Arrange
        when(validator.validate(validWikiEvent)).thenReturn(java.util.Collections.emptySet());
        
        // Act
        boolean result = validationService.isValid(validWikiEvent);
        
        // Assert
        assertTrue(result);
        verify(validator).validate(validWikiEvent);
    }
    
    @Test
    void isValid_withInvalidObject_returnsFalse() {
        // Arrange
        when(validator.validate(invalidWikiEvent)).thenReturn(
            java.util.Set.of(mock(jakarta.validation.ConstraintViolation.class))
        );
        
        // Act
        boolean result = validationService.isValid(invalidWikiEvent);
        
        // Assert
        assertFalse(result);
        verify(validator).validate(invalidWikiEvent);
    }
    
    @Test
    void isValid_withNullObject_returnsFalse() {
        // Act
        boolean result = validationService.isValid(null);
        
        // Assert
        assertFalse(result);
        verify(validator, never()).validate(any());
    }
    
    @Test
    void validate_withValidObject_doesNotThrowException() {
        // Arrange
        when(validator.validate(validWikiEvent)).thenReturn(java.util.Collections.emptySet());
        
        // Act & Assert
        assertDoesNotThrow(() -> validationService.validate(validWikiEvent));
        verify(validator).validate(validWikiEvent);
    }
    
    @Test
    void validate_withInvalidObject_throwsIllegalArgumentException() {
        // Arrange
        jakarta.validation.ConstraintViolation<WikiEvent> violation = mock(jakarta.validation.ConstraintViolation.class);
        when(violation.getPropertyPath()).thenReturn(mock(jakarta.validation.Path.class));
        when(violation.getMessage()).thenReturn("must not be blank");
        
        when(validator.validate(invalidWikiEvent)).thenReturn(java.util.Set.of(violation));
        
        // Act & Assert
        IllegalArgumentException exception = assertThrows(
            IllegalArgumentException.class,
            () -> validationService.validate(invalidWikiEvent)
        );
        
        assertTrue(exception.getMessage().contains("Validation failed"));
        verify(validator).validate(invalidWikiEvent);
    }
    
    @Test
    void validate_withNullObject_throwsIllegalArgumentException() {
        // Act & Assert
        IllegalArgumentException exception = assertThrows(
            IllegalArgumentException.class,
            () -> validationService.validate(null)
        );
        
        assertEquals("Object cannot be null", exception.getMessage());
        verify(validator, never()).validate(any());
    }
}