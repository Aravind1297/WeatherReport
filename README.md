# WeatherReport
A full-stack weather application built with Spring Boot (backend) and Vite + React (frontend), deployed on Google Cloud Platform.

Features:
**Backend (Spring Boot)**
-> Internal cache using ConcurrentHashMap with 1-hour expiry

-> Deployed on Google App Engine with auto-scaling

-> Configuration via application.properties or environment variables

-> Lightweight and RESTful endpoints

**Frontend (React + Vite)**
-> Responsive design for both mobile and desktop

-> Debounced search by city name

-> Displays weather details and air quality index

->Uses .env for environment-specific config

-> Development with npm run dev, build with npm run build


**Quick Start:**

1. Clone the Repo
   git clone https://github.com/Aravind1297/WeatherReport.git
   cd WeatherReport

2. Backend Setup
   cd backend
   // Add your API key in application.properties (or set via env):
   //weatherstack.api.key=YOUR_API_KEY
   //weatherstack.api.url=http://api.weatherstack.com
  ./mvnw clean package
  gcloud app deploy app.yaml

3. Frontend Setup
   cd ../frontend
  npm install
  // Create .env:
   // VITE_WEATHER_API_URL=https://<your-app-id>.appspot.com/weather/data**
  npm run dev         # Development mode
  npm run build       # Production build

4. Deploy Frontend to GCP
   Copy dist/ to GCP App Engine or host via Firebase/GCS.

**HTTP Endpoints**
GET /weather/data?city={city}

Example:
GET https://<your-app-id>.appspot.com/weather/data?city=chennai

Response:

{
  "city": "chennai",
  "temperature": 34.0,
  "humidity": 53,
  "feelsLike": 39,
  "windSpeed": 22,
  "pressure": 1005,
  "weatherDescriptions": ["Haze"],
  "weatherIcons": ["..."],
  "pm2_5": 31.635,
  "pm10": 77.33,
  "country": "India",
  "region": "Tamil Nadu",
  "latitude": 13.083,
  "longitude": 80.283
}

**Testing**
cd backend
./mvnw test


**Contributing**
1.Fork the repo
2.Create feature/<name> branch
3.Make changes & test
4.Create a Pull Request to main
