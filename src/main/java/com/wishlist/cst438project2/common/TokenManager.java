package com.wishlist.cst438project2.common;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wishlist.cst438project2.document.User;
import com.wishlist.cst438project2.dto.UserTokenDTO;
import com.wishlist.cst438project2.exception.UnauthorizedException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.security.Key;
import java.util.Date;

@Component
@Slf4j
public class TokenManager {

    /**
     * This method is used to generate a access token for users when they sign-in
     * @param user, to store some of the user information in the token.
     * @return jwt token
     */
    public String issueToken(User user) {

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

        JwtBuilder builder = Jwts.builder().claim("user", userTokenDTO).setId(userTokenDTO.getUserId()).setIssuedAt(now);

        return builder.signWith(signatureAlgorithm, signingKey).compact();
    }

    /**
     * This method is used to validate accessToken and fetch user details
     * @param accessToken
     * @return user info
     */
    public UserTokenDTO getUser(String accessToken) {

        Claims claims = Jwts.parser().setSigningKey(DatatypeConverter.parseBase64Binary(Constants.KEY_SECRET_TOKEN))
                .parseClaimsJws(accessToken).getBody();

        UserTokenDTO userTokenDTO;

        if(claims.containsKey("user")) {

            ObjectMapper objectMapper = new ObjectMapper();
            userTokenDTO = objectMapper.convertValue(claims.get("user"), UserTokenDTO.class);

            //TODO: Need to verify the token against userId from DB
        } else {
            log.warn("User token invalid or expired!");
            throw new UnauthorizedException();
        }

        return userTokenDTO;
    }
}
