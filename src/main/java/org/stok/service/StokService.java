package org.stok.service;

import org.stok.exceptions.ServiceException;
import org.stok.model.Product;
import org.stok.protocol.request.Request;
import org.stok.repository.ProductRepository;

public class StokService {
    ValidateProduct validator;
    private final ProductRepository db;

    public StokService(ProductRepository repository) {
        this.db = repository;
        this.validator = new ValidateProduct();
    }

    public Object handleRequest(Request req) throws ServiceException {
        return switch (req.getAction()) {
            case P_CREATE -> handleCreate(req);
            case P_INFO -> handleInfo(req);
            case P_EDIT -> handleEdit(req);
            case P_REMOVE -> handleRemove(req);
            case S_ADD -> handleAdd(req);
            case S_SELL, S_LOSS -> handleSellandLoss(req);
        };
    }

    private Product handleCreate(Request req) throws ServiceException {
        validator.validateCreate(req);
        return db.createProduct(req.getBody());
    }

    private Object handleInfo(Request req) throws ServiceException {
        if(req.getId() == null) {
            return db.findAll();
        }
        return db.findProductById(req.getId());
    }

    private Product handleEdit(Request req) throws ServiceException {
        validator.validateEdit(req);
        return db.updateProduct(req);
    }

    private Product handleRemove(Request req) throws ServiceException {
        return db.deleteProduct(req.getId());
    }

    private Product handleAdd(Request req) throws ServiceException {
        return db.incrementQuantity(req.getId(), req.getBody().getQuantity());
    }

    private Product handleSellandLoss(Request req) throws ServiceException {
        Integer availableQuantity = db.getQuantityById(req.getId());

        validator.validateDecrement(req, availableQuantity);
        return db.decrementQuantity(req.getId(), req.getBody().getQuantity());
    }
}
