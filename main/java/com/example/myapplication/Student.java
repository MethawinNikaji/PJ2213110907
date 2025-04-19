package com.example.myapplication;

public class Student {
    public int id;
    public String name;
    public String section;
    public int thai;
    public int science;
    public int maths;
    public int english;
    public int total;
    public double percentage;
    public String grade;

    //  Default constructor (No arguments)
    public Student() {}

    //  Constructor with all arguments
    public Student(int id, String name, String section, int thai, int science, int maths, int english, int total, double percentage, String grade) {
        this.id = id;
        this.name = name;
        this.section = section;
        this.thai = thai;
        this.science = science;
        this.maths = maths;
        this.english = english;
        this.total = total;
        this.percentage = percentage;
        this.grade = grade;
    }

    //  Getter and Setter Methods
    public int getStudentID() {
        return id; }
    public void setStudentID(int id) {
        this.id = id; }

    public String getName() {
        return name; }
    public void setName(String name) {
        this.name = name; }

    public String getSection() {
        return section; }
    public void setSection(String section) {
        this.section = section; }

    public int getThai() {
        return thai; }
    public void setThai(int thai) {
        this.thai = thai; }

    public int getScience() {
        return science; }
    public void setScience(int science) {
        this.science = science; }

    public int getMaths() {
        return maths; }
    public void setMaths(int maths) {
        this.maths = maths; }

    public int getEnglish() {
        return english; }
    public void setEnglish(int english) {
        this.english = english; }

    public int getTotal() {
        return total; }
    public void setTotal(int total) {
        this.total = total; }

    public double getPercentage() {
        return percentage; }
    public void setPercentage(double percentage) {
        this.percentage = percentage; }

    public String getGrade() {
        return grade; }
    public void setGrade(String grade) {
        this.grade = grade; }
}
