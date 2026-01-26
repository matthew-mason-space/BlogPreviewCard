package com.mms.blogpreviewcard.advices;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.time.Instant;
import java.time.OffsetDateTime;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.env.Environment;
import org.springframework.core.env.Profiles;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;

import com.mms.blogpreviewcard.controllers.BlogController;
import com.mms.blogpreviewcard.dto.BlogErrorDTO;
import com.mms.blogpreviewcard.exceptions.BlogResourceNotFoundException;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolationException;
import lombok.RequiredArgsConstructor;

@RestControllerAdvice(assignableTypes = {
    BlogController.class
})
@RequiredArgsConstructor
public class BlogAdvice {
  private static final Logger log = LoggerFactory
      .getLogger(BlogAdvice.class);

  private final Environment environment;

  // HttpMediaTypeNotAcceptableException: client Accept header cannot be
  // satisfied.
  // -> response: 406 NOT_ACCEPTABLE
  @ExceptionHandler(exception = {
      HttpMediaTypeNotAcceptableException.class
  })
  public ResponseEntity<Map<String, Object>> handleNotAcceptable(
      HttpMediaTypeNotAcceptableException exception,
      HttpServletRequest request) {

    String details = exception.getSupportedMediaTypes() == null ||
        exception.getSupportedMediaTypes().isEmpty()
            ? "No available representation"
            : "Supported: " + exception.getSupportedMediaTypes();

    return toResponse(
        Map.of("details", details),
        request,
        HttpStatus.NOT_ACCEPTABLE,
        "Not acceptable");
  }

  // ConstraintViolationException: when method-parameter validation fails
  // (e.g. @Positive on id) and method validation is enabled (@Validated).
  // -> response: 400 BAD_REQUEST (Validation failed).
  @ExceptionHandler(exception = {
      ConstraintViolationException.class
  })
  public ResponseEntity<Map<String, Object>> handleConstraintViolation(
      ConstraintViolationException exception,
      HttpServletRequest request) {

    List<String> details = exception
        .getConstraintViolations()
        .stream()
        .map(cv -> cv.getPropertyPath() + ": " + cv.getMessage())
        .collect(Collectors.toList());

    return toResponse(
        Map.of("details", details),
        request,
        HttpStatus.BAD_REQUEST,
        "Validation failed");
  }

  // DataIntegrityViolationException: DB constraint or SQL error during
  // repository access (e.g. unique constraint, foreign key failure).
  // -> response: 409 CONFLICT.
  @ExceptionHandler(exception = {
      DataIntegrityViolationException.class
  })
  public ResponseEntity<Map<String, Object>> handleDataIntegrity(
      DataIntegrityViolationException exception,
      HttpServletRequest request) {

    return toResponse(
        Map.of("detail", exception.getMostSpecificCause().getMessage()),
        request,
        HttpStatus.CONFLICT,
        "Database constraint violation");
  }

  // EmptyResultDataAccessException: not typical for findById; would occur for
  // repository methods that expect a row (e.g. remove/getSingleResult) or
  // delete-by-id semantics.
  // -> response: 404 NOT_FOUND.
  @ExceptionHandler(exception = {
      EmptyResultDataAccessException.class
  })
  public ResponseEntity<Map<String, Object>> handleEmptyResult(
      EmptyResultDataAccessException exception,
      HttpServletRequest request) {

    return toResponse(
        null,
        request,
        HttpStatus.NOT_FOUND,
        "Resource not found");
  }

