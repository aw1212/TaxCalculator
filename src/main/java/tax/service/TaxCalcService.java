package tax.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tax.TaxLevel;
import tax.dto.TaxDTO;
import tax.repository.TaxCalcRepository;

@Service
public class TaxCalcService {

    @Autowired
    private TaxCalcRepository taxCalcRepository;

    public TaxDTO calculateTax(String taxYear, double grossSalary) {

        List<TaxLevel> taxLevels =  taxCalcRepository.getTax(taxYear, grossSalary);
        List<Double> caps = new ArrayList<>();
        Map<Double, Double> capToRate = new HashMap<>();
        for (TaxLevel taxLevel : taxLevels) {
            caps.add(taxLevel.getCap());
            capToRate.put(taxLevel.getCap(), taxLevel.getRate());
        }

        double totalTax = getTotalTax(caps, capToRate, grossSalary);

        TaxDTO taxDTO = new TaxDTO();
        taxDTO.setTax(totalTax);
        taxDTO.setTakeHomePay(grossSalary - totalTax);
        taxDTO.setTaxYear(taxYear);

        return taxDTO;
    }

    public double getTotalTax(List<Double> caps, Map<Double, Double> capToRate, double grossSalary) {
        double remain = grossSalary;
        double totalTax = 0;
        double previousCap = 0;
        for (int i = 0; i < caps.size(); i++) {
            double currentCap = caps.get(i);

            if (i == caps.size() - 1) {
                return totalTax + (remain * capToRate.get(currentCap) / 100);
            }

            double difference = currentCap - previousCap;
            totalTax += difference * (capToRate.get(currentCap) / 100);
            remain -= difference;
            previousCap = currentCap;
        }

        return totalTax;
    }

}