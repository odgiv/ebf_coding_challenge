package backend.exception;

import org.springframework.http.HttpStatus;

public class BackendException extends Exception {

    private HttpStatus status;

    public BackendException(final String message, final HttpStatus status) {
        super(message);

        this.status = status;
    }

    public HttpStatus getStatus() {
        return this.status;
    }

    public void setStatus(HttpStatus status) {
        this.status = status;
    }

}
