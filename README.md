# modifierOverrider

#### This plugin is used to override the modifier of the `composposable` functions in the specified package and with a

specified criteria, for testing, debugging, development purposes, the used example is for adding semantics to the UI
without changing the code.

### Running the example app.

- in `build.gradle.kts` of the android app.

```kotlin
plugins {
    //....
    alias(libs.plugins.modifierOverrider)
}
```

```kotlin
modifierOverrider {
    affectedPackages = listOf("dev.supersam")
    printDebugInfo = true
    overriderClassPath = "dev.supersam.ModifierHelper"
    overriderClassEntryFunctionName = "override"
    affectedVariants = listOf("test")
    affectedVariantsStrictCheck = false
}
```


```kotlin
package dev.supersam

object ModifierHelper {

    fun override(functionName: String, modifier: Modifier? = Modifier): Modifier {
        return buildModifier(functionName, modifier ?: Modifier)
    }

    // This function is used to build the modifier, you can add your custom logic here
    // this will be called each time a composition happens.
    private fun buildModifier(functionName: String, modifier: Modifier): Modifier {
        return Modifier
            .semantics { contentDescription = functionName.plus("FULL FREEDOM!") }
            .then(modifier)
    }
}
```

### Explanation.

- `affectedPackages` : List of packages to be affected.
- `printDebugInfo` : Print debug info.
- `overriderClassPath` : The package name of the overrider class.
- `overriderClassEntryFunctionName` : The entry function name of the overrider class.
- `affectedVariants` : List of affected variants.
- `affectedVariantsStrictCheck` : Strict check for the affected variants, case-sensitive or not, contains or equals.
    - for example, if `affectedVariants` is `listOf("test")` and `affectedVariantsStrictCheck` is `true`, then only the
      variant `test` will be affected.
    - if `affectedVariants` is `listOf("test")` and `affectedVariantsStrictCheck` is `false`, then the variants `test`,
      `Test`, `testDebug` will be affected.

- in `ModifierHelper.kt` file.
- You can call the object `ModifierHelper` anything you want, but the function name should be the same as the
  `overriderClassEntryFunctionName` in the `build.gradle.kts` file.
- You can place this file anywhere in the project, but the package name should be the same as the
  `overriderClassPath` in the `build.gradle.kts` file.
- The `override` function should have the same signature as the below function.
- the `buildModifier` function is used to build the modifier, you can add your custom logic here, this will be called
  each time a composition happens.
