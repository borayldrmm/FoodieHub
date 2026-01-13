# FoodieHub - Modern Food Delivery Application

<div align="center">
  
  ### A production-ready Android food delivery app showcasing Clean Architecture and modern development practices
  
  [![Kotlin](https://img.shields.io/badge/Kotlin-1.9.0-purple.svg?style=flat&logo=kotlin)](https://kotlinlang.org)
  [![Jetpack Compose](https://img.shields.io/badge/Jetpack%20Compose-1.5.4-brightgreen.svg?style=flat)](https://developer.android.com/jetpack/compose)
  [![Android](https://img.shields.io/badge/Android-8.0%2B-green.svg?style=flat&logo=android)](https://developer.android.com)
  [![Architecture](https://img.shields.io/badge/Architecture-Clean%20%2B%20MVVM-blue.svg?style=flat)](https://developer.android.com/topic/architecture)
  
</div>

---

## 📋 Overview

FoodieHub is a comprehensive food delivery application built with modern Android development standards. The project demonstrates professional architecture patterns, reactive programming, and industry-standard best practices including Clean Architecture, SOLID principles, and comprehensive documentation.

**Development Context:** Built as part of Android Mobile Application Development Course under the mentorship of Atıl Samancıoğlu and Kasım Adalan.

---

## ✨ Key Features

### User Experience
- **Smart Catalog** - Category-based browsing with real-time search
- **Reactive Favorites** - Instant UI updates with persistent storage
- **Dynamic Cart** - Live quantity management with animated interactions
- **Order Customization** - Spice levels, toppings, and side options
- **Multi-Address Management** - Save multiple delivery locations with auto-default handling
- **Payment Methods** - Secure card storage with default selection
- **Order Tracking** - Complete order history with status updates

### Technical Highlights
- **Offline-First Architecture** - Room database with reactive Flow queries
- **State Management** - Activity-level ViewModel for global state
- **Animated Interactions** - Smooth loading→success state transitions
- **Database Migrations** - Professional schema evolution (v1→v6)
- **Type-Safe Navigation** - Jetpack Navigation with deep linking
- **Comprehensive Documentation** - Full KDoc coverage on public APIs

---

## 🏗️ Architecture

### Clean Architecture Implementation
```
┌─────────────────────────────────────────────────────────────┐
│                     Presentation Layer                      │
│  ┌──────────────┐  ┌──────────────┐  ┌──────────────────┐   │
│  │   Screens    │→ │  ViewModels  │→ │   UI States      │   │
│  │  (Compose)   │  │  (StateFlow) │  │  (Data Classes)  │   │
│  └──────────────┘  └──────────────┘  └──────────────────┘   │
└─────────────────────────────────────────────────────────────┘
                           ↓
┌─────────────────────────────────────────────────────────────┐
│                      Domain Layer                           │
│  ┌──────────────┐  ┌──────────────┐  ┌──────────────────┐   │
│  │   UseCases   │  │ Repositories │  │  Domain Models   │   │
│  │  (Business)  │→ │ (Interfaces) │  │  (Pure Kotlin)   │   │
│  └──────────────┘  └──────────────┘  └──────────────────┘   │
└─────────────────────────────────────────────────────────────┘
                           ↓
┌─────────────────────────────────────────────────────────────┐
│                       Data Layer                            │
│  ┌──────────────┐  ┌──────────────┐  ┌──────────────────┐   │
│  │ Repository   │→ │   Room DAO   │→ │    Entities      │   │
│  │     Impl     │  │   (Flow)     │  │  (Database)      │   │
│  └──────────────┘  └──────────────┘  └──────────────────┘   │
└─────────────────────────────────────────────────────────────┘
```

### Dependency Flow
- **Presentation** depends on **Domain**
- **Data** depends on **Domain**
- **Domain** has no dependencies (pure business logic)

---

## 🛠️ Tech Stack

| Category | Technology |
|----------|-----------|
| **Language** | Kotlin 1.9.0 |
| **UI** | Jetpack Compose + Material3 |
| **Architecture** | Clean Architecture + MVVM |
| **Dependency Injection** | Hilt |
| **Database** | Room with Flow |
| **Async** | Kotlin Coroutines + Flow |
| **Navigation** | Navigation Component |
| **Image Processing** | UCrop |
| **Preferences** | DataStore |

### Design Patterns
- Repository Pattern
- UseCase Pattern (Single Responsibility)
- Observer Pattern (Flow-based reactivity)
- Mapper Pattern (Entity ↔ Domain)
- Dependency Injection (Hilt)

---

## 📊 Project Metrics
```
12,000+   Lines of Code
90+       Kotlin Files
7         Repository Interfaces
25+       UseCase Classes
40+       Custom Composables
6         Database Migrations
```

---

## 🗄️ Database Architecture

### Entity Relationships
```
Users (1) ─────→ (N) Addresses
Users (1) ─────→ (N) PaymentCards
Users (1) ─────→ (N) Orders
Orders (1) ────→ (N) OrderItems
Foods (1) ─────→ (N) CartItems
```

### Key Features
- **Foreign Key Constraints** with CASCADE delete
- **Indexed Columns** for query optimization
- **Migration Strategies** for schema evolution
- **Single-Default Enforcement** via repository logic

---

## 🔄 Key Implementations

### 1. Reactive Favorite System
```kotlin
// Database persistence + Instant UI sync
override suspend fun toggleFavorite(foodId: Int) {
    val food = foodDao.getFoodByIdSync(foodId)
    food?.let {
        foodDao.updateFood(it.copy(isFavorite = !it.isFavorite))
    }
}
```

### 2. Animated Add-to-Cart Flow
```
IDLE (+ icon) → LOADING (1.5s spinner) → SUCCESS (✓ + green, 3s) → IDLE
```

### 3. Automatic Default Management
```kotlin
// Repository enforces single-default constraint
override suspend fun insertAddress(address: Address) {
    if (address.isDefault) {
        addressDao.clearAllDefaults(address.userId)
    }
    addressDao.insertAddress(address.toEntity())
}
```

### 4. Global State Management
```kotlin
// Activity-level ViewModel for cart badge
@HiltViewModel
class MainActivityViewModel @Inject constructor(
    getCartItemCountUseCase: GetCartItemCountUseCase
) : ViewModel()
```

---

## 📱 Screenshots

<div align="center">
  <img src="https://github.com/borayldrmm/FoodieHub/blob/main/screenshots/Home.jpg?raw=true" alt="Home Screen" width="200"/>
  <img src="https://github.com/borayldrmm/FoodieHub/blob/main/screenshots/Detail.jpg?raw=true" alt="Food Detail" width="200"/>
  <img src="https://github.com/borayldrmm/FoodieHub/blob/main/screenshots/Cart.jpg?raw=true" alt="Cart" width="200"/>
  <img src="https://github.com/borayldrmm/FoodieHub/blob/main/screenshots/Profile.jpg?raw=true" alt="Profile" width="200"/>
</div>

---

## 🚀 Getting Started

### Prerequisites
- Android Studio Hedgehog or later
- JDK 17
- Android SDK 34
- Gradle 8.0+

### Installation

1. **Clone the repository**
```bash
git clone https://github.com/borayldrmm/FoodieHub.git
cd FoodieHub
```

2. **Open in Android Studio**
```bash
File > Open > Select FoodieHub folder
```

3. **Sync Gradle**
```bash
File > Sync Project with Gradle Files
```

4. **Run the app**
```bash
Select device/emulator > Run 'app'
```

---

## 📂 Project Structure
```
app/src/main/java/com/borayildirim/foodiehub/
├── data/
│   ├── local/
│   │   ├── dao/              # Room DAOs with Flow
│   │   ├── entity/           # Database entities
│   │   ├── mapper/           # Entity-Domain mappers
│   │   └── preferences/      # DataStore
│   └── repository/           # Repository implementations
├── domain/
│   ├── model/                # Domain models
│   ├── repository/           # Repository interfaces
│   └── usecase/              # Business logic
│       ├── address/
│       ├── cart/
│       ├── food/
│       ├── order/
│       ├── payment/
│       └── user/
└── presentation/
    ├── screens/              # Compose screens
    ├── viewmodels/           # ViewModels
    ├── navigation/           # Navigation graph
    └── ui/
        ├── components/       # Reusable composables
        └── theme/            # Material3 theme
```

---

## 🎯 Learning Outcomes

This project demonstrates proficiency in:

- ✅ Clean Architecture principles and layer separation
- ✅ SOLID design principles application
- ✅ Reactive programming with Kotlin Flow
- ✅ Modern Android UI with Jetpack Compose
- ✅ Database design and migration strategies
- ✅ Dependency injection and testability
- ✅ Professional documentation standards
- ✅ Git workflow (feature branches, conventional commits)
- ✅ State management patterns
- ✅ Memory-efficient design

---

## 📝 Code Quality

### Documentation
- **KDoc Coverage** - All public APIs documented
- **Architecture Documentation** - Layer responsibilities explained
- **Business Rules** - Domain constraints documented

### Best Practices
- **No Deprecated APIs** - Modern Android standards only
- **Type Safety** - Sealed classes for state management
- **Memory Efficiency** - Proper lifecycle management
- **Error Handling** - Result types and exception handling

---

## 🤝 Contributing

This is a portfolio project and not open for contributions. However, feedback and suggestions are welcome!

---

## 📄 License

This project is for educational purposes and portfolio demonstration.

---

## 👤 Author

**Bora Yıldırım**

- GitHub: [@borayldrmm](https://github.com/borayldrmm)
- LinkedIn: [Bora Yıldırım](https://www.linkedin.com/in/borayldrmm/)

---

## 🙏 Acknowledgments

Special thanks to **Atıl Samancıoğlu** and **Kasım Adalan** for mentorship and guidance throughout the Android Mobile Application Development Course!

---

<div align="center">
  
**Built with ❤️ using Kotlin and Jetpack Compose**

⭐ Star this repo if you found it helpful!

</div>
