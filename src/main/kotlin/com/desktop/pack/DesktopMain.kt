// Copyright 2000-2021 JetBrains s.r.o. and contributors. Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
package com.desktop.pack

import java.io.BufferedInputStream
import java.io.BufferedReader
import java.io.InputStreamReader
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths
import java.nio.file.StandardOpenOption
import java.util.*
import kotlin.io.path.exists

object DesktopMain {
    private val KEYS = arrayOf(
        "app.name",
        "app.handler-type",
        "app.visibility",
        "app.menu",
        "app.id"
    )

    private fun getStartPath(): String {
        val jarPath: String = DesktopMain::class.java
            .protectionDomain
            .codeSource
            .location
            .toURI()
            .path
        return "/home/HouXinLin/project/cooldesktop-apps/text-editor"
    }

    fun loadProperties(path: Path): Properties {
        var properties = Properties()
        properties.load(InputStreamReader(Files.newInputStream(path), "utf-8"))
        return properties;
    }

    private fun checkConfig(properties: Properties) {
        KEYS.forEach {
            if (!properties.containsKey(it)) {
                throw IllegalArgumentException("配置错误，找不到 ${it} 属性")
            }
        }
    }

    @JvmStatic
    fun main(arg: Array<String>) {
        var propertiesPath = Paths.get(getStartPath(), "app.properties")

        if (!propertiesPath.exists()) {
            println("找不到配置文件")
            return
        }

        var properties = loadProperties(propertiesPath)
        checkConfig(properties)
        var name = properties["app.name"]
        var applicationPath = Paths.get(getStartPath(), "${name}.app")
        if (applicationPath.exists()) {
            Files.delete(applicationPath)
        }
        var bytes = ApplicationPackage.getPackage(properties, getStartPath())
        Files.write(applicationPath, bytes)
    }
}
