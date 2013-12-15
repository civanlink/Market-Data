package persistence;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import com.mysql.jdbc.exceptions.jdbc4.CommunicationsException;

public class DatabaseAccess
{

    private Connection connect   = null;
    private Statement  statement = null;
    private ResultSet  resultSet = null;

    public DatabaseAccess()
    {
        readDataBase();
    }

    public void insertMarketData(String symbol, String price)
    {
        String query = "INSERT INTO `marketdata`.`market_data` (`ID`, `SYMBOL`, `PRICE`, `TIMESTAMP`) VALUES (NULL, '" + symbol + "', '" + price + "', CURRENT_TIMESTAMP);";
        System.out.println("Inserting ( " + symbol + ", " + price + " )");
        try
        {
            connect.createStatement().execute(query);
        }
        catch (CommunicationsException e)
        {
            readDataBase();
            insertMarketData(symbol, price);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

    }

    public void readDataBase()
    {
        try
        {
            System.out.println("Initializing database...");
            Class.forName("com.mysql.jdbc.Driver");

            connect = DriverManager.getConnection("jdbc:mysql://localhost/marketdata?" + "user=root&password=");
        }
        catch (Exception e)
        {
            e.printStackTrace();
            System.exit(0);
        }
    }

    public void close()
    {
        try
        {
            if (resultSet != null)
            {
                resultSet.close();
            }

            if (statement != null)
            {
                statement.close();
            }

            if (connect != null)
            {
                connect.close();
            }
        }
        catch (Exception e)
        {

        }
    }

}
