package instrumentation

import COMPOSABLE_ANNOTATION
import FIRST_PARAMETER_INDEX
import HELPER_FUNCTION_SIGNATURE
import INSTANCE
import org.objectweb.asm.AnnotationVisitor
import org.objectweb.asm.MethodVisitor
import org.objectweb.asm.Opcodes.ALOAD
import org.objectweb.asm.Opcodes.ASTORE
import org.objectweb.asm.Opcodes.GETSTATIC
import org.objectweb.asm.Opcodes.INVOKEVIRTUAL
import startsWithModifierParameter
import toOwner


class ModifierTestTagInjector(
    api: Int,
    visitor: MethodVisitor,
    private val name: String?,
    private val descriptor: String?,
    private val params: ClassVisitorFactory.Params
) : MethodVisitor(api, visitor) {

    private var isComposable = false

    override fun visitAnnotation(descriptor: String?, visible: Boolean): AnnotationVisitor? {
        if (descriptor == COMPOSABLE_ANNOTATION) {
            isComposable = true
        }
        return super.visitAnnotation(descriptor, visible)
    }

    override fun visitLabel(label: org.objectweb.asm.Label?) {
        super.visitLabel(label)

        // If the method is annotated with @Composable and has a Modifier parameter
        // At this point, we know we have a valid Modifier in the local variable
        // The Modifier is stored in local variable 0
        if (isComposable && descriptor.startsWithModifierParameter()) {

            // Debug info
            if (params.printDebugInfo.get()) {
                println("Modifying method $name with descriptor $descriptor")
            }

            val helperClassOwner = params.overriderClassPath.get().toOwner

            // Load ModifierHelper.INSTANCE
            mv.visitFieldInsn(
                GETSTATIC,
                helperClassOwner,
                INSTANCE,
                "L$helperClassOwner;"
            )

            // Load the function name to be used as a string parameter to the overrider class.
            mv.visitLdcInsn(name ?: "UnnamedComposable!KindaImpossibleToHappen!")

            // Load the Modifier
            mv.visitVarInsn(ALOAD, FIRST_PARAMETER_INDEX)

            val entryFunctionName = params.overriderClassEntryFunctionName.get()
            // Call cd()
            mv.visitMethodInsn(
                INVOKEVIRTUAL,
                helperClassOwner,
                entryFunctionName,
                HELPER_FUNCTION_SIGNATURE,
                false
            )

            // Store the result back in the local modifier variable at index 0 to replace the original
            mv.visitVarInsn(ASTORE, FIRST_PARAMETER_INDEX)
        }
    }
}