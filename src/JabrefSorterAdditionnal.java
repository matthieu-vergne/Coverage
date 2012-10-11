import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import model.clover.CompilationUnitCoverageSummary;

public class JabrefSorterAdditionnal extends AbstractJabrefSorter {

	public static void main(String[] args) {
		new JabrefSorterAdditionnal().run();
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

		final Map<String, Set<Integer>> remainingData = new HashMap<String, Set<Integer>>();
		for (String test : data.keySet()) {
			remainingData.put(test, new HashSet<Integer>(data.get(test)));
		}
		final List<String> tests = new ArrayList<String>();
		final List<String> remainingTests = new ArrayList<String>(
				remainingData.keySet());
		while (!remainingTests.isEmpty()) {
			Collections.sort(remainingTests, new Comparator<String>() {
				@Override
				public int compare(String t1, String t2) {
					Integer s1 = remainingData.get(t1).size();
					Integer s2 = remainingData.get(t2).size();
					return s2.compareTo(s1);
				}
			});

			String best = remainingTests.get(0);
			if (remainingData.get(best).size() == 0) {
				for (String test : remainingData.keySet()) {
					remainingData.get(test).addAll(data.get(test));
				}
			} else {
				for (Collection<Integer> lines : remainingData.values()) {
					lines.removeAll(data.get(best));
				}
				remainingTests.remove(best);
				remainingData.remove(best);
				tests.add(best);
			}
		}

		int counter = 0;
		final List<Integer> readlines = new ArrayList<Integer>();
		String report = "";
		for (String test : tests) {
			counter++;
			List<Integer> lines = new ArrayList<Integer>(data.get(test));
			if (readlines.containsAll(lines)) {
				readlines.clear();
				report += "(restart)\n";
			} else {
				lines.removeAll(readlines);
			}
			Collections.sort(lines);
			report += counter + ": " + test + " = " + lines.size() + " "
					+ lines + "\n";
			readlines.addAll(lines);
		}
		return report;
	}
}
