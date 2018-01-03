package principal;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import java.awt.Font;
import java.io.File;
import java.io.IOException;

import principal.Metodos;

import javax.swing.SwingConstants;

import javax.swing.DefaultListModel;
import javax.swing.JButton;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JList;
import javax.swing.JOptionPane;

import java.awt.Color;
import javax.swing.JRadioButton;
import javax.swing.Box;
import javax.swing.ButtonGroup;
import javax.swing.border.BevelBorder;
import javax.swing.filechooser.FileNameExtensionFilter;

public class CodigoPrincipal {

	private JFrame frame;
	private JButton btnOpenkore;
	private JButton btnControls;
	private JButton btnSaveLocation;
	private JButton btnGerar;
	private String path_openkorepl, path_save_location; 
	private File[] paths_control_folders;
	private JLabel lblPathToOpenkore;
	private JLabel lblPathToSaveShortcuts;
	private JList<String> list;
	private String selectedButton = "openkore.pl";
	private FileNameExtensionFilter perlFilter = new FileNameExtensionFilter("Perl Files", "pl");
	private FileNameExtensionFilter exeFilter = new FileNameExtensionFilter("Exe Files", "exe");
	private FileNameExtensionFilter filter;
	DefaultListModel<String> modelo;
	private JRadioButton rdbtnOpenkorepl;
	private JRadioButton rdbtnWxstartexe;
	private JRadioButton rdbtnPoseidonpl;


