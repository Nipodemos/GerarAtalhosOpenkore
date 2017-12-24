package principal;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import java.awt.Font;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import principal.Metodos;

import javax.swing.SwingConstants;

import mslinks.ShellLink;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFileChooser;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JList;
import javax.swing.JOptionPane;

import java.awt.Color;

public class CodigoPrincipal {

	private JFrame frame;
	private JButton btnOpenkore;
	private JButton btnControls;
	private JButton btnSaveLocation;
	private JButton btnGerar;
	private String path_openkorepl, path_save_location; 
	private ArrayList<String> paths_control_folders;
	private JLabel lblPathToOpenkore;
	private JLabel lblPathToSaveShortcuts;
	private JList<String> list;
	DefaultListModel<String> modelo;


	public static void main(String[] args) throws IOException {
				
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					CodigoPrincipal window = new CodigoPrincipal();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public CodigoPrincipal() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		Metodos metodos = new Metodos();
		frame = new JFrame();
		frame.setBounds(100, 100, 439, 520);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JLabel lblTitulo = new JLabel("Gerar atalhos para o Openkore");
		lblTitulo.setHorizontalAlignment(SwingConstants.CENTER);
		lblTitulo.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblTitulo.setBounds(45, 11, 259, 32);
		frame.getContentPane().add(lblTitulo);
		
		JLabel lblSelecioneOOpenkorepl = new JLabel("Selecione o openkore.pl OU wxstart.exe");
		lblSelecioneOOpenkorepl.setBounds(10, 89, 304, 14);
		frame.getContentPane().add(lblSelecioneOOpenkorepl);
		
		JLabel lblSelecioneOLocal = new JLabel("Selecione o local onde ser\u00E1 salvo os Atalhos");
		lblSelecioneOLocal.setBounds(10, 139, 304, 14);
		frame.getContentPane().add(lblSelecioneOLocal);
		
		JLabel lblSelecionePastasControl = new JLabel("<html>Selecione a pasta control<br>Pode ser várias pastas.");
		lblSelecionePastasControl.setHorizontalAlignment(SwingConstants.CENTER);
		lblSelecionePastasControl.setBounds(10, 195, 233, 78);
		frame.getContentPane().add(lblSelecionePastasControl);
		
		btnOpenkore = new JButton("Selecionar");
		btnOpenkore.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				 File theDir = null;
			        theDir = metodos.selectFile(frame);
			        if(theDir != null) {
			            path_openkorepl = theDir.getAbsolutePath().toString();
			            lblPathToOpenkore.setForeground(Color.BLACK);
			            lblPathToOpenkore.setText("Local: " + path_openkorepl);
			        } else {
			        	JOptionPane.showMessageDialog(null, "Erro ao conseguir o local do openkore.pl");
			        }
			        return;
			}
		});
		btnOpenkore.setBounds(309, 85, 104, 23);
		frame.getContentPane().add(btnOpenkore);
		
		btnControls = new JButton("Selecionar");
		btnControls.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				File[] theDir = null;
		        theDir = metodos.selectMultipleDirs(frame);
		        if(theDir != null) {
		        	modelo.clear();
		        	paths_control_folders = new ArrayList<String>();
		            for(File z : theDir) {
		            	System.out.println(z.getAbsolutePath());
		            	modelo.addElement(z.getName().toString());
		            	paths_control_folders.add(z.getAbsolutePath());
		            }
		        }
		        return;
			}
		});
		btnControls.setBounds(74, 303, 104, 23);
		frame.getContentPane().add(btnControls);
		
		btnSaveLocation = new JButton("Selecionar");
		btnSaveLocation.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				File folderToSave = metodos.selectSingleDir(frame);
				path_save_location = folderToSave.getAbsolutePath().toString();
				lblPathToSaveShortcuts.setForeground(Color.BLACK);
				lblPathToSaveShortcuts.setText("Local: " + path_save_location);
			}
		});
		btnSaveLocation.setBounds(309, 135, 104, 23);
		frame.getContentPane().add(btnSaveLocation);
		
		btnGerar = new JButton("Gerar");
		btnGerar.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				if ( path_openkorepl != null && path_save_location != null && paths_control_folders != null) {
					try {
						metodos.gerarAtalhos(path_openkorepl, path_save_location, paths_control_folders);
						JOptionPane.showMessageDialog(null, "Criação do(s) atalhos concluída com sucesso!"
								+ "\nUm Atalho foi criado também dentro de cada pasta control");
						modelo.clear();
					} catch (IOException e) {
						e.printStackTrace();
					}
				} else {
					JOptionPane.showMessageDialog(null, "Você precisa definir o local do openkore.pl, "
							+ "o local que vai slavar os atalhos e as pastas control antes de clicar em Gerar");
				}
			}
		});
		btnGerar.setBounds(165, 424, 89, 23);
		frame.getContentPane().add(btnGerar);
		
		lblPathToOpenkore = new JLabel("O local escolhido aparecer\u00E1 aqui");
		lblPathToOpenkore.setForeground(Color.LIGHT_GRAY);
		lblPathToOpenkore.setToolTipText("");
		lblPathToOpenkore.setHorizontalAlignment(SwingConstants.LEFT);
		lblPathToOpenkore.setBounds(10, 114, 403, 14);
		frame.getContentPane().add(lblPathToOpenkore);
		
		lblPathToSaveShortcuts = new JLabel("O local escolhido aparecer\u00E1 aqui");
		lblPathToSaveShortcuts.setForeground(Color.LIGHT_GRAY);
		lblPathToSaveShortcuts.setBounds(10, 164, 403, 14);
		frame.getContentPane().add(lblPathToSaveShortcuts);
		
		modelo = new DefaultListModel<String>();
		list = new JList<String>(modelo);
		list.setBounds(239, 195, 160, 178);
		frame.getContentPane().add(list);
	}
	
	
}

