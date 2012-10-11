package model.clover.testTargets;

import java.util.Arrays;
import java.util.Collection;

public class TestTarget {
	
	//statements executed by this test
	private SourceLine[] methods;
	// test name
	private String name;
	// flag for passed test
	private boolean pass;
	//methods executed by this test
	private SourceLine[] statements;
	
	public Collection<SourceLine> getMethods() {
		return Arrays.asList(methods);
	}
	
	public String getTestName() {
		return name;
	}
	
	public boolean isPassed() {
		return pass;
	}
	
	public Collection<SourceLine> getStatements() {
		return Arrays.asList(statements);
	}
	
	public String toString(){
		StringBuffer buf = new StringBuffer();
		buf.append(name);
		buf.append(" methods: ");
		for (SourceLine s: getMethods())
			buf.append(s.toString() + ", ");
		buf.append(" statements: ");
		for (SourceLine s: getStatements())
			buf.append(s.toString() + ", ");
		return buf.toString();
	}

}
