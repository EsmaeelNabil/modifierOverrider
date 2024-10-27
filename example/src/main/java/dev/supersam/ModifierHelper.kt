package dev.supersam

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.padding
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp

/**
 * Helper class to override the modifier of the Composable functions.
 * This class is used by the ModifierOverrider plugin to override the modifier of the Composable functions.
 * The plugin will call the `override` function of this class and pass the function name and the modifier.
 * You can add your custom logic in the `buildModifier` function to modify the modifier.
 */
object ModifierHelper {

    // Modifier must be nullable for the bytecode to be generated correctly
    // Signature of this function must be the same
    fun override(functionName: String, modifier: Modifier? = Modifier): Modifier {
        // Add your custom logic here
        return buildModifier(functionName, modifier ?: Modifier)
    }

    // This function is used to build the modifier, you can add your custom logic here
    // this will be called each time a composition happens.
    private fun buildModifier(functionName: String, modifier: Modifier): Modifier {
        return Modifier
            .padding(10.dp)
            .border(1.dp, Color.Red)
            .semantics { contentDescription = functionName.plus("FULL FREEDOM!") }
            .then(modifier)
    }
}