package org.example

import com.garmin.fit.Decode
import com.garmin.fit.MesgBroadcaster
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializationFeature
import com.garmin.fit.MesgListener
import java.io.File
import java.io.FileInputStream

fun main() {
    var fitFile: File? = null
    do {
        println("Bitte FIT-Dateipfad eingeben:")
        var input = readlnOrNull()
        if (input.isNullOrBlank()) {
            println("Eingabe darf nicht leer sein.")
            continue
        }

        // Entferne Anführungszeichen am Anfang und Ende, wenn vorhanden
        if (input.startsWith("\"") && input.endsWith("\"")) {
            input = input.substring(1, input.length - 1)
        }

        fitFile = File(input)
        if (!fitFile.exists()) {
            println("Datei nicht gefunden: ${fitFile.absolutePath}")
            fitFile = null // zurücksetzen, damit die Schleife weiterläuft
        }
    } while (fitFile == null)

    val decoder = Decode()
    val broadcaster = MesgBroadcaster()
    val messages = mutableListOf<Map<String, Any?>>()

    // Listener für jede Nachricht
    broadcaster.addListener(MesgListener { mesg ->
        val map = mutableMapOf<String, Any?>()
        // Wir iterieren über alle Felder der Nachricht
        for (field in mesg.fields) {
            map[field.name] = field.value
        }
        messages.add(map)
    })

    // Datei lesen
    try {
        FileInputStream(fitFile).use { decoder.read(it, broadcaster) }

        // JSON Export
        val mapper = ObjectMapper().enable(SerializationFeature.INDENT_OUTPUT)
        File("${fitFile.nameWithoutExtension}.json").writeText(mapper.writeValueAsString(messages))
        val parentDir = fitFile.parentFile ?: File(".")
        val outputFile = File(parentDir, "${fitFile.nameWithoutExtension}.json")
        outputFile.writeText(mapper.writeValueAsString(messages))

        println("Erfolg: ${messages.size} Nachrichten konvertiert.")
    } catch (e: Exception) {
        println("Fehler beim Lesen: ${e.message}")
    }
}