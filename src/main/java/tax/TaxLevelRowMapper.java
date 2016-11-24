package tax;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

@Component
public class TaxLevelRowMapper implements RowMapper<TaxLevel> {

    @Override
    public TaxLevel mapRow(final ResultSet rs, final int rowNum) throws SQLException {
        String taxYear = rs.getString("tax_year");
        Double cap = rs.getDouble("max_cap");
        Double rate = rs.getDouble("rate");

        return new TaxLevel(taxYear, cap, rate);
    }

}
