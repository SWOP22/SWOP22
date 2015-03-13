package gui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import main.FrontController;
import main.TaskManInitFileChecker;

import javax.swing.DefaultListModel;
import javax.swing.JButton;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.FileReader;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JList;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;

import exceptions.InvalidProjectDataException;
import data.ProjectData;
import data.TaskData;
import data.TaskUpdateData;
import project.Project;
import task.Failed;
import task.Finished;
import task.Ongoing;
import task.Status;
import task.Task;
import time.InvalidTimeStampException;
import user.User;

import javax.swing.JComboBox;

public class MainUI extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6669589071809532758L;
	private JPanel contentPane;
	
	private DateTimeFormatter formatter;
	private static FrontController fc;
	private JButton btnShowProjects;
	private JButton btnCreateProject;
	private JButton btnCreateTask;
	private JButton btnUpdateTask;
	private JButton btnShowTasks;
	private JButton btnShowTaskDetails;
	private DefaultListModel<Project> projectListModel;
	private JList<Project> projectList;
	private DefaultListModel<Task> taskListModel;
	private JList<Task> taskList;
	private JButton btnAdvanceTime;
	private JTextField textField_start;
	private JTextField textField_end;
	private JTextField textField_date;
	private JTextField textField_task_description;
	private JTextField textField_task_duration;
	private JTextField textField_task_deviation;
	private DefaultListModel<Task> taskListModel_dependencies;
	private JList<Task> list_task_dependencies;
	private JTextField textField_project_name;
	private JTextField textField_project_description;
	private JTextField textField_project_start;
	private JTextField textField_project_due;
	private DefaultComboBoxModel<User> userListModel;
	private JComboBox<User> comboBox_task_user;
	private DefaultComboBoxModel<Task> taskListModel_alternate;
	private JComboBox<Task> comboBox_task_alternate;
	private DefaultComboBoxModel<Status> statusListModel;
	private JComboBox<Status> comboBox_status;
	private JTextField textField_user;
	private JButton btnAddUser;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainUI frame = new MainUI();
					new TaskManInitFileChecker(new FileReader("src/gui/input.tman"),fc).checkFile();
					frame.setVisible(true);
				} catch (Exception e) {
					JOptionPane.showMessageDialog(null, e.getMessage(), "Error",
                            JOptionPane.ERROR_MESSAGE);
				}
			}
		});
	}

	/**
	 * Create the main user interface.
	 * Initialize required elements.
	 */
	public MainUI() {
		formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
		setupFrames();
		createEvents();
		try {
		    // System time has to be initialized when starting the system
		    fc = new FrontController(LocalDateTime.of(2014, 1, 1, 8, 0));
		} catch (Exception e) {
		    System.out.println("Could not initialize the front controller!");
		}	
		try {
			fc.addUser(new User("Dries"));
			fc.addUser(new User("Joren"));
			fc.addUser(new User("Mika"));
			fc.addUser(new User("Vincent"));
		} catch (Exception e) {
			e.printStackTrace();
		}
		ArrayList<Status> statuses = new ArrayList<Status>();
		statuses.add(new Ongoing());
		statuses.add(new Finished());
		statuses.add(new Failed());
		for(Status status : statuses){
			statusListModel.addElement(status);
		}
		for(User user : fc.getUsers()){
			userListModel.addElement(user);
		}
	}
	
	/**
	 * Setup the user interface frames.
	 */
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
		lblProjects.setBounds(150, 5, 70, 14);
		
		JLabel lblTasks = new JLabel("Tasks");
		lblTasks.setBounds(475, 5, 59, 14);
		
		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(475, 30, 300, 190);
		
		btnShowTasks = new JButton("show Tasks");

		btnShowTasks.setBounds(15, 99, 117, 23);
		
		btnShowTaskDetails = new JButton("show Task Details");

		btnShowTaskDetails.setBounds(15, 140, 117, 23);
		
		taskListModel = new DefaultListModel<Task>();
		taskList = new JList<Task>(taskListModel);
		taskList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		scrollPane_1.setViewportView(taskList);
		
		projectListModel = new DefaultListModel<Project>();
		projectList = new JList<Project>(projectListModel);
		projectList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
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
		
		JLabel lblDate = new JLabel("date");
		lblDate.setBounds(150, 482, 46, 14);
		contentPane.add(lblDate);
		
		JLabel lblStartDate = new JLabel("start date");
		lblStartDate.setBounds(150, 404, 70, 14);
		contentPane.add(lblStartDate);
		
		JLabel lblEndDate = new JLabel("end date");
		lblEndDate.setBounds(150, 435, 70, 14);
		contentPane.add(lblEndDate);
		
		textField_start = new JTextField();
		textField_start.setBounds(230, 401, 86, 20);
		contentPane.add(textField_start);
		textField_start.setColumns(10);
		
		textField_end = new JTextField();
		textField_end.setBounds(230, 432, 86, 20);
		contentPane.add(textField_end);
		textField_end.setColumns(10);
		
		textField_date = new JTextField();
		textField_date.setBounds(230, 479, 86, 20);
		contentPane.add(textField_date);
		textField_date.setColumns(10);
		
		JLabel lblState = new JLabel("state");
		lblState.setBounds(340, 404, 59, 14);
		contentPane.add(lblState);
		
		JLabel lblDescription = new JLabel("description");
		lblDescription.setBounds(150, 324, 70, 14);
		contentPane.add(lblDescription);
		
		textField_task_description = new JTextField();
		textField_task_description.setBounds(230, 321, 545, 20);
		contentPane.add(textField_task_description);
		textField_task_description.setColumns(10);
		
		JLabel lblDuration = new JLabel("duration");
		lblDuration.setBounds(150, 349, 70, 14);
		contentPane.add(lblDuration);
		
		textField_task_duration = new JTextField();
		textField_task_duration.setBounds(230, 346, 86, 20);
		contentPane.add(textField_task_duration);
		textField_task_duration.setColumns(10);
		
		JLabel lblDeviation = new JLabel("deviation");
		lblDeviation.setBounds(150, 374, 70, 14);
		contentPane.add(lblDeviation);
		
		textField_task_deviation = new JTextField();
		textField_task_deviation.setBounds(230, 371, 86, 20);
		contentPane.add(textField_task_deviation);
		textField_task_deviation.setColumns(10);
		
		JLabel lblUser = new JLabel("user");
		lblUser.setBounds(340, 349, 59, 14);
		contentPane.add(lblUser);
		
		JLabel lblAlternate = new JLabel("alternate");
		lblAlternate.setBounds(340, 374, 59, 14);
		contentPane.add(lblAlternate);
		
		userListModel = new DefaultComboBoxModel<User>();
		comboBox_task_user = new JComboBox<User>(userListModel);
		comboBox_task_user.setBounds(409, 346, 93, 20);
		contentPane.add(comboBox_task_user);
		
		taskListModel_alternate = new DefaultComboBoxModel<Task>();
		comboBox_task_alternate = new JComboBox<Task>(taskListModel_alternate);
		comboBox_task_alternate.setBounds(409, 371, 93, 20);
		contentPane.add(comboBox_task_alternate);
		
		statusListModel = new DefaultComboBoxModel<Status>();
		comboBox_status = new JComboBox<Status>(statusListModel);
		comboBox_status.setBounds(409, 401, 93, 20);
		contentPane.add(comboBox_status);
		
		JLabel lblDependencies = new JLabel("dependencies");
		lblDependencies.setBounds(512, 352, 78, 14);
		contentPane.add(lblDependencies);
		
		JScrollPane scrollPane_2 = new JScrollPane();
		scrollPane_2.setBounds(600, 352, 175, 50);
		contentPane.add(scrollPane_2);
		
		taskListModel_dependencies = new DefaultListModel<Task>();
		list_task_dependencies = new JList<Task>(taskListModel_dependencies);
		scrollPane_2.setViewportView(list_task_dependencies);
		
		JLabel lblName = new JLabel("name");
		lblName.setBounds(150, 244, 70, 14);
		contentPane.add(lblName);
		
		textField_project_name = new JTextField();
		textField_project_name.setBounds(230, 241, 86, 20);
		contentPane.add(textField_project_name);
		textField_project_name.setColumns(10);
		
		JLabel lblDescription_1 = new JLabel("description");
		lblDescription_1.setBounds(150, 269, 70, 14);
		contentPane.add(lblDescription_1);
		
		textField_project_description = new JTextField();
		textField_project_description.setBounds(230, 266, 545, 20);
		contentPane.add(textField_project_description);
		textField_project_description.setColumns(10);
		
		JLabel lblStartDate_1 = new JLabel("start date");
		lblStartDate_1.setBounds(340, 244, 59, 14);
		contentPane.add(lblStartDate_1);
		
		textField_project_start = new JTextField();
		textField_project_start.setBounds(409, 241, 86, 20);
		contentPane.add(textField_project_start);
		textField_project_start.setColumns(10);
		
		JLabel lblDueDate = new JLabel("due date");
		lblDueDate.setBounds(520, 244, 70, 14);
		contentPane.add(lblDueDate);
		
		textField_project_due = new JTextField();
		textField_project_due.setBounds(600, 241, 86, 20);
		contentPane.add(textField_project_due);
		textField_project_due.setColumns(10);
		
		btnAddUser = new JButton("add user");
		btnAddUser.setBounds(409, 478, 89, 23);
		contentPane.add(btnAddUser);
		
		textField_user = new JTextField();
		textField_user.setBounds(512, 479, 86, 20);
		contentPane.add(textField_user);
		textField_user.setColumns(10);
		
	}
	
	/**
	 * Create interaction events.
	 */
	public void createEvents() {
		
		/**
		 * Show all the projects in the system.
		 */
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
		
		/**
		 * Show all the tasks of the selected project.
		 */
		btnShowTasks.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				taskListModel.clear();
				Project project = projectList.getSelectedValue();
				int i = 0;
				for(Task task : project.getAllTasks()){
					taskListModel.add(i,task);
					taskListModel_dependencies.add(i, task);
					i++;
				}
			}
		});
		
		/**
		 * Show the details of the selected task in a seperate window.
		 */
		btnShowTaskDetails.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Task task = taskList.getSelectedValue();
				new TaskDetailsUI(task);
			}
		});
		
		/**
		 * Create a new project with the entered information.
		 */
		btnCreateProject.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ProjectData pData = fc.getProjectData();
				pData.setName(textField_project_name.getText());
				pData.setDescription(textField_project_description.getText());
				try {
					LocalDateTime creationTime = LocalDateTime.parse(textField_project_start.getText(), formatter);
					pData.setCreationTime(creationTime);
					LocalDateTime dueTime = LocalDateTime.parse(textField_project_due.getText(), formatter);
					pData.setDueTime(dueTime);
				} catch (DateTimeParseException de){ 
					JOptionPane.showMessageDialog(null, de.getMessage(), "Error",
                            JOptionPane.ERROR_MESSAGE);
				}
				try {
					fc.createProject(pData);
				} catch (InvalidProjectDataException e1) {
					JOptionPane.showMessageDialog(null, e1.getMessage(), "Error",
                            JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		
		/**
		 * Create a new task with the entered information.
		 */
		btnCreateTask.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(projectList.getSelectedValue() != null){
					TaskData tData = fc.getTaskData(projectList.getSelectedValue());
					tData.setDescription(textField_task_description.getText());
					tData.setEstimatedDuration(Integer.parseInt(textField_task_duration.getText()));
					tData.setAcceptableDeviation(Integer.parseInt(textField_task_deviation.getText()));
					tData.setUser((User) comboBox_task_user.getSelectedItem());
					tData.setAlternateFor((Task) comboBox_task_alternate.getSelectedItem()); 
					tData.setDependencyTasks(list_task_dependencies.getSelectedValuesList());
					try {
						fc.createTask(tData);
					} catch (Exception e1) {
						JOptionPane.showMessageDialog(null, e1.getMessage(), "Error",
                                JOptionPane.ERROR_MESSAGE);
					}
				} else {
					JOptionPane.showMessageDialog(null, "Please select a project from the projects list", "Error",
                            JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		
		/**
		 * Update a task with the entered information.
		 */
		btnUpdateTask.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(taskList.getSelectedValue() != null){
					TaskUpdateData tUData = fc.getTaskUpdateData(taskList.getSelectedValue(),projectList.getSelectedValue());
					try {
						LocalDateTime startTime = LocalDateTime.parse(textField_start.getText(), formatter);
						tUData.setStartTime(startTime);
						LocalDateTime endTime = LocalDateTime.parse(textField_end.getText(), formatter);
						tUData.setEndTime(endTime);
					} catch (DateTimeParseException de) {
						JOptionPane.showMessageDialog(null, de.getMessage(), "Error",
	                            JOptionPane.ERROR_MESSAGE);
					}
					tUData.setStatus((Status) comboBox_status.getSelectedItem());
					try {
						fc.taskStatusUpdate(tUData);
					} catch (Exception e1) {
						JOptionPane.showMessageDialog(null, e1.getMessage(), "Error",
                                JOptionPane.ERROR_MESSAGE);
					}
				} else {
					JOptionPane.showMessageDialog(null, "Please select a task from the tasks list", "Error",
                            JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		
		/**
		 * Advance the time of the system to the entered time.
		 */
		btnAdvanceTime.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				LocalDateTime time = null;
				try {
					time = LocalDateTime.parse(textField_date.getText(), formatter);
				} catch (DateTimeParseException de) {
					JOptionPane.showMessageDialog(null, de.getMessage(), "Error",
                            JOptionPane.ERROR_MESSAGE);
				}
				try {
					fc.advanceTime(time);
				} catch (InvalidTimeStampException e1) {
					JOptionPane.showMessageDialog(null, e1.getMessage(), "Error",
                            JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		btnAddUser.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					fc.addUser(new User(textField_user.getText()));
				} catch (Exception e) {
					JOptionPane.showMessageDialog(null, e.getMessage(), "Error",
                            JOptionPane.ERROR_MESSAGE);
				}
			}
		});
	}
}
