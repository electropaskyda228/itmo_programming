package org.example.lab3;

import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Inject;
import jakarta.inject.Named;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.hibernate.Session;
import org.hibernate.Transaction;

@Named
@SessionScoped
public class AreaChecker implements Serializable {
    private final List<Integer> r_values = Arrays.asList(1, 2, 3, 4, 5);
    private double x = 0;
    private double y = 0;
    private double r = 2;

    @Inject
    private Results results;


    public Results getResults() {
        return results;
    }

    // get values for r-buttons
    public List<Integer> getR_values() {
        return r_values;
    }
    // getters and setters
    public String getR() {
        return Double.toString(this.r);
    }
    public String getY() {
        return Double.toString(this.y);
    }
    public String getX() {
        return Double.toString(this.x);
    }
    public void setX(String x) {
        try {
            Double x_temporary = Double.parseDouble(x);
            this.x = (validX(x_temporary) ? x_temporary : this.x);
        } catch (Exception _) {}
    }
    public void setY(String y) {
        try {
            Double y_temporary = Double.parseDouble(y);
            this.y = (validY(y_temporary) ? y_temporary : this.y);
        } catch (Exception _) {}
    }
    public void setR(String r) {
        try {
            Double r_temporary = Double.parseDouble(r);
            this.r = (validR(r_temporary) ? r_temporary : this.r);
        } catch (Exception _) {}
    }
    public boolean validR(Double r) {
        return (r >= 1 && r <= 5);
    }
    public boolean validX(Double x)  {
        return (x >= -3 && x <= 3);
    }
    public boolean validY(Double y)  {
        return (y > -5 && y < 3);
    }

    // action for r-button
    public String changeParamR() {
        FacesContext context = FacesContext.getCurrentInstance();
        Map<String, String> params = context.getExternalContext().getRequestParameterMap();
        String r = params.get("buttonName");

        try {
            Double r_temporary = Double.parseDouble(r);
            this.r = validR(r_temporary) ? r_temporary : this.r;
        } catch (Exception _) {
        }
        return null;
    }

    // provide form
    public void provideForm() {
        FacesContext context = FacesContext.getCurrentInstance();
        Map<String, String> params = context.getExternalContext().getRequestParameterMap();
        String x = params.get("x");
        System.out.println(x);
        results.addResult(this.x, this.y, this.r, checkAccuracy());

        // Работа с БД
        PointEntity pointEntity = new PointEntity();
        pointEntity.setX(this.x);
        pointEntity.setY(this.y);
        pointEntity.setR(this.r);
        pointEntity.setResult(checkAccuracy());
        createPointOnDB(pointEntity);
    }

    public boolean checkAccuracy() {
        if (this.x >= 0 && this.x <= this.r && this.y <= 0 && this.y >= -this.r) return true;
        if (this.x <= 0 && this.x >= -this.r && this.y >= 0 && this.y <= this.r && (this.r - this.y >= -this.x)) return true;
        return (this.x <= 0 && this.y <= 0 && (this.x * this.x + this.y * this.y <= this.r * this.r / 4));
    }

    @PostConstruct
    public void createPointOnDB(PointEntity point) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;

        try {
            System.out.println(2);
            transaction = session.beginTransaction();
            System.out.println(1);
            session.save(point);
            System.out.println(3);
            transaction.commit();
        } catch(Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        } finally {
            session.close();
        }
    }
}
