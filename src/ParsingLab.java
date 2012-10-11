import java.io.FileNotFoundException;
import java.io.FileReader;
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
import model.clover.JavaScriptData;

public class ParsingLab {
	public void parse() throws FileNotFoundException {
		String filename = "../Exercise-4-CurrentAccount/report/html/bankAccount/CurrentAccount.js";
		FileReader reader = new FileReader(filename);
		JavaScriptData script = new JavaScriptData(reader);
		CompilationUnitCoverageSummary summary;
		if (script.isDataScript()) {
			summary = script.getSummary();

			System.out.println("Summary:");
			System.out.println(summary);
			System.out.println("\nTotal statement coverage:");
			displayTotalStatementCoverageSort(summary);
			System.out.println("\nAdditionnal statement coverage:");
			displayAdditionnalStatementCoverageSort(summary);
		}
	}

	public void displayTotalStatementCoverageSort(
			CompilationUnitCoverageSummary summary) {
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
		for (String test : tests) {
			counter++;
			List<Integer> lines = new ArrayList<Integer>(data.get(test));
			Collections.sort(lines);
			System.out.println(counter + ": " + test + " = "
					+ data.get(test).size() + " " + lines);
		}
	}

	public void displayAdditionnalStatementCoverageSort(
			CompilationUnitCoverageSummary summary) {
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
		for (String test : tests) {
			counter++;
			List<Integer> lines = new ArrayList<Integer>(data.get(test));
			if (readlines.containsAll(lines)) {
				readlines.clear();
				System.out.println("(restart)");
			}
			else {
				lines.removeAll(readlines);
			}
			Collections.sort(lines);
			System.out.println(counter + ": " + test + " = " + lines.size()
					+ " " + lines);
			readlines.addAll(lines);
		}
	}

	public static void main(String[] args) {
		try {
			new ParsingLab().parse();
		} catch (FileNotFoundException e) {
			throw new RuntimeException(e);
		}
	}
}
