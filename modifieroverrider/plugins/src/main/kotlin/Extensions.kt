import com.android.build.api.variant.ApplicationAndroidComponentsExtension
import org.gradle.api.Project

internal const val COMPOSABLE_ANNOTATION = "Landroidx/compose/runtime/Composable;"

internal const val FIRST_PARAMETER_INDEX = 0

internal const val INSTANCE = "INSTANCE"

internal const val MODIFIER = "(Landroidx/compose/ui/Modifier;"

internal fun String?.hasModifierParameter() = this?.contains(MODIFIER, ignoreCase = true) == true

internal fun String?.startsWithModifierParameter() = this?.startsWith(MODIFIER) == true

internal const val MODIFIER_OVERRIDER_EXTENSION_NAME = "modifierOverrider"

internal val String.toOwner: String
    get() = this.replace(".", "/")

internal const val HELPER_FUNCTION_SIGNATURE =
    "(Ljava/lang/String;Landroidx/compose/ui/Modifier;)Landroidx/compose/ui/Modifier;"

internal fun Project.getModifierOverriderExtension(): ModifierOverriderExtension =
    extensions.create(
        MODIFIER_OVERRIDER_EXTENSION_NAME,
        ModifierOverriderExtension::class.java
    )

internal fun Project.getAndroidExtension(): ApplicationAndroidComponentsExtension =
    extensions.getByType(ApplicationAndroidComponentsExtension::class.java)


fun List<String>.isSupportedVariant(variantName: String, strictCheck: Boolean = false): Boolean =
    if (strictCheck)
        this.contains(variantName)
    else
        this.any { it.contains(variantName, ignoreCase = true) }