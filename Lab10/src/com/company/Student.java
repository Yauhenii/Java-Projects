package com.company;

import java.util.Objects;

public class Student implements Comparable<Student> {

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Student)) return false;
        Student student = (Student) o;
        return getRecBookNum() == student.getRecBookNum() &&
                getCourse() == student.getCourse() &&
                getGroup() == student.getGroup() &&
                Objects.equals(getSurname(), student.getSurname());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getRecBookNum(), getSurname(), getCourse(), getGroup());
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

    private int recBookNum;
    private String surname;
    private int course;
    private int group;

    public Student(int recBookNum, String surname, int course, int group) {
        this.recBookNum = recBookNum;
        this.surname = surname;
        this.course = course;
        this.group = group;
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
        return result.toString();
    }

    public String toXMLString(){
        StringBuffer resultXML=new StringBuffer();

        resultXML.append("<student>\n");

        resultXML.append("<recBookNum>");
        resultXML.append(Integer.toString(recBookNum));
        resultXML.append("</recBookNum>\n");

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
