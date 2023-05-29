# Wypożyczalnia pojazdów

Funkcjonalności

- Przeglądanie/dodawanie/edycja/usuwanie pojazdów.
- Możliwość składania rezerwacji
- Możliwość przeglądania rezerwacji (wypożyczony (przez kogo), dostępny, zarezerwowany).
- Automatyczne cofanie rezerwacji (z powiadomieniem przez e-mail) w przypadku braku potwierdzenia otrzymania płatności
  do trzech godzin po złożeniu rezerwacji.
- Przesyłanie wiadomości o złożonej rezerwacji/potwierdzeniu otrzymania płatności/anulowaniu rezerwacji.
- Uwierzytelnianie.

# TODO

### Car CRUD ✔

- create ✔ Mateusz
- read ✔ Mateusz
- update ✔ Mateusz
- delete ✔ Piotrek

### System

- automatyczne cofanie rezerwacji 🚧 Mateusz
- Uwierzytelnianie 🚧 Mateusz

### Strona klienta ❌

- rejestracja ❌
    - walidacja emaila ✔ Mateusz
    - haszowanie hasła ❌
- logowanie ✔ Mateusz
- wypożyczenia
    - przeglądanie ofert wypożyczeń ✔ Piotrek
    - szczegóły ofert wypożyczeń ✔ Piotrek
    - przeglądanie swoich wypożyczeń ❌
    - rezerwacja ❌
    - opłata ❌
    - anulowanie rezerwacji ❌

### Testy

- CarBean ❌
    - persist ✔ Mateusz
    - delete ✔ Piotrek
    - getCar ❌
- Dao ❌
    - CarDao ❌
- Servlet ✔ Mateusz
    - RegisterServlet ✔ Mateusz
        - handleRegister ✔ Mateusz
    - LoginServlet ✔ Mateusz
        - handleLogin ✔ Mateusz
- UserService ❌
    - authenticateAndAuthorizeUser ❌