package es.javierdearcos.bookstore.util;

public class TextUtil {

    public String sanitize(String text) {
        return text.replace("\\s+", " ").trim();
    }
}
