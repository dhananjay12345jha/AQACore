package com.automation.core.allure;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;

@Slf4j
public class Beautify {
	public static void main(String[] args) throws IOException {
		final File reportPath = new File(AllurePaths.REPORT_PATH);
		if (reportPath.exists() && reportPath.isDirectory()) {
			log.info("Copying custom CSS");
			final InputStream inputStream = Beautify.class.getClassLoader().getResourceAsStream("styles.css");
			final File file = new File(AllurePaths.REPORT_PATH + "/styles.css");
			FileUtils.copyInputStreamToFile(inputStream, file);
		}
		else {
			log.warn("There is no default allure report to beautify at the path " + AllurePaths.REPORT_PATH);
			return;
		}

		log.info("Copying the reports for backup");
		final File sourceDir = new File(AllurePaths.REPORT_PATH);
		final File targetDir = new File(String.format("%s%s", AllurePaths.BACKUP_REPORT_PATH, new Date().toString()
																										.replace(":",
																												 "-")
																										.replace(" ",
																												 "_")));
		log.info("Report backup path is " + targetDir.getAbsolutePath());
		FileUtils.copyDirectory(sourceDir, targetDir);
	}
}