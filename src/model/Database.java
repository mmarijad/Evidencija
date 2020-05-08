package model;

import java.sql.*;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@Retention(RetentionPolicy.RUNTIME)
@Repeatable(RepetedEntity.class)
@interface Entity {
    String type();
    int size() default 0;
    boolean primary() default false;
    boolean isnull() default true;
}

@Retention(RetentionPolicy.RUNTIME)
@Repeatable(RepetedForeignKey.class)
@interface ForeignKey {
    String table();
    String attribute();
}

@Retention(RetentionPolicy.RUNTIME)
@interface RepetedEntity{
    Entity[] value();
}

@Retention(RetentionPolicy.RUNTIME)
@interface RepetedForeignKey{
    ForeignKey[] value();
}

public class Database{

    private static String hostname = "localhost";
    private static String user = "root";
    private static String password = "";
    private static String database = "pjava";

    public static Connection CONNECTION = null;

    static {
        try {
            CONNECTION = DriverManager.getConnection(

                    "jdbc:mysql://"+ hostname +":3306/"+database+"?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", user, password

            );
        } catch (SQLException e) {
            System.out.println("Nisam se uspio spojiti na bazu podataka: " + e.getMessage());
        }
    }
}