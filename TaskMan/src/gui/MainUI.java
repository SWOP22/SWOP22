package gui;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import main.FrontController;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.LayoutStyle.ComponentPlacement;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import javax.swing.JScrollPane;
import javax.swing.JList;
import javax.swing.JLabel;
import javax.swing.JTextField;

import project.Project;
import task.Task;

public class MainUI extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6669589071809532758L;
	private JPanel contentPane;
	
	private FrontController fc;
	private JButton btnShowProjects;
	private JButton btnCreateProject;
	private JButton btnCreateTask;
	private JButton btnUpdateTask;
	private JButton btnShowTasks;
	private JButton btnShowTaskDetails;
	private JTextField textField;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainUI frame = new MainUI();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public MainUI() {
		setupFrames();
		createEvents();
		fc = new FrontController();
	}
	
	public void setupFrames() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 800, 550);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		btnCreateProject = new JButton("create Project");
		btnCreateProject.setBounds(15, 240, 117, 23);
		
		btnShowProjects = new JButton("show Projects");
		btnShowProjects.setBounds(15, 58, 117, 23);
		
		btnCreateTask = new JButton("create Task");
		btnCreateTask.setBounds(15, 320, 117, 23);
		
		btnUpdateTask = new JButton("update Task");
		btnUpdateTask.setBounds(15, 400, 117, 23);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(150, 30, 300, 190);
		
		JLabel lblProjects = new JLabel("Projects");
		lblProjects.setBounds(150, 5, 39, 14);
		
		JLabel lblTasks = new JLabel("Tasks");
		lblTasks.setBounds(475, 5, 27, 14);
		
		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(475, 30, 300, 190);
		
		btnShowTasks = new JButton("show Tasks");
		btnShowTasks.setBounds(15, 99, 117, 23);
		
		btnShowTaskDetails = new JButton("show Task Details");
		btnShowTaskDetails.setBounds(15, 140, 117, 23);
		
		JList<Task> taskList = new JList<Task>();
		scrollPane_1.setViewportView(taskList);
		
		JList<Project> projectList = new JList<Project>();
		scrollPane.setViewportView(projectList);
		contentPane.setLayout(null);
		contentPane.add(btnShowProjects);
		contentPane.add(btnShowTasks);
		contentPane.add(btnShowTaskDetails);
		contentPane.add(lblProjects);
		contentPane.add(scrollPane);
		contentPane.add(scrollPane_1);
		contentPane.add(lblTasks);
		contentPane.add(btnCreateProject);
		contentPane.add(btnCreateTask);
		contentPane.add(btnUpdateTask);
		
		textField = new JTextField();
		textField.setBounds(241, 241, 86, 20);
		contentPane.add(textField);
		textField.setColumns(10);
	}
	
	public void createEvents() {
		btnShowProjects.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
			}
		});
		
		btnCreateProject.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
			}
		});
		
		btnCreateTask.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
			}
		});
		
		btnUpdateTask.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
			}
		});
	}
}
