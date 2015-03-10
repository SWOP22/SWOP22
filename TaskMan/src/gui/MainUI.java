package gui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import main.FrontController;

import javax.swing.DefaultListModel;
import javax.swing.JButton;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JList;
import javax.swing.JLabel;
import javax.swing.JTextField;

import data.InvalidProjectDataException;
import data.InvalidTaskDataException;
import data.InvalidTaskUpdateDataException;
import data.ProjectData;
import data.TaskData;
import data.TaskUpdateData;
import project.Project;
import task.Task;
import time.InvalidTimeStampException;
import time.TimeStamp;

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
	private DefaultListModel<Project> projectListModel;
	private JList<Project> projectList;
	private DefaultListModel<Task> taskListModel;
	private JList<Task> taskList;
	private JButton btnAdvanceTime;
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
		
		taskListModel = new DefaultListModel<Task>();
		taskList = new JList<Task>(taskListModel);
		scrollPane_1.setViewportView(taskList);
		
		projectListModel = new DefaultListModel<Project>();
		projectList = new JList<Project>(projectListModel);
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
		
		btnAdvanceTime = new JButton("advance time");

		btnAdvanceTime.setBounds(15, 478, 117, 23);
		contentPane.add(btnAdvanceTime);
		
	}
	
	public void createEvents() {
		btnShowProjects.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				projectListModel.clear();
				int i = 0;
				for(Project project : fc.getProjects()){
					projectListModel.add(i,project);
					i++;
				}
			}
		});
		
		btnShowTasks.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				taskListModel.clear();
				Project project = projectList.getSelectedValue();
				int i = 0;
				for(Task task : project.getTasks()){
					taskListModel.add(i,task);
					i++;
				}
			}
		});
		
		btnShowTaskDetails.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Task task = taskList.getSelectedValue();
				new TaskDetailsUI(task);
			}
		});
		
		btnCreateProject.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ProjectData pData = fc.getProjectData();
				//TODO input => pdata
				try {
					fc.createProject(pData);
				} catch (InvalidProjectDataException e1) {
					JOptionPane.showMessageDialog(null, e.toString(), "Error",
                            JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		
		btnCreateTask.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(projectList.getSelectedValue() != null){
					TaskData tData = fc.getTaskData(projectList.getSelectedValue());
					//TODO input => tdata
					try {
						fc.createTask(tData);
					} catch (InvalidTaskDataException e1) {
						JOptionPane.showMessageDialog(null, e.toString(), "Error",
                                JOptionPane.ERROR_MESSAGE);
					}
				} else {
					JOptionPane.showMessageDialog(null, "Please select a project from the projects list", "Error",
                            JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		
		btnUpdateTask.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(taskList.getSelectedValue() != null){
					TaskUpdateData tUData = fc.getTaskUpdateData(taskList.getSelectedValue());
					//TODO input => tudata
					try {
						fc.taskStatusUpdate(tUData);
					} catch (InvalidTaskUpdateDataException e1) {
						JOptionPane.showMessageDialog(null, e.toString(), "Error",
                                JOptionPane.ERROR_MESSAGE);
					}
				} else {
					JOptionPane.showMessageDialog(null, "Please select a task from the tasks list", "Error",
                            JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		
		btnAdvanceTime.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				TimeStamp time = new TimeStamp();
				//TODO input => timestamp
				try {
					fc.advanceTime(time);
				} catch (InvalidTimeStampException e1) {
					JOptionPane.showMessageDialog(null, e.toString(), "Error",
                            JOptionPane.ERROR_MESSAGE);
				}
			}
		});
	}
}
