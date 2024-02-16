import java.time.ZonedDateTime;

public class Movie implements Comparable<Movie> {
    private Integer id; //Поле не может быть null, Значение поля должно быть больше 0, Значение этого поля должно быть уникальным, Значение этого поля должно генерироваться автоматически
    private String name; //Поле не может быть null, Строка не может быть пустой
    private Coordinates coordinates; //Поле не может быть null
    private java.time.ZonedDateTime creationDate; //Поле не может быть null, Значение этого поля должно генерироваться автоматически
    private int oscarsCount; //Значение поля должно быть больше 0
    private Long goldenPalmCount; //Значение поля должно быть больше 0, Поле не может быть null
    private MovieGenre genre; //Поле не может быть null
    private MpaaRating mpaaRating; //Поле может быть null
    private Person operator; //Поле не может быть null

    public Integer getId() {
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

    public void setId(Integer id) {
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

    public Movie(Integer id, String name, Coordinates coordinates, java.time.ZonedDateTime creationDate,
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

    @Override
    public int compareTo(Movie movie) {
        return Integer.compare(getId(), movie.getId());
    }
}
