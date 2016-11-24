package tax.service;

import java.util.Calendar;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;

import org.springframework.stereotype.Service;

import tax.controller.exceptions.InvalidRequestParameterException;

@Service
public class RequestValidatorService {

    private static final double MIN_SALARY = 1.0;
    private static final double MAX_SALARY = 200000.0;

    public void validateRequest(String email, String taxYear, double grossSalary) {
        validateEmail(email);
        validateTaxYear(taxYear);
        validateGrossSalary(grossSalary);
    }

    private void validateEmail(String email) {
        try {
            InternetAddress emailAddr = new InternetAddress(email);
            emailAddr.validate();
        } catch (AddressException ex) {
            throw new InvalidRequestParameterException("Please provide valid email");
        }
    }

    private void validateTaxYear(String taxYear) {
        Calendar now = Calendar.getInstance();
        int currentYear = now.get(Calendar.YEAR);
        if (getTaxYearAsInt(taxYear) > currentYear) {
            throw new InvalidRequestParameterException("No information for future tax years");
        } else if (getTaxYearAsInt(taxYear) + 1 < currentYear) {
            throw new InvalidRequestParameterException("tax information only available for current and past tax years");
        }
    }

    private int getTaxYearAsInt(String taxYear) {
        String taxYearStart = taxYear.substring(0, 2);
        String taxYearEnd = taxYear.substring(5);
        return Integer.valueOf(taxYearStart + taxYearEnd);
    }

    private void validateGrossSalary(double grossSalary) {
        if (grossSalary < MIN_SALARY || grossSalary > MAX_SALARY) {
            throw new InvalidRequestParameterException("Not valid salary for this application");
        }
    }
}
