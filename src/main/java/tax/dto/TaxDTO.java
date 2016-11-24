package tax.dto;

public class TaxDTO {

    private double tax;
    private double takeHomePay;
    private String taxYear;

    public double getTax() {
        return tax;
    }

    public void setTax(double tax) {
        this.tax = tax;
    }

    public double getTakeHomePay() {
        return takeHomePay;
    }

    public void setTakeHomePay(double takeHomePay) {
        this.takeHomePay = takeHomePay;
    }

    public String getTaxYear() {
        return taxYear;
    }

    public void setTaxYear(String taxYear) {
        this.taxYear = taxYear;
    }
}
