package tax.repository;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.util.List;

import org.dbunit.IDatabaseTester;
import org.dbunit.JdbcDatabaseTester;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.dbunit.operation.DatabaseOperation;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Spy;
import org.mockito.runners.MockitoJUnitRunner;

import tax.DataSourceProvider;
import tax.TaxLevel;
import tax.TaxLevelRowMapper;

@RunWith(MockitoJUnitRunner.class)
public class TaxCalcRepositoryTest {

    @Spy
    private DataSourceProvider dataSourceProvider;
    @Spy
    private TaxLevelRowMapper taxLevelRowMapper;
    @InjectMocks
    private TaxCalcRepository taxCalcRepository = new TaxCalcRepository();

    private static final String JDBC_DRIVER = org.postgresql.Driver.class.getName();
    private static final String JDBC_URL = "jdbc:postgresql://10.2.134.60:5432/alexdb";
    private static final String USER = "postgres";
    private static final String PASSWORD = "alex";

    @Before
    public void importDataSet() throws Exception {
        IDataSet dataSet = readDataSet();
        cleanlyInsert(dataSet);
    }

    private IDataSet readDataSet() throws Exception {
        return new FlatXmlDataSetBuilder().build(new File("src/test/resources/dataset.xml"));
    }

    private void cleanlyInsert(IDataSet dataSet) throws Exception {
        IDatabaseTester databaseTester = new JdbcDatabaseTester(JDBC_DRIVER, JDBC_URL, USER, PASSWORD);
        databaseTester.setSetUpOperation(DatabaseOperation.CLEAN_INSERT);
        databaseTester.setDataSet(dataSet);
        databaseTester.onSetup();
    }

    @Test
    public void givenValidTaxYear_whenGettingTaxLevelsForCertainYear_thenTaxLevelsAreReturned() throws Exception {
        List<TaxLevel> taxLevels = taxCalcRepository.getTax("2014/15", 80000.0);

        assertEquals(3, taxLevels.size());
        assertEquals(10000.0, taxLevels.get(0).getCap(), 0.001);
        assertEquals("2014/15", taxLevels.get(1).getTaxYear());
        assertEquals(40.0, taxLevels.get(2).getRate(), 0.001);
    }

}
