package com.example.demo.dto;

import com.example.demo.models.UserApp;

import java.time.LocalDate;

public class ContratDto
{
    private Integer id;
    private LocalDate dateDebut;
    private LocalDate dateFin;
    private UserDto userApp;
    
    public ContratDto()
    {
    }
    
    public ContratDto(Integer id, LocalDate dateDebut, LocalDate dateFin, UserDto userApp)
    {
        this.id = id;
        this.dateDebut = dateDebut;
        this.dateFin = dateFin;
        this.userApp = userApp;
    }
    
    /**
     * Getter
     * @return id
     */
    public Integer getId()
    {
        return id;
    }
    
    /**
     * Setter
     * @param id sets value
     */
    public void setId(Integer id)
    {
        this.id = id;
    }
    
    /**
     * Getter
     * @return dateDebut
     */
    public LocalDate getDateDebut()
    {
        return dateDebut;
    }
    
    /**
     * Setter
     * @param dateDebut sets value
     */
    public void setDateDebut(LocalDate dateDebut)
    {
        this.dateDebut = dateDebut;
    }
    
    /**
     * Getter
     * @return dateFin
     */
    public LocalDate getDateFin()
    {
        return dateFin;
    }
    
    /**
     * Setter
     * @param dateFin sets value
     */
    public void setDateFin(LocalDate dateFin)
    {
        this.dateFin = dateFin;
    }
    
    /**
     * Getter
     * @return userApp
     */
    public UserDto getUserApp()
    {
        return userApp;
    }
    
    /**
     * Setter
     * @param userApp sets value
     */
    public void setUserApp(UserDto userApp)
    {
        this.userApp = userApp;
    }
}
