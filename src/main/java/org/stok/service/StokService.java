package org.stok.service;

import org.stok.model.Product;
import org.stok.protocol.request.Request;
import org.stok.repository.ProductRepository;

import java.io.Serializable;

public class StokService {
    private final ProductRepository db;

    public StokService(ProductRepository repository) {
        this.db = repository;
    }

    public Object handleRequest(Request req) {
        return switch (req.getAction()) {
            case P_CREATE -> handleCreate(req);
            case P_INFO -> handleInfo(req);
            case P_EDIT -> handleEdit(req);
            case P_REMOVE -> handleRemove(req);
            case S_ADD -> handleAdd(req);
            case S_SELL -> handleSell(req);
            case S_LOSS -> handleLoss(req);
        };
    }

    private Product handleCreate(Request req) {}

    private Product handleInfo(Request req) {}

    private Product handleEdit(Request req) {}

    private Product handleRemove(Request req) {}

    private Product handleAdd(Request req) {}

    private Product handleSell(Request req) {}

    private Product handleLoss(Request req) {}
}
