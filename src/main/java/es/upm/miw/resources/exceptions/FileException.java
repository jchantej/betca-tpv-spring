package es.upm.miw.resources.exceptions;

public class FileException extends Exception {
    private static final long serialVersionUID = -971479862516984984L;

    public static final String DESCRIPTION = "Error con el fichero yml";

    public FileException() {
        this("");
    }

    public FileException(String detail) {
        super(DESCRIPTION + ". " + detail);
    }

}