	public static void main(String[] args) {
				
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
		lblTitulo.setBounds(10, 11, 403, 32);
		frame.getContentPane().add(lblTitulo);
		
		JLabel lblSelecioneArquivoExecutavel = new JLabel("Selecione o local do openkore.pl");
		lblSelecioneArquivoExecutavel.setBounds(10, 119, 304, 14);
		frame.getContentPane().add(lblSelecioneArquivoExecutavel);
		
		JLabel lblSelecioneOLocal = new JLabel("Selecione o local onde ser\u00E1 salvo os Atalhos");
		lblSelecioneOLocal.setBounds(10, 184, 304, 14);
		frame.getContentPane().add(lblSelecioneOLocal);
		
		JLabel lblSelecionePastasControl = new JLabel("<html>Selecione a pasta control<br>Pode ser várias pastas.");
		lblSelecionePastasControl.setHorizontalAlignment(SwingConstants.CENTER);
		lblSelecionePastasControl.setBounds(10, 255, 233, 78);
		frame.getContentPane().add(lblSelecionePastasControl);
		
		btnOpenkore = new JButton("Selecionar");
		btnOpenkore.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				File theDir = null;
				if (rdbtnOpenkorepl.isSelected() || rdbtnPoseidonpl.isSelected()) {
					filter = perlFilter;
				} else if (rdbtnWxstartexe.isSelected()) {
					filter = exeFilter;
				}
			    theDir = metodos.selectFile(frame, filter);
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
		btnOpenkore.setBounds(309, 115, 104, 23);
		frame.getContentPane().add(btnOpenkore);
		
		btnControls = new JButton("Selecionar");
		btnControls.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				File[] theDir = null;
		        theDir = metodos.selectMultipleDirs(frame);
		        if(theDir != null) {
		        	modelo.clear();
		        	paths_control_folders = theDir;
		            for(File z : theDir) {
		            	System.out.println(z.getAbsolutePath());
		            	modelo.addElement(z.getName().toString());
		            }
		        } else {
		        	JOptionPane.showMessageDialog(null, "Erro ao conseguir pasta(s) control(s)");
		        }
		        return;
			}
		});
		btnControls.setBounds(68, 366, 104, 23);
		frame.getContentPane().add(btnControls);
		
		btnSaveLocation = new JButton("Selecionar");
		btnSaveLocation.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				File folderToSave = metodos.selectSingleDir(frame);
				if (folderToSave != null) {
					path_save_location = folderToSave.getAbsolutePath().toString();
					lblPathToSaveShortcuts.setForeground(Color.BLACK);
					lblPathToSaveShortcuts.setText("Local: " + path_save_location);
				} else {
					JOptionPane.showMessageDialog(null, "Erro ao conseguir o local de salvamento");
				}
			}
		});
		btnSaveLocation.setBounds(309, 180, 104, 23);
		frame.getContentPane().add(btnSaveLocation);
		
		btnGerar = new JButton("Gerar");
		btnGerar.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				if ( path_openkorepl != null && path_save_location != null && paths_control_folders != null) {
					try {
						metodos.gerarAtalhos(path_openkorepl, path_save_location, paths_control_folders, selectedButton);
						String mensagem =  "Criação do(s) atalho(s) concluída com sucesso!";
						if (selectedButton == "openkore.pl" || selectedButton == "wxstart.exe") {
							mensagem += "\nUm Atalho foi criado também dentro de cada pasta control";
						}
						JOptionPane.showMessageDialog(null, mensagem);
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
		btnGerar.setBounds(166, 447, 89, 23);
		frame.getContentPane().add(btnGerar);
		
		lblPathToOpenkore = new JLabel("O local escolhido aparecer\u00E1 aqui");
		lblPathToOpenkore.setForeground(Color.LIGHT_GRAY);
		lblPathToOpenkore.setToolTipText("");
		lblPathToOpenkore.setHorizontalAlignment(SwingConstants.LEFT);
		lblPathToOpenkore.setBounds(10, 144, 403, 14);
		frame.getContentPane().add(lblPathToOpenkore);
		
		lblPathToSaveShortcuts = new JLabel("O local escolhido aparecer\u00E1 aqui");
		lblPathToSaveShortcuts.setForeground(Color.LIGHT_GRAY);
		lblPathToSaveShortcuts.setBounds(10, 209, 403, 14);
		frame.getContentPane().add(lblPathToSaveShortcuts);
		
		modelo = new DefaultListModel<String>();
		list = new JList<String>(modelo);
		list.setBounds(241, 250, 160, 178);
		frame.getContentPane().add(list);
		
		rdbtnOpenkorepl = new JRadioButton("openkore.pl");
		rdbtnOpenkorepl.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				lblSelecioneArquivoExecutavel.setText("Selecione o local do " + rdbtnOpenkorepl.getText());
				selectedButton = rdbtnOpenkorepl.getText();
				path_openkorepl = null;
				lblPathToOpenkore.setText("O local escolhido aparecer\u00E1 aqui");
				lblPathToOpenkore.setForeground(Color.LIGHT_GRAY);
			}
		});
		rdbtnOpenkorepl.setSelected(true);
		rdbtnOpenkorepl.setBounds(6, 70, 109, 23);
		frame.getContentPane().add(rdbtnOpenkorepl);
		
		rdbtnWxstartexe = new JRadioButton("wxstart.exe");
		rdbtnWxstartexe.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				lblSelecioneArquivoExecutavel.setText("Selecione o local do " + rdbtnWxstartexe.getText());
				selectedButton = rdbtnWxstartexe.getText();
				filter = new FileNameExtensionFilter("Exe Files", "exe");
				path_openkorepl = null;
				lblPathToOpenkore.setText("O local escolhido aparecer\u00E1 aqui");
				lblPathToOpenkore.setForeground(Color.LIGHT_GRAY);
			}
		});
		rdbtnWxstartexe.setBounds(146, 70, 109, 23);
		frame.getContentPane().add(rdbtnWxstartexe);
		
		rdbtnPoseidonpl = new JRadioButton("poseidon.pl");
		rdbtnPoseidonpl.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				lblSelecioneArquivoExecutavel.setText("Selecione o local do " + rdbtnPoseidonpl.getText());
				selectedButton = rdbtnPoseidonpl.getText();
				path_openkorepl = null;
				lblPathToOpenkore.setText("O local escolhido aparecer\u00E1 aqui");
				lblPathToOpenkore.setForeground(Color.LIGHT_GRAY);
			}
		});
		rdbtnPoseidonpl.setBounds(292, 70, 109, 23);
		frame.getContentPane().add(rdbtnPoseidonpl);
		
		ButtonGroup btnGroup = new ButtonGroup();
		btnGroup.add(rdbtnPoseidonpl);
		btnGroup.add(rdbtnOpenkorepl);
		btnGroup.add(rdbtnWxstartexe);		
		
		Box verticalBox = Box.createVerticalBox();
		verticalBox.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		verticalBox.setBounds(5, 174, 410, 59);
		frame.getContentPane().add(verticalBox);
		
		Box verticalBox_1 = Box.createVerticalBox();
		verticalBox_1.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		verticalBox_1.setBounds(5, 105, 410, 58);
		frame.getContentPane().add(verticalBox_1);
		
		Box verticalBox_2 = Box.createVerticalBox();
		verticalBox_2.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		verticalBox_2.setBounds(5, 243, 410, 193);
		frame.getContentPane().add(verticalBox_2);
	}
}

