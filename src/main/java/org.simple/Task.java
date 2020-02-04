package org.simple;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;



@Entity
public class Task {

    @Id
    @GeneratedValue
    public int id;
    public String name;

}
