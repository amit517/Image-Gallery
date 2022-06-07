plugins {
    `kotlin-dsl`
}

gradlePlugin {
    plugins {
        register("module-plugin") {
            id = "module-plugin"
            implementationClass = "CommonModulePlugin"
        }
    }
}

repositories {
    google()
    mavenCentral()
}

dependencies {
    compileOnly(gradleApi())
    implementation("com.google.dagger:hilt-android-gradle-plugin:2.38.1")
    implementation("androidx.navigation:navigation-safe-args-gradle-plugin:2.4.2")
}
