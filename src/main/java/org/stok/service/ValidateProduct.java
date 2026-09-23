package org.stok.service;

import org.stok.exceptions.ResponseCodes;
import org.stok.exceptions.ServiceException;
import org.stok.protocol.request.Request;
import org.stok.repository.ProductRepository;

import java.math.BigDecimal;
import java.util.Objects;

public class ValidateProduct {
    protected void validateCreate(Request req) throws ServiceException {
        if (req.getBody().getName().length() > 50) {
            throw new ServiceException(
                ResponseCodes.BAD_REQUEST,
                "Invalid Value: Name is too long"
            );
        }
        if (req.getBody().getDescription().length() > 200) {
            throw new ServiceException(
                    ResponseCodes.BAD_REQUEST,
                    "Invalid Value: Description is too long"
            );
        }
        if (Objects.equals(req.getBody().getAmount(), new BigDecimal("0"))) {
            throw new ServiceException(
                    ResponseCodes.BAD_REQUEST,
                    "Invalid Value: Amount cannot be 0"
            );
        }
        String sanitizedCode = req.getBody().getCode().replace(" ", "").replace("-", "");
        if (sanitizedCode.length() != 15) {
            throw new ServiceException(
                    ResponseCodes.BAD_REQUEST,
                    "Invalid Value: Code must have 15 valid characters"
            );
        }
    }

    protected void validateEdit(Request req) throws ServiceException {
        if (req.getBody().getName() != null) {
            if (req.getBody().getName().length() > 50) {
                throw new ServiceException(
                        ResponseCodes.BAD_REQUEST,
                        "Invalid Value: Name is too long"
                );
            }
        }
        if (req.getBody().getDescription() != null) {
            if (req.getBody().getDescription().length() > 200) {
                throw new ServiceException(
                        ResponseCodes.BAD_REQUEST,
                        "Invalid Value: Description is too long"
                );
            }
        }
        if (req.getBody().getAmount() != null) {
            if (Objects.equals(req.getBody().getAmount(), new BigDecimal("0"))) {
                throw new ServiceException(
                        ResponseCodes.BAD_REQUEST,
                        "Invalid Value: Amount cannot be 0"
                );
            }
        }
        if (req.getBody().getCode() != null) {
            String sanitizedCode = req.getBody().getCode().replace(" ", "").replace("-", "");
            if (sanitizedCode.length() != 15) {
                throw new ServiceException(
                        ResponseCodes.BAD_REQUEST,
                        "Invalid Value: Code must have 15 valid characters"
                );
            }
        }
        if (req.getBody().getQuantity() != null) {
            if (req.getBody().getQuantity() < 0) {
                throw new ServiceException(
                        ResponseCodes.BAD_REQUEST,
                        "Invalid Value: Quantity cannot be less then 0"
                );
            }
        }
    }

    protected void validateDecrement(Request req, ProductRepository db) throws ServiceException {
        Integer availableQuantity = db.getQuantityById(req.getId());
        if (availableQuantity == null) {
            throw new ServiceException(
                 ResponseCodes.NOT_FOUND,
                 "Could not find product with the requested id - " + req.getId()
            );
        }
        if (req.getBody().getQuantity() > availableQuantity) {
            throw new ServiceException(
                    ResponseCodes.BAD_REQUEST,
                    "Requested quantity is not available in the stok. - Available Quantity = " + availableQuantity
            );
        }
    }
}
