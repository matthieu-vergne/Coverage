import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import model.clover.CompilationUnitCoverageSummary;

public class JabrefSorterTotal extends AbstractJabrefSorter {

	public static void main(String[] args) {
		new JabrefSorterTotal().run();
	}

	@Override
	protected String createReport(CompilationUnitCoverageSummary summary) {
		final Map<String, Set<Integer>> data = new HashMap<String, Set<Integer>>();
		for (int line = summary.getFirstLine(); line < summary.getLastLine(); line++) {
			for (int id : summary.getTestIdsCoveringLine(line)) {
				String test = summary.getTestTargetById(id).getTestName();
				if (!data.containsKey(test)) {
					data.put(test, new HashSet<Integer>());
				}
				data.get(test).add(line);
			}
		}

		final List<String> tests = new ArrayList<String>(data.keySet());
		Collections.sort(tests, new Comparator<String>() {
			@Override
			public int compare(String t1, String t2) {
				Integer s1 = data.get(t1).size();
				Integer s2 = data.get(t2).size();
				return s2.compareTo(s1);
			}
		});

		int counter = 0;
		String report = "";
		for (String test : tests) {
			counter++;
			List<Integer> lines = new ArrayList<Integer>(data.get(test));
			Collections.sort(lines);
			report = counter + ": " + test + " = " + data.get(test).size()
					+ " " + lines + "\n";
		}
		return report;
	}
}
