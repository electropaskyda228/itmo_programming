package main.java.models;

import main.java.utility.Element;
import main.java.utility.Validatable;

import java.io.Serial;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.time.ZonedDateTime;
import java.time.format.DateTimeParseException;
import java.util.*;

public class Movie extends Element implements Validatable, Cloneable, Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    private long id; //Поле не может быть null, Значение поля должно быть больше 0, Значение этого поля должно быть уникальным, Значение этого поля должно генерироваться автоматически
    private String name; //Поле не может быть null, Строка не может быть пустой
    private Coordinates coordinates; //Поле не может быть null
    private ZonedDateTime creationDate; //Поле не может быть null, Значение этого поля должно генерироваться автоматически
    private int oscarsCount; //Значение поля должно быть больше 0
    private Long goldenPalmCount; //Значение поля должно быть больше 0, Поле не может быть null
    private MovieGenre genre; //Поле не может быть null
    private MpaaRating mpaaRating; //Поле может быть null
    private Person operator; //Поле не может быть null

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Coordinates getCoordinates() {
        return coordinates;
    }

    public ZonedDateTime getCreationDate() {
        return creationDate;
    }

    public int getOscarsCount() {
        return oscarsCount;
    }

    public Long getGoldenPalmCount() {
        return goldenPalmCount;
    }

    public MovieGenre getGenre() {
        return genre;
    }

    public MpaaRating getMpaaRating() {
        return mpaaRating;
    }

    public Person getOperator() {
        return operator;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCoordinates(Coordinates coordinates) {
        this.coordinates = coordinates;
    }

    public void setCreationDate(ZonedDateTime creationDate) {
        this.creationDate = creationDate;
    }

    public void setOscarsCount(int oscarsCount) {
        this.oscarsCount = oscarsCount;
    }

    public void setGoldenPalmCount(Long goldenPalmCount) {
        this.goldenPalmCount = goldenPalmCount;
    }

    public void setGenre(MovieGenre genre) {
        this.genre = genre;
    }

    public void setMpaaRating(MpaaRating mpaaRating) {
        this.mpaaRating = mpaaRating;
    }

    public void setOperator(Person operator) {
        this.operator = operator;
    }

    public Movie(long id, String name, Coordinates coordinates, ZonedDateTime creationDate,
                 int oscarsCount, Long goldenPalmCount, MovieGenre genre, MpaaRating mpaaRating, Person operator) {
        setId(id);
        setName(name);
        setCoordinates(coordinates);
        setCreationDate(creationDate);
        setOscarsCount(oscarsCount);
        setGoldenPalmCount(goldenPalmCount);
        setGenre(genre);
        setMpaaRating(mpaaRating);
        setOperator(operator);
    }

    public Movie() {
    }

    @Override
    public String toString() {
        List<String> result = new ArrayList<>(){
            {
                add("ID: " + getId());
                add("name: " + getName());
                add("coordinates: " + getCoordinates().toString());
                add("creation date: " + getCreationDate().toString());
                add("oscars count: " + getOscarsCount());
                add("golden palm count: " + getGoldenPalmCount());
                add("genre: " + genre.toString());
                add("mpaa rating: " + mpaaRating.toString());
                add(getOperator().toString());
            }
        };
        return String.join("\n", result);

    }

    @Override
    public boolean validate(){
        if (id <= 0) return false;
        if (name == null || name.isEmpty()) return false;
        if (coordinates == null) return false;
        if (creationDate == null) return false;
        if (oscarsCount <= 0) return false;
        if (goldenPalmCount == null || goldenPalmCount <= 0) return false;
        if (genre == null) return false;
        if (mpaaRating == null) return false;
        return operator != null && operator.validate();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Movie that = (Movie) obj;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, coordinates, creationDate, oscarsCount, goldenPalmCount, genre,
                mpaaRating, operator);
    }

    @Override
    public int compareTo(Element element) {
        return Comparator.comparing(Movie::getMpaaRating, (s1, s2) -> {
            return MpaaRating.compareTo(s1, s2);
        }).thenComparing(Movie::getOscarsCount).thenComparing(Movie::getGoldenPalmCount)
                .thenComparing(Movie::getId).compare(this, (Movie) element);
    }

    @Override
    public String[] toArrayString() {
        List<String> result = new ArrayList<>(){
            {
                add(getId() + "");
                add(getName());
                add(getCoordinates().toString());
                add(getCreationDate().toString());
                add(String.valueOf(getOscarsCount()));
                add(String.valueOf(getGoldenPalmCount()));
                add(genre.toString());
                add(mpaaRating.toString());
                addAll(Arrays.asList(operator.toArrayString()));
            }
        };
        return result.toArray(new String[result.size()]);
    }

    public static Movie fromArrayString(String[] data, String[] order) {
        Movie movie = new Movie();
        Person person = new Person();

        for (int i = 0; i < data.length; i++) {
            switch (order[i].toLowerCase().replace(" ", "")) {
                case "id" -> {
                    try {
                        movie.setId(Long.parseLong(data[i]));
                    }  catch(NumberFormatException e) {movie.setId(-1);}
                }
                case "name" -> movie.setName(data[i]);
                case "coordinates" -> {
                    try {
                        movie.setCoordinates(new Coordinates(Integer.parseInt(data[i].split(" ")[0]),
                                Double.parseDouble(data[i].split(" ")[1])));
                    } catch (NumberFormatException e) {movie.setCoordinates(null);}
                }
                case "creationdate" -> {
                    try {
                        movie.setCreationDate(ZonedDateTime.parse(data[i]));
                    } catch (DateTimeParseException e) {movie.setCreationDate(null);}
                }
                case "oscarscount" -> {
                    try {
                        movie.setOscarsCount(Integer.parseInt(data[i]));
                    } catch (NumberFormatException e) {movie.setOscarsCount(-1);}
                }
                case "goldenpalmcount" -> {
                    try {
                        movie.setGoldenPalmCount(Long.parseLong(data[i]));
                    } catch (NumberFormatException e) {movie.setGoldenPalmCount(null);}
                }
                case "genre" -> {
                    try {
                        movie.setGenre(MovieGenre.getFromString(data[i]));
                    } catch (IllegalStateException e) {movie.setGenre(null);}
                }
                case "mpaarating" -> {
                    try {
                        movie.setMpaaRating(MpaaRating.getFromString(data[i]));
                    } catch(IllegalStateException e) {movie.setMpaaRating(null);}
                }
                case "operator'sname" -> person.setName(data[i]);
                case "operator'sbirthday" -> {
                    try {
                        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH);
                        person.setBirthday(formatter.parse(data[i]));
                    } catch(Exception e) {person.setBirthday(null);}
                }
                case "operator'sweight" -> {
                    try {
                        person.setWeight(Integer.parseInt(data[i]));
                    } catch (NumberFormatException e) {person.setWeight(null);}
                }
                case "operator'spassportid" -> {
                    if (data[i].toLowerCase().trim().equals("null")) person.setPassportID(null);
                    else person.setPassportID(data[i]);
                }
                case "operator'snationality" -> {
                    try {
                        person.setNationality(Country.getFromString(data[i]));
                    } catch (IllegalStateException e) {person.setNationality(null);}
                }
            }
        }
        movie.setOperator(person);
        return movie;
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}
