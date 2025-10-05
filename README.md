# 🎲 PlayLytics
A full-stack **board-game analytics web application** that lets users track their play sessions, view statistics, and connect with other players.

---

## 📁 Project Overview
PlayLytics consists of:
- **Frontend:** Angular 20 hosted on **Netlify**
- **Backend:** Spring Boot 3 (Java 21) hosted on **Render**
- **Database:** PostgreSQL 17 hosted on **Neon**
- **Purpose:** Track board-game sessions, analyze player statistics, and manage player connections.

---

## 🚀 Live Demo
🌐 **Frontend (Netlify):** [https://playlytics.netlify.app](https://playlytics.netlify.app)  
⚙️ **Backend API (Render):** [https://playlytics.onrender.com](https://playlytics.onrender.com)

---

## 🧩 Technologies Used

### Frontend
- Angular 20
- TypeScript 5 + RxJS
- Tailwind CSS + ng2-charts (Chart.js)
- Node 20.x / npm 10.x
- Hosted on **Netlify**

### Backend
- Java 21 (OpenJDK)
- Spring Boot 3.5.5
- Spring Data JPA / Hibernate 6
- Spring Security 6
- PostgreSQL 17 (Neon Cloud DB)
- Maven 4 project structure
- Hosted on **Render**

---

## 🛠️ Local Setup

### 1️⃣ Clone the Repository

git clone https://gitlab.com/your-wgu-repo-url/d424-software-engineering-capstone.git
cd d424-software-engineering-capstone

## ⚙️ Backend Deployment (Render)

### 1️⃣ Create a Render Account
- Register at [https://render.com](https://render.com)
- Select the **Professional Tier** for 24/7 warm instance access.

### 2️⃣ Create a New Web Service
- **Repository:** Connect your GitLab repository
- **Service Name:** `playlytics`
- **Language:** Docker
- **Branch:** `working_branch`
- **Region:** Virginia (US East)
- **Root Directory:** `backend`
- **Dockerfile Path:** `playlytics/Dockerfile`

### 3️⃣ Environment Variables
Add the following under **Render → Settings → Environment**:

| Variable | Value |
|-----------|--------|
| `SPRING_DATASOURCE_URL` | `jdbc:postgresql://<HOST>.aws.neon.tech/<DB>?sslmode=require&channel_binding=require` |
| `SPRING_DATASOURCE_USERNAME` | `<USERNAME>` |
| `SPRING_DATASOURCE_PASSWORD` | `<PASSWORD>` |
| `JAVA_OPTS` | `-Xmx512m -Xss512k` |
| `SERVER_PORT` | `8080` |
| `SPRING_JPA_HIBERNATE_DDL_AUTO` | `update` |

### 4️⃣ Update CORS Configuration
In your backend, open:

/backend/playlytics/src/main/java/com/mparker/playlytics/security/SecurityConfig.java

Locate the `SecurityFilterChain` bean and **add your Netlify frontend URL** to the allowed origins:


corsConfiguration.setAllowedOrigins(List.of(
"https://playlytics.netlify.app"
));

### 5️⃣ Deploy
- Save and click **Deploy Web Service**.
- Render automatically builds the backend from the Dockerfile and starts the service.
- After successful deployment, verify your backend endpoint (e.g., `https://

---

## 💾 Database Deployment (Neon)

### 1️⃣ Create a Neon Project
- Go to [https://neon.tech](https://neon.tech)
- **Project name:** `playlytics`
- **Postgres version:** 17
- **Region:** AWS US East 1 (N. Virginia)

### 2️⃣ Get the Connection String
- From the Neon dashboard → click **Connect to Your Database**
- Copy the full string:


### 3️⃣ Set Environment Variables on Render
- In your **Render** service settings, open the **Environment Variables** section and set the following:

- These values must match your Neon connection string.

### 4️⃣ Upload Board Game Data
- Download the CSV file from `/d424-software-engineering-capstone/data/board_games.csv`
- Open **Command Prompt** or **PSQL CLI** and connect to your Neon database using the connection string.
- Once connected, run the following command (adjusting the path to your CSV file):

sql
\COPY board_games(id, game_title)
FROM 'C:/path/to/board_games.csv'
DELIMITER ','
CSV HEADER;

## 🌐 Frontend Deployment (Netlify)

### 1️⃣ Build the Angular App
- In IntelliJ or VS Code, open the **frontend** directory.
- Run the production build command:    ng build --configuration production
- After the build completes, locate the generated files at:  /frontend/dist/frontend/browser

### 2️⃣ Deploy to Netlify
- Go to [https://www.netlify.com/](https://www.netlify.com/).
- Choose **Add new site → Deploy manually**.
- Drag and drop the **browser** folder into the upload area.
- Once deployed, Netlify will generate a live URL like:

### 4️⃣ Update Frontend Environment Files
- Open both files under:  /frontend/src/environments

- **Update the API base URL** to point to your Render backend:


export const environment = {
production: false,
apiUrl: 'https://playlytics.onrender.com'
};

### 5️⃣ Rebuild and Redeploy Frontend

- Rebuild the Angular production bundle with: ng build --configuration production
- Then, drag and drop the newly generated folder: /frontend/dist/frontend/browser into Netlify’s drag-and-drop upload area to redeploy.

