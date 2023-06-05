public class Customer {
	private int id;
	private String name;
	private String phoneNumber;
	private String currentAddress;
	private String energyTariff;
	private String meterType;

	// Constructor
	public Customer(int id, String name, String phoneNumber, String currentAddress, String energyTariff, String meterType) {
		this.id = id;
		this.name = name;
		this.phoneNumber = phoneNumber;
		this.currentAddress = currentAddress;
		this.energyTariff = energyTariff;
		this.meterType = meterType;
	}

	// Getters and setters
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getCurrentAddress() {
		return currentAddress;
	}

	public void setCurrentAddress(String currentAddress) {
		this.currentAddress = currentAddress;
	}

	public String getEnergyTariff() {
		return energyTariff;
	}

	public void setEnergyTariff(String energyTariff) {
		this.energyTariff = energyTariff;
	}

	public String getMeterType() {
		return meterType;
	}

	public void setMeterType(String meterType) {
		this.meterType = meterType;
	}
}
