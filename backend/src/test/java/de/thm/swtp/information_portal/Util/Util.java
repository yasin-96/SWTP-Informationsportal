package de.thm.swtp.information_portal.Util;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.util.Date;
import java.util.UUID;

public class Util {

    public static String generateJWTToken() {
        var jwtTokenPW = "swtp-info-portal-project";
        var claims = Jwts.claims();

        claims = Jwts.claims();
        claims.put("GivenName", "Info");
        claims.put("Surname", "Portal");
        claims.put("Email", "info-portal@example.com");

        var jwtToken = Jwts.builder()
            .setHeaderParam("typ", "JWT")
            .setIssuer("SWTP")
            .setSubject("swtp-info-portal")
            .setClaims(claims)
            .setAudience("SWTP")
            .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24 * 7 * 4 * 365))
            .setNotBefore(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * -24))
            .setIssuedAt(new Date(System.currentTimeMillis() + 1000 * 60 * 60))
            .setId(UUID.randomUUID().toString())


            .signWith(
                    SignatureAlgorithm.HS256,
                    new SecretKeySpec(
                            DatatypeConverter.parseBase64Binary("swtp-info-portal"),
                            (SignatureAlgorithm.HS512).getJcaName()))
            .compact();
        return jwtToken;
    }
}
