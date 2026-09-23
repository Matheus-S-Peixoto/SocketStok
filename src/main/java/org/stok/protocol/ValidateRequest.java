package org.stok.protocol;

import org.jspecify.annotations.NonNull;
import org.stok.exceptions.ProtocolException;
import org.stok.exceptions.ResponseCodes;
import org.stok.protocol.request.Request;

public class ValidateRequest {
    public void validateRequest(@NonNull Request req) throws ProtocolException {

        if (req.getAction() == null) {
            throw new ProtocolException(ResponseCodes.BAD_REQUEST, "Invalid JSON Protocol: Null Value on 'action' field");
        }

        switch (req.getAction()) {
            case P_CREATE -> validateCreateRequest(req);
            case P_INFO -> validateInfoRequest(req);
            case P_EDIT -> validateEditRequest(req);
            case P_REMOVE -> validateRemoveRequest(req);
            case S_ADD, S_SELL, S_LOSS -> validateStokActions(req);
            default -> throw new ProtocolException(ResponseCodes.BAD_REQUEST, "Invalid action");
        };
    }

    private void validateCreateRequest(Request req) throws ProtocolException {
        if (req.getId() != null) {
            throw new ProtocolException(ResponseCodes.BAD_REQUEST, "P_CREATE does not accept an ID");
        }
        if (req.getBody() == null) {
            throw new ProtocolException(ResponseCodes.BAD_REQUEST, "P_CREATE must hava a valid body");
        }
        if (req.getBody().getName() == null) {
            throw new ProtocolException(ResponseCodes.BAD_REQUEST, "Missing value: name");
        }
        if (req.getBody().getDescription() == null) {
            throw new ProtocolException(ResponseCodes.BAD_REQUEST, "Missing value: description");
        }
        if (req.getBody().getAmount() == null) {
            throw new ProtocolException(ResponseCodes.BAD_REQUEST, "Missing value: amount");
        }
        if (req.getBody().getCode() == null) {
            throw new ProtocolException(ResponseCodes.BAD_REQUEST, "Missing value: code");
        } else if (req.getBody().getCode().length() != 15) {
            throw new ProtocolException(ResponseCodes.BAD_REQUEST, "Invalid code - must be a 15 character alphanumeric code");
        }
        if (req.getBody().getQuantity() != null) {
            throw new ProtocolException(ResponseCodes.BAD_REQUEST, "Invalid field - \"quantity\" should not be initialized here");
        }
    }

    private void validateInfoRequest(Request req) throws ProtocolException {
        if(req.getBody() != null) {
            throw new ProtocolException(ResponseCodes.BAD_REQUEST, "P_INFO should not have a body");
        }
    }

    private void validateEditRequest(Request req) throws ProtocolException {
        if (req.getId() == null) {
            throw new ProtocolException(ResponseCodes.BAD_REQUEST, "Missing value: id");
        }
        if (req.getBody() == null) {
            throw new ProtocolException(ResponseCodes.BAD_REQUEST, "Missing value: body");
        }
        if (req.getBody().isEmpty()) {
            throw new ProtocolException(ResponseCodes.BAD_REQUEST, "Invalid field - \"body\" is empty");
        }
    }

    private void validateRemoveRequest(Request req) throws ProtocolException {
        if (req.getId() == null) {
            throw new ProtocolException(ResponseCodes.BAD_REQUEST, "Missing value: id");
        }
        if (req.getBody() != null) {
            throw new ProtocolException(ResponseCodes.BAD_REQUEST, "P_REMOVE does not accept a body");
        }
    }

    private void validateStokActions(Request req) throws ProtocolException {
        String doesNotAccept = "Stok Actions do not accept ";

        if (req.getId() == null) {
            throw new ProtocolException(ResponseCodes.BAD_REQUEST, "Missing value: id");
        }
        if (req.getBody() == null) {
            throw new ProtocolException(ResponseCodes.BAD_REQUEST, "S_ADD must have a valid body");
        }
        if (req.getBody().getName() != null) {
            throw new ProtocolException(ResponseCodes.BAD_REQUEST, doesNotAccept + "name");
        }
        if (req.getBody().getDescription() != null) {
            throw new ProtocolException(ResponseCodes.BAD_REQUEST, doesNotAccept + "description");
        }
        if (req.getBody().getAmount() != null) {
            throw new ProtocolException(ResponseCodes.BAD_REQUEST, doesNotAccept + "amount");
        }
        if (req.getBody().getCode() != null) {
            throw new ProtocolException(ResponseCodes.BAD_REQUEST, doesNotAccept + "code");
        }
        if (req.getBody().getQuantity() == null) {
            throw new ProtocolException(ResponseCodes.BAD_REQUEST, "Stok Actions must have a valid \"quantity\" value");
        } else if (req.getBody().getQuantity() <= 0) {
            throw new ProtocolException(ResponseCodes.BAD_REQUEST, "\"quantity\" value should be positive and bigger then 0");
        }
    }
}
