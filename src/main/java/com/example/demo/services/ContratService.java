package com.example.demo.services;

import com.example.demo.dto.ContratDto;
import com.example.demo.mapper.ContratMapper;
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

    public List<ContratDto> getAll() {     //retourner list de dto (évite de filtrer les pw)
        return ContratMapper.toDtoList(contratRepository.findAll());
    }

    public List<ContratDto> getAllByUserAppId(Integer userAppId) throws Exception {  //retourner list de dto (évite de filtrer les pw)
        UserApp userApp = userAppService.getUserAppById(userAppId);
        if(userApp == null){
            throw new RuntimeException("Utilisateur non trouvé"); // exception si utilisateur non trouvé
        }
        return ContratMapper.toDtoList(contratRepository.findByUserAppId(userAppId));
    }

    public void createContrat(LocalDate dateDebut, LocalDate dateFin, UserApp userApp) {
        contratRepository.save(new Contrat(dateDebut, dateFin, userApp));
    }

    public void deleteContratById(Integer id) {
        Contrat contrat = contratRepository.findById(id).orElseThrow(() -> new RuntimeException("Pas de contrat trouvé")); //exception si contrat non trouvé
        contrat.setUserApp(null);
        contratRepository.delete(contrat);
    }
}
