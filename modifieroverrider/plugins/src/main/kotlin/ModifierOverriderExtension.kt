import org.gradle.api.provider.ListProperty
import org.gradle.api.provider.Property
import org.gradle.api.tasks.Input

/**
 * User configuration for the ModifierOverriderExtension
 */
interface ModifierOverriderExtension {

    @get:Input
    val affectedPackages: ListProperty<String>

    @get:Input
    val overriderClassPath: Property<String>

    @get:Input
    val overriderClassEntryFunctionName: Property<String>

    @get:Input
    val printDebugInfo: Property<Boolean>

    @get:Input
    val affectedVariants: ListProperty<String>

    @get:Input
    val affectedVariantsStrictCheck: Property<Boolean>
}