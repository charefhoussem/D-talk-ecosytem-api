package com.dtalk.ecosystem.utils;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.Instant;
import java.util.Base64;
import java.util.UUID;

public class UniqueIdentifierUtil {
    public static String generateSecureIdentifier(String originalFileName) throws NoSuchAlgorithmException {
        String extension = "";
        int lastDotIndex = originalFileName.lastIndexOf('.');
        if (lastDotIndex > 0 && lastDotIndex < originalFileName.length() - 1) {
            extension = originalFileName.substring(lastDotIndex + 1);
        }

        String uuid = UUID.randomUUID().toString();

        String timestamp = String.valueOf(Instant.now().toEpochMilli());

        String rawData = uuid + "_" + timestamp;

        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] hash = digest.digest(rawData.getBytes(StandardCharsets.UTF_8));

        String secureIdentifier = Base64.getUrlEncoder().withoutPadding().encodeToString(hash);

        return extension.isEmpty() ? secureIdentifier : secureIdentifier + "." + extension;
    }


}
