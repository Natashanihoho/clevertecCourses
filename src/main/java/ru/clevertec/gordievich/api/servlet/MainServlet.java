package ru.clevertec.gordievich.api.servlet;

import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.flywaydb.core.Flyway;
import ru.clevertec.gordievich.api.servlet.handling.CommandProvider;

import java.util.function.BiConsumer;

import static ru.clevertec.gordievich.infrastructure.connection.PropertiesUtil.*;

@WebServlet("/command")
public class MainServlet extends HttpServlet {

    private static final Flyway flyway = Flyway.configure()
            .dataSource(getDbUrl(),getDbUser(),getDbPassword())
            .baselineOnMigrate(true)
            .cleanDisabled(false)
            .load();

    @Override
    public void init(ServletConfig config) throws ServletException {
        flyway.migrate();
        super.init(config);
    }

    @Override
    public void destroy() {
        flyway.clean();
        super.destroy();
    }

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp) {
        router(req, resp);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
        router(req, resp);
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) {
        router(req, resp);
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) {
        router(req, resp);
    }

    private void router(HttpServletRequest req, HttpServletResponse resp) {
        BiConsumer<HttpServletRequest, HttpServletResponse> commandProvider = CommandProvider.byEndpointAndMethod(
                req.getParameter("type"), req.getMethod()
        );
        resp.setContentType("application/json");
        commandProvider.accept(req, resp);
    }


}
