package com.travelBill.util;

public final class TextSanitizer {

    private TextSanitizer() {
    }

    /**
     * The production DB (JawsDB) uses the legacy MySQL {@code utf8} charset, which only supports
     * 3-byte sequences, so supplementary-plane characters (most emoji, incl. flag emoji) fail to
     * insert with "Incorrect string value". The schema can't be migrated to utf8mb4, so strip
     * those characters before persisting instead.
     */
    public static String stripSupplementaryCharacters(String text) {
        if (text == null) {
            return null;
        }

        StringBuilder result = new StringBuilder(text.length());
        text.codePoints()
                .filter(codePoint -> codePoint <= Character.MAX_VALUE)
                .forEach(result::appendCodePoint);

        return result.toString();
    }
}
