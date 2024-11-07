package org.example;

import com.zaxxer.hikari.HikariDataSource;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

public class ConnectionManager {
/*  1
    public static DataSource getDataSource() {

        // Hikari 데이터소스를 사용하기 위해 의존성 추가( implementation('com.zaxxer:HikariCP:5.0.1') )

        HikariDataSource hikariDataSource = new HikariDataSource();
        hikariDataSource.setDriverClassName("org.h2.Driver");
        hikariDataSource.setJdbcUrl("jdbc:h2:mem://localhost/~/jdbc-practice;MODE=MySQL;DB_CLOSE_DELAY=-1");
        hikariDataSource.setUsername("sa");
        hikariDataSource.setPassword("");
        return  hikariDataSource;

    }
 */



    // 커넥션 관련된 코드는 ConnectionManager 가 모두 처리하게해서 책임들을 모두 모았음
    private static final String DB_DRIVER = "org.h2.Driver";
    private static final String DB_URL = "jdbc:h2:mem://localhost/~/jdbc-practice;MODE=MySQL;DB_CLOSE_DELAY=-1";
    private static final String DB_USERNAME = "sa";
    private static final String DB_PW = "";
    private static final int MAX_POOL_SIZE = 40;

    private static final DataSource ds;

    static { // 커넥션풀을 하나만 가지도록 설정
        HikariDataSource hikariDataSource = new HikariDataSource();
        hikariDataSource.setDriverClassName(DB_DRIVER);
        hikariDataSource.setJdbcUrl(DB_URL);
        hikariDataSource.setUsername(DB_USERNAME);
        hikariDataSource.setPassword(DB_PW);
        hikariDataSource.setMaximumPoolSize(MAX_POOL_SIZE); // 얼만큼 커넥션을 가질지 설정
        hikariDataSource.setMinimumIdle(MAX_POOL_SIZE);

        ds = hikariDataSource;
    }

    public static Connection getConnection() {
        try {
            return ds.getConnection(); // DataSource()로부터 커넥션을 받아온다
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
    }

    public static DataSource getDataSource() {
        return ds;

    }
}