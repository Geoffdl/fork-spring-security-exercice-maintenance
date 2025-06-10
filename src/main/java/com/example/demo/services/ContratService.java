package com.example.demo.services;

import com.example.demo.models.Contrat;
import com.example.demo.models.UserApp;
import com.example.demo.repositories.ContratRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class ContratService {
    @Autowired
    private ContratRepository contratRepository;
    @Autowired
    private UserAppService userAppService;

    public List<Contrat> getAll() {
        return contratRepository.findAll();
    }

    public List<Contrat> getAllByUserAppId(Integer userAppId) throws Exception {
        UserApp userApp = userAppService.getUserAppById(userAppId);
        return contratRepository.findByUserAppId(userAppId);
    }

    public void createContrat(LocalDate dateDebut, LocalDate dateFin, UserApp userApp) {
        contratRepository.save(new Contrat(dateDebut, dateFin, userApp));
    }

    public void deleteContratById(Integer id) {
        Contrat contrat = contratRepository.findById(id).get();
        contrat.setUserApp(null);
        contratRepository.delete(contrat);
    }
}
