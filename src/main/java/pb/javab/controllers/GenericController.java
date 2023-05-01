package pb.javab.controllers;

import jakarta.inject.Inject;
import jakarta.servlet.http.HttpServlet;
import pb.javab.daos.IGenericDao;

public abstract class GenericController<T, D extends IGenericDao<T>> extends HttpServlet {
    protected D dao;

    @Inject
    protected void setDao(D dao) {
        this.dao = dao;
    }
}
