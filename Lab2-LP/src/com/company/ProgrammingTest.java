package com.company;

import java.util.HashSet;
import java.util.Set;

public class ProgrammingTest {

    public static void main(String[] args) {
        HashSet<Student> students = new HashSet<>();
        students.add(new Undergraduate("John", "john12", "john12345@gmail.com", new Academic("Kastritsa")));
        students.add(new Undergraduate("Joe", "joeTheBrave", "bravejoe@yandex.ru", new Academic("Razmyslovich")));
        Academic academic= new Academic("Radyno");
        students.add(new Postgraduate("Peter", "peterG", "peterGriffin@gmail.com", academic));
        students.add(new Postgraduate("Pavel", "pashaSSS", "pavelIO@gmail.com", academic));
        students.add(new Postgraduate("Patric", "star111", "patricar@yandex.ru", new Academic("Orlovich")));

        Course course= new Course(students,"Math");

        Set<Postgraduate> postgraduates=course.getPostgraduates("Radyno");
        Notifier<Postgraduate> notifier=new Notifier<>(postgraduates);
        notifier.doNotifyAll("Pass your exams!");
    }
}
