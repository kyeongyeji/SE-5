package Information;

public class Working extends Scholar{
	public String Working_type;
	public int std_income;	//standard of income
	public int Standard;	//standard of score
	
	public Working(String name) {
		this.name = name;
		// TODO Auto-generated constructor stub
	}
	
	public int getStd_income() {
		return std_income;
	}

	public void setStd_income(int std_income) {
		this.std_income = std_income;
	}

	public String getWorking_type() {
		return Working_type;
	}

	public void setWorking_type(String working_type) {
		Working_type = working_type;
	}
	
}