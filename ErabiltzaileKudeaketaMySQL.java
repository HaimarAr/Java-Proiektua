package taulausuarioa;

import java.sql.*;
import java.util.Scanner;

public class ErabiltzaileKudeaketaMySQL {
    private static final String DB_URL = "jdbc:mysql://localhost:3308/usuarioak";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "";

    public static void main(String[] args) {
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            if (connection != null) {
                System.out.println("Datu basearekin konexioa eginda.");

                while (true) {
                    System.out.println("Aukeratu ekintza:");
                    System.out.println("1. Erabiltzaile zerrenda erakutsi");
                    System.out.println("2. Erabiltzaileak aldatu");
                    System.out.println("3. Erabiltzaile berri bat gehitu");
                    System.out.println("4. Erabiltzaile bat ezabatu");
                    System.out.println("0. Irten");

                    String aukera = new Scanner(System.in).nextLine();

                    switch (aukera) {
                        case "1":
                            erabiltzaileZerrendaErakutsi(connection);
                            break;
                        case "2":
                            erabiltzaileakAldatu(connection);
                            break;
                        case "3":
                            erabiltzaileBerriaGehitu(connection);
                            break;
                        case "4":
                            erabiltzaileaEzabatu(connection);
                            break;
                        case "0":
                            System.exit(0);
                        default:
                            System.out.println("Aukera okerra.");
                    }
                }
            }
        } catch (SQLException e) {
            System.err.println("Errorea datu basearekin konexioa egiten: " + e.getMessage());
        }
    }

    private static void erabiltzaileZerrendaErakutsi(Connection connection) {
        // Hemen exekutatu SELECT kontsulta datu basean
        // Erabiltzaile zerrenda datu baseatik lortu eta erakutsi
    	 try {
             Statement statement = connection.createStatement();
             String sql = "SELECT * FROM usuarioak";
             ResultSet resultSet = statement.executeQuery(sql);
             System.out.println("Erabiltzaile zerrenda:");
             while (resultSet.next()) {
                 String id = resultSet.getString("Id");
                 String izena = resultSet.getString("Izena");
                 String abizena = resultSet.getString("Abizena");
                 String pk = resultSet.getString("KodigoPostala");
                 System.out.println("Id: " + id + " Izena: " + izena + " Abizena: " + abizena + " PostaKodea: " + pk);
             }
             resultSet.close();
             statement.close();
         } catch (SQLException e) {
             System.err.println("Errorea erabiltzaileen zerrenda irakurtzerakoan: " + e.getMessage());
         }
    }

    private static void erabiltzaileakAldatu(Connection connection) {
        // Hemen exekutatu UPDATE kontsulta datu basean
        // Erabiltzaileak aldatu datu basean
    	erabiltzaileZerrendaErakutsi(connection);
        System.out.println("Aldatu nahi duzun erabiltzailearen id-a sartu:");
        String id = new Scanner(System.in).nextLine();
        System.out.println("Aldatu nahi duzun erabiltzailearen Izena sartu:");
        String izena = new Scanner(System.in).nextLine();
        System.out.println("Aldatu nahi duzun erabiltzailearen Abizena sartu:");
        String abizena = new Scanner(System.in).nextLine();
        System.out.println("Aldatu nahi duzun erabiltzailearen PK sartu:");
        String pk = new Scanner(System.in).nextLine();
        

        try {
            String sql = "UPDATE usuarioak SET Izena=? , Abizena=? , KodigoPostala=? WHERE id=?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, izena);
            preparedStatement.setString(2, abizena);
            preparedStatement.setString(3, pk);
            preparedStatement.setString(4, id);
            int affectedRows = preparedStatement.executeUpdate();
            if (affectedRows > 0) {
                System.out.println("Erabiltzailearen datuak aldatu dira.");
            } else {
                System.out.println("Ez da id-a topatu.");
            }
            preparedStatement.close();
        } catch (SQLException e) {
            System.err.println("Errorea erabiltzailea aldatzerakoan: " + e.getMessage());
        }
    }
    

    private static void erabiltzaileBerriaGehitu(Connection connection) {
        // Hemen exekutatu INSERT kontsulta datu basean
        // Erabiltzaile berria gehitu datu basean
    	System.out.println("Erabiltzaile berriaren id-a sartu:");
        String id = new Scanner(System.in).nextLine();
        System.out.println("Erabiltzailearen izena sartu:");
        String izena = new Scanner(System.in).nextLine();
        System.out.println("Erabiltzailearen abizena sartu:");
        String abizena = new Scanner(System.in).nextLine();
        System.out.println("Erabiltzailearen PostaKodea sartu:");
        String pk = new Scanner(System.in).nextLine();

        try {
            String sql = "INSERT INTO usuarioak (id, Izena, Abizena, KodigoPostala) VALUES (?, ?, ?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, id);
            preparedStatement.setString(2, izena);
            preparedStatement.setString(3, abizena);
            preparedStatement.setString(4, pk);
            int affectedRows = preparedStatement.executeUpdate();
            if (affectedRows > 0) {
                System.out.println("Erabiltzailea ongi gehitu da.");
            }
            preparedStatement.close();
        } catch (SQLException e) {
            System.err.println("Errorea erabiltzaile berria gehitzerakoan: " + e.getMessage());
        }
    }

    private static void erabiltzaileaEzabatu(Connection connection) {
        // Hemen exekutatu DELETE kontsulta datu basean
        // Erabiltzailea ezabatu datu basean
    	erabiltzaileZerrendaErakutsi(connection);
        System.out.println("Ezabatu nahi duzun erabiltzailearen id-a sartu:");
        String id = new Scanner(System.in).nextLine();

        try {
            String sql = "DELETE FROM usuarioak WHERE id=?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, id);
            int affectedRows = preparedStatement.executeUpdate();
            if (affectedRows > 0) {
                System.out.println("Erabiltzailea ezabatuta dago.");
            } else {
                System.out.println("Erabiltzailea ez da topatu.");
            }
            preparedStatement.close();
        } catch (SQLException e) {
            System.err.println("Errorea erabiltzailea ezabatzerakoan: " + e.getMessage());
        }
    }
}

       
