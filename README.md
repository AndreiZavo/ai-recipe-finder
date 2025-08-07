# 🍽️ AI-Powered Recipe Finder

A modern Android app where users can describe what they want to eat and get personalized recipe suggestions powered by AI.

Built entirely with **Jetpack Compose**, the app demonstrates clean architecture, unidirectional state management with `StateFlow`, and seamless integration with an AI service for natural language recipe generation. It also places a strong emphasis on a responsive, user-friendly UI — from animated feedback to thoughtful error states.

---

## ✨ Features

- 🔎 **AI-powered search** — Users can type prompts like _"something quick with tofu"_ to get smart suggestions
- ❤️ **Favorites** — Save favorite recipes locally with offline support via **Proto DataStore**
- 📱 **Recipe Details** — View full ingredient lists, instructions, and cooking time
- 🔄 **State sync** — Favorite state remains consistent across list and detail views
- 💬 **Empty state UX** — Graceful no-results UI when a search returns nothing
- ⏳ **Custom loading animation** — Subtle Lottie animation while fetching data
- 🧭 **Animated top bar** — Dynamic scroll-based toolbar reveal on the details screen

---

## 🧠 Tech Stack


| **Layer**           | **Tools/Approach**                                     |
|:-------------------:|:------------------------------------------------------:|
| **UI**              | Jetpack Compose, Material 3, Lottie                    |
| **Architecture**    | MVVM, Unidirectional Data Flow, `StateFlow`, `LaunchedEffect` |
| **AI Service**      | Gemini API                                             |
| **Local Storage**   | Proto DataStore with custom serializer                |
| **Async**           | Kotlin Coroutines                                      |
| **Navigation**      | Manual navigation with type-safe route mapping        |
| **State Handling**  | Sealed `DataState<T>` (Uninitialized, Loading, Success, Failed) |

---

## 📁 Project Structure
```bash
com.example.recipefinder
│
├── ui/
│ ├── recipes/ # Recipe screen, view model for logic on search and favorite
│ ├── recipe/ # Detailed view for a selected recipe
│ ├── components/ # Reusable UI components (e.g., buttons, dialogs, image loaders)
│ ├── utils/ # Helper functions and components related to UI
│
├── data/
│ ├── models/ # Serializable models like Recipe
│ ├── local/ # Proto DataStore setup and custom serializer
│ ├── services/ # Gemini AI integration for fetching recipes
│
├── utils/ # DataState, state helpers, formatting extensions
```
---

## 🧩 Architecture Highlights

The app is structured around a **single source of truth** in `RecipesViewModel`. A unified state model (`RecipeInfo`) holds:

- `searchedRecipes` — results from AI queries  
- `favoriteRecipes` — persisted locally  
- `displayedRecipes` — the current list rendered in the UI  

This ensures seamless transitions between searching, favoriting, and navigating between screens. The app uses optimistic updates to reflect favorite state immediately and includes a `selectedRecipeFlow` for managing detail screen content reactively.

---

## 🖼️ Screenshots

<p float="left">
  <img src="screenshots/search_results.png" width="240" />
  <img src="screenshots/no_results.png" width="240" />
  <img src="screenshots/loading.png" width="240" />
  <img src="screenshots/details_scroll.png" width="240" />
</p>

---

## 🔮 Possible Feature Improvements

While the current implementation offers a complete experience, future enhancements could include:

- 📡 Syncing favorite state via a **remote API**
- ☁️ Fetching recipes from a **server-side source**, rather than local AI
- 🔐 Adding user accounts and authentication
- 🧾 Categorization or tagging for saved recipes
- 🎯 Filter options for dietary preference, cuisine type, or cooking time

---

## 🚀 Final Thoughts

This project was part of a coding challenge, but honestly, it never really felt like one. It quickly turned into something I genuinely enjoyed building.
From setting up the Compose UI and managing state flows, to integrating the AI search and syncing everything across screens, I found myself experimenting and polishing details like the animated loading state, the “no results” screen, and the scroll-triggered top bar.
It’s a small but complete app, and I’m proud of how cohesive and responsive it feels. If anything, it reminded me how much I enjoy crafting thoughtful user experiences with clean architecture behind them.

Thanks for taking the time to explore it!
