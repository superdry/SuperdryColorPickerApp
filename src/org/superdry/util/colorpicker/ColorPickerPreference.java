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

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.preference.Preference;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;

public class ColorPickerPreference extends Preference {

	private int color = 0xff000000;
	private int borderViewWidth;

	public ColorPickerPreference(Context context, AttributeSet attrs) {
		super(context, attrs);
		if (this.getWidgetLayoutResource() != R.layout.prefcolorview) {
			setWidgetLayoutResource(R.layout.prefcolorview);
		}

	}

	public ColorPickerPreference(Context context, AttributeSet attrs,
			int defStyle) {
		super(context, attrs, defStyle);
		if (this.getWidgetLayoutResource() != R.layout.prefcolorview) {
			setWidgetLayoutResource(R.layout.prefcolorview);
		}
	}

	protected void onBindView(View view) {
		super.onBindView(view);
		LinearLayout linearlayout = (LinearLayout) view
				.findViewById(R.id.prefcolorlayout);
		if (linearlayout.getChildCount() == 0) {
			linearlayout.addView(new BorderView(getContext()), borderViewWidth,
					borderViewWidth);
		}
	}

	public class BorderView extends View {
		public BorderView(Context context) {
			super(context);
		}

		protected void onDraw(Canvas canvas) {
			Paint paint = new Paint();
			paint.setAntiAlias(true);
			paint.setStyle(Paint.Style.FILL);
			paint.setColor(Color.WHITE);
			canvas.drawRoundRect(new RectF(0, 0, borderViewWidth,
					borderViewWidth), 3, 3, paint);
			paint.setStyle(Paint.Style.FILL);
			paint.setColor(Color.BLACK);
			canvas.drawRoundRect(new RectF(1, 1, borderViewWidth - 1,
					borderViewWidth - 1), 3, 3, paint);
			paint.setStyle(Paint.Style.FILL);
			paint.setColor(color);
			canvas.drawRoundRect(new RectF(2, 2, borderViewWidth - 2,
					borderViewWidth - 2), 3, 3, paint);
		}

	}

	public void setDpi(int dpi) {
		this.borderViewWidth = (int) (dpi * 75 / 240);
	}

	public void setColor(int color) {
		this.color = color;
	}
}