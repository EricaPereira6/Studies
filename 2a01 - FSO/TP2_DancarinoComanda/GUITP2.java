import javax.swing.JFrame;
import javax.swing.JScrollPane;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Enumeration;
import java.awt.event.ActionEvent;
import javax.swing.JCheckBox;
import javax.swing.JList;
import javax.swing.DefaultListModel;

public class GUITP2 {

	private JFrame frame;
	private JLabel lblDancarinos, lblCoregrafos;
	private JButton btnLimpaLogging, btnInciarTodosDancarino, btnRemoverDancarino, btnAdicionarDancarino,
			btnIniciarTodosCoreografo, btnRemoverCoreografo, btnAdicionarCoreografo;
	private JCheckBox chckbxActivarTodosDancarino, chckbxActivarTodosCoreografo;
	private JTextArea textAreaLog;
	private JScrollPane scrollCoreografo, scrollDancarino, scrollLog;

	private DefaultListModel<String> modelC, modelD;
	private JList<String> listCoreografo, listDancarino;

	private BDGUIT2 v;
	private ArrayList<Coreografo> processosCoreo;
	private ArrayList<Dancarino> processosDancer;
	private ArrayList<DancarinoComanda> processosDancerComanda;
	private BDDancarino bd;
	private BDDancarinoComanda bd2;
	private int contadorCoreografos, contadorDancarinos;
	Enumeration<String> nomesProcessos;

	private final static String newline = "\n";
	
	private CanalComunicacao com;

	public void run() {
		
		
	}

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		GUITP2 gui = new GUITP2();
		gui.run();
		
//		CanalComunicacao com = new CanalComunicacao();
//		new Thread(com).start();
//		
//		int idL = com.OpenLeitor();
//		int idC = com.OpenEscritor();
//		
////		MyMessage a = com.ler(idC);
//		MyMessage b = com.ler(idL);
//		
////		System.out.println("a " + a);
//		System.out.println("b " + b);
//		
//		com.escrever(new MyMessage(9, 9), idL);
//		com.escrever(new MyMessage(9, 9), idC);
		
	}

	/**
	 * Create the application.
	 */
	public GUITP2() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		
		v = new BDGUIT2();
		
		com = new CanalComunicacao();
		new Thread(com).start();
		
		processosCoreo = new ArrayList<Coreografo>();
		processosDancer = new ArrayList<Dancarino>();
		processosDancerComanda = new ArrayList<DancarinoComanda>();
		
		contadorCoreografos = 1;
		contadorDancarinos = 1;

		frame = new JFrame("GUI T2");
		frame.setBounds(100, 100, 579, 633);
