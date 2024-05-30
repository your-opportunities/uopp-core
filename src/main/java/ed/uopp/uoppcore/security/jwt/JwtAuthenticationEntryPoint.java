package ed.uopp.uoppcore.security.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import ed.uopp.uoppcore.security.constant.SecurityConstant;
import ed.uopp.uoppcore.security.data.HttpErrorResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.Http403ForbiddenEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.OutputStream;

import static org.springframework.http.HttpStatus.FORBIDDEN;
import static org.springframework.http.HttpStatus.UNAUTHORIZED;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Component
public class JwtAuthenticationEntryPoint extends Http403ForbiddenEntryPoint {

    public static final String UNAUTHORIZED_MESSAGE = "Invalid username or password";

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception)
            throws IOException {
        HttpErrorResponse httpErrorResponse;
        int httpStatus = FORBIDDEN.value();

        if (exception instanceof BadCredentialsException) {
            httpErrorResponse = new HttpErrorResponse(UNAUTHORIZED.value(),
                    UNAUTHORIZED,
                    UNAUTHORIZED.getReasonPhrase().toUpperCase(),
                    UNAUTHORIZED_MESSAGE);
            httpStatus = UNAUTHORIZED.value();
        } else {
            httpErrorResponse = new HttpErrorResponse(FORBIDDEN.value(),
                    FORBIDDEN,
                    FORBIDDEN.getReasonPhrase().toUpperCase(),
                    SecurityConstant.FORBIDDEN_MESSAGE);
            httpStatus = FORBIDDEN.value();
        }

        response.setContentType(APPLICATION_JSON_VALUE);
        response.setStatus(httpStatus);
        OutputStream outputStream = response.getOutputStream();
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.writeValue(outputStream, httpErrorResponse);
        outputStream.flush();
    }

}
