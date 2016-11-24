package tax;

import org.springframework.jdbc.datasource.SingleConnectionDataSource;
import org.springframework.stereotype.Component;

@Component
public class DataSourceProvider {

    public SingleConnectionDataSource getDataSource() {
        SingleConnectionDataSource ds = new SingleConnectionDataSource();
        ds.setDriverClassName("org.postgresql.Driver");
        ds.setUrl("jdbc:postgresql://10.2.134.60:5432/alexdb");
        ds.setUsername("postgres");
        ds.setPassword("alex");

        return ds;
    }

}