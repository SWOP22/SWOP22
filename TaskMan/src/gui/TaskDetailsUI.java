package gui;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.EmptyBorder;

import task.Task;

public class TaskDetailsUI extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3473149660506824937L;
	private JPanel contentPane;
	private JTextArea textArea;

	/**
	 * Create the frame.
	 */
	public TaskDetailsUI(Task task) {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setVisible(true);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		JScrollPane scrollPane = new JScrollPane();
		contentPane.add(scrollPane, BorderLayout.CENTER);
		
		textArea = new JTextArea();
		textArea.setEditable(false);
		scrollPane.setViewportView(textArea);
		
		JLabel lblTaskDetails = new JLabel("Task details");
		contentPane.add(lblTaskDetails, BorderLayout.NORTH);
		
		printDetails(task);
	}
	
	public void printDetails(Task task){
		textArea.append(task.getDescription());
		// TODO other details to show
	}

}
