package com.wishlist.cst438project2.common;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wishlist.cst438project2.document.AccessToken;
import com.wishlist.cst438project2.document.User;
import com.wishlist.cst438project2.dto.UserTokenDTO;
import com.wishlist.cst438project2.exception.UnauthorizedException;
import com.wishlist.cst438project2.integration.FirebaseIntegration;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Component;

import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.security.Key;
import java.util.Date;
import java.util.Objects;

@Component
@Slf4j
@DependsOn("firebaseConfig")
public class TokenManager {

    @Autowired
    private FirebaseIntegration firebaseIntegration;

    /**
     * This method is used to generate token and save token on firebase
     * @param user, for which token needs to be generated
     * @return jwt token
     */
    public String generateToken(User user) {

        String token = issueToken(user);

        if(Objects.nonNull(token) && !token.isEmpty()) {
            firebaseIntegration.saveAccessToken(new AccessToken(user.getUserId(), token));
            return token;
        } else
            return null;
    }

    /**
     * This method is used to validate accessToken and fetch user details
     * @param accessToken
     * @return user info
     */
    public UserTokenDTO getUser(String accessToken) {

        try {

            Claims claims = Jwts.parser().setSigningKey(DatatypeConverter.parseBase64Binary(Constants.KEY_SECRET_TOKEN))
                    .parseClaimsJws(accessToken).getBody();

            UserTokenDTO userTokenDTO;

            if (claims.containsKey("user")) {

                ObjectMapper objectMapper = new ObjectMapper();
                userTokenDTO = objectMapper.convertValue(claims.get("user"), UserTokenDTO.class);

                AccessToken token = firebaseIntegration.fetchAccessToken(accessToken);
                if(Objects.isNull(token))
                    throw new UnauthorizedException();

            } else {
                log.warn("User token invalid or expired!");
                throw new UnauthorizedException();
            }

            return userTokenDTO;
        } catch (Exception ex) {
            log.error(ex.getMessage());
            throw new UnauthorizedException();
        }
    }

    /**
     * This method is used to generate a access token for users when they sign-in
     * @param user, to store some of the user information in the token.
     * @return jwt token
     */
    private String issueToken(User user) {

        // The JWT signature algorithm we will be using to sign the token
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;

        byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary(Constants.KEY_SECRET_TOKEN);
        Key signingKey = new SecretKeySpec(apiKeySecretBytes, signatureAlgorithm.getJcaName());

        long nowMillis = System.currentTimeMillis();
        Date now = new Date(nowMillis);

        UserTokenDTO userTokenDTO = new UserTokenDTO();
        userTokenDTO.setUserId(user.getUserId());
        userTokenDTO.setEmailId(user.getEmailId());
        userTokenDTO.setUsername(user.getUsername());
        userTokenDTO.setRole(user.getRole().getValue());

        JwtBuilder builder = Jwts.builder().claim("user", userTokenDTO).setId(userTokenDTO.getUsername()).setIssuedAt(now);

        return builder.signWith(signatureAlgorithm, signingKey).compact();
    }
}
