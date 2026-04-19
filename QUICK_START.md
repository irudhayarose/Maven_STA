# 🚀 Class Sync Maven - Quick Start Guide

## ⚡ 30-Second Setup

```bash
# Navigate to project
cd class-sync-maven/class-sync

# Build project
mvn clean install -DskipTests

# Run application
mvn spring-boot:run
```

**Access**: http://localhost:8082

---

## 🔐 Login Credentials

### Admin Account
```
Username: admin
Password: admin123
```

### Faculty Account
```
Username: faculty1
Password: faculty123
```

### Student Account
```
Username: student1
Password: student123
```

---

## 📚 Pre-Loaded Data

✅ **Users**: 1 Admin + 2 Faculty + 3 Students
✅ **Courses**: 3 courses (CS, Math)
✅ **Attendance**: Sample records
✅ **Academic Records**: Sample grades

---

## 🎯 Pages Available

### Public Pages
- `/` - Landing page
- `/login` - Login page

### After Login (Role-Based)
- `/dashboard` - Main dashboard with statistics
- `/student/...` - Student pages
- `/faculty/...` - Faculty pages
- `/admin/...` - Admin pages

---

## 🗄️ Database Setup

### Option 1: PostgreSQL (Production)

```bash
# Create database
createdb classsync

# Update application.properties
spring.datasource.url=jdbc:postgresql://localhost:5432/classsync
spring.datasource.username=postgres
spring.datasource.password=postgres
```

### Option 2: H2 (Testing - Default)

```properties
# In application.properties, uncomment:
spring.datasource.url=jdbc:h2:mem:classsyncdb
spring.datasource.driverClassName=org.h2.Driver
spring.h2.console.enabled=true
```

Access H2 console: http://localhost:8082/h2-console

---

## 🛠️ Build & Run

### Development Mode
```bash
cd class-sync
mvn spring-boot:run
```

### Production JAR
```bash
mvn clean package -DskipTests
java -jar target/class-sync-1.0.0.jar
```

### Run on Different Port
```bash
mvn spring-boot:run -Dspring-boot.run.arguments="--server.port=8083"
```

---

## 📁 Project Structure

```
class-sync/
├── pom.xml                          # Maven dependencies
├── README.md                        # Full documentation
├── src/main/
│   ├── java/com/example/classsync/
│   │   ├── ClassSyncApplication.java
│   │   ├── model/                   # 6 Entity classes
│   │   ├── repository/              # 6 JPA repositories
│   │   ├── service/                 # Business logic (to be implemented)
│   │   ├── controller/              # Controllers
│   │   └── config/                  # Security & Data init
│   └── resources/
│       ├── templates/               # HTML templates
│       │   ├── landing.html
│       │   ├── login.html
│       │   ├── dashboard.html
│       │   ├── admin/
│       │   ├── faculty/
│       │   └── student/
│       ├── static/                  # CSS, JS
│       └── application.properties   # Configuration
```

---

## ✨ Features Implemented

✅ Spring Boot setup with Maven
✅ PostgreSQL + H2 database support
✅ 6 Entity models (User, Student, Faculty, Course, Attendance, AcademicRecord)
✅ JPA repositories with custom queries
✅ Spring Security authentication
✅ Role-based access control (ADMIN, FACULTY, STUDENT)
✅ Sample data initialization
✅ Professional UI (landing, login, dashboard)
✅ Responsive design
✅ Font Awesome icons

---

## 🎯 Next Steps

1. **Expand Dashboard**: Add role-specific views
2. **Implement Services**: Create service layer for business logic
3. **Student Pages**: 
   - View courses and attendance
   - Check marks and GPA
4. **Faculty Pages**:
   - Mark attendance
   - Upload marks
   - Export Excel reports
5. **Admin Pages**:
   - CRUD for users, students, courses
   - System statistics

---

## 🔧 Configuration

Edit `src/main/resources/application.properties`:

```properties
# Server
server.port=8082

# Database
spring.datasource.url=jdbc:postgresql://localhost:5432/classsync
spring.datasource.username=postgres
spring.datasource.password=postgres

# JPA
spring.jpa.hibernate.ddl-auto=create-drop
spring.jpa.show-sql=false

# Logging
logging.level.com.example.classsync=DEBUG
```

---

## 🐛 Troubleshooting

### Build Fails
```bash
# Clean Maven cache
mvn clean install -U -DskipTests
```

### Port 8082 Already in Use
```bash
# Use different port
mvn spring-boot:run -Dspring-boot.run.arguments="--server.port=8083"
```

### Database Connection Error
```bash
# Verify PostgreSQL is running
# Check application.properties credentials
# Ensure database exists: createdb classsync
```

### Can't Login
- Use demo credentials: admin/admin123
- Check console for data initialization messages
- Verify data was loaded in database

---

## 📖 Tech Stack

| Component | Technology |
|-----------|------------|
| Framework | Spring Boot 4.0.5 |
| Build | Maven 3.6+ |
| Database | PostgreSQL 12+ / H2 |
| ORM | Spring Data JPA + Hibernate |
| Security | Spring Security + BCrypt |
| Frontend | Thymeleaf + HTML5 + CSS3 |
| Icons | Font Awesome 6 |
| Excel | Apache POI |

---

## 🎨 UI Preview

### Landing Page
- Hero section with features
- Call-to-action buttons
- Feature cards with icons
- Responsive gradient design

### Login Page
- Split-screen design
- Demo credentials displayed
- Professional styling
- Form validation

### Dashboard
- Welcome message
- Statistics cards
- Quick access buttons
- Role-based quick links

---

## 📝 License & Info

**Version**: 1.0.0
**Status**: ✅ Ready for Development
**Last Updated**: 2024

---

**Ready to use! 🎓**
