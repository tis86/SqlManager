/**
 * Created by Тарас on 12.01.2016.
 */
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.*;
import java.util.Arrays;
import java.util.Random;

public class Manager {
    private Connection connection;

    public static void main(String[] args) throws ClassNotFoundException, SQLException, IOException {
        //connect
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Welcome to SqlCMD!");
        System.out.println("database name: ");
        String database = reader.readLine();
        System.out.println("username: ");
        String user = reader.readLine();
        System.out.println("password: ");
        String password = reader.readLine();
        System.out.println("we can continue!");

        Manager manager = new Manager();

        manager.connect(database, user, password);

        Connection connection = manager.getConnection();

        //table list
        String[] tables = manager.getTableNames();
        System.out.println(Arrays.toString(tables));

        //select table sqlcmd
        String tableName = "user";
        String select = "SELECT * FROM public." + tableName;
       /* String rsCount = "SELECT COUNT(*) FROM public." + tableName;
        select(connection, rsCount);
       */
        manager.select(select);

        //update table sqlcmd
        String insert = "INSERT INTO public.user (name, password, id) VALUES" +
                "('Lilya', '123qwe', '5') ";
        manager.update(insert);

        //update table sqlcmd
        String update = "UPDATE public.user " +
                "SET password = ? WHERE id > 0";
        manager.modify(update);

        //delete table sqlcmd
        String delete = "DELETE FROM public.user " +
                "WHERE id > 3";
        manager.update(delete);

        connection.close();
    }

    public String[] getTableNames() {
        try {
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT TABLE_NAME FROM information_schema.tables WHERE" +
                    " table_schema = 'public' AND table_type = 'BASE TABLE'");
            String[] tables = new String[100];
            int index = 0;
            while (rs.next()) {
                tables[index++] = rs.getString("table_name");
            }
            tables = Arrays.copyOf(tables, index, String[].class);
            stmt.close();
            rs.close();
            return tables;
        } catch (SQLException e) {
            e.printStackTrace();
            return new String[0];
        }
    }

    public void modify(String update) {
            try {
                PreparedStatement ps = connection.prepareStatement(update);
                ps.setString(1, "password_" + new Random().nextInt());
                ps.executeUpdate();
                ps.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
    }

    public void connect(String database, String user, String password) {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            System.out.println("Please add jdbc jar to project.");
            e.printStackTrace();
        }
        try {
            connection = DriverManager.getConnection(
                    "jdbc:postgresql://localhost/" + database, user, password);
        } catch (SQLException e) {
            System.out.println(String.format("Cant get Connection for database: %s user: %s", database, user));
            e.printStackTrace();
            connection = null;
        }
    }

    public void select(String sql) {
        try {
            Statement stmt;
            stmt = connection.createStatement();
            ResultSet rsCount = stmt.executeQuery("SELECT COUNT(*) FROM public.user");
            rsCount.next();
            ResultSet rs1 = stmt.executeQuery(sql);
            while (rs1.next()) {
                System.out.println("id: " + rs1.getString("id"));
                System.out.println("name: " + rs1.getString("name"));
                System.out.println("password: " + rs1.getString("password"));
                System.out.println("-----------------------------");
            }
            rs1.close();
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void update(String sql) {
        try {
            Statement stmt;
            stmt = connection.createStatement();
            stmt.executeUpdate(sql);
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private Connection getConnection() {
        return connection;
    }
}
