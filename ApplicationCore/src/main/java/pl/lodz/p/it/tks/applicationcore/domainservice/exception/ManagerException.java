package pl.lodz.p.it.tks.applicationcore.domainservice.exception;

import pl.lodz.p.pas.controller.functional.ResourceBundleService;

import java.util.PropertyResourceBundle;

public class ManagerException extends RuntimeException {
    public ManagerException() {
        super();
    }

    public ManagerException(String message) {
        super(message);
    }

    public ManagerException(String message, Throwable cause) {
        super(message, cause);
    }

    public ManagerException(Throwable cause) {
        super(cause);
    }

    @Override
    public String getMessage() {
        return getLocalizedMessage();
    }

    @Override
    public String getLocalizedMessage() {
        PropertyResourceBundle resourceBundle = ResourceBundleService.getBundle();
        if (resourceBundle != null && resourceBundle.containsKey("ManagerException." + super.getMessage())) {
            return resourceBundle.getString("ManagerException." + super.getMessage());
        }
        return super.getMessage();
    }
}
