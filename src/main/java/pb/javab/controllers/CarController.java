package pb.javab.controllers;

import jakarta.ejb.EJB;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import pb.javab.daos.ICarDao;

import java.io.IOException;

@WebServlet(name = "CarController", urlPatterns = {"/car/list", "/car/edit/*", "/car/create", "/car/delete/*"})
public class CarController extends HttpServlet {
    @EJB
    private ICarDao dao;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        var path = req.getServletPath();
        switch (path) {
            case "/car/list":
                handleList(req, res);
                break;
            case "/car/details":
                handleDetails(req, res);
                break;
            case "/car/delete":
                handleDelete(req, res);
                break;
            case "/car/create":
            case "/car/edit":
                req.setAttribute("formAction", path);
                req.getRequestDispatcher("/WEB-INF/views/car/carForm.xhtml").forward(req, res);
                break;
        }
    }

    private void handleDelete(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
    }

    private void handleDetails(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
    }

    private void handleList(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        var cars = dao.getAll();
        req.setAttribute("carList", cars);
        req.getRequestDispatcher("/WEB-INF/views/car/list.xhtml").forward(req, res);
    }

}
