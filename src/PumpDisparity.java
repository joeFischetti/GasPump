public class PumpDisparity{
	
	//Private variables for member info
	//
	private String date;
	private String time;
	private String expectedReading;
	private String actualReading;
	private String expectedMember;
	private String actualMember;
		
	
	//Default constructor
	//
	public PumpDisparity(){
		
		date = new String();
		time = new String();
		expectedReading = new String();
		actualReading = new String();
		expectedMember = new String();
		actualMember = new String();
	}
	
	
	//Getter methods
	//
	public String getDate(){
		return date;
	}
	
	public String getTime(){
		return time;
	}
	
	public String getExpectedReading(){
		return expectedReading;
	}
	
	public String getActualReading(){
		return actualReading;
	}
	
	public String getExpectedMember(){
		return expectedMember;
	}
	
	public String getActualMember(){
		return actualMember;
	}
	
	public String toString(){
		
		return new String(date +"\n"+ time +"\n"+ expectedReading 
			+"\n"+ actualReading +"\n"+ 
			expectedMember +"\n"+ actualMember);
		
	}
		
	public void setDate(String input){
		date = input;
	}
	
	public void setTime(String input){
		time = input;
	}
		
	public void setExpectedReading(String input){
		expectedReading = input;
	}
	
	public void setActualReading(String input){
		actualReading = input;
	}
	
	public void setExpectedMember(String input){
		expectedMember = input;
	}
	
	public void setActualMember(String input){
		actualMember = input;
	}

}