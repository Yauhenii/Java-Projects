package com.company;

import java.io.Serializable;
import java.util.Objects;

public class Student implements Comparable<Student>, Serializable {

    private int recBookNum;
    private String surname;
    private int course;
    private int group;
    private String city;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Student)) return false;
        Student student = (Student) o;
        return getRecBookNum() == student.getRecBookNum() &&
                getCourse() == student.getCourse() &&
                getGroup() == student.getGroup() &&
                Objects.equals(getSurname(), student.getSurname()) &&
                Objects.equals(getCity(),student.getCity());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getRecBookNum(), getSurname(), getCourse(), getGroup(), getCity());
    }

    public int getRecBookNum() {
        return recBookNum;
    }

    public String getSurname() {
        return surname;
    }

    public int getCourse() {
        return course;
    }

    public int getGroup() {
        return group;
    }

    public String getCity() {
        return city;
    }

    public Student(int recBookNum, String surname, int course, int group, String city) {
        this.recBookNum = recBookNum;
        this.surname = surname;
        this.course = course;
        this.group = group;
        this.city = city;
    }

    @Override
    public String toString() {
        StringBuffer result = new StringBuffer();
        result.append(recBookNum);
        result.append(" ");
        result.append(surname);
        result.append(" ");
        result.append(course);
        result.append(" ");
        result.append(group);
        result.append(" ");
        result.append(city);
        return result.toString();
    }

    public String toXMLString(){
        StringBuffer resultXML=new StringBuffer();

        resultXML.append("<student id=\"");
        resultXML.append(recBookNum);
        resultXML.append("\" city=\"");
        resultXML.append(city);
        resultXML.append("\">\n");

        resultXML.append("<surname>");
        resultXML.append(surname);
        resultXML.append("</surname>\n");

        resultXML.append("<course>");
        resultXML.append(Integer.toString(course));
        resultXML.append("</course>\n");

        resultXML.append("<group>");
        resultXML.append(Integer.toString(group));
        resultXML.append("</group>\n");

        resultXML.append("</student>\n");

        return resultXML.toString();
    }

    public int compareTo(Student student){
        return surname.compareTo(student.surname);
    }
}
