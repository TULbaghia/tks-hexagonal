package pl.lodz.p.it.tks.user.soap.exception;

import org.json.JSONObject;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Provider
public class ConstraintValidationMapper implements ExceptionMapper<ConstraintViolationException> {
    @Override
    public Response toResponse(ConstraintViolationException exception) {
        Set<ConstraintViolation<?>> constraintViolations = exception.getConstraintViolations();
        List<String> errorMessages = constraintViolations.stream()
                .map(x -> JSONObject.wrap(x.getMessage()).toString())
                .collect(Collectors.toList());
        JSONObject jsObj = new JSONObject();
        jsObj.put("constraints", errorMessages);
        return Response
                .status(Response.Status.BAD_REQUEST)
                .type(MediaType.APPLICATION_JSON_TYPE)
                .entity(jsObj.toString())
                .build();
    }
}