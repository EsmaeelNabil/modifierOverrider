plugins {
    `java-gradle-plugin`
    alias(libs.plugins.kotlin.jvm)
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(17))
    }
}

dependencies {
    compileOnly(libs.android.gradlePlugin.api)
    implementation(gradleKotlinDsl())
    implementation("org.ow2.asm:asm-util:9.7")
//    implementation("androidx.compose.ui:ui:1.7.4")
}

gradlePlugin {
    plugins {
        create("ModifierOverriderPlugin") {
            id = "ModifierOverriderPlugin"
            implementationClass = "ModifierOverriderPlugin"
        }
    }
}
