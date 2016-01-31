package ua.com.SqlCmd.model; /**
 * Created by Тарас on 12.01.2016.
 */

import java.sql.*;
import java.util.Arrays;

public class JDBCManager implements dbManager {
    private Connection connection;

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
            return null;
        }
    }

    public void connect(String database, String userName, String password) {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Please add jdbc jar to project.", e);
        }
        try {
            connection = DriverManager.getConnection(
                    "jdbc:postgresql://localhost/" + database, userName, password);
        } catch (SQLException e) {
            connection = null;
            throw new RuntimeException(String.format("Cant get Connection for database: %s user: %s",
                    database, userName),
                    e);
        }
    }

    public DataView[] getTableData(String tableName) {
        try {
            int size = getSize(tableName);

            Statement stmt = connection.createStatement();
            ResultSet rs1 = stmt.executeQuery("SELECT * FROM public." + tableName);
            ResultSetMetaData rsmd = rs1.getMetaData();
            DataView[] result = new DataView[size];
            int index = 0;
            while (rs1.next()) {
                DataView dataView = new DataView();
                result[index++] = dataView;
                for (int i = 1; i <= rsmd.getColumnCount(); i++) {
                    dataView.put(rsmd.getColumnName(i), rs1.getObject(i));
                }
            }
            rs1.close();
            stmt.close();
            return result;
        } catch (SQLException e) {
            e.printStackTrace();
            return new DataView[0];
        }
    }

    private int getSize(String tableName) throws SQLException {
        Statement stmt = connection.createStatement();
        ResultSet rsCount = stmt.executeQuery("SELECT COUNT(*) FROM public." + tableName);
        rsCount.next();
        int size = rsCount.getInt(1);
        rsCount.close();
        return size;
    }

    public void update(String tableName, int id, DataView newValue) {
        try {
            String tableNames = getNameFormated(newValue, "%s = ?,");

            PreparedStatement ps = connection.prepareStatement("UPDATE public." + tableName +
                    " SET " + tableNames + " WHERE id = ?");

            int index = 1;
            for(Object value : newValue.getValues()) {
                ps.setObject(index, value);
                index++;
            }
            ps.setInt(index, id);

            ps.executeUpdate();
            ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private String getNameFormated(DataView newValue, String format) {
        String string = "";
        for (String name : newValue.getNames()) {
            string += String.format(format, name);
        }
        string = string.substring(0, string.length() - 1);
        return string;
    }

    public void clear(String tableName) {
        try {
            Statement stmt = connection.createStatement();
            stmt.executeUpdate("DELETE FROM public." + tableName);
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void create(DataView input) {
        try {
            Statement stmt = connection.createStatement();

            String tableNames = getNameFormated(input, "%s,");
            String values = getValuesFormated(input, "'%s',");

            stmt.executeUpdate("INSERT INTO public.user (" + tableNames + ")" +
                    "VALUES (" + values + ")");
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public String[] getTableColumns(String tableName) {
        try {
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM information_schema.columns WHERE" +
                    " table_schema = 'public' AND table_name = '" + tableName + "'");
            String[] tables = new String[100];
            int index = 0;
            while (rs.next()) {
                tables[index++] = rs.getString("column_name");
            }
            tables = Arrays.copyOf(tables, index, String[].class);
            stmt.close();
            rs.close();
            return tables;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public boolean isConnected() {
        return connection != null;
    }

    private String getValuesFormated(DataView input, String format) {
        String values = "";
        for (Object value : input.getValues()) {
            values += String.format(format, value);
        }
        values = values.substring(0, values.length() - 1);
        return values;
    }
}
