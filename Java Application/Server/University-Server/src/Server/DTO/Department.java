/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Server.DTO;

/**
 *
 * @author DELL
 */
public class Department {
    private String id;
    private String name;

    public Department(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public Department(String id) {
        this.id = id;
    }
    
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
}
