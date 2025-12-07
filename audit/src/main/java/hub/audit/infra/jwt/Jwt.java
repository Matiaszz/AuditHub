package hub.audit.infra.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import hub.audit.domain.user.User;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

@Service
public class Jwt {

    private final String secret = "senhaSuperSecreta123";

    public String generateToken(User user){
        Algorithm algorithm = Algorithm.HMAC256(secret);
        return JWT.create()
                .withSubject(user.getId().toString())
                .withClaim("firstName", user.getFirstName())
                .withClaim("lastName", user.getLastName())
                .withClaim("username", user.getUsername())
                .withClaim("email", user.getEmail())
                .withExpiresAt(Instant.now().plus(2, ChronoUnit.DAYS))
                .sign(algorithm);
    }

    public String verifyToken(String token){
        try{
            Algorithm algorithm = Algorithm.HMAC256(secret);
            return JWT.require(algorithm).build().verify(token).getSubject();
        } catch (Exception e){
            return null;
        }
    }
}
