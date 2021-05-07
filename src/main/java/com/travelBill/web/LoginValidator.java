package com.travelBill.web;

import org.apache.commons.codec.binary.Hex;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static java.nio.charset.StandardCharsets.UTF_8;
import static java.util.Objects.nonNull;


public class LoginValidator {
    // Validates login info provided by client
    // See https://core.telegram.org/widgets/login#checking-authorization
    static boolean validate(TelegramLoginDto telegramLoginDto, String secret) throws NoSuchAlgorithmException, InvalidKeyException {
        List<String> keyValuesList = new ArrayList<>();

        if (nonNull(telegramLoginDto.getAuthDate())) {
            keyValuesList.add("auth_date=" + telegramLoginDto.getAuthDate());
        }
        if (nonNull(telegramLoginDto.getFirstName())) {
            keyValuesList.add("first_name=" + telegramLoginDto.getFirstName());
        }

        if (nonNull(telegramLoginDto.getId())) {
            keyValuesList.add("id=" + telegramLoginDto.getId());
        }

        if (nonNull(telegramLoginDto.getLastName())) {
            keyValuesList.add("last_name=" + telegramLoginDto.getLastName());
        }

        if (nonNull(telegramLoginDto.getPhotoUrl())) {
            keyValuesList.add("photo_url=" + telegramLoginDto.getPhotoUrl());
        }

        if (nonNull(telegramLoginDto.getUsername())) {
            keyValuesList.add("username=" + telegramLoginDto.getUsername());
        }

        String dataCheckString = String.join("\n", keyValuesList);
        Mac sha256HMAC = Mac.getInstance("HmacSHA256");
        SecretKeySpec secretKey = new SecretKeySpec(MessageDigest.getInstance("SHA-256").digest(secret.getBytes(StandardCharsets.UTF_8)), "SHA256");

        sha256HMAC.init(secretKey);
        String hash = Hex.encodeHexString(sha256HMAC.doFinal(dataCheckString.getBytes(UTF_8)));
        return Objects.equals(hash, telegramLoginDto.getHash());
    }
}
