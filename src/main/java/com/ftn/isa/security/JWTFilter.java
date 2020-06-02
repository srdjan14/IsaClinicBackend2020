package com.ftn.isa.security;

import com.ftn.isa.service.ITokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

public class JWTFilter extends UsernamePasswordAuthenticationFilter {

    private ITokenProvider _tokenProvider;

    private UserDetailsService _userDetailsService;

    @Autowired
    public JWTFilter(ITokenProvider tokenProvider,
                     @Qualifier("SecurityService") UserDetailsService userDetailsService) {
        _tokenProvider = tokenProvider;
        _userDetailsService = userDetailsService;
    }

    /**
     * This method sets authentication on user who doesn't have authentication
     */
    @Override
    public void doFilter(ServletRequest request, ServletResponse response,
                         FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        String authToken = httpRequest.getHeader("Authorization");
        String username = _tokenProvider.getUsernameFromToken(authToken);
        if (username != null
                && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = _userDetailsService
                    .loadUserByUsername(username);
            if (_tokenProvider.validateToken(authToken, userDetails)) {
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities());
                authentication.setDetails(new WebAuthenticationDetailsSource()
                        .buildDetails(httpRequest));
                SecurityContextHolder.getContext().setAuthentication(
                        authentication);
            }
        }

        chain.doFilter(request, response);
    }
}