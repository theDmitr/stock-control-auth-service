package dmitr.stockcontrol.authservice.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import dmitr.stockcontrol.authservice.dto.auth.AuthUserDto;
import dmitr.stockcontrol.authservice.exception.base.CommonException;
import dmitr.stockcontrol.authservice.exception.handler.common.response.CommonErrorResponse;
import dmitr.stockcontrol.authservice.helper.HttpExceptionHelper;
import dmitr.stockcontrol.authservice.service.face.auth.AuthUserTokenExtractorService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter {

    private final HttpExceptionHelper httpExceptionHelper;
    private final AuthUserTokenExtractorService authUserTokenExtractorService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String accessToken = extractAccessToken(request);

        if (accessToken != null) {
            try {
                AuthUserDto authUser = authUserTokenExtractorService.getAuthUserFromAccessToken(accessToken);
                Authentication authentication = new UsernamePasswordAuthenticationToken(authUser, null, authUser.getAuthorities());
                SecurityContextHolder.getContext().setAuthentication(authentication);
            } catch (CommonException e) {
                handleCommonException(e, response);
                return;
            }
        }

        filterChain.doFilter(request, response);
    }

    private String extractAccessToken(HttpServletRequest request) {
        String authorizationHeader = request.getHeader("Authorization");
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            return authorizationHeader.substring(7);
        }
        return null;
    }

    private void handleCommonException(CommonException e, HttpServletResponse response) throws IOException {
        HttpStatus httpStatus = HttpStatus.UNAUTHORIZED;
        CommonErrorResponse errorResponse = httpExceptionHelper.generateErrorResponse(e, httpStatus);
        response.setStatus(httpStatus.value());
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(new ObjectMapper().writeValueAsString(errorResponse));
    }
}
