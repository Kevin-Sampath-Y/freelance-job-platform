# Freelance Job Posting Platform

A comprehensive Java Swing-based desktop application for managing freelance job postings, applications, and freelancer profiles. This project demonstrates Object-Oriented Programming principles including **Encapsulation**, **Inheritance**, **Polymorphism**, and **Abstraction**.

## 🎯 Project Overview

The Freelance Job Posting Platform is an OOP mini-project developed in Java that provides a complete solution for:
- Clients to post freelance job opportunities
- Freelancers to browse and apply for jobs
- Managing applications and tracking their status
- Generating comprehensive platform reports

## ✨ Features

### Core Functionality
- **Post New Jobs**: Clients can create job postings with details like title, category, description, required skills, budget, and deadline
- **Browse Jobs**: View all available jobs in an organized table format
- **Apply to Jobs**: Freelancers can submit applications with cover letters and proposed rates
- **Manage Applications**: Accept or reject applications for posted jobs
- **Freelancer Management**: View all registered freelancers and their statistics
- **Generate Reports**: Create detailed platform reports with statistics and analytics
- **Delete Jobs**: Remove jobs and associated applications

### Technical Features
- Clean and intuitive graphical user interface (GUI)
- Real-time statistics dashboard
- Data validation and error handling
- Export reports to text files
- Print functionality for reports

## 🛠️ Technologies Used

- **Language**: Java (JDK 8 or higher)
- **GUI Framework**: Java Swing
- **IDE**: Eclipse / IntelliJ IDEA / NetBeans
- **Design Pattern**: Object-Oriented Programming (OOP)

## 📋 Prerequisites

- Java Development Kit (JDK) 8 or higher
- Any Java IDE (Eclipse, IntelliJ IDEA, NetBeans, or VS Code with Java extensions)
- Basic understanding of Java and OOP concepts

## 🚀 Installation & Setup

### Option 1: Using Eclipse

1. **Clone the repository**
   ```bash
   git clone https://github.com/YOUR-USERNAME/freelance-job-platform.git
   ```

2. **Open Eclipse**
   - Go to `File` → `Import` → `General` → `Existing Projects into Workspace`
   - Browse to the cloned repository folder
   - Click `Finish`

3. **Run the Application**
   - Right-click on `FreelancePlatformManager.java`
   - Select `Run As` → `Java Application`

### Option 2: Using Command Line

1. **Clone the repository**
   ```bash
   git clone https://github.com/YOUR-USERNAME/freelance-job-platform.git
   cd freelance-job-platform
   ```

2. **Compile the Java file**
   ```bash
   javac FreelancePlatformManager.java
   ```

3. **Run the application**
   ```bash
   java FreelancePlatformManager
   ```

## 📁 Project Structure

```
freelance-job-platform/
│
├── FreelancePlatformManager.java    # Main application file
├── README.md                         # Project documentation
├── .gitignore                        # Git ignore file
├── screenshots/                      # Application screenshots
│   ├── main-dashboard.png
│   ├── post-job.png
│   ├── apply-job.png
│   └── ...
└── docs/                            # Additional documentation
    └── project-report.pdf           # Complete project report
```

## 🎓 OOP Concepts Demonstrated

### 1. **Encapsulation**
- Private fields in `Job`, `User`, `Freelancer`, and `Application` classes
- Public getter/setter methods for controlled access
- Data hiding and validation

### 2. **Inheritance**
- `User` class serves as the base class
- `Freelancer` class extends `User` class
- Code reusability and hierarchical classification

### 3. **Polymorphism**
- Method overriding in child classes
- Dynamic method dispatch
- Interface implementations

### 4. **Abstraction**
- Clear separation of concerns
- Abstract representation of real-world entities
- Focus on essential features

## 🎯 Key Classes

### Job Class
Represents a freelance job posting with attributes:
- Job ID, Title, Category
- Description, Required Skills
- Budget, Duration, Deadline
- Status, Application Count

### User Class (Base Class)
Contains common user attributes:
- User ID, Name, Email

### Freelancer Class (Extends User)
Specialized user type with:
- Skills, Hourly Rate
- Portfolio information

### Application Class
Manages job applications:
- Application ID, Job Reference
- Freelancer Reference
- Cover Letter, Proposed Rate
- Application Status

## 📊 Sample Data

The application comes pre-loaded with sample data including:
- 5 sample job postings across different categories
- 3 sample freelancer profiles
- 3 sample applications

This allows immediate testing and exploration of features.

## 🔮 Future Enhancements

- **Database Integration**: MySQL/PostgreSQL for persistent storage
- **User Authentication**: Login system with password encryption
- **Payment Integration**: Secure payment gateway
- **Advanced Search**: Filter jobs by multiple criteria
- **Rating System**: Review and rating for freelancers
- **Real-time Notifications**: Email/SMS alerts
- **Messaging System**: In-app chat between clients and freelancers
- **Portfolio Upload**: File attachment capabilities
- **Mobile App**: Android/iOS version
- **AI Matching**: Machine learning-based job matching

## 🤝 Contributing

Contributions are welcome! Please feel free to submit a Pull Request.

1. Fork the project
2. Create your feature branch (`git checkout -b feature/AmazingFeature`)
3. Commit your changes (`git commit -m 'Add some AmazingFeature'`)
4. Push to the branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request

## 👨‍💻 Author

**Kevin Sampath Y**
- Register Number: 2117240070155 
- Department: AI&DS
- Institution: Rajalakshmi Institute of Technology

## 🙏 Acknowledgments

- Thanks to my professors for guidance on OOP concepts
- Inspired by modern freelance platforms like Upwork and Fiverr
- Built as part of Object-Oriented Programming course mini-project

## ⭐ Show Your Support

If you found this project helpful, please give it a ⭐ on GitHub!

---

**Made with ❤️ for learning OOP concepts in Java**
