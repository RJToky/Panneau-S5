package com.rjtoky.panneau.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.rjtoky.panneau.model.Source;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@Controller
@RequestMapping("/test")
public class TestController {
    @PersistenceContext
    private EntityManager entityManager;

    @GetMapping
    public ResponseEntity index() {
        Source source = Source.getAll(entityManager).get(0);
        return ResponseEntity.ok(source.predireCoupure(entityManager, "2023-12-25"));
    }

}
