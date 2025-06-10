package com.example.demo.controllers;


import com.example.demo.models.Contrat;
import com.example.demo.models.UserApp;
import com.example.demo.services.ContratService;
import com.example.demo.services.JwtAuthentificationService;
import com.example.demo.services.UserAppService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/contrat")
public class ContratController {

    @Autowired
    ContratService contratService;
    @Autowired
    UserAppService userAppService;
    @Autowired
    JwtAuthentificationService jwtAuthentificationService;

    @GetMapping("/get-all")
    public List<Contrat> getAll() throws Exception {
        return contratService.getAll();
    }

    @GetMapping("/get-all-by-user-app-id/{idUserApp}")
    public List<Contrat> getAllByUserAppId(@PathVariable Integer idUserApp) throws Exception {
        return contratService.getAllByUserAppId(idUserApp);
    }

    @PostMapping("/create")
    public String createContrat(@RequestBody Contrat contrat, HttpServletRequest request) throws Exception {
        UserApp userApp = userAppService.getUserApp(
                jwtAuthentificationService.getUsernameFromCookie(request)
        ) ;
        contratService.createContrat(
                contrat.getDateDebut(),
                contrat.getDateFin(),
                userApp
        );
        return "contrat créé";
    }

    @GetMapping("/delete-contrat-by-id/{idContrat}")
    public String deleteContratById(@PathVariable Integer idContrat) throws Exception {
         contratService.deleteContratById(idContrat);
        return "contrat supprimé";
    }
}
