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

        //getTableData table sqlcmd
        String tableName = "user";
        manager.getTableData(tableName);

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

    public DataView[] getTableData(String tableName) throws SQLException {
        int size = getSize(tableName);

        Statement stmt = connection.createStatement();
        ResultSet rs1 = stmt.executeQuery("SELECT * FROM public." + tableName);
        ResultSetMetaData rsmd = rs1.getMetaData();
        DataView[] result = new DataView[size];
        int index = 0;
        while (rs1.next()) {
            DataView dataView = new DataView();
            result[index++] = dataView;
            for (int i = 1; i < rsmd.getColumnCount(); i++) {
                dataView.put(rsmd.getColumnName(i), rs1.getObject(i));
            }
        }
        rs1.close();
        stmt.close();
        return result;
    }

    private int getSize(String tableName) throws SQLException {
        Statement stmt = connection.createStatement();
        ResultSet rsCount = stmt.executeQuery("SELECT COUNT(*) FROM public." + tableName);
        rsCount.next();
        int size = rsCount.getInt(1);
        rsCount.close();
        return size;
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
