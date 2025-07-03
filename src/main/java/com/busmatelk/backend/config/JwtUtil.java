//package com.busmatelk.backend.config;
//import io.jsonwebtoken.Claims;
//import io.jsonwebtoken.Jwts;
//import org.springframework.stereotype.Component;
//
//@Component
//public class JwtUtil {
//    private final String SUPABASE_JWT_SECRET = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6ImdjamtoaXF5dnpncXZpanRvb2hjIiwicm9sZSI6ImFub24iLCJpYXQiOjE3NTA5OTY1NTcsImV4cCI6MjA2NjU3MjU1N30.lexsIeZTg2VppM-7k7ro0HwWy8c5ZfDIMeVuHfhlC_8"; // Get this from Supabase settings
//
//    public Claims validateToken(String token) {
//        return Jwts.parserBuilder()
//                .setSigningKey(SUPABASE_JWT_SECRET.getBytes())
//                .build()
//                .parseClaimsJws(token)
//                .getBody();
//    }
//
//}
