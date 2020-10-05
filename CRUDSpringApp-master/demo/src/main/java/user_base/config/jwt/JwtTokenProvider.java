package user_base.config.jwt;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import user_base.config.AppProperties;

import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.function.Function;
import java.util.stream.Collectors;


@RequiredArgsConstructor
@Component
public class JwtTokenProvider {

    private final AppProperties appProperties;

    public String generateToken(Authentication authentication){
        String authorities = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));

        Date now = new Date();
        Date validity = new Date(now.getTime() + appProperties.getTokenValidity());
        return Jwts.builder()
                .setSubject(authentication.getName())
                .claim(appProperties.getAuthoritiesKey(), authorities)
                .signWith(SignatureAlgorithm.HS256 , appProperties.getKey())
                .setIssuedAt(now)
                .setExpiration(validity)
                .compact();
    }

    private Claims getAllClaimsFromToken(String token){
        return Jwts.parser()
                .setSigningKey(appProperties.getKey())
                .parseClaimsJws(token)
                .getBody();
    }

    public <T> T getClaimsFromToken(String token, Function<Claims, T> claimsResolver){
        Claims claims = getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }

    public String getUsernameFromToken(String token){
        return getClaimsFromToken(token, Claims::getSubject);
    }

    public Date getExpirationDateFromToken(String token){
        return getClaimsFromToken(token, Claims::getExpiration);
    }

    public Boolean isTokenExpired(String token){
        Date expiration = getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }

    public Boolean validateToken(String token, UserDetails userDetails){
        String username = getUsernameFromToken(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    public UsernamePasswordAuthenticationToken getAuthentication(
            String token, Authentication authentication,UserDetails userDetails
    ){
        JwtParser jwtParser = Jwts.parser().setSigningKey(appProperties.getKey());
        Jws<Claims> claimsJws = jwtParser.parseClaimsJws(token); // ROLE_ADMIN , ROLE_USER
        Claims claims = claimsJws.getBody();

        Collection<? extends  GrantedAuthority> authorities =
                Arrays.stream(claims.get(appProperties.getAuthoritiesKey()).toString().split(","))
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());

        return new UsernamePasswordAuthenticationToken(userDetails, "", authorities);
    }
}
