package com.hello;

import java.util.Random;
import java.util.StringJoiner;

class OOPTest {

    public static void main(String[] args) {
        Student s = new Student("小明", 13, 7);
        s.eat();
        s.speck();
        s.invokeSuperEat();
        System.out.println("父类的名称 == > " + s.className);
        System.out.println("年龄 == > " + s.getAge());

        Person p = s;
        p.eat();
        p.speck();
        System.out.println(p.className);

        String[] str = {"I", "am", "KD"};
        StringJoiner stringJoiner = new StringJoiner("==");
        for (String s1 : str) {
            stringJoiner.add(s1);
        }
        System.out.println("字符拼接 == > " + stringJoiner);
    }
}

class Person {
    protected String className = "Person";
    private String name;
    private int age;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public Person(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public void speck() {
        System.out.println("我是父类的speck函数");
    }

    public void eat() {
        System.out.println("我是父类的eat函数");
    }
}

class Student extends Person {
    private int great;

    public int getGreat() {
        return great;
    }

    public void setGreat(int great) {
        this.great = great;
    }

    public Student(String name, int age, int great) {
        super(name, age);
        this.great = great;
    }

    @Override
    public void speck() {
        System.out.println("我是子类的speck函数");
    }

    @Override
    public void eat() {
        System.out.println("我是子类的eat函数");
    }

    public void invokeSuperEat() {
        super.eat();
    }
}