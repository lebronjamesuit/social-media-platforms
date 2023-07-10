package com.social.media.confessionmedia.authorizationserver.config;


import com.nimbusds.jwt.JWT;
import com.nimbusds.jwt.JWTParser;
import com.social.media.confessionmedia.authorizationserver.exceptions.TokenException;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.jwt.BadJwtException;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain
    ) throws ServletException, IOException {
        if (request.getServletPath().contains("/api/auth")) {
            filterChain.doFilter(request, response);
            return;
        }
        final String authHeader = request.getHeader("Authorization");
        final String jwtValue;
        String userName = "";
        if (authHeader == null ||!authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }
        jwtValue = authHeader.substring(7);

        try {
            JWT jwtNimbus = JWTParser.parse(jwtValue);
            boolean isExpiredAccessToken = jwtNimbus.getJWTClaimsSet().getExpirationTime().before(new Date());
            if(isExpiredAccessToken) {
                throw new TokenException(" Access Token is expired since:" + jwtNimbus.getJWTClaimsSet().getExpirationTime());
            }
            userName =  jwtNimbus.getJWTClaimsSet().getSubject();
        } catch (Exception var3) {
            throw new TokenException( new BadJwtException(var3.getMessage()) ," Access Token is expired since:");
        }

        if ( !userName.isEmpty() && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = this.userDetailsService.loadUserByUsername(userName);
            UsernamePasswordAuthenticationToken authToken =
                    new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
            authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(authToken);
        }
        // End filter
        filterChain.doFilter(request, response);
    }

}

