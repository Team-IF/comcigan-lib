# Comcigan Library (JVM)

Comcigan Library written in Kotlin (JVM)

[Javadoc.io](https://www.javadoc.io/doc/io.teamif/comcigan-lib/latest/index.html) to see Javadoc online!

## Repositories

### Gradle (Groovy DSL)

```groovy
allprojects {
    repositories {
        mavenCentral() // or maven { url 'https://repo.maven.apache.org/maven2/' }
    }
}
```

```groovy
dependencies {
    implementation 'io.teamif:comcigan-lib:1.2.1'
}
```

### Gradle (Kotlin DSL)

```kotlin
allprojects {
    repositories {
        mavenCentral() // or maven("https://repo.maven.apache.org/maven2/")
    }
}
```

```kotlin
dependencies {
    implementation("io.teamif:comcigan-lib:1.2.1")
}
```

## How to use

See [Test Code](https://github.com/Team-IF/comcigan-lib/blob/master/src/test/kotlin/io/teamif/patrick/comcigan/ComciganAPITest.kt)