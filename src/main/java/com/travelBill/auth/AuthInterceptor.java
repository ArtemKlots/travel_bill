package com.travelBill.auth;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;
import java.util.Objects;

@Component
public class AuthInterceptor implements HandlerInterceptor {
    private final Algorithm algorithmHS = Algorithm.HMAC256("secret");
    private final JWTVerifier verifier = JWT.require(algorithmHS)
            .acceptExpiresAt(2592000) //30 days
            .build();

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        String authHeader = request.getHeader("Authorization");
        if (!isAuthRequired(request)) return true;
        if (request.getMethod().equals("OPTIONS")) return true;

        if (authHeader != null) {
            String[] chunks = authHeader.split(" ");
            try {
                DecodedJWT jwt = verifier.verify(chunks[1]);
                Map<String, Claim> claims = jwt.getClaims();
                Long userId = claims.get("userId").asLong();
                request.setAttribute("userId", userId);
                return true;
            } catch (Exception e) {
                response.setStatus(401);
                return false;
            }
        } else {
            response.setStatus(401);
            return false;
        }
    }

    private boolean isAuthRequired(HttpServletRequest request) {
        if (Objects.equals(request.getRequestURI(), "/ping")) return false;
        if (Objects.equals(request.getRequestURI(), "/login")) return false;

        return true;
    }
}
