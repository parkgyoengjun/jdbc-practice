package org.example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.init.DatabasePopulatorUtils;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;

import java.sql.SQLException;

import static org.assertj.core.api.Assertions.assertThat;

public class UserDaoTest {

    @BeforeEach // 테스트 코드를 실행하기 앞서 수행해야 할 작업
    void setUp() {
        // 테스트 실행 전 SQL 먼저 실행시켜주기
        // 1 데이터소스를 실행시켜주기위한 객체 생성
        ResourceDatabasePopulator populator = new ResourceDatabasePopulator();
        /*
            공용 클래스 ResourceDatabasePopulator는 객체를 확장하고 DatabasePopulator를 구현합니다 .
            외부 리소스에 정의된 SQL 스크립트를 사용하여 데이터베이스를 채우거나 초기화하거나 정리합니다.
            DataSource를 지정하여 자동으로 SQL을 실행해준다.
         */

        // 2. 객체안에 데이터베이스를 채우기위해 스크립트 추가
        populator.addScript(new ClassPathResource("db_schema.sql"));
        // new ClassPathResource() -> classpath에 위치한 resource를 가져오는 방법
        // addScript() -> 데이터베이스를 채우기 위해 실행할 스크립트를 추가합니다.
        // db_schema.sql 이라는 스크립트 파일을 ClassPathResource 로 읽어와서 스크립트에 추가함(addScript)
        // 테스트코드 실행 전 SQL 수행하기 위해  implementation('org.springframework:spring-jdbc:5.3.22') 의존성 추가
        // -> ResourceDatabasePopulator 는 spring-jdbc:5.3.22 에 의존함

        // 3. 실행시키기
        DatabasePopulatorUtils.execute(populator, ConnectionManager.getDataSource());
        // DatabasePopulator 에 대해 주어진 것을 실행합니다
        // execute 메서드에 Data Source 가  ConnectionManager.getDataSource() 임
    }

    @Test
    void createTest() throws SQLException {
        UserDao userDao = new UserDao();

        userDao.create(new User("wizard", "password", "name", "email"));
        // create 메서드를 호출할 때 해당 값을 전달하면 Userdao 는 DB에 해당하는 유저 정보를 저장하는 역할

        User user = userDao.findByUserId("wizard");
        // 해당하는 유저 조회

        assertThat(user).isEqualTo(new User("wizard", "password", "name", "email"));
    }   // 확인
}
