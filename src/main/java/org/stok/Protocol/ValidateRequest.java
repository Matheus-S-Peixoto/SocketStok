package org.stok.Protocol;

import org.jspecify.annotations.NonNull;
import org.stok.Protocol.request.Request;

import java.net.ProtocolException;

public class ValidateRequest {
    public void validateRequest(@NonNull Request req) throws ProtocolException {
        switch (req.getAction()) {
            case P_CREATE -> validateCreateRequest(req);
            case P_INFO -> validateInfoRequest(req);
            case P_EDIT -> validateEditRequest(req);
            case P_REMOVE -> validateRemoveRequest(req);
            case S_ADD -> validateAddRequest(req);
            case S_SELL -> validateSellRequest(req);
            case S_LOSS -> validateLossRequest(req);
            default -> throw new ProtocolException("Invalid action");
        };
    }

    private void validateCreateRequest(Request req) throws ProtocolException {
        if (req.getId() != null) {
            throw new ProtocolException("P_CREATE does not accept an ID");
        }
        if (req.getBody() == null) {
            throw new ProtocolException("P_CREATE must hava a valid body");
        }
        if (req.getBody().getName() == null) {
            throw new ProtocolException("Missing value: name");
        }
        if (req.getBody().getDescription() == null) {
            throw new ProtocolException("Missing value: description");
        }
        if (req.getBody().getAmount() == null) {
            throw new ProtocolException("Missing value: amount");
        }
        if (req.getBody().getCode() == null) {
            throw new ProtocolException("Missing value: code");
        } else if (req.getBody().getCode().length() != 15) {
            throw new ProtocolException("Invalid code - must be a 15 character alphanumeric code");
        }
        if (req.getBody().getQuantity() != 0 || req.getBody().getQuantity() != null) {
            throw new ProtocolException("Invalid field - \"quantity\" should not be initialized here");
        }
    }

    private void validateInfoRequest(Request req) throws ProtocolException {
        if(req.getBody() != null) {
            throw new ProtocolException("P_INFO should not have a body");
        }
    }

    private void validateEditRequest(Request req) throws ProtocolException {
        if (req.getId() == null) {
            throw new ProtocolException("Missing value: id");
        }
        if (req.getBody().isEmpty()) {
            throw new ProtocolException("Invalid field - \"body\" is empty");
        }
    }

    private void validateRemoveRequest(Request req) throws ProtocolException {
        if (req.getId() == null) {
            throw new ProtocolException("Missing value: id");
        }
        if (req.getBody() != null) {
            throw new ProtocolException("P_REMOVE does not accept a body");
        }
    }

    private void validateAddRequest(Request req) throws ProtocolException {
        String doesNotAccept = "S_ADD does not accept ";

        if (req.getId() == null) {
            throw new ProtocolException("Missing value: id");
        }
        if (req.getBody() == null) {
            throw new ProtocolException("S_ADD must have a valid body");
        }
        if (req.getBody().getName() != null) {
            throw new ProtocolException(doesNotAccept + "name");
        }
        if (req.getBody().getDescription() != null) {
            throw new ProtocolException(doesNotAccept + "description");
        }
        if (req.getBody().getAmount() != null) {
            throw new ProtocolException(doesNotAccept + "amount");
        }
        if (req.getBody().getCode() != null) {
            throw new ProtocolException(doesNotAccept + "code");
        }
        if (req.getBody().getQuantity() == null) {
            throw new ProtocolException("S_ADD must have a valid \"quantity\" value");
        } else if (req.getBody().getQuantity() <= 0) {
            throw new ProtocolException("\"quantity\" value should be positive and bigger then 0");
        }
    }

    private void validateSellRequest(Request req) throws ProtocolException {
        String doesNotAccept = "S_SELL does not accept ";

        if (req.getId() == null) {
            throw new ProtocolException("Missing value: id");
        }
        if (req.getBody() == null) {
            throw new ProtocolException("S_SELL must have a valid body");
        }
        if (req.getBody().getName() != null) {
            throw new ProtocolException(doesNotAccept + "name");
        }
        if (req.getBody().getDescription() != null) {
            throw new ProtocolException(doesNotAccept + "description");
        }
        if (req.getBody().getAmount() != null) {
            throw new ProtocolException(doesNotAccept + "amount");
        }
        if (req.getBody().getCode() != null) {
            throw new ProtocolException(doesNotAccept + "code");
        }
        if (req.getBody().getQuantity() == null) {
            throw new ProtocolException("S_SELL must have a valid \"quantity\" value");
        } else if (req.getBody().getQuantity() <= 0) {
            throw new ProtocolException("\"quantity\" value should be positive and bigger then 0");
        }
    }

    private void validateLossRequest(Request req) throws ProtocolException {
        String doesNotAccept = "S_LOSS does not accept ";

        if (req.getId() == null) {
            throw new ProtocolException("Missing value: id");
        }
        if (req.getBody() == null) {
            throw new ProtocolException("S_LOSS must have a valid body");
        }
        if (req.getBody().getName() != null) {
            throw new ProtocolException(doesNotAccept + "name");
        }
        if (req.getBody().getDescription() != null) {
            throw new ProtocolException(doesNotAccept + "description");
        }
        if (req.getBody().getAmount() != null) {
            throw new ProtocolException(doesNotAccept + "amount");
        }
        if (req.getBody().getCode() != null) {
            throw new ProtocolException(doesNotAccept + "code");
        }
        if (req.getBody().getQuantity() == null) {
            throw new ProtocolException("S_LOSS must have a valid \"quantity\" value");
        } else if (req.getBody().getQuantity() <= 0) {
            throw new ProtocolException("\"quantity\" value should be positive and bigger then 0");
        }
    }
}
