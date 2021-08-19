package dev.orion.api.v1.mappers;

import dev.orion.api.v1.mappers.models.DefaultErrorResponse;

import javax.validation.ConstraintViolationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;
import java.text.MessageFormat;
import java.util.logging.Logger;

@Provider
public class ValidationExceptionMapper implements ExceptionMapper<ConstraintViolationException> {

    private static final Logger LOGGER = Logger.getLogger(ValidationExceptionMapper.class.getName());

    @Override
    public Response toResponse(ConstraintViolationException exception) {
        var constraintViolations = exception.getConstraintViolations();
        DefaultErrorResponse defaultErrorResponseDtoV1 = new DefaultErrorResponse();

        if (constraintViolations != null) {
            constraintViolations.forEach(constraint -> defaultErrorResponseDtoV1.addError(constraint.getMessageTemplate()));
            var errors = defaultErrorResponseDtoV1.getErrors();

            var message = MessageFormat.format("Invalid body request with the following errors: {0}", errors);
            LOGGER.warning(message);

            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(defaultErrorResponseDtoV1)
                    .build();
        }

        return null;
    }
}
