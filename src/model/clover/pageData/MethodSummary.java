package model.clover.pageData;

public class MethodSummary {
	
	//start line
	private int sl;
	//end line
	private int el;

	private int sc;
	
	
	public MethodSummary(){}
	
	public int getFirstLine() {
		return sl;
	}
	
	public int getLastLine() {
		return el;
	}
	
	public int getSc() {
		return sc;
	}
	
	
	
	public String toString(){
		return "" + sl+ "-" +el ;
	}
	
}
