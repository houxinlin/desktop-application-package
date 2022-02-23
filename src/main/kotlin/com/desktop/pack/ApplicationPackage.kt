package com.desktop.pack

import com.alibaba.fastjson.JSON
import com.desktop.application.definition.application.Application
import com.hxl.fm.io.ByteArrayIO
import com.hxl.fm.pk.FilePackage
import java.util.*

object ApplicationPackage {
    private fun createList(data: String, delimiters: String): List<String> {
        return data.split(delimiters)
    }

    private fun getExcludePaths(properties: Properties): List<String> {
        if (properties.containsKey("exclude")) {
            return (properties["exclude"] as String).split(",")
        }
        return arrayListOf()
    }

    fun getPackage(properties: Properties, direction: String): ByteArray {
        var application = Application().apply {
            applicationId = properties["app.id"] as String
            applicationName = properties["app.name"] as String
            visibilityIsDesktop = (properties["app.visibility"] as String).lowercase() == "true"
            handlerMediaTypes = createList(properties["app.handler-type"] as String, ",")
            menus = createList(properties["app.menu"] as String, ",")
        }
        var jsonString = JSON.toJSONString(application)
        var generator = FilePackage.generator(direction, *getExcludePaths(properties).toTypedArray())
        var byteArrayIO = ByteArrayIO()
        byteArrayIO.writeInt(495934)
        byteArrayIO.writeInt(jsonString.toByteArray().size)
        byteArrayIO.writeByte(jsonString.toByteArray())
        byteArrayIO.writeInt(generator.size)
        byteArrayIO.writeByte(generator)
        return byteArrayIO.get()
    }
}