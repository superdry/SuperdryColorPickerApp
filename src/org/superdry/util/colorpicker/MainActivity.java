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

import android.app.Activity;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends Activity implements View.OnClickListener {

	private Paint mPaint;
	private Button startBtn;
	private Button startPref;
	View color;
	int changedColor;
	private static final int initColor = 0xFFFF0000;
	private static final int ACTION_GETCOLOR = 1;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		changedColor = initColor;
		color = (View) findViewById(R.id.Color);
		color.setBackgroundColor(changedColor);
		startBtn = (Button) findViewById(R.id.StartButton);
		startBtn.setOnClickListener(this);
		startPref = (Button) findViewById(R.id.StartPreference);
		startPref.setOnClickListener(this);
		mPaint = new Paint();
		mPaint.setAntiAlias(true);
		mPaint.setDither(true);
		mPaint.setColor(changedColor);
		mPaint.setStyle(Paint.Style.STROKE);
		mPaint.setStrokeJoin(Paint.Join.ROUND);
		mPaint.setStrokeCap(Paint.Cap.ROUND);
		mPaint.setStrokeWidth(12);
		mPaint.setXfermode(null);
		mPaint.setAlpha(0xFF);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.StartButton:
			color.setBackgroundColor(changedColor);
			Intent intent = new Intent(this,
					org.superdry.util.colorpicker.lib.SuperdryColorPicker.class);
			intent.putExtra("SelectedColor", changedColor);
			startActivityForResult(
					intent,
					org.superdry.util.colorpicker.lib.SuperdryColorPicker.ACTION_GETCOLOR);
			break;
		case R.id.StartPreference:
			Intent prefIntent = new Intent(this, PrefActivity.class);
			startActivity(prefIntent);
			break;
		}
	}

	public void onActivityResult(int requestCode, int resultCode, Intent intent) {
		if (requestCode == ACTION_GETCOLOR) {

			if (resultCode == RESULT_OK) {
				Bundle b = intent.getExtras();
				if (b != null) {
					changedColor = b.getInt("SelectedColor");
				}
			} else if (resultCode == RESULT_CANCELED) {
				// 何もしない
			}
			mPaint.setColor(changedColor);
			color.setBackgroundColor(changedColor);
		}
	}
}