//		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		lblCoregrafos = new JLabel("Core\u00F3grafos");
		lblCoregrafos.setBounds(45, 5, 92, 55);
		frame.getContentPane().add(lblCoregrafos);

		lblDancarinos = new JLabel("Dan\u00E7arinos");
		lblDancarinos.setBounds(298, 18, 70, 28);
		frame.getContentPane().add(lblDancarinos);

		
		// TODO Jlist's Coreografo e Dancarino

		modelC = new DefaultListModel<>();
		listCoreografo = new JList<>(modelC);
		listCoreografo.setLayoutOrientation(JList.VERTICAL);

		scrollCoreografo = new JScrollPane(listCoreografo);
		scrollCoreografo.setBounds(45, 50, 221, 91);
		frame.getContentPane().add(scrollCoreografo);

		modelD = new DefaultListModel<>();
		listDancarino = new JList<>(modelD);
		listDancarino.setBounds(342, 50, 177, 91);
		listDancarino.setLayoutOrientation(JList.VERTICAL);

		scrollDancarino = new JScrollPane(listDancarino);
		scrollDancarino.setBounds(298, 50, 225, 91);
		frame.getContentPane().add(scrollDancarino);
		
		
		// TODO JButton's Adicionar e Remover do Coreografo e Dancarino

		btnAdicionarCoreografo = new JButton("Adicionar");
		btnAdicionarCoreografo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				modelC = (DefaultListModel<String>) listCoreografo.getModel();
				modelC.addElement("Core�grafo " + contadorCoreografos);
				contadorCoreografos++;
				listCoreografo.ensureIndexIsVisible(modelC.size() - 1);
			}
		});
		btnAdicionarCoreografo.setBounds(45, 152, 221, 46);
		frame.getContentPane().add(btnAdicionarCoreografo);

		btnRemoverCoreografo = new JButton("Remover");
		btnRemoverCoreografo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				int index = listCoreografo.getSelectedIndex();
				if (index != -1)
					modelC.removeElementAt(index);
			}
		});
		btnRemoverCoreografo.setBounds(45, 209, 221, 46);
		frame.getContentPane().add(btnRemoverCoreografo);
		

		btnAdicionarDancarino = new JButton("Adicionar");
		btnAdicionarDancarino.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				modelD = (DefaultListModel<String>) listDancarino.getModel();
				modelD.addElement("Dan�arino " + contadorDancarinos);
				contadorDancarinos++;
				listDancarino.ensureIndexIsVisible(modelD.size() - 1);
			}
		});
		btnAdicionarDancarino.setBounds(298, 152, 225, 46);
		frame.getContentPane().add(btnAdicionarDancarino);

		btnRemoverDancarino = new JButton("Remover");
		btnRemoverDancarino.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				int index = listDancarino.getSelectedIndex();
				if (index != -1)
					modelD.removeElementAt(index);
			}
		});
		btnRemoverDancarino.setBounds(298, 209, 225, 46);
		frame.getContentPane().add(btnRemoverDancarino);
		
		// TODO JButton's IniciarTodos do Coreografo e Dancarino

		btnIniciarTodosCoreografo = new JButton("Iniciar Todos");
		btnIniciarTodosCoreografo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				//get coreo model
				nomesProcessos = modelC.elements();

				//for each string
				clearArrayCoreografos();
				while (nomesProcessos.hasMoreElements()) {

					String nomeProcesso = nomesProcessos.nextElement();
					processosCoreo.add(new Coreografo(nomeProcesso, com));
				}

				//Inicia todas os processos coreografos				
				for(Coreografo c : processosCoreo) {	
					Thread threadC = new Thread(c);
					threadC.start();

//					printLog("-------------------------------");
//					printLog(threadC.getName());
					threadC.setName(threadC.getName() + ", Core�grafo");
					printLog(threadC.getName());
//					printLog("-------------------------------");
				}
				
				if(processosCoreo.size() > 0)
					chckbxActivarTodosCoreografo.setEnabled(true);
				else
					chckbxActivarTodosCoreografo.setEnabled(false);
			}
		});
		btnIniciarTodosCoreografo.setBounds(45, 266, 221, 46);
		frame.getContentPane().add(btnIniciarTodosCoreografo);


		btnInciarTodosDancarino = new JButton("Iniciar Todos");
		btnInciarTodosDancarino.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				//get dancer model
				Enumeration<String> nomesProcessos = modelD.elements();
				
				//for each string
				clearArrayDancarinos();
				while (nomesProcessos.hasMoreElements()) {
					
					String nomeProcesso = nomesProcessos.nextElement();
					bd = new BDDancarino();
					bd2 = new BDDancarinoComanda();
					processosDancer.add(new Dancarino(com, bd, bd2));
					processosDancerComanda.add(new DancarinoComanda(nomeProcesso, bd, bd2));
				}

				//Inicia todas os processos dancarino				
				for(Dancarino d : processosDancer) {	
					Thread threadD = new Thread(d);
					threadD.start();
					
//					printLog("-------------------------------");
//					printLog(threadD.getName());
					threadD.setName(threadD.getName() + ", Dan�arino");
					printLog(threadD.getName());

				}
				for(DancarinoComanda d : processosDancerComanda) {	
					Thread threadD = new Thread(d);
					threadD.start();
					
//					printLog("-------------------------------");
//					printLog(threadD.getName());
					threadD.setName(threadD.getName() + ", Dan�arinoComanda");
					printLog(threadD.getName());
				}
				
				if(processosDancer.size() > 0)
					chckbxActivarTodosDancarino.setEnabled(true);
				else
					chckbxActivarTodosDancarino.setEnabled(false);
			}
		});
		btnInciarTodosDancarino.setBounds(298, 266, 225, 46);
		frame.getContentPane().add(btnInciarTodosDancarino);
		
		
		// TODO CheckBox's activar Todos do Coreografo e do Dancarino

		chckbxActivarTodosCoreografo = new JCheckBox("Activar Todos");
		chckbxActivarTodosCoreografo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				v.setCoreoAtivos(chckbxActivarTodosCoreografo.isSelected());
				
				String adjetivo = new String("inactivo");
				if(chckbxActivarTodosCoreografo.isSelected())
					adjetivo = new String("activo");
				
				for(Coreografo c : processosCoreo) {
					c.ativarCoreo(v.isCoreoAtivos());
					c.activateButton(v.isCoreoAtivos());
					printLog("Coreografo " + c.getId() + " " + adjetivo);
				}

			}
		});
		chckbxActivarTodosCoreografo.setBounds(45, 319, 113, 25);
		frame.getContentPane().add(chckbxActivarTodosCoreografo);

		chckbxActivarTodosDancarino = new JCheckBox("Activar Todos");
		chckbxActivarTodosDancarino.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
