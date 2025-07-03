//package com.busmatelk.backend.controller;
//import com.busmatelk.backend.config.JwtUtil;
//import jakarta.servlet.FilterChain;
//import jakarta.servlet.ServletException;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
//import org.springframework.stereotype.Component;
//import org.springframework.web.filter.OncePerRequestFilter;
//
//import java.io.IOException;
//
//@Component
//public class AuthController extends OncePerRequestFilter {
//
//    private final JwtUtil jwtUtil;
//
//    public AuthController(JwtUtil jwtUtil) {
//        this.jwtUtil = jwtUtil;
//    }
//
//    @Override
//    protected void doFilterInternal(HttpServletRequest request,
//                                    HttpServletResponse response,
//                                    FilterChain filterChain) throws ServletException, IOException {
//
//        String authHeader = request.getHeader("Authorization");
//
//        if (authHeader != null && authHeader.startsWith("Bearer ")) {
//            try {
//                String token = authHeader.substring(7);
//                var claims = jwtUtil.validateToken(token);
//                String userId = claims.getSubject(); // Supabase stores user_id in "sub"
//
//                // Here you can set the authentication context (optional)
//                //var auth = new SupabaseAuthentication(userId);
//                //SecurityContextHolder.getContext().setAuthentication(auth);
//
//            } catch (Exception e) {
//                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid Token");
//                return;
//            }
//        }
//
//        filterChain.doFilter(request, response);
//    }
//
//}
