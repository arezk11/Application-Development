/**********************************************
Workshop 3
Course:APD545 Fall24
Last Name:Rezk
First Name:Ali
ID:105593222
Section:ZAA
This assignment represents my own work in accordance with Seneca Academic Policy.
Signature
Date:2024/10/27
**********************************************/
package application;

public class LoanData {
    private String vehicleType;
    private String vehicleAge;
    private double vehiclePrice;
    private double downPayment;
    private double interestRate;
    private int loanDurationMonths;
    private String paymentFrequency;

    // Constructors, getters, and setters
    public LoanData() {}

    public String getVehicleType() { return vehicleType; }
    public void setVehicleType(String vehicleType) { this.vehicleType = vehicleType; }

    public String getVehicleAge() { return vehicleAge; }
    public void setVehicleAge(String vehicleAge) { this.vehicleAge = vehicleAge; }

    public double getVehiclePrice() { return vehiclePrice; }
    public void setVehiclePrice(double vehiclePrice) { this.vehiclePrice = vehiclePrice; }

    public double getDownPayment() { return downPayment; }
    public void setDownPayment(double downPayment) { this.downPayment = downPayment; }

    public double getInterestRate() { return interestRate; }
    public void setInterestRate(double interestRate) { this.interestRate = interestRate; }

    public int getLoanDurationMonths() { return loanDurationMonths; }
    public void setLoanDurationMonths(int loanDurationMonths) { this.loanDurationMonths = loanDurationMonths; }

    public String getPaymentFrequency() { return paymentFrequency; }
    public void setPaymentFrequency(String paymentFrequency) { this.paymentFrequency = paymentFrequency; }

    // Calculate the payment based on loan parameters and payment frequency
    public double calculatePayment() {
        double loanAmount = vehiclePrice - downPayment;
        double monthlyInterestRate = (interestRate / 100) / 12;
        int numberOfPayments = loanDurationMonths;

        // Determine the number of payments and interest rate based on the frequency
        if ("Weekly".equalsIgnoreCase(paymentFrequency)) {
            numberOfPayments = loanDurationMonths * 4;
            monthlyInterestRate /= 4;
        } else if ("Bi-weekly".equalsIgnoreCase(paymentFrequency)) {
            numberOfPayments = loanDurationMonths * 2;
            monthlyInterestRate /= 2; 
        }

        if (monthlyInterestRate == 0) {
            return loanAmount / numberOfPayments;
        }

        
        return (loanAmount * monthlyInterestRate) / (1 - Math.pow(1 + monthlyInterestRate, -numberOfPayments));
    }
}