//				RobotLegoEV3 rb = bd.getRobotLego();
				MyRobotLego rb = bd.getRobotLego();
				if(chckbxActivarTodosDancarino.isSelected()) {
					if (rb.OpenEV3(bd.getNomeRobot())) {
						bd.setOnOff(true);
					}
					else {
						chckbxActivarTodosDancarino.setSelected(false);
					}
				}
				else {
					bd.setOnOff(false);
					rb.CloseEV3();
				}
				
				v.setDancerAtivos(chckbxActivarTodosDancarino.isSelected());
				
				String adjetivo = new String("inactivo");
				if(chckbxActivarTodosDancarino.isSelected())
					adjetivo = new String("activo");
				
				for(Dancarino d : processosDancer) {
					d.ativarDancer(v.isDancerAtivos());
					d.activateButtons(chckbxActivarTodosDancarino.isSelected());
					printLog("Dancarino " + d.getId() + " " + adjetivo);
				}
				
			}
		});
		chckbxActivarTodosDancarino.setBounds(298, 319, 113, 25);
		frame.getContentPane().add(chckbxActivarTodosDancarino);
		
		
		// TODO JButton e TextArea do LOG

		btnLimpaLogging = new JButton("Limpa Logging");
		btnLimpaLogging.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				v.setConsolaLog(" ");
				textAreaLog.setText(v.getConsolaLog());
				
			}
		});
		btnLimpaLogging.setBounds(45, 359, 478, 25);
		frame.getContentPane().add(btnLimpaLogging);

		scrollLog = new JScrollPane();
		scrollLog.setBounds(45, 395, 478, 187);
		frame.getContentPane().add(scrollLog);
		
		textAreaLog = new JTextArea();
		scrollLog.setViewportView(textAreaLog);
		textAreaLog.setLineWrap(true);
		textAreaLog.setEditable(false);
		
		chckbxActivarTodosCoreografo.setEnabled(false);
		chckbxActivarTodosDancarino.setEnabled(false);

		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	private void clearArrayDancarinos() {
		int size = processosDancer.size();
		for(int i = 0; i < size; i++) {
			processosDancer.remove(0);
		}
		int size2 = processosDancerComanda.size();
		for(int i = 0; i < size2; i++) {
			processosDancerComanda.remove(0);
		}
	}
	
	private void clearArrayCoreografos() {
		int size = processosCoreo.size();
		for(int i = 0; i < size; i++) {
			processosCoreo.remove(0);
		}
	}
	
	public void printLog(String text) {
		v.setConsolaLog(text);
		textAreaLog.append(v.getConsolaLog() + newline);
		textAreaLog.setCaretPosition(textAreaLog.getDocument().getLength());
	}
}

