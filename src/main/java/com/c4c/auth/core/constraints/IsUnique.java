package com.c4c.auth.core.constraints;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import com.c4c.auth.core.constraints.validators.IsUniqueValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

@Constraint(validatedBy = IsUniqueValidator.class)
@Target({
    TYPE, FIELD,
    ANNOTATION_TYPE
})
@Retention(RUNTIME)
@Documented
public @interface IsUnique {
  String message() default "{constraints.is-unique}";

  Class<?>[] groups() default {};

  Class<? extends Payload>[] payload() default {};

  String property();

  String repository();

  IsUniqueValidator.UpdateAction action() default IsUniqueValidator.UpdateAction.INSERT;

  @Target({
      TYPE, FIELD,
      ANNOTATION_TYPE
  })
  @Retention(RUNTIME)
  @Documented
  @interface List {
    IsUnique[] value();
  }
}
