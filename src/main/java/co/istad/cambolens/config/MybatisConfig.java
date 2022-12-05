package co.istad.cambolens.config;

import javax.sql.DataSource;

import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@MapperScan("co.istad.cambolens.data.repository")
public class MybatisConfig {
    
    // injection
    private DataSource dataSource;
   
    @Autowired
    public void setMybatisConfig(DataSource dataSource){
        this.dataSource=dataSource;
    }

    @Bean
    public SqlSessionFactoryBean sessionFactoryBean() throws Exception{
        SqlSessionFactoryBean sessionFactoryBean = new SqlSessionFactoryBean();
        sessionFactoryBean.setDataSource(dataSource);
        return sessionFactoryBean;
    }
}
