# Dokumentacja Aplikacji SmartSenior

## 1. Opis Ogólny

Smart Senior to edukacyjna aplikacja mobilna na system Android, stworzona z myślą o osobach starszych, które chcą w prosty i bezpieczny sposób nauczyć się korzystania z telefonu oraz Internetu. Aplikacja ma zwiększać kompetencje cyfrowe seniorów poprzez praktyczne ćwiczenia, symulacje codziennych sytuacji oraz krótkie porady dotyczące cyberbezpieczeństwa.

**Założenia projektowe** 

Główna idea aplikacji polega na przedstawianiu użytkownikowi realistycznych scenariuszy, w których senior może znaleźć się na co dzień (np. podejrzany SMS/e-mail, telefon od nieznajomego, zakupy online, rozpoznawanie fake newsów). Użytkownik podejmuje decyzje (np. wybór odpowiedzi TAK/NIE), a aplikacja po każdej decyzji wyświetla komentarz edukacyjny, który tłumaczy, dlaczego dana sytuacja była bezpieczna lub niebezpieczna.

Projekt kładzie duży nacisk na dostępność i intuicyjność: prosty interfejs, duże przyciski i czytelna kolorystyka o wysokim kontraście oraz możliwość wsparcia głosowego. Dodatkowo aplikacja ma działać offline, bez potrzeby stałego połączenia z Internetem, a dane użytkownika są przechowywane lokalnie na urządzeniu.

Aplikacja wpisuje się w ideę przeciwdziałania wykluczeniu technologicznemu osób starszych: ma pomóc im czuć się pewniej w kontakcie z nowoczesnymi technologiami, rozumieć zagrożenia i korzystać z internetu w sposób świadomy

## 2. Główne Funkcje

Aplikacja składa się z kilku kluczowych modułów:

*   **Moduły Edukacyjne (`moduleMenu`):** Interaktywne scenariusze uczące seniorów, jak rozpoznawać i reagować na współczesne zagrożenia, takie jak oszustwa telefoniczne ("na wnuczka", "na policjanta") czy phishing (podejrzane SMS-y i e-maile). Każdy scenariusz prowadzi użytkownika krok po kroku przez symulowaną sytuację.

*   **Minigry (`miniGamesMenu`):** Zestaw gier logicznych i zręcznościowych zaprojektowanych w celu stymulacji funkcji poznawczych i zapewnienia rozrywki.

*   **Powiadomienia i Przypomnienia (`notifications`):** System umożliwiający ustawianie przypomnień o lekach, wizytach u lekarza czy innych ważnych wydarzeniach.

*   **Zaufane Kontakty (`trustedContacts`):** Funkcja szybkiego dostępu do wybranych kontaktów, ułatwiająca komunikację z rodziną i opiekunami.

*   **Wirtualny Asystent (`virtualAssistant`):** Asystent głosowy, który może pomagać w nawigacji po aplikacji i wykonywaniu podstawowych czynności.

*   **Inne funkcje:**
    *   **Profil Użytkownika (`profile`):** Personalizacja ustawień.
    *   **Pomoc (`help`):** Dostęp do instrukcji i wsparcia.
    *   **Ustawienia (`settings`):** Konfiguracja aplikacji.

## 3. Wymagania techniczne

* Aplikacja działa na Android 10 lub nowszym.

* Implementacja w języku Java w Android Studio.

* Działanie offline.

* Interfejs prosty, czytelny (duże przyciski/czcionki), wysoki kontrast.

* Czas reakcji interfejsu poniżej 2 sekund.

* Dane użytkownika przechowywane lokalnie; brak konieczności logowania online.

* Obsługa ekranów co najmniej 720×1280 px.

* Czytelna kolorystyka: biało-niebiesko-szara paleta, wysoki kontrast.

## 4. Autorzy

* Karolina Bednarczyk
* Patrycja Bednarczyk
* Anastazja Frąckowiak
* Dominik Czyrny 
* Maciej Pitulski
