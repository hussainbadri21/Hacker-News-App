package com.example.hussain.hny.utils.StyledViews;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.AppCompatEditText;
import android.util.AttributeSet;

/**
 * Created by Kushagra Mangal on 30-01-2018.
 */

public class StyledEditText extends AppCompatEditText {

    public static final String ANDROID_SCHEMA = "http://schemas.android.com/apk/res/android";

    public StyledEditText(Context context) {
        super(context);

        applyCustomFont(context,null);
    }

    public StyledEditText(Context context, AttributeSet attrs) {
        super(context, attrs);

        applyCustomFont(context, attrs);
    }

    public StyledEditText(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

        applyCustomFont(context, attrs);
    }

    private void applyCustomFont(Context context, AttributeSet attrs) {
        int textStyle=0;
        if (attrs!=null){
            textStyle = attrs.getAttributeIntValue(ANDROID_SCHEMA, "textStyle", Typeface.NORMAL);
        }
        Typeface customFont = selectTypeface(context, textStyle);
        setTypeface(customFont);
    }

    private Typeface selectTypeface(Context context, int textStyle) {
        /*
        * information about the StyledTextView textStyle:
        * http://developer.android.com/reference/android/R.styleable.html#TextView_textStyle
        */
        switch (textStyle) {
            case Typeface.BOLD: // bold
                return FontCache.getTypeface("fonts/Lato-Bold.ttf", context);

            case Typeface.ITALIC: // italic
                return FontCache.getTypeface("fonts/Lato-Italic.ttf", context);

            case Typeface.BOLD_ITALIC: // bold italic
                return FontCache.getTypeface("fonts/Lato-BoldItalic.ttf", context);

            case Typeface.NORMAL: // regular
            default:
                return FontCache.getTypeface("fonts/Lato-Regular.ttf", context);
        }
    }
}
