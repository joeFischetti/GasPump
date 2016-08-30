//------------------------------------------------------------------------------
//This is the club member class.  This class holds membership info
//	that gets loaded from the database
//
//------------------------------------------------------------------------------

import java.math.BigDecimal;
import java.text.DecimalFormat;

public class Transaction{
	
	//Private variables for member info
	//
	private String trans_num;
	private String member_id;
	private String date;
	private String time;
	
	private BigDecimal price;
	private BigDecimal pump_start;
	private BigDecimal pump_end;
	private BigDecimal price_paid;
	private BigDecimal num_gallons;
		
	private DecimalFormat money;
	private DecimalFormat meter;
	
	//Default constructor
	//
	public Transaction(){
		
		trans_num = new String();
		member_id = new String();
		date = new String();
		time = new String();
		price = new BigDecimal("1");
		pump_start = new BigDecimal("1");
		pump_end = new BigDecimal("1");
		num_gallons = new BigDecimal("1");
		price_paid = new BigDecimal("1");
		
		money = new DecimalFormat("$#.##");
		money.setMinimumFractionDigits(2);
		money.setParseBigDecimal(true);
		
		meter = new DecimalFormat("#.#");
		meter.setParseBigDecimal(true);
		
	}
	
	
	//Getter methods
	//
	public String getTransNum(){
		return trans_num;
	}
	
	public String getMemberID(){
		return member_id;
	}
	
	public String getDate(){
		return date;
	}
	
	public String getTime(){
		return time;
	}
	
	public BigDecimal getPrice(){
		return price;
		
	}
	
	public BigDecimal getPumpStart(){
		return pump_start;
		
	}
	
	public BigDecimal getPumpEnd(){
		return pump_end;
	}
	
	public BigDecimal getNumberGallons(){
		return num_gallons;
	}
	
	public BigDecimal getTransactionTotal(){
		return price_paid;
	}
	
	public String toString(){
		
		return new String(trans_num +"\n"+ member_id +"\n"+ date 
			+"\n"+ time +"\n"+ 
			price +"\n"+ pump_start +"\n"+ pump_end +"\n"+ 
			num_gallons +"\n"+ price_paid);
		
	}
		
	public void setPumpStart(String input){
		try{
			pump_start = (BigDecimal)(meter.parse(input));
		}
		catch(Exception e){
			System.out.println(e);
		}
	}
	
	public void setPumpEnd(String input){
		try{
			pump_end = (BigDecimal)(meter.parse(input));
		}
		catch(Exception e){
			System.out.println(e);
		}
	}
	
	public void setDate(String input){
		date = input;
	}
	
	public void setTime(String input){
		time = input;
	}
	
	public void setMemberID(String input){
		member_id = input;
	}
	
	public void setPrice(BigDecimal input){
		price = input;
	}
	
	public void setNumGallons(){
		num_gallons = pump_end.subtract(pump_start);
	}
	
	public void setPricePaid(){
		try{
			price_paid = num_gallons.multiply(price);	
		}
		
		catch(Exception e){
			
			System.out.println(e);
			
		}	
	}
	
}
