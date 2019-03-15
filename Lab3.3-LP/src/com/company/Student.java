package com.company;

public class Student {
    private int course;
    private int group;
    private String surname;
    private String name;
    private String fathersname;

    public int getCourse() {
        return course;
    }

    public int getGroup() {
        return group;
    }

    public String getSurname() {
        return surname;
    }

    public String getName() {
        return name;
    }

    public String getFathersname() {
        return fathersname;
    }

    public Student(int course, int group, String surname, String name, String fathersname) {
        this.course = course;
        this.group = group;
        this.surname = surname;
        this.name = name;
        this.fathersname = fathersname;
    }

    @Override
    public String toString() {
        StringBuffer stringBuffer=new StringBuffer();
        stringBuffer.append(surname);
        stringBuffer.append(" ");
        stringBuffer.append(name);
        stringBuffer.append(" ");
        stringBuffer.append(fathersname);
        stringBuffer.append(" ");
        return stringBuffer.toString();
    }
}
