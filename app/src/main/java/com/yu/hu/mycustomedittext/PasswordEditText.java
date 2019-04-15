package com.yu.hu.mycustomedittext;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.support.annotation.DrawableRes;
import android.support.annotation.IntDef;
import android.support.v7.widget.AppCompatEditText;
import android.text.InputType;
import android.util.AttributeSet;
import android.view.MotionEvent;


import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * 项目名：Traveling
 * 包名：  com.android.traveling.widget.gridview
 * 文件名：PasswordEditText
 * 创建者：HY
 * 创建时间：2019/4/6 16:27
 * 描述：  带有可见/不可见图标的密码输入框
 */

@SuppressWarnings("unused")
public class PasswordEditText extends AppCompatEditText {

    /**
     * 当前密码可见
     */
    private static final int VISIBLE = 0;
    private static final int INVISIBLE = 1;

    @IntDef({VISIBLE, INVISIBLE})
    @Retention(RetentionPolicy.SOURCE)
    @interface Visibility {
    }


    /**
     * 密码可见性
     */
    @Visibility
    private int visible = INVISIBLE;

    private int left = 0;
    private Drawable visibleIcon;  //密码可见时的图标
    private Drawable invisibleIcon;//密码不可见时的图标

    public PasswordEditText(Context context) {
        this(context, null);
    }

    public PasswordEditText(Context context, AttributeSet attrs) {
        this(context, attrs, android.R.attr.editTextStyle);
    }

    public PasswordEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    /**
     * 初始化自定义属性
     *
     * @param context context
     * @param attrs   attrs
     */
    private void init(Context context, AttributeSet attrs) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.PasswordEditText);
        if (typedArray != null) {

            //visibleIcon
            Drawable drawable = typedArray.getDrawable(R.styleable.PasswordEditText_petVisibleIcon);
            visibleIcon = drawable == null ?
                    context.getResources().getDrawable(R.drawable.ic_eye, null) : drawable;
            visibleIcon.setBounds(0, 0, visibleIcon.getMinimumWidth(), visibleIcon.getMinimumHeight());

            //invisibleIcon
            drawable = typedArray.getDrawable(R.styleable.PasswordEditText_petInvisibleIcon);
            invisibleIcon = drawable == null ?
                    context.getResources().getDrawable(R.drawable.ic_eye_slash, null) : drawable;
            invisibleIcon.setBounds(0, 0, invisibleIcon.getMinimumWidth(), invisibleIcon.getMinimumHeight());

            typedArray.recycle();

            //设置图标
            setCompoundDrawables(null, null, invisibleIcon, null);
            //设置密码不可见
            setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD | InputType.TYPE_CLASS_TEXT);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                /*
                  判断点击的是否是右边的按钮
                 */
                if (left == 0) {
                    left = getWidth() - getTotalPaddingRight() - getCompoundDrawablePadding();
                }
                boolean touchable = event.getX() > left && event.getX() < getWidth();
                if (touchable) {
                    changeVisible();
                } else {
                    performClick();
                }
                break;
        }
        return super.onTouchEvent(event);
    }

    /**
     * 设置密码可见时的图标
     *
     * @param visibleIcon visibleIcon
     */
    public void setVisibleIcon(Drawable visibleIcon) {
        this.visibleIcon = visibleIcon;
    }

    /**
     * 设置密码可见时的图标
     *
     * @param res DrawableRes
     */
    public void setVisibleIcon(@DrawableRes int res) {
        this.visibleIcon = getResources().getDrawable(res, null);
    }

    /**
     * 设置密码不可见时的图标
     *
     * @param invisibleIcon invisibleIcon
     */
    public void setInvisibleIcon(Drawable invisibleIcon) {
        this.invisibleIcon = invisibleIcon;
    }

    /**
     * 设置密码不可见时的图标
     *
     * @param res DrawableRes
     */
    public void setInvisibleIcon(@DrawableRes int res) {
        this.invisibleIcon = getResources().getDrawable(res, null);
    }

    /**
     * 改变密码可见性
     */
    public void changeVisible() {
        switch (visible) {
            case VISIBLE:
                visible = INVISIBLE;
                //设置密码不可见
                setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD | InputType.TYPE_CLASS_TEXT);
                setCompoundDrawables(null, null, invisibleIcon, null);
                break;
            case INVISIBLE:
                visible = VISIBLE;
                setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                setCompoundDrawables(null, null, visibleIcon, null);
                break;
        }
        setSelection(getText().toString().length());
    }

    @Override
    public boolean performClick() {
        return super.performClick();
    }
}