  // Generic Exception (Exception.class): any other uncaught runtime/checked
  // exception during handling.
  // -> response: 500 INTERNAL_SERVER_ERROR.
  @ExceptionHandler(exception = {
      Exception.class
  })
  public ResponseEntity<Map<String, Object>> handleGeneric(
      Exception exception,
      HttpServletRequest request) {
    log.error(
        "Unhandled error for {}: {}",
        request.getRequestURI(),
        exception.getMessage(),
        exception);

    BlogErrorDTO blogErrorDTO = BlogErrorDTO.builder()
        .error(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase())
        .message("Unexpected error")
        .path(request.getRequestURI())
        .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
        .timestamp(Instant.now())
        .build();

    if (includeTrace(request)) {
      blogErrorDTO.setTrace(this.stackTrace(exception));
    }

    return toResponse(
        Map.of("detail", exception.getMessage()),
        request,
        HttpStatus.INTERNAL_SERVER_ERROR,
        "Internal server error");
  }

  // HttpRequestMethodNotSupportedException: client used wrong HTTP method
  // (e.g. POST on an endpoint that only accepts GET).
  // -> response: 405 METHOD_NOT_ALLOWED.
  @ExceptionHandler(exception = {
      HttpRequestMethodNotSupportedException.class
  })
  public ResponseEntity<Map<String, Object>> handleMethodNotAllowed(
      HttpRequestMethodNotSupportedException exception,
      HttpServletRequest request) {

    return toResponse(
        null,
        request,
        HttpStatus.METHOD_NOT_ALLOWED,
        exception.getMessage());
  }

  // NoHandlerFoundException: when no matching request mapping exists (only if
  // spring.mvc.throw-exception-if-no-handler-found=true).
  // -> response: 404 NOT_FOUND.
  @ExceptionHandler(exception = {
      NoHandlerFoundException.class
  })
  public ResponseEntity<Map<String, Object>> handleNoHandler(
      NoHandlerFoundException exception,
      HttpServletRequest request) {

    return toResponse(
        null,
        request,
        HttpStatus.NOT_FOUND,
        "Not Found");
  }

  // BlogResourceNotFoundException: thrown by blogService.getById(id) when
  // findById returns empty (you explicitly throw this).
  // -> response: 404 NOT_FOUND (Resource not found message).
  @ExceptionHandler(exception = {
      BlogResourceNotFoundException.class
  })
  public ResponseEntity<BlogErrorDTO> handleNotFound(
      BlogResourceNotFoundException exception,
      HttpServletRequest request) {

    log.info(
        "Not found: {} - {}",
        request.getRequestURI(),
        exception.getMessage());

    BlogErrorDTO blogErrorDTO = BlogErrorDTO.builder()
        .error(HttpStatus.NOT_FOUND.getReasonPhrase())
        .message(exception.getMessage())
        .path(request.getRequestURI())
        .status(HttpStatus.NOT_FOUND.value())
        .timestamp(Instant.now())
        .build();

    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(blogErrorDTO);
  }

  // MethodArgumentTypeMismatchException / NumberFormatException: when path
  // variable cannot be converted to Long (non-numeric) or is out-of-range
  // (too big) -> conversion throws NumberFormatException, wrapped as
  // MethodArgumentTypeMismatchException.
  // -> response: 400 BAD_REQUEST (Invalid numeric value).
  @ExceptionHandler(exception = {
      MethodArgumentTypeMismatchException.class,
      NumberFormatException.class
  })
  public ResponseEntity<Map<String, Object>> handleTypeMismatch(
      Exception exception,
      HttpServletRequest request) {

    String message = exception instanceof MethodArgumentTypeMismatchException
        ? String.format("Invalid value '%s' for parameter '%s'",
            ((MethodArgumentTypeMismatchException) exception).getValue(),
            ((MethodArgumentTypeMismatchException) exception).getName())
        : "Invalid numeric value";

    return toResponse(
        null,
        request,
        HttpStatus.BAD_REQUEST,
        message);
  }

