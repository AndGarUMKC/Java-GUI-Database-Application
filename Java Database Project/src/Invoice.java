import java.time.LocalDate;

public class Invoice {
	private int id;
	private Customer customer;
	private LocalDate invoiceDate;
	private double totalAmount;
	private boolean isPaid;

	public Invoice(int id, Customer customer, LocalDate invoiceDate, double totalAmount, boolean isPaid) {
		this.id = id;
		this.customer = customer;
		this.invoiceDate = invoiceDate;
		this.totalAmount = totalAmount;
		this.isPaid = isPaid;
	}

	public int getId() {
		return id;
	}

	public Customer getCustomer() {
		return customer;
	}

	public LocalDate getInvoiceDate() {
		return invoiceDate;
	}

	public double getTotalAmount() {
		return totalAmount;
	}

	public boolean isPaid() {
		return isPaid;
	}

	public void setPaid(boolean paid) {
		isPaid = paid;
	}
}
