package ru.astrosoup.weblab3.services.security;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;
import jakarta.json.JsonWriter;

import ru.astrosoup.weblab3.entities.authorisation.UserEntity;
import ru.astrosoup.weblab3.exceptions.InvalidJwtException;
import ru.astrosoup.weblab3.filters.AuthorisationFilter;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.StringReader;
import java.io.StringWriter;
import java.nio.charset.StandardCharsets;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;


@ApplicationScoped
public class JwtService {

    public enum TokenLiveSpan {
        SHORT,
        LONG;

        public long getTtl() {
            return switch (this) {
                case SHORT -> (15 * 60);
                case LONG -> (7 * 24 * 60 * 60);
            };
        }
    }

    private Set<String> revokedJti;

    public JwtService() {
        this.revokedJti = new HashSet<>();
    }

    public String generateToken(UserEntity user, TokenLiveSpan span) throws Exception {
        JsonObject header = Json.createObjectBuilder()
                .add("alg", "RS256")
                .add("typ", "JWT")
                .build();

        long currentTimeInSecs = System.currentTimeMillis() / 1000;
        long expirationTime = currentTimeInSecs + span.getTtl();
        String jti = UUID.randomUUID().toString();
        while (revokedJti.contains(jti)) {
            jti = UUID.randomUUID().toString();
        };

        JsonObject claims = Json.createObjectBuilder()
                .add("iss", "weblab3")
                .add("sub", span.toString())
                .add("upn", user.getId())
                .add("group", "user")
                .add("iat", currentTimeInSecs)
                .add("exp", expirationTime)
                .add("jti", jti)
                .build();

        Base64.Encoder encoder = Base64.getUrlEncoder().withoutPadding();
        String encodedHeader = encoder.encodeToString(jsonToString(header).getBytes(StandardCharsets.UTF_8));
        String encodedPayload = encoder.encodeToString(jsonToString(claims).getBytes(StandardCharsets.UTF_8));

        String signingInput = encodedHeader + "." + encodedPayload;

        Signature signature = Signature.getInstance("SHA256withRSA");
        signature.initSign(readPrivateKey("META-INF/private_key.pem"));
        signature.update(signingInput.getBytes(StandardCharsets.UTF_8));
        byte[] signatureBytes = signature.sign();

        String encodedSignature = encoder.encodeToString(signatureBytes);

        return signingInput + "." + encodedSignature;
    }

    private static String jsonToString(JsonObject obj) {
        StringWriter sw = new StringWriter();
        try (JsonWriter writer = Json.createWriter(sw)) {
            writer.writeObject(obj);
        }
        return sw.toString();
    }

    public PrivateKey readPrivateKey(String resourceName) throws Exception {
        try (InputStream is = getClass().getClassLoader().getResourceAsStream(resourceName)) {
            if (is == null) {
                throw new FileNotFoundException("Resource not found: " + resourceName);
            }
            String keyPem = new String(is.readAllBytes(), StandardCharsets.UTF_8)
                    .replaceAll("-----BEGIN (.*)-----", "")
                    .replaceAll("-----END (.*)-----", "")
                    .replaceAll("\\s", "");

            byte[] decoded = Base64.getDecoder().decode(keyPem);
            PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(decoded);
            return KeyFactory.getInstance("RSA").generatePrivate(keySpec);
        }
    }


    public Set<String> getRevokedJti() {
        return revokedJti;
    }

    public void revokeJti(String jti) {
        this.revokedJti.add(jti);
    }

    public boolean isRevoked(String jti) {
        return this.revokedJti.contains(jti);
    }

    public JsonObject validateTokenAndGetClaims(String jwt) throws InvalidJwtException {


        String[] parts = jwt.split("\\.");

        if (parts.length != 3) {

            throw new InvalidJwtException("JWT structure is invalid");
        }


        String header = parts[0];
        String payload = parts[1];
        String signature = parts[2];


        String signingInput = header + "." + payload;
        byte[] signatureBytes = Base64.getUrlDecoder().decode(signature);
        try {
            PublicKey publicKey = readPublicKey("META-INF/public.pem");

            Signature verifier = Signature.getInstance("SHA256withRSA");
            verifier.initVerify(publicKey);
            verifier.update(signingInput.getBytes(StandardCharsets.UTF_8));

            if (!verifier.verify(signatureBytes)) {
                throw new InvalidJwtException("Invalid JWT signature");
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new InvalidJwtException("Exception occurred while validating JWT");
        }


        String decodedPayload = new String(Base64.getUrlDecoder().decode(payload), StandardCharsets.UTF_8);
        JsonReader reader = Json.createReader(new StringReader(decodedPayload));
        JsonObject claims = reader.readObject();
        reader.close();

        if (claims.containsKey("jti") && isRevoked(claims.getString("jti"))) {
            throw new InvalidJwtException("JWT is already revoked");
        }

        if (claims.containsKey("exp") && claims.getJsonNumber("exp").longValue() * 1000L < System.currentTimeMillis()) {
            throw new InvalidJwtException("JWT is expired");
        }
        if (claims.containsKey("iss") && !claims.getString("iss").equals("weblab3")) {
            throw new InvalidJwtException("Invalid issuer in JWT");
        }


        return claims;


    }


    private PublicKey readPublicKey(String resourceName) throws Exception {
        try (InputStream is = AuthorisationFilter.class.getClassLoader().getResourceAsStream(resourceName)) {
            if (is == null) {
                throw new FileNotFoundException("Resource not found: " + resourceName);
            }
            String keyPem = new String(is.readAllBytes(), StandardCharsets.UTF_8)
                    .replaceAll("-----BEGIN (.*)-----", "")
                    .replaceAll("-----END (.*)-----", "")
                    .replaceAll("\\s", "");

            byte[] decoded = Base64.getDecoder().decode(keyPem);
            X509EncodedKeySpec keySpec = new X509EncodedKeySpec(decoded);
            return KeyFactory.getInstance("RSA").generatePublic(keySpec);
        }
    }
}
