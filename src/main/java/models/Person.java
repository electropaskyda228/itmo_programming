package main.java.models;

import main.java.utility.Validatable;
import main.java.utility.isCSVImaginable;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Person implements Validatable, isCSVImaginable {
    public String getName() {
        return name;
    }

    public Date getBirthday() {
        return birthday;
    }

    public String getBirthdayString() {
        String pattern = "dd-MM-yyyy";
        DateFormat df = new SimpleDateFormat(pattern);
        return df.format(getBirthday());
    }

    public Integer getWeight() {
        return weight;
    }

    public String getPassportID() {
        return passportID;
    }

    public Country getNationality() {
        return nationality;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public void setWeight(Integer weight) {
        this.weight = weight;
    }

    public void setPassportID(String passportID) {
        this.passportID = passportID;
    }

    public void setNationality(Country nationality) {
        this.nationality = nationality;
    }

    private String name; //Поле не может быть null, Строка не может быть пустой
    private Date birthday; //Поле не может быть null
    private Integer weight; //Поле не может быть null, Значение поля должно быть больше 0
    private String passportID; //Значение этого поля должно быть уникальным, Длина строки не должна быть больше 27, Строка не может быть пустой, Поле может быть null
    private Country nationality; //Поле не может быть null

    public Person(String name, Date birthday, Integer weight, String passportID, Country nationality) {
        setName(name);
        setBirthday(birthday);
        setWeight(weight);
        setPassportID(passportID);
        setNationality(nationality);
    }

    public Person(){}

    @Override
    public String toString() {
        List<String> result = new ArrayList<>(){
            {
                add("operator's name: " + getName());
                add("operator's birthday: " + getBirthdayString());
                add("operator's weight: " + getWeight());
                add("operator's passportID: " + getPassportID());
                add("operator's nationality: " + getNationality().toString());
            }
        };
        return String.join("\n", result);
    }

    @Override
    public boolean validate() {
        if (name == null || name.isEmpty()) return false;
        if (birthday == null) return false;
        if (weight <= 0) return false;
        if (passportID != null) {
            if ((passportID.isEmpty() || passportID.length() < 7 || passportID.length() > 27)) return false;
        }
        if (nationality == null) return false;
        return true;
    }

    @Override
    public String[] toArrayString() {
        List<String> result = new ArrayList<>(){
            {
                add(getName());
                add(getBirthdayString());
                add(String.valueOf(getWeight()));
                add(getPassportID());
                add(getNationality().toString());
            }
        };
        return result.toArray(new String[result.size()]);
    }

    public static String checkWeightString(String weightString) {
        if (weightString.isEmpty()) return "Line can't be empty";
        try {
            Integer weight = Integer.parseInt(weightString);
            if (weight > 0) return "yes";
            return "Weight should be bigger than zero";
        } catch (NumberFormatException exception) {
            return "Weight should be an integer";
        }
    }
}
