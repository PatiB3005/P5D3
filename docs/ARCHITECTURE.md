# Architektura Aplikacji SmartSenior

## 1. Przegląd Architektury

Aplikacja SmartSenior jest zbudowana w oparciu o klasyczną, wielowarstwową architekturę typową dla natywnych aplikacji na platformę Android. Główne założenia to separacja odpowiedzialności (Separation of Concerns) oraz modułowość, co ułatwia zarządzanie kodem i jego dalszy rozwój.

Architektura opiera się na następujących kluczowych komponentach:

*   **Warstwa Prezentacji (UI):** Odpowiada za wyświetlanie interfejsu użytkownika i obsługę interakcji.
*   **Warstwa Danych (Data):** Zarządza danymi i logiką biznesową.
*   **Moduły Funkcjonalne:** Specjalizowane komponenty, takie jak syntezator mowy (TTS).

## 2. Diagram Architektury

Poniższy diagram przedstawia uproszczony schemat zależności między głównymi komponentami aplikacji.

```
+---------------------------------+
|       Warstwa Prezentacji (UI)  |
| (Aktywności, Fragmenty, Widoki) |
+---------------------------------+
               |                 
               v
+---------------------------------+
|        Warstwa Danych (Data)    |
|   (Modele, Logika biznesowa)    |
+---------------------------------+

+---------------------------------+
|       Narzędzia (Utils)         |
+---------------------------------+

+---------------------------------+
|    Syntezator Mowy (TTS)        |
+---------------------------------+
```

## 3. Szczegółowy Opis Komponentów

### Warstwa Prezentacji (`ui`)

*   **Struktura:** Zbudowana jest głównie z komponentów `Activity`. Każdy ekran lub krok w interaktywnym scenariuszu jest osobną Aktywnością.
*   **Organizacja:** Kod w pakiecie `ui` jest podzielony na pod-pakiety odpowiadające głównym funkcjom aplikacji (np. `moduleMenu`, `notifications`, `profile`), co odzwierciedla modułową naturę aplikacji.
*   **Nawigacja:** Nawigacja między ekranami realizowana jest za pomocą jawnych `Intentów` (`startActivity(...)`).

### Warstwa Danych (`data`)

*   **Cel:** Ten pakiet jest przeznaczony do zarządzania wszystkimi danymi w aplikacji. Powinien zawierać klasy modelu (np. `User`, `Reminder`) oraz logikę biznesową niezależną od interfejsu użytkownika.

### Moduł Syntezatora Mowy (`tts`)

*   **`BaseTTSActivity`:** Kluczowym elementem jest klasa bazowa `BaseTTSActivity`. Wszystkie Aktywności, które potrzebują funkcji odczytywania tekstu na głos, dziedziczą po tej klasie. Takie podejście promuje reużywalność kodu i centralizuje logikę związaną z Text-To-Speech.
*   **Działanie:** Inicjalizuje ona silnik TTS i udostępnia metody do odczytywania przekazanego tekstu.

### Narzędzia (`utils`)

*   Jest to pakiet pomocniczy zawierający reużywalne klasy i funkcje, które mogą być wykorzystywane w całej aplikacji (np. do formatowania dat, walidacji danych itp.).
