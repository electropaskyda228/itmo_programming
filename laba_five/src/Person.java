import java.util.Date;

public class Person {
    public String getName() {
        return name;
    }

    public Date getBirthday() {
        return birthday;
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
    private java.util.Date birthday; //Поле не может быть null
    private Integer weight; //Поле не может быть null, Значение поля должно быть больше 0
    private String passportID; //Значение этого поля должно быть уникальным, Длина строки не должна быть больше 27, Строка не может быть пустой, Поле может быть null
    private Country nationality; //Поле не может быть null

    public Person(String name, java.util.Date birthday, Integer weight, String passportID, Country nationality) {
        setName(name);
        setBirthday(birthday);
        setWeight(weight);
        setPassportID(passportID);
        setNationality(nationality);
    }
}
