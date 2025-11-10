package kev;
import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;
import javax.swing.border.*;
import java.util.Calendar;

public class FreelancePlatformManager extends JFrame {
    private static final long serialVersionUID = 1L;
    private List<Job> jobs = new ArrayList<>();
    private List<Freelancer> freelancers = new ArrayList<>();
    private List<Application> applications = new ArrayList<>();
    private DefaultTableModel jobTableModel;
    private JTable jobTable;
    private JLabel totalJobsLabel, openJobsLabel, avgBudgetLabel;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new FreelancePlatformManager().setVisible(true);
        });
    }

    public FreelancePlatformManager() {
        setTitle("Freelance Job Posting Platform");
        setSize(1200, 700);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        
        initializeUI();
        loadSampleData();
    }

    private void initializeUI() {
        setLayout(new BorderLayout(10, 10));

        // Top Panel - Title and Stats
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        topPanel.setBackground(new Color(41, 128, 185));

        JLabel titleLabel = new JLabel("Freelance Job Posting Platform", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Times New Roman", Font.BOLD, 26));
        titleLabel.setForeground(Color.WHITE);
        topPanel.add(titleLabel, BorderLayout.NORTH);

        // Statistics Panel
        JPanel statsPanel = new JPanel(new GridLayout(1, 3, 10, 0));
        statsPanel.setBorder(new EmptyBorder(10, 0, 0, 0));
        statsPanel.setBackground(new Color(41, 128, 185));

        totalJobsLabel = createStatLabel("Total Jobs: 0");
        openJobsLabel = createStatLabel("Open Jobs: 0");
        avgBudgetLabel = createStatLabel("Avg Budget: $0");

        statsPanel.add(totalJobsLabel);
        statsPanel.add(openJobsLabel);
        statsPanel.add(avgBudgetLabel);

        topPanel.add(statsPanel, BorderLayout.CENTER);
        add(topPanel, BorderLayout.NORTH);

        // Center Panel - Table
        JPanel centerPanel = new JPanel(new BorderLayout());
        centerPanel.setBorder(new EmptyBorder(0, 10, 10, 10));

        String[] columns = {"Job ID", "Title", "Category", "Required Skills", "Budget ($)", 
                           "Deadline", "Status", "Applications"};

        jobTableModel = new DefaultTableModel(columns, 0) {
            private static final long serialVersionUID = 1L;

            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        jobTable = new JTable(jobTableModel);
        jobTable.setRowHeight(30);
        jobTable.setFont(new Font("Times New Roman", Font.PLAIN, 12));
        jobTable.getTableHeader().setFont(new Font("Times New Roman", Font.BOLD, 13));
        jobTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        JScrollPane scrollPane = new JScrollPane(jobTable);
        centerPanel.add(scrollPane, BorderLayout.CENTER);
        add(centerPanel, BorderLayout.CENTER);

        // Right Panel - Buttons
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
        buttonPanel.setBorder(new EmptyBorder(10, 10, 10, 10));

        JButton postJobButton = createButton("Post New Job");
        JButton viewJobButton = createButton("View Job Details");
        JButton applyJobButton = createButton("Apply to Job");
        JButton viewApplicationsButton = createButton("View Applications");
        JButton manageFreelancersButton = createButton("Manage Freelancers");
        JButton generateReportButton = createButton("Generate Report");
        JButton deleteJobButton = createButton("Delete Job");

        postJobButton.addActionListener(e -> showPostJobDialog());
        viewJobButton.addActionListener(e -> viewJobDetails());
        applyJobButton.addActionListener(e -> showApplyJobDialog());
        viewApplicationsButton.addActionListener(e -> viewApplications());
        manageFreelancersButton.addActionListener(e -> manageFreelancers());
        generateReportButton.addActionListener(e -> generateReport());
        deleteJobButton.addActionListener(e -> deleteSelectedJob());

        buttonPanel.add(postJobButton);
        buttonPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        buttonPanel.add(viewJobButton);
        buttonPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        buttonPanel.add(applyJobButton);
        buttonPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        buttonPanel.add(viewApplicationsButton);
        buttonPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        buttonPanel.add(manageFreelancersButton);
        buttonPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        buttonPanel.add(generateReportButton);
        buttonPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        buttonPanel.add(deleteJobButton);

        add(buttonPanel, BorderLayout.EAST);
    }

    private JLabel createStatLabel(String text) {
        JLabel label = new JLabel(text, SwingConstants.CENTER);
        label.setFont(new Font("Times New Roman", Font.BOLD, 14));
        label.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(Color.WHITE, 2),
            new EmptyBorder(8, 15, 8, 15)
        ));
        label.setOpaque(true);
        label.setBackground(new Color(52, 152, 219));
        label.setForeground(Color.WHITE);
        return label;
    }

    private JButton createButton(String text) {
        JButton button = new JButton(text);
        button.setFont(new Font("Times New Roman", Font.PLAIN, 13));
        button.setPreferredSize(new Dimension(180, 40));
        button.setMaximumSize(new Dimension(180, 40));
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        button.setBackground(new Color(41, 128, 185));
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        return button;
    }

    private void showPostJobDialog() {
        JDialog dialog = new JDialog(this, "Post New Job", true);
        dialog.setSize(500, 550);
        dialog.setLocationRelativeTo(this);
        dialog.setLayout(new BorderLayout(10, 10));

        JPanel formPanel = new JPanel(new GridLayout(8, 2, 10, 10));
        formPanel.setBorder(new EmptyBorder(20, 20, 10, 20));

        JLabel titleLabel = new JLabel("Job Title:");
        JTextField titleField = new JTextField();

        JLabel categoryLabel = new JLabel("Category:");
        String[] categories = {"Web Development", "Mobile Development", "Design", 
                              "Writing", "Marketing", "Data Science", "Other"};
        JComboBox<String> categoryCombo = new JComboBox<>(categories);

        JLabel descLabel = new JLabel("Description:");
        JTextArea descArea = new JTextArea(3, 20);
        JScrollPane descScroll = new JScrollPane(descArea);

        JLabel skillsLabel = new JLabel("Required Skills:");
        JTextField skillsField = new JTextField();

        JLabel budgetLabel = new JLabel("Budget ($):");
        JTextField budgetField = new JTextField();

        JLabel durationLabel = new JLabel("Duration (days):");
        JTextField durationField = new JTextField();

        JLabel experienceLabel = new JLabel("Experience Level:");
        String[] levels = {"Beginner", "Intermediate", "Expert"};
        JComboBox<String> experienceCombo = new JComboBox<>(levels);

        formPanel.add(titleLabel);
        formPanel.add(titleField);
        formPanel.add(categoryLabel);
        formPanel.add(categoryCombo);
        formPanel.add(descLabel);
        formPanel.add(descScroll);
        formPanel.add(skillsLabel);
        formPanel.add(skillsField);
        formPanel.add(budgetLabel);
        formPanel.add(budgetField);
        formPanel.add(durationLabel);
        formPanel.add(durationField);
        formPanel.add(experienceLabel);
        formPanel.add(experienceCombo);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JButton postButton = new JButton("Post Job");
        JButton cancelButton = new JButton("Cancel");

        postButton.addActionListener(e -> {
            try {
                String title = titleField.getText().trim();
                String category = (String) categoryCombo.getSelectedItem();
                String description = descArea.getText().trim();
                String skills = skillsField.getText().trim();
                double budget = Double.parseDouble(budgetField.getText().trim());
                int duration = Integer.parseInt(durationField.getText().trim());
                String experience = (String) experienceCombo.getSelectedItem();

                if (title.isEmpty() || description.isEmpty() || skills.isEmpty()) {
                    JOptionPane.showMessageDialog(dialog, "Please fill all fields!", 
                        "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                if (budget <= 0 || duration <= 0) {
                    JOptionPane.showMessageDialog(dialog, 
                        "Budget and duration must be positive values!", 
                        "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                Job job = new Job(title, category, description, skills, budget, 
                                 duration, experience);
                jobs.add(job);
                addJobToTable(job);
                updateStatistics();

                JOptionPane.showMessageDialog(dialog, "Job posted successfully!", 
                    "Success", JOptionPane.INFORMATION_MESSAGE);
                dialog.dispose();
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(dialog, 
                    "Please enter valid numbers for budget and duration!", 
                    "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        cancelButton.addActionListener(e -> dialog.dispose());

        buttonPanel.add(postButton);
        buttonPanel.add(cancelButton);

        dialog.add(formPanel, BorderLayout.CENTER);
        dialog.add(buttonPanel, BorderLayout.SOUTH);
        dialog.setVisible(true);
    }

    private void addJobToTable(Job job) {
        Object[] rowData = {
            job.jobId,
            job.title,
            job.category,
            truncate(job.requiredSkills, 20),
            String.format("%.2f", job.budget),
            job.deadline,
            job.status,
            job.applicationCount
        };
        jobTableModel.addRow(rowData);
    }

    private void viewJobDetails() {
        int selectedRow = jobTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select a job to view details!", 
                "Warning", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int jobId = (Integer) jobTableModel.getValueAt(selectedRow, 0);
        Job job = null;
        for (Job j : jobs) {
            if (j.jobId == jobId) {
                job = j;
                break;
            }
        }

        if (job != null) {
            String details = String.format(
                "Job ID: %d\n" +
                "Title: %s\n" +
                "Category: %s\n" +
                "Description: %s\n" +
                "Required Skills: %s\n" +
                "Budget: $%.2f\n" +
                "Duration: %d days\n" +
                "Deadline: %s\n" +
                "Experience Level: %s\n" +
                "Status: %s\n" +
                "Applications Received: %d\n" +
                "Posted Date: %s",
                job.jobId, job.title, job.category, job.description,
                job.requiredSkills, job.budget, job.duration, job.deadline,
                job.experienceLevel, job.status, job.applicationCount, job.postedDate
            );

            JTextArea textArea = new JTextArea(details);
            textArea.setEditable(false);
            textArea.setFont(new Font("Times New Roman", Font.PLAIN, 12));
            JScrollPane scrollPane = new JScrollPane(textArea);
            scrollPane.setPreferredSize(new Dimension(450, 300));

            JOptionPane.showMessageDialog(this, scrollPane, "Job Details",
                JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private void showApplyJobDialog() {
        int selectedRow = jobTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select a job to apply!", 
                "Warning", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int jobId = (Integer) jobTableModel.getValueAt(selectedRow, 0);
        Job job = null;
        for (Job j : jobs) {
            if (j.jobId == jobId) {
                job = j;
                break;
            }
        }

        if (job == null) return;

        final Job selectedJob = job;

        JDialog dialog = new JDialog(this, "Apply to Job: " + job.title, true);
        dialog.setSize(500, 450);
        dialog.setLocationRelativeTo(this);
        dialog.setLayout(new BorderLayout(10, 10));

        JPanel formPanel = new JPanel(new GridLayout(6, 2, 10, 10));
        formPanel.setBorder(new EmptyBorder(20, 20, 10, 20));

        JLabel nameLabel = new JLabel("Freelancer Name:");
        JTextField nameField = new JTextField();

        JLabel emailLabel = new JLabel("Email:");
        JTextField emailField = new JTextField();

        JLabel skillsLabel = new JLabel("Your Skills:");
        JTextField skillsField = new JTextField();

        JLabel rateLabel = new JLabel("Proposed Rate ($):");
        JTextField rateField = new JTextField();

        JLabel coverLabel = new JLabel("Cover Letter:");
        JTextArea coverArea = new JTextArea(4, 20);
        JScrollPane coverScroll = new JScrollPane(coverArea);

        formPanel.add(nameLabel);
        formPanel.add(nameField);
        formPanel.add(emailLabel);
        formPanel.add(emailField);
        formPanel.add(skillsLabel);
        formPanel.add(skillsField);
        formPanel.add(rateLabel);
        formPanel.add(rateField);
        formPanel.add(coverLabel);
        formPanel.add(coverScroll);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JButton submitButton = new JButton("Submit Application");
        JButton cancelButton = new JButton("Cancel");

        submitButton.addActionListener(e -> {
            try {
                String name = nameField.getText().trim();
                String email = emailField.getText().trim();
                String skills = skillsField.getText().trim();
                double rate = Double.parseDouble(rateField.getText().trim());
                String cover = coverArea.getText().trim();

                if (name.isEmpty() || email.isEmpty() || skills.isEmpty() || cover.isEmpty()) {
                    JOptionPane.showMessageDialog(dialog, "Please fill all fields!", 
                        "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                if (rate <= 0) {
                    JOptionPane.showMessageDialog(dialog, "Rate must be positive!", 
                        "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                Freelancer freelancer = new Freelancer(name, email, skills, rate);
                freelancers.add(freelancer);

                Application app = new Application(selectedJob, freelancer, cover, rate);
                applications.add(app);
                selectedJob.applicationCount++;

                jobTableModel.setValueAt(selectedJob.applicationCount, selectedRow, 7);
                updateStatistics();

                JOptionPane.showMessageDialog(dialog, 
                    "Application submitted successfully!", 
                    "Success", JOptionPane.INFORMATION_MESSAGE);
                dialog.dispose();
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(dialog, 
                    "Please enter a valid number for rate!", 
                    "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        cancelButton.addActionListener(e -> dialog.dispose());

        buttonPanel.add(submitButton);
        buttonPanel.add(cancelButton);

        dialog.add(formPanel, BorderLayout.CENTER);
        dialog.add(buttonPanel, BorderLayout.SOUTH);
        dialog.setVisible(true);
    }

    private void viewApplications() {
        int selectedRow = jobTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select a job to view applications!", 
                "Warning", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int jobId = (Integer) jobTableModel.getValueAt(selectedRow, 0);
        Job job = null;
        for (Job j : jobs) {
            if (j.jobId == jobId) {
                job = j;
                break;
            }
        }

        if (job == null) return;

        List<Application> jobApplications = new ArrayList<>();
        for (Application app : applications) {
            if (app.job.jobId == jobId) {
                jobApplications.add(app);
            }
        }

        if (jobApplications.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No applications received for this job!", 
                "Information", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        JDialog dialog = new JDialog(this, "Applications for: " + job.title, true);
        dialog.setSize(800, 500);
        dialog.setLocationRelativeTo(this);
        dialog.setLayout(new BorderLayout(10, 10));

        String[] columns = {"App ID", "Freelancer", "Email", "Skills", "Rate ($)", 
                           "Status", "Applied Date"};
        DefaultTableModel appTableModel = new DefaultTableModel(columns, 0) {
            private static final long serialVersionUID = 1L;

            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        for (Application app : jobApplications) {
            Object[] rowData = {
                app.applicationId,
                app.freelancer.name,
                app.freelancer.email,
                truncate(app.freelancer.skills, 15),
                String.format("%.2f", app.proposedRate),
                app.status,
                app.appliedDate
            };
            appTableModel.addRow(rowData);
        }

        JTable appTable = new JTable(appTableModel);
        appTable.setRowHeight(25);
        appTable.setFont(new Font("Times New Roman", Font.PLAIN, 12));
        JScrollPane scrollPane = new JScrollPane(appTable);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JButton acceptButton = new JButton("Accept Application");
        JButton rejectButton = new JButton("Reject Application");
        JButton closeButton = new JButton("Close");

        acceptButton.addActionListener(e -> {
            int selectedAppRow = appTable.getSelectedRow();
            if (selectedAppRow == -1) {
                JOptionPane.showMessageDialog(dialog, "Please select an application!", 
                    "Warning", JOptionPane.WARNING_MESSAGE);
                return;
            }

            int appId = (Integer) appTableModel.getValueAt(selectedAppRow, 0);
            Application app = null;
            for (Application a : jobApplications) {
                if (a.applicationId == appId) {
                    app = a;
                    break;
                }
            }

            if (app != null) {
                app.status = "Accepted";
                appTableModel.setValueAt("Accepted", selectedAppRow, 5);
                JOptionPane.showMessageDialog(dialog, "Application accepted!", 
                    "Success", JOptionPane.INFORMATION_MESSAGE);
            }
        });

        rejectButton.addActionListener(e -> {
            int selectedAppRow = appTable.getSelectedRow();
            if (selectedAppRow == -1) {
                JOptionPane.showMessageDialog(dialog, "Please select an application!", 
                    "Warning", JOptionPane.WARNING_MESSAGE);
                return;
            }

            int appId = (Integer) appTableModel.getValueAt(selectedAppRow, 0);
            Application app = null;
            for (Application a : jobApplications) {
                if (a.applicationId == appId) {
                    app = a;
                    break;
                }
            }

            if (app != null) {
                app.status = "Rejected";
                appTableModel.setValueAt("Rejected", selectedAppRow, 5);
                JOptionPane.showMessageDialog(dialog, "Application rejected!", 
                    "Success", JOptionPane.INFORMATION_MESSAGE);
            }
        });

        closeButton.addActionListener(e -> dialog.dispose());

        buttonPanel.add(acceptButton);
        buttonPanel.add(rejectButton);
        buttonPanel.add(closeButton);

        dialog.add(scrollPane, BorderLayout.CENTER);
        dialog.add(buttonPanel, BorderLayout.SOUTH);
        dialog.setVisible(true);
    }

    private void manageFreelancers() {
        if (freelancers.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No freelancers registered yet!", 
                "Information", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        JDialog dialog = new JDialog(this, "Registered Freelancers", true);
        dialog.setSize(800, 500);
        dialog.setLocationRelativeTo(this);
        dialog.setLayout(new BorderLayout(10, 10));

        String[] columns = {"ID", "Name", "Email", "Skills", "Rate ($/hr)", "Applications"};
        DefaultTableModel freelancerTableModel = new DefaultTableModel(columns, 0) {
            private static final long serialVersionUID = 1L;

            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        for (Freelancer freelancer : freelancers) {
            int appCount = 0;
            for (Application app : applications) {
                if (app.freelancer.userId == freelancer.userId) {
                    appCount++;
                }
            }

            Object[] rowData = {
                freelancer.userId,
                freelancer.name,
                freelancer.email,
                truncate(freelancer.skills, 20),
                String.format("%.2f", freelancer.hourlyRate),
                appCount
            };
            freelancerTableModel.addRow(rowData);
        }

        JTable freelancerTable = new JTable(freelancerTableModel);
        freelancerTable.setRowHeight(25);
        freelancerTable.setFont(new Font("Times New Roman", Font.PLAIN, 12));
        JScrollPane scrollPane = new JScrollPane(freelancerTable);

        JButton closeButton = new JButton("Close");
        closeButton.addActionListener(e -> dialog.dispose());

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.add(closeButton);

        dialog.add(scrollPane, BorderLayout.CENTER);
        dialog.add(buttonPanel, BorderLayout.SOUTH);
        dialog.setVisible(true);
    }

    private void generateReport() {
        if (jobs.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No jobs available to generate report!", 
                "Warning", JOptionPane.WARNING_MESSAGE);
            return;
        }

        JDialog reportDialog = new JDialog(this, "Platform Report", true);
        reportDialog.setSize(800, 650);
        reportDialog.setLocationRelativeTo(this);
        reportDialog.setLayout(new BorderLayout(10, 10));

        JPanel headerPanel = new JPanel();
        headerPanel.setLayout(new BoxLayout(headerPanel, BoxLayout.Y_AXIS));
        headerPanel.setBorder(new EmptyBorder(20, 20, 10, 20));
        headerPanel.setBackground(new Color(240, 248, 255));

        JLabel titleLabel = new JLabel("Freelance Platform Report");
        titleLabel.setFont(new Font("Times New Roman", Font.BOLD, 24));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        SimpleDateFormat dateFormat = new SimpleDateFormat("MMMM dd, yyyy");
        JLabel dateLabel = new JLabel("Generated on: " + dateFormat.format(new Date()));
        dateLabel.setFont(new Font("Times New Roman", Font.PLAIN, 14));
        dateLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        headerPanel.add(titleLabel);
        headerPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        headerPanel.add(dateLabel);

        reportDialog.add(headerPanel, BorderLayout.NORTH);

        JTextArea reportArea = new JTextArea();
        reportArea.setFont(new Font("Courier New", Font.PLAIN, 12));
        reportArea.setEditable(false);
        reportArea.setBorder(new EmptyBorder(10, 10, 10, 10));

        StringBuilder reportText = new StringBuilder();
        reportText.append("========================================================================\n");
        reportText.append("                    FREELANCE PLATFORM SUMMARY REPORT\n");
        reportText.append("========================================================================\n\n");

        reportText.append("PLATFORM STATISTICS:\n");
        reportText.append("--------------------------------------------------------------------\n");
        reportText.append(String.format("Total Jobs Posted: %d\n", jobs.size()));
        
        int openJobs = 0;
        int closedJobs = 0;
        for (Job j : jobs) {
            if (j.status.equals("Open")) openJobs++;
            if (j.status.equals("Closed")) closedJobs++;
        }
        
        reportText.append(String.format("Open Jobs: %d\n", openJobs));
        reportText.append(String.format("Closed Jobs: %d\n", closedJobs));
        reportText.append(String.format("Total Freelancers: %d\n", freelancers.size()));
        reportText.append(String.format("Total Applications: %d\n\n", applications.size()));

        double totalBudget = 0;
        double maxBudget = 0;
        double minBudget = Double.MAX_VALUE;
        
        for (Job j : jobs) {
            totalBudget += j.budget;
            if (j.budget > maxBudget) maxBudget = j.budget;
            if (j.budget < minBudget) minBudget = j.budget;
        }
        
        double avgBudget = jobs.isEmpty() ? 0 : totalBudget / jobs.size();
        if (jobs.isEmpty()) minBudget = 0;

        reportText.append("BUDGET ANALYSIS:\n");
        reportText.append("--------------------------------------------------------------------\n");
        reportText.append(String.format("Total Budget Pool: $%.2f\n", totalBudget));
        reportText.append(String.format("Average Job Budget: $%.2f\n", avgBudget));
        reportText.append(String.format("Highest Budget: $%.2f\n", maxBudget));
        reportText.append(String.format("Lowest Budget: $%.2f\n\n", minBudget));

        reportText.append("JOBS BY CATEGORY:\n");
        reportText.append("--------------------------------------------------------------------\n");
        Map<String, Integer> categoryCount = new HashMap<>();
        for (Job job : jobs) {
            categoryCount.put(job.category, categoryCount.getOrDefault(job.category, 0) + 1);
        }
        for (Map.Entry<String, Integer> entry : categoryCount.entrySet()) {
            reportText.append(String.format("%-20s: %d job(s)\n", entry.getKey(), entry.getValue()));
        }
        reportText.append("\n");

        reportText.append("APPLICATION STATISTICS:\n");
        reportText.append("--------------------------------------------------------------------\n");
        int pendingApps = 0;
        int acceptedApps = 0;
        int rejectedApps = 0;
        
        for (Application a : applications) {
            if (a.status.equals("Pending")) pendingApps++;
            if (a.status.equals("Accepted")) acceptedApps++;
            if (a.status.equals("Rejected")) rejectedApps++;
        }

        reportText.append(String.format("Pending Applications: %d\n", pendingApps));
        reportText.append(String.format("Accepted Applications: %d\n", acceptedApps));
        reportText.append(String.format("Rejected Applications: %d\n\n", rejectedApps));

        reportText.append("TOP 5 JOBS BY APPLICATIONS:\n");
        reportText.append("--------------------------------------------------------------------\n");
        
        List<Job> sortedJobs = new ArrayList<>(jobs);
        sortedJobs.sort((j1, j2) -> Integer.compare(j2.applicationCount, j1.applicationCount));
        
        int count = 0;
        for (Job job : sortedJobs) {
            if (count >= 5) break;
            reportText.append(String.format("%-30s | Applications: %d | Budget: $%.2f\n",
                truncate(job.title, 28), job.applicationCount, job.budget));
            count++;
        }
        
        reportText.append("\n");

        reportText.append("TOP ACTIVE FREELANCERS:\n");
        reportText.append("--------------------------------------------------------------------\n");
        Map<Integer, Integer> freelancerAppCount = new HashMap<>();
        for (Application app : applications) {
            int userId = app.freelancer.userId;
            freelancerAppCount.put(userId, freelancerAppCount.getOrDefault(userId, 0) + 1);
        }

        List<Freelancer> sortedFreelancers = new ArrayList<>(freelancers);
        sortedFreelancers.sort((f1, f2) -> Integer.compare(
            freelancerAppCount.getOrDefault(f2.userId, 0),
            freelancerAppCount.getOrDefault(f1.userId, 0)));

        count = 0;
        for (Freelancer freelancer : sortedFreelancers) {
            if (count >= 5) break;
            int appCount = freelancerAppCount.getOrDefault(freelancer.userId, 0);
            reportText.append(String.format("%-25s | Applications: %d | Rate: $%.2f/hr\n",
                truncate(freelancer.name, 23), appCount, freelancer.hourlyRate));
            count++;
        }

        reportText.append("\n");
        reportText.append("========================================================================\n");
        reportText.append("                         END OF REPORT\n");
        reportText.append("========================================================================\n");

        reportArea.setText(reportText.toString());
        JScrollPane scrollPane = new JScrollPane(reportArea);
        reportDialog.add(scrollPane, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JButton exportButton = new JButton("Export to File");
        JButton printButton = new JButton("Print Report");
        JButton closeButton = new JButton("Close");

        exportButton.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setDialogTitle("Save Report");
            fileChooser.setSelectedFile(new java.io.File("Platform_Report_" + 
                new SimpleDateFormat("yyyyMMdd").format(new Date()) + ".txt"));
            
            if (fileChooser.showSaveDialog(reportDialog) == JFileChooser.APPROVE_OPTION) {
                try {
                    java.io.FileWriter writer = new java.io.FileWriter(
                        fileChooser.getSelectedFile());
                    writer.write(reportArea.getText());
                    writer.close();
                    JOptionPane.showMessageDialog(reportDialog, 
                        "Report exported successfully!", "Success", 
                        JOptionPane.INFORMATION_MESSAGE);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(reportDialog, 
                        "Error exporting: " + ex.getMessage(), "Error", 
                        JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        printButton.addActionListener(e -> {
            try {
                reportArea.print();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(reportDialog, 
                    "Error printing: " + ex.getMessage(), "Error", 
                    JOptionPane.ERROR_MESSAGE);
            }
        });

        closeButton.addActionListener(e -> reportDialog.dispose());

        buttonPanel.add(exportButton);
        buttonPanel.add(printButton);
        buttonPanel.add(closeButton);

        reportDialog.add(buttonPanel, BorderLayout.SOUTH);
        reportDialog.setVisible(true);
    }

    private void deleteSelectedJob() {
        int selectedRow = jobTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select a job to delete!", 
                "Warning", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(this, 
            "Are you sure you want to delete this job?\nAll associated applications will also be removed.", 
            "Confirm Delete", JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            int jobId = (Integer) jobTableModel.getValueAt(selectedRow, 0);
            
            applications.removeIf(app -> app.job.jobId == jobId);
            jobs.removeIf(job -> job.jobId == jobId);
            
            jobTableModel.removeRow(selectedRow);
            updateStatistics();
            
            JOptionPane.showMessageDialog(this, "Job deleted successfully!", 
                "Success", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private void updateStatistics() {
        int totalJobs = jobs.size();
        int openJobs = 0;
        double totalBudget = 0;
        
        for (Job j : jobs) {
            if (j.status.equals("Open")) openJobs++;
            totalBudget += j.budget;
        }
        
        double avgBudget = jobs.isEmpty() ? 0 : totalBudget / jobs.size();

        totalJobsLabel.setText("Total Jobs: " + totalJobs);
        openJobsLabel.setText("Open Jobs: " + openJobs);
        avgBudgetLabel.setText(String.format("Avg Budget: $%.0f", avgBudget));
    }

    private String truncate(String text, int length) {
        return text.length() > length ? text.substring(0, length - 2) + ".." : text;
    }

    private void loadSampleData() {
        jobs.add(new Job("E-commerce Website Development", "Web Development", 
            "Looking for experienced developer to build a modern e-commerce platform", 
            "React, Node.js, MongoDB", 5000, 45, "Expert"));
        
        jobs.add(new Job("Mobile App UI/UX Design", "Design", 
            "Need creative designer for iOS and Android app interface", 
            "Figma, Adobe XD, Sketch", 2500, 30, "Intermediate"));
        
        jobs.add(new Job("SEO Content Writing", "Writing", 
            "Write 20 SEO-optimized blog posts for tech website", 
            "SEO, Content Writing, Research", 800, 20, "Beginner"));
        
        jobs.add(new Job("Data Analysis Dashboard", "Data Science", 
            "Create interactive dashboard for sales analytics", 
            "Python, Tableau, SQL", 3500, 35, "Expert"));
        
        jobs.add(new Job("Social Media Marketing Campaign", "Marketing", 
            "Manage social media accounts and create engaging content", 
            "Social Media, Content Creation", 1500, 60, "Intermediate"));

        freelancers.add(new Freelancer("Priya Sharma", "priya.sharma@email.com", 
            "React, Node.js, MongoDB, JavaScript", 50));
        
        freelancers.add(new Freelancer("Rahul Verma", "rahul.verma@email.com", 
            "Figma, Adobe XD, UI/UX Design", 40));
        
        freelancers.add(new Freelancer("Anjali Patel", "anjali.patel@email.com", 
            "SEO, Content Writing, Blogging", 25));

        if (!jobs.isEmpty() && !freelancers.isEmpty()) {
            Application app1 = new Application(jobs.get(0), freelancers.get(0), 
                "I have 5 years of experience in full-stack development...", 4800);
            applications.add(app1);
            jobs.get(0).applicationCount++;

            Application app2 = new Application(jobs.get(1), freelancers.get(1), 
                "I'm a passionate UI/UX designer with strong portfolio...", 2400);
            applications.add(app2);
            jobs.get(1).applicationCount++;

            Application app3 = new Application(jobs.get(2), freelancers.get(2), 
                "I specialize in SEO content writing and have written 100+ articles...", 750);
            applications.add(app3);
            jobs.get(2).applicationCount++;
        }

        for (Job job : jobs) {
            addJobToTable(job);
        }

        updateStatistics();
    }

    static // Inner Classes
    class Job {
        static int counter = 1;
        int jobId;
        String title;
        String category;
        String description;
        String requiredSkills;
        double budget;
        int duration;
        String experienceLevel;
        String status;
        String postedDate;
        String deadline;
        int applicationCount;

        public Job(String title, String category, String description, String skills,
                   double budget, int duration, String experience) {
            this.jobId = counter++;
            this.title = title;
            this.category = category;
            this.description = description;
            this.requiredSkills = skills;
            this.budget = budget;
            this.duration = duration;
            this.experienceLevel = experience;
            this.status = "Open";
            this.applicationCount = 0;

            Date now = new Date();
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            this.postedDate = dateFormat.format(now);

            Calendar cal = Calendar.getInstance();
            cal.setTime(now);
            cal.add(Calendar.DAY_OF_MONTH, duration);
            this.deadline = dateFormat.format(cal.getTime());
        }
    }

    static class User {
        static int userCounter = 1;
        int userId;
        String name;
        String email;

        public User(String name, String email) {
            this.userId = userCounter++;
            this.name = name;
            this.email = email;
        }
    }

    class Freelancer extends User {
        String skills;
        double hourlyRate;

        public Freelancer(String name, String email, String skills, double rate) {
            super(name, email);
            this.skills = skills;
            this.hourlyRate = rate;
        }
    }

    static class Application {
        static int appCounter = 1;
        int applicationId;
        Job job;
        Freelancer freelancer;
        String coverLetter;
        double proposedRate;
        String status;
        String appliedDate;

        public Application(Job job, Freelancer freelancer, String cover, double rate) {
            this.applicationId = appCounter++;
            this.job = job;
            this.freelancer = freelancer;
            this.coverLetter = cover;
            this.proposedRate = rate;
            this.status = "Pending";

            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            this.appliedDate = dateFormat.format(new Date());
        }
    }
}