package com.smartfitai.config;

import com.smartfitai.services.JwtService;
import javax.annotation.Priority;
import javax.inject.Inject;
import javax.ws.rs.Priorities;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;
import java.io.IOException;

// jwt authentication filter for secured endpoints
@Provider
@Secured
@Priority(Priorities.AUTHENTICATION)
public class JwtAuthenticationFilter implements ContainerRequestFilter {

    @Inject
    private JwtService jwtService;

    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {
        // grab the auth header from the request
        String authorizationHeader = requestContext.getHeaderString(HttpHeaders.AUTHORIZATION);

        // check if the auth header is valid
        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            abortWithUnauthorized(requestContext, "Missing or invalid Authorization header");
            return;
        }

        // pull the token out of the auth header
        String token = authorizationHeader.substring("Bearer".length()).trim();

        try {
            // check if the token is good
            if (!jwtService.validateToken(token)) {
                abortWithUnauthorized(requestContext, "Invalid JWT token");
                return;
            }

            // see if the token is expired
            if (jwtService.isTokenExpired(token)) {
                abortWithUnauthorized(requestContext, "JWT token has expired");
                return;
            }

            // pull user info and add to context
            Long userId = jwtService.getUserIdFromToken(token);
            String email = jwtService.getEmailFromToken(token);
            
            // put user info in the request context for endpoints
            requestContext.setProperty("userId", userId);
            requestContext.setProperty("email", email);

        } catch (Exception e) {
            abortWithUnauthorized(requestContext, "Error processing JWT token: " + e.getMessage());
        }
    }

    private void abortWithUnauthorized(ContainerRequestContext requestContext, String message) {
        requestContext.abortWith(
            Response.status(Response.Status.UNAUTHORIZED)
                .entity("{\"error\": \"" + message + "\"}")
                .build()
        );
    }
}
