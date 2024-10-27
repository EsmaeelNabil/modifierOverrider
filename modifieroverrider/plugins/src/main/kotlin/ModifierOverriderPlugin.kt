import com.android.build.api.instrumentation.InstrumentationScope
import com.android.build.gradle.AppPlugin
import instrumentation.ClassVisitorFactory
import org.gradle.api.Plugin
import org.gradle.api.Project
import java.util.Collections.emptyList

/**
 * This custom plugin will register a callback that is applied to all variants.
 */
class ModifierOverriderPlugin : Plugin<Project> {

    override fun apply(project: Project) {
        with(project) {

            // create the extension
            val pluginConfiguration = project.getModifierOverriderExtension()

            plugins.withType(AppPlugin::class.java) {


                // Register a callback that will be applied to all variants
                getAndroidExtension().onVariants { variant ->

                    // Get the configuration
                    val affectedPackages =
                        pluginConfiguration.affectedPackages.getOrElse(emptyList())
                    val overriderClassPath =
                        pluginConfiguration.overriderClassPath.getOrElse("YOU_HAVE_TO_PROVIDE_A_PACKAGE_NAME")
                    val overriderClassEntryFunctionName =
                        pluginConfiguration.overriderClassEntryFunctionName.getOrElse("YOU_HAVE_TO_PROVIDE_A_FUNCTION_NAME")
                    val printDebugInfo = pluginConfiguration.printDebugInfo.getOrElse(true)
                    val strictCheck =
                        pluginConfiguration.affectedVariantsStrictCheck.getOrElse(false)

                    val shouldNotApplyOnVariant =
                        pluginConfiguration.affectedVariants.getOrElse(emptyList())
                            .isSupportedVariant(variant.name, strictCheck).not()

                    // If the variant is not supported, we skip it
                    if (shouldNotApplyOnVariant)
                        return@onVariants

                    // Apply the transformation
                    if (printDebugInfo)
                        println("Applying on Variant: ${variant.name}")


                    // Apply the transformation
                    variant.instrumentation.transformClassesWith(
                        ClassVisitorFactory::class.java,
                        InstrumentationScope.PROJECT
                    ) { params ->
                        params.affectedPackages.set(affectedPackages)
                        params.printDebugInfo.set(printDebugInfo)
                        params.overriderClassPath.set(overriderClassPath)
                        params.overriderClassEntryFunctionName.set(overriderClassEntryFunctionName)
                    }

                }
            }
        }
    }
}
