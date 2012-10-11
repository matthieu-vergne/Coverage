package model.clover.pageData;

import java.util.Arrays;
import java.util.Iterator;

public class ClassSummary implements Iterable<MethodSummary>{
	
	// start line
	private int sl;
	// end line
	private int el;
	// unique identifier
	private int id;
	// class name (without package)
	private String name;
	// class methods
	private MethodSummary[] methods;
	
	
	
	public ClassSummary(){}

	public int getFirstLine() {
		return sl;
	}
	
	public int getLastLine() {
		return el;
	}

	public int getClassIdentifier() {
		return id;
	}

	public MethodSummary getMethodAt(int index) {
		return methods[index];
	}
	
	public MethodSummary[] getMethods() {
		return methods;
	}
	
	public String getClassName() {
		return name;
	}
	
	@Override
	public Iterator<MethodSummary> iterator() {
		return Arrays.asList(methods).iterator();
	}

	public String toString(){
		StringBuffer b = new StringBuffer();
		b.append( "\tel:" + el + " id:" + id );
		b.append( " methods:[");
		for (MethodSummary s:this)
			b.append(s.toString() +", ");
		b.append( "]" +  " name:" + name + " sl:" + sl);
		b.append(";\n");
		return b.toString();
	}
	

}
