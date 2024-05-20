package main.java.managers;

import main.java.models.*;
import main.java.utility.CSVReader;
import main.java.utility.CSVWriter;
import main.java.utility.Console;
import main.java.utility.Crypto;

import java.sql.Date;
import java.sql.DriverManager;
import java.util.*;
import java.sql.*;
import java.time.ZoneId;


public class DumpManager {
    private Console console;
    private String url;
    private final String userConnection = "postgres";
    private final String passwordConnection = "George0404";
    private final String zoneId = "America/Montreal";
    private final String[] columns = {"ID", "name", "coordinates", "creation date", "oscars count", "golden palm count",
            "genre", "mpaa rating", "operator's name", "operator's birthday",
            "operator's weight", "operator's passportID", "operator's nationality"};

    private DumpManager() {
    }

    public void setConsole(Console console) {
        this.console = console;
    }

    private static class DumpManagerHolder{
        private static final DumpManager HOLDER_INSTANCE = new DumpManager();
    }

    public static DumpManager getInstance() {
        return DumpManagerHolder.HOLDER_INSTANCE;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public boolean setDriver(String driver) {
        try {
            Class.forName(driver);
            return true;
        } catch (ClassNotFoundException exception) {
            console.printError("Database's driver hasn't been found");
            return false;
        }
    }

    private String collectionToCSV(Collection<Movie> collection) {
        try {
            CSVWriter writer = new CSVWriter(";");
            writer.writeColumnsName(columns);
            for(var element: collection) {
                writer.writeNext(element);
            }
            return writer.getCsvRow();
        } catch (Exception e) {
            console.printError("Serialization error");
            return null;
        }
    }

    private TreeSet<Movie> CSVToCollection(String s) {
        try {
            CSVReader reader = new CSVReader(";");
            reader.setCsvRow(s);
            TreeSet<Movie> collection = new TreeSet<>();
            String[] order = (reader.hasNextLine()) ? reader.readNextLine() : columns;
            String[] record;
            while (reader.hasNextLine()) {
                record = reader.readNextLine();
                Movie movie = Movie.fromArrayString(record, order);
                if (movie.validate())
                    collection.add(movie);
                else
                    console.printError("File with collection has invalid data");
            }
            return collection;
        } catch (Exception e) {
            console.printError("Deserialization error");
            return null;
        }
    }

    public int registerUser(User user) {

        short alreadyExists = checkUserExistence(user);
        if (alreadyExists == -1) return -1;
        else if (alreadyExists == 1) return 0;

        Crypto crypto = Crypto.getInstance();
        String salt = crypto.getSalt(7);
        String hashPassword = crypto.getHashCodeSHA224(user.password(), salt);

        try (Connection connection = DriverManager.getConnection(url, userConnection, passwordConnection)) {
            String statement = "INSERT INTO Users(name, password, salt) VALUES(?, ?, ?)";
            try (PreparedStatement preparedStatement = connection.prepareStatement(statement, Statement.RETURN_GENERATED_KEYS)) {
                preparedStatement.setString(1, user.name());
                preparedStatement.setString(2, hashPassword);
                preparedStatement.setString(3, salt);
                int cntRows = preparedStatement.executeUpdate();
                ResultSet resultSet = preparedStatement.getGeneratedKeys();
                if (cntRows == 1 && resultSet.next()) return resultSet.getInt("id");
                return -1;
            }
        } catch (SQLException e) {
            return -1;
        }
    }

    public int autorizateUser(User user) {
        try (Connection connection = DriverManager.getConnection(url, userConnection, passwordConnection)) {
            String statement = "SELECT * FROM Users WHERE name = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(statement)) {
                preparedStatement.setString(1, user.name());
                ResultSet resultSet = preparedStatement.executeQuery();
                if (resultSet.next()) {
                    Crypto crypto = Crypto.getInstance();
                    if (crypto.getHashCodeSHA224(user.password(), resultSet.getString("salt")).equals(resultSet.getString("password"))) {
                        return resultSet.getInt("id");
                    }
                }
                return 0;
            }
        } catch (SQLException e) {
            return 0;
        }
    }

