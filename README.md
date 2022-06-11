# Entwicklungsumgebung einrichten
## Umgebungsvariablen für die Lokale Entwicklung verwenden

1. Im Projektordner die Datei `App.kt` Rechtsklick > `Run 'App.kt'`. Erst danach zur Schritt 2 übergehen.
2. Datenbankvariablen in `IntelliJ Idea > Menü > Run > Edit configurations... > Configuration > Environment variables:` eintragen.
Darauf achten, dass keine leerzeichen nach dem String beim Copy Paste genommen werden
```
POSTGRES_HOST=localhost;POSTGRES_USER=postgres;POSTGRES_PASSWORD=postgres;POSTGRES_DB=project_dictionary;POSTGRES_PORT=7055;JAVALIN_PORT=7000
```
Für Entwicklungsumgebung (local) Im Terminal diese Befehle ausführen.

3. docker-compose down
4. docker-compose up dev_datenbank_service

Dev Info: Das API verbindet sich mit dem exposed Port 7055 des Docker Containers über den localhost.


# Build Produktion
`gradle shadowJar`
das docker-compose up funktioniert nicht wenn `gradle shadowJar` vorher nicht ausgefürht wurde

Prod Info: Das API verbindet sich mit der Docker Service `datenbank_service`

# Erklärung zu docker-compose
`docker-compose up -d --build`
Da docker die Veränderungen in der `*.jar` Datei nicht nachverfolgen kann und die Dateien
`docker-compose.yml` und `Dockerfile` sich nicht ändern, wird die neu Erstelung der Applikation Image nicht getriggert.
Das Kommando `--build` erzwingt die Erstellung der Applikation Images mit der neuen `*.jar` Datei.
Das sollte bei jedem Start vom Kontainer passieren deswegen wird es in den Dockerfile als Zwischenschritt übernommen.


# Build Produktion Server
1. Verbindung per ssh mit dem Production Server aufbauen
2. `git clone`
3. `cd dictionary`
4. Um die Ausgabe der Programme die im Docker ausgeführt werden anzuschauen, starte docker-compose ohne flag `-d`
- `docker-compose -f docker-compose-production.yml up --build`
5. Um in die Produktion zu gehen, starte mit dem flag `-d`
- `docker-compose -f docker-compose-production.yml up -d --build`
