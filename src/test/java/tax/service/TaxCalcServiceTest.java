package tax.service;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import com.google.common.collect.ImmutableList;

import tax.TaxLevel;
import tax.dto.TaxDTO;
import tax.repository.TaxCalcRepository;

@RunWith(MockitoJUnitRunner.class)
public class TaxCalcServiceTest {

    @Mock
    private TaxCalcRepository taxCalcRepository;
    @InjectMocks
    private TaxCalcService taxCalcService = new TaxCalcService();

    @Test
    public void given2014To2015TaxLevels_whenCalculatingTax_thenTaxIsCalculated() {
        String taxYear = "2014/15";
        double grossSalary = 80000.0;
        TaxLevel firstTaxLevel = new TaxLevel(taxYear, 10000.0, 0.0);
        TaxLevel secondTaxLevel = new TaxLevel(taxYear, 41865.0, 20.0);
        TaxLevel thirdTaxLevel = new TaxLevel(taxYear, 150000.0, 40.0);
        List<TaxLevel> taxLevels = ImmutableList.of(firstTaxLevel, secondTaxLevel, thirdTaxLevel);

        when(taxCalcRepository.getTax(taxYear, grossSalary)).thenReturn(taxLevels);

        TaxDTO taxDTO = taxCalcService.calculateTax(taxYear, grossSalary);

        assertEquals(21627.0, taxDTO.getTax(), 0.001);
        assertEquals(58373.0, taxDTO.getTakeHomePay(), 0.001);
        assertEquals("2014/15", taxDTO.getTaxYear());
    }

    @Test
    public void given2015To2016TaxLevels_whenCalculatingTax_thenTaxIsCalculated() {
        String taxYear = "2015/16";
        double grossSalary = 200000.0;
        TaxLevel firstTaxLevel = new TaxLevel(taxYear, 10600.0, 0.0);
        TaxLevel secondTaxLevel = new TaxLevel(taxYear, 42385.0, 20.0);
        TaxLevel thirdTaxLevel = new TaxLevel(taxYear, 150000.0, 40.0);
        TaxLevel fourthTaxLevel = new TaxLevel(taxYear, 200000.0, 45.0);
        List<TaxLevel> taxLevels = ImmutableList.of(firstTaxLevel, secondTaxLevel, thirdTaxLevel, fourthTaxLevel);

        when(taxCalcRepository.getTax(taxYear, grossSalary)).thenReturn(taxLevels);

        TaxDTO taxDTO = taxCalcService.calculateTax(taxYear, grossSalary);

        assertEquals(71903.0, taxDTO.getTax(), 0.001);
        assertEquals(128097.0, taxDTO.getTakeHomePay(), 0.001);
        assertEquals("2015/16", taxDTO.getTaxYear());
    }

}