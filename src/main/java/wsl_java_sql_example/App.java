package wsl_java_sql_example;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.apache.commons.cli.PosixParser;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.*;  
import java.util.*;

public class App 
{
    public static void main( String[] args )
    {
        Options options = new Options();
        Option opt;

        // -hostname
        opt = new Option("H","hostname", true, "Hostname of the SQL Server");
        opt.setRequired(true);
        options.addOption(opt);

        // -username
        opt = new Option("u", "username", true, "Username of sql login");
        opt.setRequired(true);
        options.addOption(opt);

        // -password
        opt = new Option("p", "password", true, "Password of sql login");
        opt.setRequired(true);
        options.addOption(opt);

        CommandLineParser parser = new PosixParser();
        HelpFormatter formatter = new HelpFormatter();

        CommandLine cmd;
        try {
            cmd = parser.parse(options, args);
        }
        catch (ParseException parseException) {
            System.err.println(parseException.getMessage());
            formatter.printHelp("wsl_java_sql_example", options);
            System.exit(3);
            return;
        }

        if (cmd.hasOption("help")) {
            formatter.printHelp("wsl_java_sql_example", options);
            System.exit(3);
        }

        String connectionString = 
            "jdbc:sqlserver://" + cmd.getOptionValue("hostname") + ":" + "1433" + ";"
            + "database=master;"
            + "user=" + cmd.getOptionValue("username") + ";"
            + "password=" + cmd.getOptionValue("password") + ";"
            + "multisubnetfailover=true;";

        
        String serverName = null;
        
        String query = String.format("select @@servername as servername");

        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;

        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            connection = DriverManager.getConnection(connectionString);

            statement = connection.createStatement();
            resultSet = statement.executeQuery(query);

            while (resultSet.next()) { 
                serverName = resultSet.getString("servername");
            }
        }
        catch (Exception e) {
            //e.printStackTrace();
            String err = String.format("Caught exception connecting to SQL Server %s", e.getMessage());
            System.err.println(err);
            System.exit(1);
        }
        finally { 
            if (connection != null) try { connection.close(); } catch (Exception e) {}
        }

        System.out.println(serverName);
    }
}
