package tax.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import tax.DataSourceProvider;
import tax.TaxLevel;
import tax.TaxLevelRowMapper;

@Repository
public class TaxCalcRepository {

    @Autowired
    private DataSourceProvider dataSourceProvider;
    @Autowired
    private TaxLevelRowMapper taxLevelRowMapper;

    private static final String GET_TAX_SQL = " SELECT tax_year, max_cap, rate " +
            " FROM taxes " +
            " WHERE tax_year = :taxYear " +
            " AND min_cap <= :grossSalary " +
            " ORDER BY max_cap ASC ";

    public List<TaxLevel> getTax(String taxYear, double grossSalary) {
        NamedParameterJdbcTemplate njt = new NamedParameterJdbcTemplate(new JdbcTemplate(dataSourceProvider.getDataSource()));

        MapSqlParameterSource sqlParams = new MapSqlParameterSource()
                .addValue("taxYear", taxYear)
                .addValue("grossSalary", grossSalary);

        return njt.query(GET_TAX_SQL, sqlParams, taxLevelRowMapper);
    }

}
