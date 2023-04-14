package hello.jdbc.connection;

import com.zaxxer.hikari.HikariDataSource;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import static hello.jdbc.connection.ConnectionConst.*;

@Slf4j
public class ConnectionTest {

    @Test
    void driverManger() throws SQLException {
        Connection con1 = DriverManager.getConnection(URL, USERNAME, PASSWORD);
        Connection con2 = DriverManager.getConnection(URL, USERNAME, PASSWORD);
        log.info("connection={}, class={}", con1, con1.getClass());
        log.info("connection={}, class={}", con2, con2.getClass());

    }

    @Test
    void dataSourceDriverManager() throws SQLException {
        // 처음 생성할 때에만 URL, USERNAME, PASSWORD 같은 파라미터를 전달하고, 이후에 사용할 경우에는 단순히 사용하기만 하면 된다.
        // 설정과 사용의 분리
        DataSource dataSource = new DriverManagerDataSource(URL, USERNAME, PASSWORD);
        userDataSource(dataSource);
    }

    @Test
    void dataSourceConnectionPool() throws SQLException, InterruptedException {
        HikariDataSource dataSource = new HikariDataSource();
        dataSource.setJdbcUrl(URL);
        dataSource.setUsername(USERNAME);
        dataSource.setPassword(PASSWORD);
        dataSource.setMaximumPoolSize(10);
        dataSource.setPoolName("MyPool");

        userDataSource(dataSource);
        // 별도의 쓰레드에서 생성하기 때문에 로그를 보기 힘들다.
        // 따라서 1초 정도 시간을 줌
        // 별도의 쓰레드에서 생성하는 이유 : 커넥션 풀에 커넥션을 채우는 것은 오래 걸리므로 애플리케이션 실행 시간에 영향을 주지 않기 위해서이다.
        Thread.sleep(1000);
    }

    private void userDataSource(DataSource dataSource) throws SQLException {
        Connection con1 = dataSource.getConnection();
        Connection con2 = dataSource.getConnection();
    }
}
