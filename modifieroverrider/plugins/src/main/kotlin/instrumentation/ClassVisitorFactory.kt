package instrumentation

import com.android.build.api.instrumentation.AsmClassVisitorFactory
import com.android.build.api.instrumentation.ClassContext
import com.android.build.api.instrumentation.ClassData
import com.android.build.api.instrumentation.InstrumentationParameters
import org.gradle.api.provider.ListProperty
import org.gradle.api.provider.Property
import org.gradle.api.tasks.Input
import org.objectweb.asm.ClassVisitor

/**
 * This class is responsible for creating the [ClassMethodVisitor] that will transform our methods code
 */
abstract class ClassVisitorFactory :
    AsmClassVisitorFactory<ClassVisitorFactory.Params> {

    override fun createClassVisitor(
        classContext: ClassContext,
        nextClassVisitor: ClassVisitor
    ): ClassVisitor {
        // This will transform the methods of the isInstrumental classes
        return ClassMethodVisitor(
            instrumentationContext.apiVersion.get(),
            nextClassVisitor,
            parameters.get()
        )
    }

    override fun isInstrumentable(classData: ClassData): Boolean {
        // This will only transform classes that are in the affected packages
        return parameters.get().affectedPackages.get().any {
            val match = classData.className.startsWith(it)
            // Print debug info if requested
            if (parameters.get().printDebugInfo.get() && match)
                println("MATCH : ${classData.className} against $it")
            match
        }
    }

    interface Params : InstrumentationParameters {
        @get:Input
        val affectedPackages: ListProperty<String>

        @get:Input
        val overriderClassPath: Property<String>

        @get:Input
        val overriderClassEntryFunctionName: Property<String>

        @get:Input
        val printDebugInfo: Property<Boolean>
    }
}