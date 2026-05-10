package cu.axel.smartdock.preferences

import android.content.Context
import android.util.AttributeSet
import androidx.preference.Preference
import androidx.preference.PreferenceViewHolder
import com.google.android.material.button.MaterialButton
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.materialswitch.MaterialSwitch
import cu.axel.smartdock.R
import androidx.core.content.edit

val CLOCK_TAP_ACTIONS = listOf("clock", "notifications", "qs", "unpin")

class ClockActionChooserPreference(private val context: Context, attrs: AttributeSet?) :
    Preference(context, attrs) {

    init {
        setupPreference()
    }

    override fun onBindViewHolder(holder: PreferenceViewHolder) {
        super.onBindViewHolder(holder)
        val configureButton = holder.findViewById(R.id.configurable_switch_button) as MaterialButton
        val switch = holder.findViewById(R.id.configurable_switch) as MaterialSwitch
        switch.isChecked = sharedPreferences!!.getBoolean(key, true)
        switch.setOnCheckedChangeListener { button, state ->
            setState(state)
        }
        configureButton.setOnClickListener { setAction() }
        holder.itemView.setOnClickListener { setAction() }
    }

    private fun setupPreference() {
        widgetLayoutResource = R.layout.preference_configurable_switch
    }

    fun setAction() {
        val dialogBuilder = MaterialAlertDialogBuilder(context)
        dialogBuilder.setTitle(R.string.enable_qs_date_action)
        val item = CLOCK_TAP_ACTIONS.indexOf(getAction())
        dialogBuilder.setSingleChoiceItems(
            R.array.clock_tap_actions,
            item
        ) { dialogInterface, position ->
            sharedPreferences!!.edit {
                putString("${key}_action", CLOCK_TAP_ACTIONS[position])
            }
            dialogInterface.dismiss()
        }
        dialogBuilder.show()
    }

    fun setState(state: Boolean) {
        persistBoolean(state)
    }

    fun getAction() = sharedPreferences!!.getString("${key}_action", "clock")
}
