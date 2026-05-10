# fit-file-parser-java

Ein Open-Source-Parser für Garmin FIT-Dateien, der die Inhalte nach **JSON** exportiert.  
Das Programm ist als ausführbare JAR-Datei verfügbar und kann mit Gradle gebaut werden.

## Features

- Liest Garmin FIT-Dateien mithilfe der offiziellen Garmin FIT SDK-Klassen (`Decode`, `MesgBroadcaster`, `MesgListener`).
- Exportiert alle Nachrichten und Felder in eine strukturierte JSON-Datei.
- Fragt beim Start interaktiv nach dem Pfad zur FIT-Datei.
- Erstellt automatisch eine gleichnamige `.json`-Datei im selben Verzeichnis wie die Eingabedatei.
- Nutzt Jackson (`ObjectMapper`) für formatierten JSON-Output.

## Nutzung

1. Lade die JAR-Datei aus den [Releases](https://github.com/illampu/fit-file-parser-java/releases) herunter.
2. Starte das Programm:

```bash
java -jar fit-file-parser-java.jar
