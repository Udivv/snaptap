package dev.kingtux.tms.api.config

import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import net.fabricmc.loader.api.FabricLoader

/** SnapTap addition: when the "+" (add alternative) button is shown next to a keybind. */
enum class PlusMode(val label: String) {
    ALWAYS("Always"),
    HOVER("On hover"),
    HIDDEN("Hidden");

    fun next(): PlusMode = entries[(ordinal + 1) % entries.size]
}

@Serializable
private data class SnapTapFile(val plusMode: String = "HOVER")

object SnapTapSettings {
    private val json = Json { encodeDefaults = true; ignoreUnknownKeys = true }
    private var current: PlusMode? = null

    private fun file() = FabricLoader.getInstance().configDir.resolve("snaptap.json")

    var plusMode: PlusMode
        get() {
            if (current == null) load()
            return current!!
        }
        set(value) {
            current = value
            save()
        }

    private fun load() {
        current = PlusMode.HOVER
        try {
            val f = file().toFile()
            if (f.exists()) {
                val data = json.decodeFromString<SnapTapFile>(f.readText())
                current = PlusMode.entries.firstOrNull { it.name == data.plusMode } ?: PlusMode.HOVER
            }
        } catch (_: Exception) {
            // keep default
        }
    }

    private fun save() {
        try {
            file().toFile().writeText(json.encodeToString(SnapTapFile(current!!.name)))
        } catch (_: Exception) {
        }
    }
}
