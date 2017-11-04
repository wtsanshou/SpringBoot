package ie.dublin.ppb.betgame.util.output;

import java.util.Optional;

import ie.dublin.ppb.betgame.model.Report;

public interface PpbFileCreater {
	public void createFile(Optional<Report> report, String folderPath, String fileName);
}
