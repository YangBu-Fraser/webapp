package com.example.api;

import com.example.model.HealthCheck;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import com.example.config.HibernateUtil;

import java.io.IOException;
import java.time.Instant;

@WebServlet("/healthz")
public class HealthCheckServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // Check if the request has a body
        if (req.getContentLength() > 0 || req.getReader().readLine() != null) {
            resp.setStatus(HttpServletResponse.SC_SERVICE_UNAVAILABLE); // 503
            resp.setHeader("Cache-Control", "no-cache");
            return;
        }

        // Insert health check record
        EntityManager entityManager = HibernateUtil.getEntityManagerFactory().createEntityManager();
        EntityTransaction transaction = null;

        try {
            transaction = entityManager.getTransaction();
            transaction.begin();

            HealthCheck healthCheck = new HealthCheck();
            healthCheck.setDatetime(Instant.now());
            entityManager.persist(healthCheck);

            transaction.commit();
            resp.setStatus(HttpServletResponse.SC_OK); // 200
        } catch (Exception e) {
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
            resp.setStatus(HttpServletResponse.SC_SERVICE_UNAVAILABLE); // 503
        } finally {
            entityManager.close();
        }

        // Set cache-control header
        resp.setHeader("Cache-Control", "no-cache");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setStatus(HttpServletResponse.SC_METHOD_NOT_ALLOWED); // 405
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setStatus(HttpServletResponse.SC_METHOD_NOT_ALLOWED); // 405
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setStatus(HttpServletResponse.SC_METHOD_NOT_ALLOWED); // 405
    }
}
