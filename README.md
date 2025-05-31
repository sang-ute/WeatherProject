<h1 align="center">🌦️ WeatherProject</h1>

<p align="center">
  A Kotlin Android app that fetches data from the <a href="https://openweathermap.org/api" target="_blank">OpenWeatherMap API</a> and displays global weather information.
</p>

---

## 📱 About The Project

WeatherProject is a modern weather forecasting app that provides real-time and 4-day forecasts for any location worldwide. With support for multiple units, languages, and GPS integration, it offers a smooth and informative user experience.

### ✨ Features

- ⏱ Real-time weather data
- 📅 4-day weather forecast
- 🌍 Worldwide location search
- 🌡️ Unit selection: Metric (°C), Imperial (°F), and Standard (K)
- 📍 GPS integration to detect current location
- 🌐 Language: English
- ❤️ Save up to 3 favorite locations (3+ locations available in the full version)
- 🔔 Notifications based on weather conditions (e.g., rain, snow, etc.) (in development)

---

## 🛠️ Technologies Used

| Tech                      | Description                              |
| ------------------------- | ---------------------------------------- |
| **Kotlin**                | Modern Android programming language      |
| **Android SDK**           | Core toolkit for Android development     |
| **MVVM Architecture**     | Clean separation of UI and data layers   |
| **SharedPreferences**     | Store user settings locally              |
| **ViewModel**             | Handle UI-related data lifecycle-aware   |
| **LiveData**              | Observe data changes and update UI       |
| **ViewBinding**           | Safer access to layout views             |
| **Coroutines**            | Simplified asynchronous programming      |
| **Flows**                 | Reactive data stream management          |
| **Retrofit**              | REST API networking client               |
| **MPAndroidChart**        | Beautiful chart and graph rendering      |
| **Glide**                 | Fast image loading and caching           |
| **Algolia Search Client** | Blazing fast search for location queries |
| **Dagger Hilt**           | Powerful dependency injection framework  |

---

## 🚀 Getting Started

### ✅ Prerequisites

- Android Studio installed
- Android device or emulator

### 🧩 Installation

1. **Clone the repository**:

   ```bash
   git clone https://github.com/sang-ute/WeatherProject.git
   ```

2. **Open the project in Android Studio**

3. Apply for API key in OpenWeatherMap, the key should be free for everyone

4. You will need to create a free account on <a href="https://www.algolia.com/es/">Algolia</a>. Then, create an application
5. Generate a search API key, save the ID of the app you have created, and then create an index under that app. You will need to save the index name to set up the search functionality in this app

6. Next, populate the index with the following <a href="https://drive.google.com/file/d/1ImLwmPhV83evkeQs1zu2iR8N6k7bQ-Je/view?usp=sharing">records</a>

7. Wait for the API key to activate, which should take about 10 minutes to 3 hours (at worst 1 day)

8. Go to your local.properties and add these accordingly:

```
OPENWEATHER_KEY=Your_OpenWeather_API_Key
ALGOLIA_KEY=Your_Agolia_Key
ALGOLIA_APP_ID=your_Agolia_App_id
ALGOLIA_INDEX_NAME=Locations
```

8. **Build and run on your device or emulator**

---

## 📌 Roadmap

- [x] Real-time weather display
- [x] Forecast for 4 days
- [x] Search by location
- [x] GPS support
- [x] Save favorite locations (up to 3 places)
- [x] Switching metrics (Celsius/Fahrenheit/Kelvin)
- [x] Notification system
