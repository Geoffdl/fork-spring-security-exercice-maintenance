package com.example.demo.dto;

public class UserDto
{
    private Integer id;
    private String username;
    private String role;
    
    public UserDto()
    {
    }
    
    public UserDto(Integer id, String username, String role)
    {
        this.id = id;
        this.username = username;
        this.role = role;
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
     * @return username
     */
    public String getUsername()
    {
        return username;
    }
    
    /**
     * Setter
     * @param username sets value
     */
    public void setUsername(String username)
    {
        this.username = username;
    }
    
    /**
     * Getter
     * @return role
     */
    public String getRole()
    {
        return role;
    }
    
    /**
     * Setter
     * @param role sets value
     */
    public void setRole(String role)
    {
        this.role = role;
    }
}
