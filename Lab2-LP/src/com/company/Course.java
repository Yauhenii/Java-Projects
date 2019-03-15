package com.company;

import java.util.HashSet;
import java.util.Set;

public class Course {
    //fields
    private Set<Student> students;
    private String name;
    //constructors

    public Course(Set<Student> students, String name) {
        this.students = students;
        this.name = name;
    }

    //methods
    Set<Postgraduate> getPostgraduates(String nameOfSupervisor){
        HashSet<Postgraduate> postgraduates=new HashSet<>();
        for(Student student : students){
            if(student instanceof Postgraduate){
                if(((Postgraduate) student).getSupervisor().getName().equals(nameOfSupervisor)){
                    postgraduates.add((Postgraduate) student);
                }
            }
        }
        return postgraduates;
    }

    public String getName() {
        return name;
    }
}
