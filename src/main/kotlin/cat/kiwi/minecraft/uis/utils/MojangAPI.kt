package cat.kiwi.minecraft.uis.utils

import com.google.gson.JsonObject
import com.google.gson.JsonParser
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.net.MalformedURLException
import java.net.URL
import java.util.*
import javax.net.ssl.HttpsURLConnection


class MojangAPI {
    private val parser: JsonParser = JsonParser()
    private val API_PROFILE_LINK = "https://sessionserver.mojang.com/session/minecraft/profile/"

    fun getSkinUrl(uuid: String): String? {
        val json = getContent(API_PROFILE_LINK + uuid)
        var o: JsonObject = parser.parse(json).asJsonObject
        val jsonBase64: String =
            o.get("properties").asJsonArray.get(0).asJsonObject.get("value").asString
        o = parser.parse(String(Base64.getDecoder().decode(jsonBase64))).asJsonObject
        return o.get("textures").asJsonObject.get("SKIN").asJsonObject.get("url").asString
    }

    fun getContent(link: String?): String? {
        try {
            val url = URL(link)
            val conn = url.openConnection() as HttpsURLConnection
            val br = BufferedReader(InputStreamReader(conn.inputStream))
            var inputLine: String?
            while (br.readLine().also { inputLine = it } != null) {
                return inputLine
            }
            br.close()
        } catch (e: MalformedURLException) {
            e.printStackTrace()
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return null
    }
}