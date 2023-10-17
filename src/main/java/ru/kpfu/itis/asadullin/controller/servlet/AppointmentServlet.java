package ru.kpfu.itis.asadullin.controller.servlet;

import org.cloudinary.json.JSONArray;
import org.cloudinary.json.JSONObject;
import ru.kpfu.itis.asadullin.model.dao.impl.AppointmentDaoImpl;
import ru.kpfu.itis.asadullin.model.dao.impl.MasterDaoImpl;
import ru.kpfu.itis.asadullin.model.dao.impl.MasterServiceDaoImpl;
import ru.kpfu.itis.asadullin.model.dao.impl.ServiceDaoImpl;
import ru.kpfu.itis.asadullin.model.entity.Appointment;
import ru.kpfu.itis.asadullin.model.entity.Master;
import ru.kpfu.itis.asadullin.model.entity.MasterService;
import ru.kpfu.itis.asadullin.model.entity.Service;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@WebServlet(name = "appointmentServlet", urlPatterns = "/appointment")
public class AppointmentServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<Master> masters = new MasterDaoImpl().getAll();
        List<Service> services = new ServiceDaoImpl().getAll();

        req.setAttribute("masters", masters);
        req.setAttribute("services", services);

        req.getRequestDispatcher("ftl/appointment.ftl").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");

        if ("master".equals(action)) {
            updateServiceList(req, resp);
        } else {
            apply(req, resp);
        }
    }

    private void updateServiceList(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        int masterId = Integer.parseInt(req.getParameter("masterId"));

        MasterServiceDaoImpl masterServiceDao = new MasterServiceDaoImpl();
        ServiceDaoImpl serviceDao = new ServiceDaoImpl();

        List<MasterService> services = masterServiceDao.getAllForMasterId(masterId);

        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");

        // Создайте объект JSON для отправки
        JSONArray responseJSON = new JSONArray();

        for (MasterService service : services) {
            JSONObject serviceJSON = new JSONObject();
            int serviceId = service.getServiceId();

            serviceJSON.put("serviceId", serviceId);
            serviceJSON.put("name", serviceDao.getById(serviceId).getName());
            responseJSON.put(serviceJSON);
        }

        resp.getWriter().print(responseJSON);

    }

    private void apply(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String phone = req.getParameter("phone");
        int masterId = Integer.parseInt(req.getParameter("masterId"));
        int serviceId = Integer.parseInt(req.getParameter("serviceId"));
        Date day = Date.valueOf(req.getParameter("date"));
        String message;

        String s = req.getParameter("time");
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        long ms;
        try {
            ms = sdf.parse(s).getTime();
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        Time time = new Time(ms);

        Timestamp appointmentTime = new Timestamp(day.getTime() + time.getTime() + TimeUnit.HOURS.toMillis(3));

        Appointment appointment = new Appointment(masterId, serviceId, appointmentTime, phone);

        AppointmentDaoImpl appointmentDao = new AppointmentDaoImpl();

        boolean isAppointmentAvailable = appointmentDao.isAppointmentAvailable(appointment);

        String phoneNumberRegex = "^[\\+]?[(]?[0-9]{3}[)]?[-\\s\\.]?[0-9]{3}[-\\s\\.]?[0-9]{4,6}$";

        Pattern pattern = Pattern.compile(phoneNumberRegex);
        Matcher matcher = pattern.matcher(phone);

        if (appointmentTime.compareTo(new Timestamp(System.currentTimeMillis())) < 0) {
            message = "Error! Appointment time is already in the past...";
            isAppointmentAvailable = false;
        } else if (!matcher.matches()) {
            message = "Error! Can't parse your phone number...";
            isAppointmentAvailable = false;
        } else if (!isAppointmentAvailable) {
            message = "Unfortunately, appointment for this time is impossible...";
        } else {
            appointmentDao.insert(appointment);
            message = "Success! Your application has been accepted for processing :)";
        }

        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");

        JSONObject responseJSON = new JSONObject();
        responseJSON.put("message", message);

        resp.getWriter().print(responseJSON);
    }
}
