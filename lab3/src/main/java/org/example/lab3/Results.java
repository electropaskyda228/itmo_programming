package org.example.lab3;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Named;
import jdk.jfr.Name;

import java.util.ArrayList;
import java.util.List;

@Named
@ApplicationScoped
public class Results {
    private List<PointResult> results = new ArrayList<>();

    public List<PointResult> getResults() {
        return results;
    }

    public void addResult(Double x, Double y, Double r, boolean accuracy) {
        results.add(new PointResult(x, y, r, accuracy));
    }
}
