package com.example.demo.repositories;

import com.example.demo.models.Contrat;
import com.example.demo.models.UserApp;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ContratRepository extends JpaRepository<Contrat, Integer> {
    @Query(value = "SELECT * FROM contrat WHERE user_app_id = :userAppId ", nativeQuery = true)
    List<Contrat> findByUserAppIdNative(Integer userAppId);
    
    List<Contrat> findByUserAppId(Integer userAppId);
}
