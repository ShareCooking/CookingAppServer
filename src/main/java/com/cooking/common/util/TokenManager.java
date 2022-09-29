package com.cooking.common.util;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;
import org.jose4j.json.internal.json_simple.JSONObject;
import org.jose4j.json.internal.json_simple.parser.JSONParser;
import org.jose4j.json.internal.json_simple.parser.ParseException;
import org.jose4j.jwe.ContentEncryptionAlgorithmIdentifiers;
import org.jose4j.jwe.JsonWebEncryption;
import org.jose4j.jwe.KeyManagementAlgorithmIdentifiers;
import org.jose4j.jwk.JsonWebKey;
import org.jose4j.jwk.JsonWebKeySet;
import org.jose4j.jws.AlgorithmIdentifiers;
import org.jose4j.jws.JsonWebSignature;
import org.jose4j.lang.JoseException;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.Map;

@Component
public class TokenManager {
    private static JsonWebKey signKey;
    private static JsonWebKey encKey;

    @PostConstruct
    public void init() {
        try {
            Reader        reader     = new InputStreamReader(new ClassPathResource("/key/keyset.json").getInputStream());
            JSONParser    parser     = new JSONParser();
            String        keysetJson = parser.parse(reader).toString();
            JsonWebKeySet keyset     = new JsonWebKeySet(keysetJson);
            signKey = keyset.findJsonWebKey("token.signing", "oct", "sig", "HS256");
            encKey = keyset.findJsonWebKey("token.encryption", "oct", "enc", "HS256");
        } catch (JoseException | IOException | ParseException e) {
            e.printStackTrace();
        }
    }

    public String encryptToken(Map<String, Object> map) {
    	if(!map.get("vndrCd").equals("") && !map.get("userId").equals("")) {
    		
    		try {
    			final JsonWebSignature jws = new JsonWebSignature();
    			jws.setPayload(new JSONObject(map).toJSONString());
    			jws.setAlgorithmHeaderValue(AlgorithmIdentifiers.HMAC_SHA256);
    			jws.setKey(signKey.getKey());
    			String plainJwt = jws.getCompactSerialization();
    			
    			final JsonWebEncryption jwe = new JsonWebEncryption();
    			jwe.setPlaintext(plainJwt);
    			jwe.setAlgorithmHeaderValue(KeyManagementAlgorithmIdentifiers.DIRECT);
    			jwe.setEncryptionMethodHeaderParameter(ContentEncryptionAlgorithmIdentifiers.AES_128_CBC_HMAC_SHA_256);
    			jwe.setKey(encKey.getKey());
    			
    			return jwe.getCompactSerialization();
    		} catch (JoseException e) {
    			e.printStackTrace();
    		}
    	}

        return null;
    }


    public Map<String, Object> decryptToken(String token) {
        if (token == null || token.length() <= 0) {
            return null;
        }

        try {
            final JsonWebEncryption jwe = new JsonWebEncryption();
            jwe.setKey(encKey.getKey());
            jwe.setCompactSerialization(token);
            final JsonWebSignature jws = new JsonWebSignature();
            jws.setKey(signKey.getKey());
            jws.setCompactSerialization(jwe.getPayload());

            final Object       object = new JSONParser().parse(jws.getPayload());
            final ObjectMapper mapper = new ObjectMapper();

            return mapper.readValue(object.toString(), new TypeReference<Map<String, Object>>() {
            });

        } catch (JoseException | ParseException | IOException e) {
            e.printStackTrace();
        }
        
        return null;
    }
}
