package principal;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import javax.swing.JFileChooser;
import javax.swing.JFrame;

import mslinks.ShellLink;

public class Metodos {
	
	public File[] selectMultipleDirs(JFrame frame) {
		JFileChooser fileChooser = new JFileChooser(System.getProperty("user.home").toString());
		fileChooser.setMultiSelectionEnabled(true);
		fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		int showOpenDialog = fileChooser.showOpenDialog(frame);
		if (showOpenDialog != JFileChooser.APPROVE_OPTION) {
			return null;
		}
		File[] uploadDir = fileChooser.getSelectedFiles();
		// lastDir = new File(uploadDir[uploadDir.length-1].getParent());
		return uploadDir;
	}
	
	public File selectSingleDir(JFrame frame) {
		JFileChooser fileChooser = new JFileChooser(System.getProperty("user.home").toString());
		fileChooser.setMultiSelectionEnabled(false);
		fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		int showOpenDialog = fileChooser.showOpenDialog(frame);
		if (showOpenDialog != JFileChooser.APPROVE_OPTION) {
			return null;
		}
		File uploadDir = fileChooser.getSelectedFile();
		
		return uploadDir;
	}
	
	public File selectFile(JFrame frame) {
		JFileChooser fileChooser = new JFileChooser(System.getProperty("user.home").toString());
		fileChooser.setMultiSelectionEnabled(false);
		fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
		int showOpenDialog = fileChooser.showOpenDialog(frame);
		if (showOpenDialog != JFileChooser.APPROVE_OPTION) {
			return null;
		}
		File uploadDir = fileChooser.getSelectedFile();
		
		return uploadDir;
	}
	
	public void gerarAtalhos(
				String pathOfOpenkoreFile, 
				String pathofSaveLocation, 
				List<String> pathsOfControlFolders) throws IOException {
		
		pathOfOpenkoreFile.replace("\"", "");
		pathofSaveLocation.replace("\"", "");
		for (String x : pathsOfControlFolders) {
			x.replace("\"", "");
			ShellLink sl = ShellLink.createLink(pathOfOpenkoreFile)
				.setCMDArgs("--control=\"" + x + "\" --logs=\"" + x + "\\logs\"");
			Path path = Paths.get(x);
			String fileName = path.getFileName().toString();
			System.out.println(fileName);
			sl.saveTo(pathofSaveLocation + "\\opk " + fileName + ".lnk");
			sl.saveTo(x + "\\opk " + fileName + ".lnk");
		}
	}
}
