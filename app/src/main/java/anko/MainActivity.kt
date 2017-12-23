package anko

import android.app.Activity
import android.os.Bundle
import android.widget.LinearLayout
import org.jetbrains.anko.*

import com.codelang.kotlin.R

/**
 * Generate with Plugin
 * @plugin Kotlin Anko Converter For Xml
 * @version 1.2.1
 */
class MainActivity : Activity() {

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		linearLayout {
			orientation = LinearLayout.VERTICAL
			//tools:context = com.codelang.kotlin.MainActivity //not support attribute
			textView {
				id = R.id.main_text
				text = "Hello World!"
			}
		}
	}
}
