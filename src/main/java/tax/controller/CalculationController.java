package tax.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import tax.dto.TaxDTO;
import tax.service.RequestValidatorService;
import tax.service.TaxCalcService;

@RestController(value="tax")
public class CalculationController {

    @Autowired
    private RequestValidatorService requestValidatorService;
    @Autowired
    private TaxCalcService taxCalcService;

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<TaxDTO> getTax(
            @PathVariable String email,
            @PathVariable String taxYear,
            @PathVariable double grossSalary) {

        requestValidatorService.validateRequest(email, taxYear, grossSalary);
        TaxDTO taxDTO = taxCalcService.calculateTax(taxYear, grossSalary);

        return new ResponseEntity<>(taxDTO, HttpStatus.OK);
    }

}
