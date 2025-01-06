package exceptions;

public class DifferentFileException extends RuntimeException {
    public DifferentFileException(String message) {
        super(message+("+file1.getName()+"!="+file2.getName()+"));
    }
}
