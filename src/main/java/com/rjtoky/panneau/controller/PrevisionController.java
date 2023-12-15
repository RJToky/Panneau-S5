package com.rjtoky.panneau.controller;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import com.rjtoky.panneau.model.Source;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.websocket.server.PathParam;

@Controller
@RequestMapping("/")
public class PrevisionController {
    @PersistenceContext
    private EntityManager entityManager;

    @GetMapping("/")
    public RedirectView index() {
        return new RedirectView("/prevision");
    }

    @GetMapping("/prevision")
    public ModelAndView prevision(@PathParam("date") String date, @PathParam("idSource") String idSource) {
        ModelAndView mv = new ModelAndView("pages/prevision");
        mv.addObject("sources", Source.getAll(entityManager));

        if (date != null && idSource != null) {
            int id = Integer.parseInt(idSource);
            Source source = Source.getById(entityManager, id);
            mv.addObject("source", source);
            mv.addObject("coupure", source.predireCoupure(entityManager, date));

            LocalDate parseDate = LocalDate.parse(date);
            DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("d MMM yyyy", Locale.FRENCH);

            mv.addObject("date", date);
            mv.addObject("dateFormat", parseDate.format(dateFormat));
        }
        return mv;
    }

}
