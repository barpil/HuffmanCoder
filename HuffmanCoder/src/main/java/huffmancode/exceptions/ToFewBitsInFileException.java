package huffmancode.exceptions;

import java.io.IOException;

public class ToFewBitsInFileException extends IOException {
    public ToFewBitsInFileException(String message) {
        super(message);
    }
}
