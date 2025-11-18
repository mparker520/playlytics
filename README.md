# <img src="frontend/src/assets/PlayLytics Dice Logo Transparent.png" width="65"> PlayLytics


A full-stack **board-game analytics web application** that lets users track their play sessions, view statistics,
connect with other players, and track their board game inventory.

---
## 📖Table of Contents

- [Project Overview](#-project-overview)
- [Live Application](#-live-application--video-demo)
- [Technologies Used](#-technologies-used)
- [Local Setup Steps](#-local-setup-steps)
- [Current Release Features](#-current-release-features)
- [Upcoming Enhancements](#-upcoming-enhancements)
- [License](#-license)
- [About Me](#-about-me)
- [Contact](#-contact-me)

---

## 🧭 Project Overview
PlayLytics was developed the capstone project for my
B.S. in Software Engineering degree at *Western Governors University*, which I completed in October 2025.

The project demonstrates a well-structured, full-stack architecture designed to deliver a professional,
production‑ready experience. It is organized across the following layers:
- **Frontend:** Angular 20 hosted on **Render**
- **Backend:** Spring Boot 3 (Java 21) hosted on **Render**
- **Database:** PostgreSQL 17, hosted on **Neon**

---

## 🚀 Live Application
[PlayLytics Application Hosted Live on Render](https://playlytics.onrender.com)

> **Note Regarding Load Times:**
> - This application is hosted on Render’s free tier and automatically spins down after 15 minutes of inactivity.
> -  The first request after a period of idling may take up to **2–3 minutes to load** and the initial login can show a slight delay.
> - Once the app is awake, performance is normal and responsive.

---
## 🎥 Video Demo
<a href="https://onedrive.live.com/?qt=allmyphotos&photosData=%2Fshare%2FE106B216D4E61861%21s90cd55cda22a4110883c562e7b55592c%3Fithint%3Dvideo%26e%3D4%253aaj6GRI%26sharingv2%3Dtrue%26fromShare%3Dtrue%26at%3D9%26migratedtospo%3Dtrue&cid=E106B216D4E61861&id=E106B216D4E61861%21s90cd55cda22a4110883c562e7b55592c&redeem=aHR0cHM6Ly8xZHJ2Lm1zL3YvYy9FMTA2QjIxNkQ0RTYxODYxL0VjMVZ6WkFxb2hCQmlEeFdMbnRWV1N3QmpBNTZ6NUwwa1RJUXRFT3ZXclM5Tmc%5FZT00OmFqNkdSSSZzaGFyaW5ndjI9dHJ1ZSZmcm9tU2hhcmU9dHJ1ZSZhdD05&v=photos">
  <img src="frontend/src/assets/PlayLyticsLogoOriginalDiceGradientFont.png" width="350" />
</a>

---

## 🛠️ Technologies Used

### 🎨 Frontend
- Angular 20
- TypeScript 5.9 + RxJS 7.8
- Tailwind CSS 3.4
- Chart.js 4 + ng2-charts 8
- Node 20.x / npm 10.x

### ⚙️ Backend
- Java 21 (OpenJDK)
- Spring Boot 3.5.5
- Spring Data JPA 3.x  / Hibernate 6.x
- Spring Security 6.x
- Maven 3.9

### 🗄️ Database
- PostgreSQL 17 (Neon Serverless)

### ☁️️ Hosting
- Render (Frontend + Backend)
- Neon (Database)

---

## 🏠 Local Setup Steps

### 🔧 Prerequisites

Before running PlayLytics locally, ensure the following are installed:

#### 🔀 Git
- Used to clone the repository and manage branches.
- [Download Git](https://git-scm.com/downloads) for Windows, macOS, or Linux.
- Verify installation by running:
  ```bash
  git --version
  ``` 

#### 🐳 Docker Desktop
- Used to run the backend, frontend, and database with a single command without installing dependencies.
- [Download Docker Desktop](https://www.docker.com/products/docker-desktop) for Windows or macOS.  
  Linux users can install Docker Engine + Compose separately.
- Verify installation by running:
  ```bash
  docker --version
  docker-compose --version
  ``` 

### 1️⃣ Clone the Repository

From your terminal run:
```bash 
git clone https://github.com/mparker520/playlytics.git
cd playlytics
```

### 2️⃣ Run Docker Compose Command

From your terminal, run:

```bash
docker compose up --build
```

### 3️⃣ Navigate to LocalHost:8080

Open [http://localhost:8080](http://localhost:8080/) in your browser

---

## 🧩 Current Release Features

-  **User Accounts**
    - Secure account creation with unique email and display name
    - Login and authenticate user
    - Update profile details
    - Logout


-  **Analytics**
- Interactive dashboard with charts and visual insights
- Reports for past game play sessions


- **Game Play Sessions**
    - Log  game play sessions
    - Review and delete past sessions
    - Accept or decline pending game play sessions added under associated "guest player"


- **Networking**
    - **Connections**
        - Search for other players by email
        - Send, cancel, accept, or decline connection requests
        - Manage blocks and remove connections

    - **Associations**
        - Search for existing "guest players"
        - Add or create new guest players as associations
        - Manage and remove associations


- **Inventory Tracking**
    - Search across nearly 10,000 board games in the PlayLytics database
    - Add board games to personal collection
    - Search board games in collection
    - Remove board games from collection


---

##  🗓️ Upcoming Enhancements
>PlayLytics currently delivers a complete experience for gaming enthusiasts to track their sessions and gain valuable insights.
> As I continue to grow as a developer, I’m excited to build upon this strong foundation with enhancements that will elevate both functionality and user experience.

- **Authentication & User Profiles**
    - Email verification during account creation
    - Password reset & update functionality
    - Avatars for user profiles
    - Streamlined player entity model for registered and guest users


- **User Experience & Interface**
    - Modernized user interface
    - Improved responsive layout
    - Color scheme selection
    - Optimized image loading performance


- **Search & Data Insights**
    - Improved search functionality across application
    - Enhanced labeling and finer granularity in data visualizations
    - Additional analytical insights


- **Game Expansion & Integrations**
    - Add additional board games to database
    - Add scoring sheets to board games
    - Integration with future game scoring applications

---

## 📜 License
This project is licensed under the MIT License – see the [LICENSE](LICENSE) file for details.

---

## 🎓 About Me
My name is Melissa Parker, and I am a recent **Software Engineering graduate** from Western Governors University (Oct 2025).

After a decade in administrative and operational roles, I am transitioning into software engineering, bringing strong organizational, technical, and people skills
into my technical work.  Through my degree program, I gained hands‑on experience with Java, Spring Boot, Angular, TypeScript, and PostgreSQL,
applying these technologies to build full‑stack applications.

My capstone project, PlayLytics, was inspired by my passion for board games — I currently own about 80 titles in my collection!
This project reflects both my technical growth and my enthusiasm for creating tools that bring people together.

---

## 📩 Contact Me
- 📧 **Email:** [melissaparker520pro@gmail.com](mailto:melissaparker520pro@gmail.com)
- 💼 **LinkedIn:** [linkedin.com/in/mparker520](https://www.linkedin.com/in/mparker520/)
- 💻 **GitHub:** [github.com/mparker520](https://github.com/mparker520)