/******
 * SuperdryColorPickerApp
 * 
 * The MIT License
 * Copyright (c) 2011 superdry
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package org.superdry.util.colorpicker;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceManager;
import android.preference.Preference.OnPreferenceClickListener;
import android.preference.PreferenceActivity;
import android.util.DisplayMetrics;

public class PrefActivity extends PreferenceActivity {

	private SharedPreferences sharedPreferences;
	private int changedColor;
	private int dpi = 0;
	private static final String COLORVALUE_KEY = "SelectedColor";
	private static final String COLORVIEW_KEY = "colorPicker";
	private static final int DEFAULT_COLOR_VALUE = 0xFFFF0000;
	private static final int ACTION_GETCOLOR = 1;
	ColorPickerPreference scp;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if (dpi == 0) {
			DisplayMetrics metrics = new DisplayMetrics();
			this.getWindowManager().getDefaultDisplay().getMetrics(metrics);
			dpi = metrics.densityDpi;
		}
	}

	@Override
	protected void onStart() {
		super.onStart();
		this.addPreferencesFromResource(R.xml.setting);

		sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
		changedColor = sharedPreferences.getInt(COLORVALUE_KEY,
				DEFAULT_COLOR_VALUE);
		scp = (ColorPickerPreference) this.getPreferenceScreen()
				.findPreference(COLORVIEW_KEY);
		scp.setOnPreferenceClickListener(new OnPreferenceClickListener() {
			public boolean onPreferenceClick(Preference preference) {
				return onColorPreferenceClick(preference);
			}
		});
		scp.setDpi(dpi);
		scp.setColor(changedColor);
		scp.setSummary(color2String(changedColor));
	}

	private boolean onColorPreferenceClick(Preference pref) {
		Intent intent = new Intent(this,
				org.superdry.util.colorpicker.lib.SuperdryColorPicker.class);
		intent.putExtra(COLORVALUE_KEY, changedColor);
		startActivityForResult(
				intent,
				org.superdry.util.colorpicker.lib.SuperdryColorPicker.ACTION_GETCOLOR);
		return true;
	}

	public void onActivityResult(int requestCode, int resultCode, Intent intent) {
		if (requestCode == ACTION_GETCOLOR) {
			if (resultCode == RESULT_OK) {
				Bundle b = intent.getExtras();
				if (b != null) {
					changedColor = b.getInt(COLORVALUE_KEY);
				}
			} else if (resultCode == RESULT_CANCELED) {
				// なにもしない
			}
			scp = (ColorPickerPreference) this.getPreferenceScreen()
					.findPreference(COLORVIEW_KEY);
			scp.setColor(changedColor);
			scp.setSummary(color2String(changedColor));

			SharedPreferences.Editor editor = sharedPreferences.edit();
			editor.putInt(COLORVALUE_KEY, changedColor);
			editor.commit();
		}
	}
	private String color2String(int color) {
		return String.format("#%02x%02x%02x", Color.red(color),
				Color.green(color), Color.blue(color));
	}
}
