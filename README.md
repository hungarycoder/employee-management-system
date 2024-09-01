# Employee-Management-System (EMS)

Az **Employee-Management-System (EMS)** nevű alkalmazás a **Hung(a)ry Coder** nevű Udemy csatorna
*"Spring boot: Alapok Mesterfokon Kezdőknek"* című kurzusának demonstrációs céljára készítettünk. Az
alkalmazás célja, hogy bemutassa a Spring Boot alapvető funkcióit és a Java modern eszköztárát.

## Technológiai Részletek

- **Java Verzió**: 21
- **Spring Boot Verzió**: 3.3.2
- **Adatbázis**: H2 (in-memory)

## Projekt Felépítése

Az alkalmazás egy egyszerű REST API-t valósít meg, amely lehetővé teszi az alkalmazottak kezelését (
CRUD műveletek). A következő modulokat tartalmazza:

- **Controller**: Az API végpontok kezelése.
- **Service**: Az üzleti logika megvalósítása.
- **Repository**: Az adatbázis műveletek kezelése.

## Fordítás és Futtatás

### Szükséges Eszközök

- **Java 21**: Győződj meg róla, hogy a rendszeredre telepítve van a Java 21.
- **Maven**: A projekt Maven-alapú, így szükséged lesz a Maven eszközre is.

### Fordítás

A projekt fordításához és a szükséges függőségek letöltéséhez futtasd a következő parancsot a
projekt gyökérkönyvtárában:

```bash
mvnw.cmd clean install
```

### Futtatás

Az alkalmazás futtatásához használd a következő parancsot:

```bash
mvnw.cmd spring-boot:run
```

Ezt követően az alkalmazás elérhető lesz a http://localhost:8080 címen.

### Adatbázishozzáférés

Az alkalmazás egy beágyazott H2 adatbázist használ. Az adatbázis konzol elérhető a következő címen:
http://localhost:8080/h2-console

Belépési adatok az application.yaml-ben találhatóak.

Megjegyzés: Mivel az alkalmazás H2 in-memory adatbázist használ, a táblák minden
alkalmazásindításkor törlődnek és újra létrejönnek.

