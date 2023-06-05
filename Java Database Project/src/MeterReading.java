import java.util.Date;

public class MeterReading {
	private int id;
	private int customerId;
	private Date readingDate;
	private double readingValue;

	// Constructor
	public MeterReading(int id, int customerId, Date readingDate, double readingValue) {
		this.id = id;
		this.customerId = customerId;
		this.readingDate = readingDate;
		this.readingValue = readingValue;
	}

	// Getters and setters
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getCustomerId() {
		return customerId;
	}

	public void setCustomerId(int customerId) {
		this.customerId = customerId;
	}

	public Date getReadingDate() {
		return readingDate;
	}

	public void setReadingDate(Date readingDate) {
		this.readingDate = readingDate;
	}

	public double getReadingValue() {
		return readingValue;
	}

	public void setReadingValue(double readingValue) {
		this.readingValue = readingValue;
	}
}
