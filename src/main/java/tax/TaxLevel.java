package tax;

public class TaxLevel {

    private String taxYear;
    private Double cap;
    private Double rate;

    public TaxLevel(String taxYear, Double cap, Double rate) {
        this.taxYear = taxYear;
        this.cap = cap;
        this.rate = rate;
    }

    public String getTaxYear() {
        return taxYear;
    }

    public void setTaxYear(String taxYear) {
        this.taxYear = taxYear;
    }

    public Double getCap() {
        return cap;
    }

    public void setCap(Double cap) {
        this.cap = cap;
    }

    public Double getRate() {
        return rate;
    }

    public void setRate(Double rate) {
        this.rate = rate;
    }

}
