package com.example.demo.mapper;

import com.example.demo.dto.ContratDto;
import com.example.demo.dto.UserDto;
import com.example.demo.models.Contrat;
import com.example.demo.models.UserApp;

import java.util.List;
import java.util.stream.Collectors;

public class ContratMapper {
    
    public static ContratDto toDto(Contrat contrat) {
        UserApp user = contrat.getUserApp();
        UserDto userDto = new UserDto();
        userDto.setId(user.getId());
        userDto.setUsername(user.getUsername());
        userDto.setRole(user.getRole());
        
        ContratDto dto = new ContratDto();
        dto.setId(contrat.getId());
        dto.setDateDebut(contrat.getDateDebut());
        dto.setDateFin(contrat.getDateFin());
        dto.setUserApp(userDto);
        return dto;
    }
    
    public static List<ContratDto> toDtoList(List<Contrat> contrats) {
        return contrats.stream().map(ContratMapper::toDto).collect(Collectors.toList());
    }
}
