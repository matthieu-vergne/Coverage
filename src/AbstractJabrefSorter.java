import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;

import model.clover.CompilationUnitCoverageSummary;
import model.clover.JavaScriptData;
import regression.AbstractSorter;

public abstract class AbstractJabrefSorter extends AbstractSorter {

	private final Map<CompilationUnitCoverageSummary, String> reports = new LinkedHashMap<CompilationUnitCoverageSummary, String>();
	private static final Logger logger = Logger
			.getLogger(AbstractJabrefSorter.class.getName());

	static {
		getLogger().setUseParentHandlers(false);
		getLogger().addHandler(new Handler() {

			@Override
			public void publish(LogRecord arg0) {
				String date = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss.SSS")
						.format(new Date(arg0.getMillis()));
				Level level = arg0.getLevel();
				String message = arg0.getMessage();
				System.err.println(date + " - [" + level + "] " + message);
			}

			@Override
			public void flush() {
				System.err.flush();
			}

			@Override
			public void close() throws SecurityException {
				// do nothing
			}
		});
	}

	@Override
	public List<JavaScriptData> collectJavascript(String dir) {
		File directory = new File(dir);
		if (!directory.isDirectory()) {
			throw new IllegalArgumentException(dir + " is not a directory");
		}
		List<JavaScriptData> scripts = new ArrayList<JavaScriptData>();
		File[] files = directory.listFiles();
		for (File file : files) {
			if (file.isDirectory()) {
				scripts.addAll(collectJavascript(file.getPath()));
			} else if (!file.getName().endsWith(".js")) {
				// pass no javascript files
			} else {
				getLogger().info("Parse file: " + file.getPath());
				FileReader reader;
				try {
					reader = new FileReader(file);
					JavaScriptData script = new JavaScriptData(reader);
					if (script.isDataScript()) {
						getLogger().info("Add script data...");
						scripts.add(script);
					}
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				}
			}
		}
		return scripts;
	}

	@Override
	public void printSorted() {
		for (Map.Entry<CompilationUnitCoverageSummary, String> entry : reports
				.entrySet()) {
			System.out.println("Summary:");
			System.out.println(entry.getKey());
			System.out.println("Statement coverage:");
			System.out.println(entry.getValue());
			System.out.println("==================");
		}
	}

	@Override
	public void prioritize() {
		for (CompilationUnitCoverageSummary summary : getSummaries()) {
			if (summary == null) {
				getLogger().warning("Null summary => pass");
			} else {
				String report = createReport(summary);
				if (report.isEmpty()) {
					// forget it
				} else {
					reports.put(summary, report);
				}
			}
		}
	}

	protected abstract String createReport(
			CompilationUnitCoverageSummary summary);

	public static Logger getLogger() {
		return logger;
	}
}
