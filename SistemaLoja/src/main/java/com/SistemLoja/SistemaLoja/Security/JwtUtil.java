
/* 
package com.SistemLoja.SistemaLoja.Security;

import java.util.Date;
import org.springframework.stereotype.Component;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import java.security.Key;

@Component
public class JwtUtil {

    
    //private final String secreta = "FalaFigas";
    private final Key secreta = Keys.secretKeyFor(SignatureAlgorithm.HS256);

    public String generateToken(String username) {
        
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 5000 * 60 * 60))
                .signWith(SignatureAlgorithm.HS256, secreta) // can be with 512 to 
                .compact();
    }

    public String extractUser(String token){
        return Jwts.parser().setSigningKey(secreta).parseClaimsJws(token).getBody().getSubject();
    }

    public boolean validateToken(String token){
        try{
            Jwts.parser().setSigningKey(secreta).parseClaimsJws(token);
            return true;
        }catch (Exception e){
            return false;
        }
    }
}
*/

package com.SistemLoja.SistemaLoja.Security;

import java.util.Date;
import org.springframework.stereotype.Component;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import java.security.Key;

@Component
public class JwtUtil {

    // Use a fixed secret key for consistency across application restarts
    // In production, this should be loaded from environment variables
    private final String secretString = "MyVerySecretKeyForJWTTokenThatIsLongEnoughForHS256AlgorithmAndShouldBeStoredSecurely";
    private final Key secreta = Keys.hmacShaKeyFor(secretString.getBytes());

    public String generateToken(String username) {
        System.out.println("Generating token for user: " + username);
        
        String token = Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 5)) // 5 hours
                .signWith(secreta, SignatureAlgorithm.HS256)
                .compact();
                
        System.out.println("Generated token: " + token.substring(0, Math.min(token.length(), 20)) + "...");
        return token;
    }

    public String extractUser(String token) {
        try {
            Claims claims = Jwts.parserBuilder()
                    .setSigningKey(secreta)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
            return claims.getSubject();
        } catch (Exception e) {
            System.out.println("Error extracting user from token: " + e.getMessage());
            return null;
        }
    }

    public boolean validateToken(String token) {
        try {
            Claims claims = Jwts.parserBuilder()
                    .setSigningKey(secreta)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
            
            // Check if token is expired
            boolean isExpired = claims.getExpiration().before(new Date());
            if (isExpired) {
                System.out.println("Token is expired");
                return false;
            }
            
            System.out.println("Token validation successful");
            return true;
        } catch (Exception e) {
            System.out.println("Token validation failed: " + e.getMessage());
            return false;
        }
    }
}