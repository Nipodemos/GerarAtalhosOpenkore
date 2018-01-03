package principal;

import java.io.File;
import java.io.IOException;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.filechooser.FileNameExtensionFilter;

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
	
	public File selectFile(JFrame frame, FileNameExtensionFilter filter) {
		JFileChooser fileChooser = new JFileChooser(System.getProperty("user.home").toString());
		fileChooser.setFileFilter(filter);
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
				String pathOfFile, 
				String pathofSaveLocation, 
				File[] pathsOfControlFolders,
				String selectedButton) throws IOException {
		
		pathOfFile.replace("\"", "");
		pathofSaveLocation.replace("\"", "");
		
		String type;
		for (File x : pathsOfControlFolders) {
			x.getAbsolutePath().replace("\"", "");
			
			ShellLink sl = ShellLink.createLink(pathOfFile);
			
			if (selectedButton == "poseidon.pl") {
				sl.setCMDArgs("--file=\"" + x.getAbsolutePath() + "\\poseidon.txt\"");
				type = "poseidon";
			} else {
				sl.setCMDArgs("--control=\"" + x + "\" --logs=\"" + x + "\\logs\"");
				type = "opk";
				sl.saveTo(x + "\\" + type + " " + x.getName() + ".lnk");
			}
			
			System.out.println(x.getName());
			sl.saveTo(pathofSaveLocation + "\\" + type + " " + x.getName() + ".lnk");
		}
	}
}