  // MethodArgumentNotValidException: triggered when @Valid on a request body or
  // @ModelAttribute fails (DTO field constraints like @NotBlank, @Size, etc.).
  // Typical cases:
  // - POST/PUT with invalid/missing JSON fields validated by jakarta.validation.
  // - Binding errors where specific fields have validation messages.
  // Not triggered for @PathVariable/@RequestParam method-parameter validation
  // (those produce ConstraintViolationException or
  // MethodArgumentTypeMismatchException).
  // -> response: 400 BAD_REQUEST with a JSON "errors" list containing "field:
  // message".
  @ExceptionHandler(exception = {
      MethodArgumentNotValidException.class
  })
  public ResponseEntity<BlogErrorDTO> handleValidation(
      MethodArgumentNotValidException exception,
      HttpServletRequest request) {

    String message = exception
        .getBindingResult()
        .getFieldErrors()
        .stream()
        .map(f -> f.getField() + ": " + f.getDefaultMessage())
        .collect(Collectors.joining("; "));

    log.info(
        "Validation failed for {}: {}",
        request.getRequestURI(),
        message);

    BlogErrorDTO blogErrorDTO = BlogErrorDTO
        .builder()
        .error(HttpStatus.BAD_REQUEST.getReasonPhrase())
        .message(message)
        .path(request.getRequestURI())
        .status(HttpStatus.BAD_REQUEST.value())
        .timestamp(Instant.now())
        .build();

    return ResponseEntity
        .badRequest()
        .body(blogErrorDTO);
  }

  // HttpMessageNotReadableException: when request body is missing or malformed
  // (e.g. invalid JSON). Not applicable for simple GET without body, but useful
  // for other endpoints that consume JSON.
  // -> response: 400 BAD_REQUEST (Malformed request body).
  @ExceptionHandler(exception = {
      HttpMessageNotReadableException.class
  })
  public ResponseEntity<Map<String, Object>> handleUnreadable(
      HttpMessageNotReadableException exception,
      HttpServletRequest request) {
    return toResponse(
        Map.of("detail", exception.getMostSpecificCause().getMessage()),
        request,
        HttpStatus.BAD_REQUEST,
        "Malformed request body");
  }

  // HttpMediaTypeNotSupportedException: client sent Content-Type not supported
  // by the endpoint
  // -> response: 415 UNSUPPORTED_MEDIA_TYPE
  @ExceptionHandler(exception = {
      HttpMediaTypeNotSupportedException.class
  })
  public ResponseEntity<Map<String, Object>> handleUnsupportedMediaType(
      HttpMediaTypeNotSupportedException exception,
      HttpServletRequest request) {

    String detail = exception.getSupportedMediaTypes() == null ||
        exception.getSupportedMediaTypes().isEmpty()
            ? "No supported media types"
            : "Supported: " + exception.getSupportedMediaTypes();

    return toResponse(
        Map.of("detail", detail),
        request,
        HttpStatus.UNSUPPORTED_MEDIA_TYPE,
        "Unsupported media type");
  }

  private boolean includeTrace(HttpServletRequest request) {
    boolean devProfile = environment
        .acceptsProfiles(Profiles.of("dev"));

    boolean traceParam = "true"
        .equalsIgnoreCase(request.getParameter("trace"));

    return devProfile || traceParam;
  }

  private String stackTrace(Throwable throwable) {
    StringWriter stringWriter = new StringWriter();
    throwable.printStackTrace(new PrintWriter(stringWriter));
    return stringWriter.toString();
  }

  private ResponseEntity<Map<String, Object>> toResponse(
      Map<String, Object> errors,
      HttpServletRequest request,
      HttpStatus status,
      String message) {
    Map<String, Object> body = new LinkedHashMap<>();
    if (errors != null) {
      body.put("error", errors);
    } else {
      body.put("error", status.getReasonPhrase());
    }
    body.put("message", message);
    body.put("path", request.getRequestURI());
    body.put("status", status.value());
    body.put("timestamp", OffsetDateTime.now());

    return ResponseEntity
        .status(status)
        .headers((header) -> {
          header.setContentType(MediaType.APPLICATION_JSON);
        })
        .body(body);
  }
}
