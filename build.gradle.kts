/*
 * Copyright (C) 2020 PatrickKR
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301, USA.
 *
 * Contact me on <mailpatrickkr@gmail.com>
 */

import groovy.lang.MissingPropertyException
import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar
import org.gradle.jvm.tasks.Jar
import org.jetbrains.dokka.gradle.DokkaTask
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    `maven-publish`
    signing
    kotlin("jvm") version "1.4.0"
    id("org.jetbrains.dokka") version "1.4.0-rc"
    id("com.github.johnrengelman.shadow") version "6.0.0"
}

group = "io.teamif"
version = "1.1"

repositories {
    maven("https://repo.maven.apache.org/maven2/")
    maven("https://jcenter.bintray.com/")
}
dependencies {
    implementation(kotlin("stdlib-jdk8"))
    implementation("com.google.code.gson:gson:2.8.6")
    testImplementation(kotlin("test-junit"))
}

tasks {
    withType<KotlinCompile> {
        kotlinOptions.jvmTarget = "1.8"
    }

    withType<DokkaTask> {
        dokkaSourceSets {
            register("main") {
                sourceLink {
                    path = "src/main/kotlin"
                    url = "https://github.com/Team-IF/comcigan-lib/tree/master/src/main/kotlin"
                    lineSuffix = "#L"
                }
            }
        }
    }

    withType<ShadowJar> {
        archiveClassifier.set("")
    }

    create<Jar>("sourcesJar") {
        archiveClassifier.set("sources")
        from(sourceSets["main"].allSource)
    }

    create<Jar>("dokkaJar") {
        dependsOn("dokkaHtml")
        from("$buildDir/dokka/html/") {
            include("**")
        }
        from("$rootDir/src/main/resources/") {
            include("**")
        }
        archiveClassifier.set("javadoc")
    }
}

try {
    publishing {
        publications {
            create<MavenPublication>("comciganLib") {
                artifact(tasks["sourcesJar"])
                artifact(tasks["dokkaJar"])
                artifact(tasks["shadowJar"])

                repositories {
                    mavenLocal()

                    maven {
                        name = "central"

                        credentials {
                            username = project.property("centralUsername").toString()
                            password = project.property("centralPassword").toString()
                        }

                        url = if (version.endsWith("SNAPSHOT")) {
                            uri("https://oss.sonatype.org/content/repositories/snapshots/")
                        } else {
                            uri("https://oss.sonatype.org/service/local/staging/deploy/maven2/")
                        }
                    }
                }

                pom {
                    name.set("comcigan-lib")
                    description.set("A Comcigan library written in Kotlin")
                    url.set("https://github.com/Team-IF/comcigan-lib")

                    licenses {
                        license {
                            name.set("GNU General Public License v2.0")
                            url.set("https://opensource.org/licenses/gpl-2.0.php")
                        }
                    }

                    developers {
                        developer {
                            id.set("patrick-mc")
                            name.set("PatrickKR")
                            email.set("mailpatrickkorea@gmail.com")
                            url.set("https://github.com/patrick-mc")
                            roles.addAll("developer")
                            timezone.set("Asia/Seoul")
                        }
                    }

                    scm {
                        connection.set("scm:git:git://github.com/Team-IF/comcigan-lib.git")
                        developerConnection.set("scm:git:ssh://github.com:Team-IF/comcigan-lib.git")
                        url.set("https://github.com/Team-IF/comcigan-lib")
                    }
                }
            }
        }
    }

    signing {
        isRequired = true
        sign(tasks["sourcesJar"], tasks["dokkaJar"], tasks["shadowJar"])
        sign(publishing.publications["comciganLib"])
    }
} catch (ignored: MissingPropertyException) {}