    public short checkUserExistence(User user) {
        if (user == null) return 0;
        try (Connection connection = DriverManager.getConnection(url, userConnection, passwordConnection)) {
            String statement = "SELECT * FROM Users WHERE name = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(statement)) {
                preparedStatement.setString(1, user.name());
                ResultSet resultSet = preparedStatement.executeQuery();
                if (resultSet.next()) return 1;
                return 0;
            }
        } catch (SQLException exception) {
            return -1;
        }
    }

    public short checkRightsForChange(User user, long id) {
        if (user == null) return 0;
        if (user.name().equals("admin1")) return 1;
        try (Connection connection = DriverManager.getConnection(url, userConnection, passwordConnection)) {
            String statement = "SELECT * FROM Movies WHERE id = ?";
            Integer userId = getUserId(user);
            if (userId == null) return -1;
            try (PreparedStatement preparedStatement = connection.prepareStatement(statement)) {
                preparedStatement.setLong(1, id);
                ResultSet resultSet = preparedStatement.executeQuery();
                if (resultSet.next()) {
                    return (short) (resultSet.getInt("user_id") == userId ? 1 : 0);
                }
            }
            return -1;
        } catch (SQLException exception) {
            return -1;
        }
    }

    public long addElement(User user, Movie movie) {
        try (Connection connection = DriverManager.getConnection(url, userConnection, passwordConnection)) {
            Integer userId = getUserId(user);
            if (userId == null) return -1;
            String statement = "INSERT INTO Movies(name, coordinate_x, coordinate_y, creation_date, oscars_count, golden_palm_count," +
                    "genre, mpaa_rating, operators_name, operators_birthday, operators_weight, operators_passport_id, operators_nationality, user_id)" +
                    " VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            try (PreparedStatement preparedStatement = connection.prepareStatement(statement, Statement.RETURN_GENERATED_KEYS)) {
                preparedStatement.setString(1, movie.getName());
                preparedStatement.setInt(2, movie.getCoordinates().getX());
                preparedStatement.setDouble(3, movie.getCoordinates().getY());
                preparedStatement.setDate(4, java.sql.Date.valueOf(movie.getCreationDate().toLocalDate()));
                preparedStatement.setInt(5, movie.getOscarsCount());
                preparedStatement.setLong(6, movie.getGoldenPalmCount());
                preparedStatement.setString(7, movie.getGenre().toString());
                preparedStatement.setString(8, movie.getMpaaRating().toString());
                preparedStatement.setString(9, movie.getOperator().getName());
                preparedStatement.setDate(10, java.sql.Date.valueOf(movie.getOperator().getBirthday().toInstant().atZone(ZoneId.of( "Africa/Tunis" ) )  // Adjust from UTC to a particular time zone, to determine a date. Instantiating a `ZonedDateTime`.
                        .toLocalDate()));
                preparedStatement.setInt(11, movie.getOperator().getWeight());
                if (movie.getOperator().getPassportID() == null) preparedStatement.setNull(12, Types.VARCHAR);
                else preparedStatement.setString(12, movie.getOperator().getPassportID());
                preparedStatement.setString(13, movie.getOperator().getNationality().toString());
                preparedStatement.setInt(14, userId);
                int addInt = preparedStatement.executeUpdate();
                ResultSet resultSet = preparedStatement.getGeneratedKeys();
                if (addInt == 1 && resultSet.next()) {
                    return resultSet.getInt("id");
                }
            }
            return -1;
        } catch (SQLException exception) {
            return -1;
        }
    }

    public Integer getUserId(User user) {
        try (Connection connection = DriverManager.getConnection(url, userConnection, passwordConnection)) {
            String userStatement = "SELECT id FROM Users WHERE name = ?";
            try (PreparedStatement userPreparedStatement = connection.prepareStatement(userStatement)) {
                userPreparedStatement.setString(1, user.name());
                ResultSet userResult = userPreparedStatement.executeQuery();
                if (userResult.next()) {
                    return userResult.getInt("id");
                }
            }
            return null;
        } catch (SQLException exception) {
            return null;
        }
    }

    public Collection<Movie> getCollection() {
        try (Connection connection = DriverManager.getConnection(url, userConnection, passwordConnection)) {
            String statement = "SELECT * FROM Movies";
            try (Statement preparedStatement = connection.createStatement()) {
                ResultSet resultSet = preparedStatement.executeQuery(statement);
                List<Movie> collection = new ArrayList<>();
                while (resultSet.next()) {
                    collection.add(new Movie(resultSet.getLong("id"), resultSet.getString("name"),
                            new Coordinates(resultSet.getInt("coordinate_x"), resultSet.getDouble("coordinate_y")),
                            resultSet.getDate("creation_date").toLocalDate().atStartOfDay( ZoneId.of(zoneId) ), resultSet.getInt("oscars_count"),
                            resultSet.getLong("golden_palm_count"), MovieGenre.getFromString(resultSet.getString("genre")),
                            MpaaRating.getFromString(resultSet.getString("mpaa_rating")), new Person(resultSet.getString("operators_name"),
                            new Date(resultSet.getDate("operators_birthday").getTime()), resultSet.getInt("operators_weight"),
                            resultSet.getString("operators_passport_id"), Country.getFromString(resultSet.getString("operators_nationality")))));

                }
                return collection;
            }
        } catch (SQLException exception) {
            return null;
        }
    }

    public short removeElementById(long id) {
        try (Connection connection = DriverManager.getConnection(url, userConnection, passwordConnection)) {
            String statement = "DELETE FROM Movies WHERE id = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(statement)) {
                preparedStatement.setLong(1, id);
                int deleteRow = preparedStatement.executeUpdate();
                return (short) deleteRow;
            }
        } catch (SQLException exception) {
            return -1;
        }
    }

    public boolean clear(User user) {
        try (Connection connection = DriverManager.getConnection(url, userConnection, passwordConnection)) {
            String statement = "DELETE FROM Movies WHERE user_id IN (SELECT id FROM Users WHERE name = ?)";
            try (PreparedStatement preparedStatement = connection.prepareStatement(statement)) {
                preparedStatement.setString(1, user.name());
                int countRow = preparedStatement.executeUpdate();
                return countRow != 0;
            }
        } catch (SQLException exception) {
            return false;
        }
    }

    public boolean removeGreater(User user, Movie movie) {
        try (Connection connection = DriverManager.getConnection(url, userConnection, passwordConnection)) {
            String statement_mpaa = "SELECT id FROM MpaaRating WHERE rating = ?";
            String statement = "DELETE FROM Movies WHERE user_id IN (SELECT id FROM Users WHERE name = ?) AND " +
                    "((SELECT MIN(id) FROM MpaaRating WHERE rating = mpaa_rating) > ? OR " +
                    "((SELECT MIN(id) FROM MpaaRating WHERE rating = mpaa_rating) = ? AND oscars_count > ?) OR ((SELECT MIN(id) FROM MpaaRating WHERE rating = mpaa_rating) = ? AND oscars_count = ? AND golden_palm_count > ?))";
            try (PreparedStatement preparedStatement = connection.prepareStatement(statement); PreparedStatement preparedStatement1 = connection.prepareStatement(statement_mpaa)) {
                preparedStatement1.setString(1, movie.getMpaaRating().toString());
                int id_mpaa = 1;
                ResultSet resultSet1 = preparedStatement1.executeQuery();
                if (resultSet1.next()) id_mpaa = resultSet1.getInt("id");
                preparedStatement.setString(1, user.name());
                preparedStatement.setInt(2, id_mpaa);
                preparedStatement.setInt(3, id_mpaa);
                preparedStatement.setInt(4, movie.getOscarsCount());
                preparedStatement.setInt(5, id_mpaa);
                preparedStatement.setInt(6, movie.getOscarsCount());
                preparedStatement.setLong(7, movie.getGoldenPalmCount());
                int countRow = preparedStatement.executeUpdate();
                return countRow != 0;
            }
        } catch (SQLException exception) {
            return false;
        }
    }

    public boolean updateElement(Movie movie) {
        try (Connection connection = DriverManager.getConnection(url, userConnection, passwordConnection)) {
            String statement = "UPDATE Movies " +
                    "SET name = ?, coordinate_x = ?, coordinate_y = ?, oscars_count = ?, golden_palm_count = ?," +
                    "genre = ?, mpaa_rating = ?, operators_name = ?, operators_birthday = ?, operators_weight = ?, operators_passport_id = ?, operators_nationality = ? " +
                    "WHERE id = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(statement)) {
                preparedStatement.setString(1, movie.getName());
                preparedStatement.setInt(2, movie.getCoordinates().getX());
                preparedStatement.setDouble(3, movie.getCoordinates().getY());
                preparedStatement.setInt(4, movie.getOscarsCount());
                preparedStatement.setLong(5, movie.getGoldenPalmCount());
                preparedStatement.setString(6, movie.getGenre().toString());
                preparedStatement.setString(7, movie.getMpaaRating().toString());
                preparedStatement.setString(8, movie.getOperator().getName());
                preparedStatement.setDate(9, java.sql.Date.valueOf(movie.getOperator().getBirthday().toInstant().atZone(ZoneId.of( "Africa/Tunis" ) )  // Adjust from UTC to a particular time zone, to determine a date. Instantiating a `ZonedDateTime`.
                        .toLocalDate()));
                preparedStatement.setInt(10, movie.getOperator().getWeight());
                if (movie.getOperator().getPassportID() == null) preparedStatement.setNull(11, Types.VARCHAR);
                else preparedStatement.setString(11, movie.getOperator().getPassportID());
                preparedStatement.setString(12, movie.getOperator().getNationality().toString());
                preparedStatement.setInt(13, (int) movie.getId());
                int updateInt = preparedStatement.executeUpdate();
                if (updateInt == 1) {
                    return true;
                }
            }
            return false;
        } catch (SQLException exception) {
            return false;
        }
    }

}
