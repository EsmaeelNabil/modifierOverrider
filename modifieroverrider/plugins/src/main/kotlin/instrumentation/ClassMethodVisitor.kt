package instrumentation

import hasModifierParameter
import org.objectweb.asm.ClassVisitor
import org.objectweb.asm.MethodVisitor


/**
 * This class will visit all methods of a class
 */
class ClassMethodVisitor(
    apiVersion: Int,
    cv: ClassVisitor,
    private val params: ClassVisitorFactory.Params
) : ClassVisitor(apiVersion, cv) {
    override fun visitMethod(
        access: Int,
        name: String?,
        descriptor: String?,
        signature: String?,
        exceptions: Array<out String>?
    ): MethodVisitor {
        val doNoChangesVisitor = super.visitMethod(access, name, descriptor, signature, exceptions)
        // Check if method a Modifier parameter
        return if (descriptor.hasModifierParameter())
            ModifierTestTagInjector(api, doNoChangesVisitor, name, descriptor, params)
        else
            doNoChangesVisitor
    }
}

