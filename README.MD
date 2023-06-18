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

### System ❌

- automatyczne cofanie rezerwacji ✔ Mateusz
- uwierzytelnianie ✔ Mateusz
- wysyłanie emaila ❌

### Strona admina ✔

- Car CRUD ✔ Mateusz/Piotrek
    - create ✔ Mateusz
    - read ✔ Mateusz
    - update ✔ Mateusz
    - delete ✔ Piotrek
- przeglądanie wszystkich wypożyczeń ✔ Mateusz

### Strona klienta ❌

- CSS ❌
- rejestracja ✔
    - walidacja emaila ✔ Mateusz
    - haszowanie hasła ✔ Klaudia
- logowanie ✔ Mateusz
- przeglądanie ofert wypożyczeń ✔ Piotrek
- przeglądanie swoich wypożyczeń ✔ Piotrek
- przeglądanie szczegółów wypożyczenia ✔ Piotrek
- rezerwacja ✔ Piotrek
- opłata rezerwacji ✔ Piotrek
- anulowanie rezerwacji ✔ Piotrek

### Testy ✔

- CarBean ✔
    - persist ✔ Mateusz
    - delete ✔ Piotrek
    - getCar ✔ Klaudia
- Servlet ✔ Mateusz
    - RegisterServlet ✔ Mateusz
        - handleRegister ✔ Mateusz
    - LoginServlet ✔ Mateusz
        - handleLogin ✔ Mateusz
- AuthorizationFilter ✔ Mateusz
    - doFilter ✔ Mateusz
- CarRentalService ✔ Mateusz
    - pay ✔ Mateusz
    - cancel ✔ Mateusz
    - cancelAllCarRentalsThatAreNotPaidAndOlderThan ✔ Mateusz