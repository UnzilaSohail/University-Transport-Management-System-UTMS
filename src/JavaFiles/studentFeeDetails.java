package JavaFiles;

public class studentFeeDetails {

    private String firstName;
    private String lastName;
    private String routeName;
    private String Email;
    private String Username;
    private double fee;
    private String status;
 

    public studentFeeDetails(String firstName, String lastName, String routeName, double fee, String status) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.routeName = routeName;
        this.fee = fee;
        this.status = status;
    }

    public studentFeeDetails(String firstName2, String lastName2, String email2, String username2) {
		this.firstName=firstName2;
		this.lastName=lastName2;
		this.Email=email2;
		this.Username=username2;
	}

	public studentFeeDetails(String firstName2, String lastName2, String routeName2, String username2, String email2,
			double fee2) {
		this.firstName=firstName2;
		this.lastName=lastName2;
		this.Email=email2;
		this.Username=username2;
		 this.routeName = routeName2;
	        this.fee = fee2;
		
	}

	// Getters and setters for each field
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getRouteName() {
        return routeName;
    }

    public void setRouteName(String routeName) {
        this.routeName = routeName;
    }

    public double getFee() {
        return fee;
    }

    public void setFee(double fee) {
        this.fee = fee;
    }
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

	public String getEmail() {
		return Email;
	}

	public void setEmail(String email) {
		Email = email;
	}



	public String getUsername() {
		return Username;
	}

	public void setUsername(String username) {
		Username = username;
	}
}
