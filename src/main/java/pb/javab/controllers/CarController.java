package pb.javab.controllers;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import pb.javab.daos.ICarDao;
import pb.javab.models.Car;
import pb.javab.models.CarStatus;
import pb.javab.models.Transmission;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

@WebServlet(name = "CarController", urlPatterns = {"/car/list", "/car/edit/*", "/car/create", "/car/delete/*", "/car/details/*"})
public class CarController extends GenericController<Car, ICarDao> {
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
                handleGetForm(req, res);
                break;
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        var id = parseId(req);

        Map<String, String> errors = new HashMap<>();
        var car = parseCar(req, errors);

        if (!errors.isEmpty()) {
            req.setAttribute("errors", errors);
            handleGetForm(req, res, car);
            return;
        }

        if (id == null) {
            handleCreate(car);
        } else {
            handleEdit(id, car);
        }

        res.sendRedirect(req.getContextPath() + "/car/list");
    }

    private Long parseId(HttpServletRequest req) {
        var path = req.getPathInfo();

        if (path == null || !path.startsWith("/"))
            return null;

        return Long.parseLong(path.substring(1));
    }

    private void handleCreate(Car car) {
        dao.save(car);
    }

    private void handleEdit(Long id, Car car) {
        var oldCar = dao.get(id).orElseThrow();
        car.setId(id);
        car.setCarRentals(oldCar.getCarRentals());
        dao.update(car);
    }

    private Car parseCar(HttpServletRequest req, Map<String, String> errors) {
        var params = req.getParameterMap();

        int power = 0;
        BigDecimal rate = BigDecimal.ZERO;
        var model = params.get("model")[0];
        var manufacturer = params.get("manufacturer")[0];
        var transmission = Transmission.valueOf(params.get("transmission")[0]);

        if (model == null || model.isEmpty()) {
            errors.put("model", "Pole nie może być puste");
        }
        if (manufacturer == null || manufacturer.isEmpty()) {
            errors.put("manufacturer", "Pole nie może być puste");
        }

        try {
            power = Integer.parseInt(params.get("power")[0]);
            if (power <= 0) {
                errors.put("power", "Moc musi być dodatnia");
            }
        } catch (NumberFormatException e) {
            errors.put("power", "Niepoprawny format");
        }

        try {
            rate = new BigDecimal(params.get("rate")[0]);
            if (rate.compareTo(BigDecimal.ZERO) <= 0) {
                errors.put("rate", "Cena musi być dodatnia");
            }
        } catch (NumberFormatException e) {
            errors.put("rate", "Niepoprawny format");
        }

        return new Car(model, manufacturer, power, CarStatus.AVAILABLE, transmission, rate);
    }

    private void handleGetForm(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        var idStr = req.getPathInfo();
        if (idStr != null) {
            var id = Long.parseLong(idStr.substring(1));
            var car = dao.get(id).orElseThrow();

            handleGetForm(req, res, car);
            return;
        }

        req.setAttribute("carStatuses", CarStatus.class.getEnumConstants());
        req.setAttribute("carTransmission", Transmission.class.getEnumConstants());
        req.getRequestDispatcher("/WEB-INF/views/car/carForm.xhtml").forward(req, res);
    }

    private void handleGetForm(HttpServletRequest req, HttpServletResponse res, Car car) throws ServletException, IOException {
        if (car != null) {
            req.setAttribute("model", car.getModel());
            req.setAttribute("manufacturer", car.getManufacturer());
            req.setAttribute("power", car.getPower());
            req.setAttribute("transmission", car.getTransmission());
            req.setAttribute("rate", car.getRate());
        }

        req.setAttribute("carStatuses", CarStatus.class.getEnumConstants());
        req.setAttribute("carTransmission", Transmission.class.getEnumConstants());
        req.getRequestDispatcher("/WEB-INF/views/car/carForm.xhtml").forward(req, res);
    }

    private void handleDelete(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        var id = parseId(req);

        var car = dao.get(id).orElseThrow();
        dao.delete(car);

        res.sendRedirect(req.getContextPath() + "/car/list");
    }

    private void handleDetails(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        var id = parseId(req);

        var car = dao.get(id).orElseThrow();
        req.setAttribute("car", car);
        req.getRequestDispatcher("/WEB-INF/views/car/details.xhtml").forward(req, res);
    }

    private void handleList(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        var cars = dao.getAll();
        req.setAttribute("carList", cars);
        req.getRequestDispatcher("/WEB-INF/views/car/list.xhtml").forward(req, res);
    }
}
