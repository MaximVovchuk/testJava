package com.teamvoy.testJava.config;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.teamvoy.testJava.services.AuthService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.tomcat.util.http.parser.Authorization;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@Component
public class JWTFilter extends OncePerRequestFilter {

    private final JWTUtil jwtUtil;
    private final AuthService authService;

    public JWTFilter(JWTUtil jwtUtil, AuthService authService) {
        this.jwtUtil = jwtUtil;
        this.authService = authService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse
            , FilterChain filterChain) throws ServletException, IOException {
        String authHeader = httpServletRequest.getHeader(HttpHeaders.AUTHORIZATION);
        System.out.println(authHeader);
        String requestURI = httpServletRequest.getRequestURI();
        System.out.println(requestURI);
        List<String> list = List.of("/swagger-ui.html", "/swagger-ui/index.html", "/v3/api-docs", "/favicon.ico", "/v2/api-docs", "/webjars/**", "/swagger-resources/**","/test/**");
        if (list.contains(requestURI)) {
            filterChain.doFilter(httpServletRequest, httpServletResponse);
            return;
        }

        if (authHeader != null && !authHeader.isBlank() && authHeader.startsWith("Bearer ")) {
            String jwt = authHeader.substring(7);

            if (jwt.isBlank()) {
                httpServletResponse.sendError(HttpServletResponse.SC_BAD_REQUEST,
                        "Invalid JWT Token in Bearer Header");
            } else {
                try {
                    String email = jwtUtil.validateTokenAndRetrieveEmail(jwt);
                    UserDetails userDetails = authService.loadUserByUsername(email);//staged on username, can`t change

                    UsernamePasswordAuthenticationToken authToken =
                            new UsernamePasswordAuthenticationToken(userDetails,
                                    userDetails.getPassword(),
                                    userDetails.getAuthorities());

                    if (SecurityContextHolder.getContext().getAuthentication() == null) {
                        SecurityContextHolder.getContext().setAuthentication(authToken);
                    }
                } catch (JWTVerificationException e) {
                    httpServletResponse.sendError(HttpServletResponse.SC_BAD_REQUEST,
                            "Invalid JWT Token");
                }
            }
        }
        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }
}
