package onno.exoplanet;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.NinePatchDrawable;
import android.util.AttributeSet;
import android.widget.FrameLayout;

public class DrawShadowFrameLayout extends FrameLayout {
    private Drawable mShadowDrawable;
    private NinePatchDrawable mShadowNinePatchDrawable;
    private boolean mShadowVisible;
    private int mWidth, mHeight;
    private float mAlpha = 1f;

    public DrawShadowFrameLayout(Context context) {
        this(context, null, 0);
    }

    public DrawShadowFrameLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DrawShadowFrameLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        final TypedArray a = context.obtainStyledAttributes(attrs,
                R.styleable.DrawShadowFrameLayout, 0, 0);

        mShadowDrawable = a.getDrawable(R.styleable.DrawShadowFrameLayout_shadowDrawable);
        if (mShadowDrawable != null) {
            mShadowDrawable.setCallback(this);
            if (mShadowDrawable instanceof NinePatchDrawable) {
                mShadowNinePatchDrawable = (NinePatchDrawable) mShadowDrawable;
            }
        }

        mShadowVisible = a.getBoolean(R.styleable.DrawShadowFrameLayout_shadowVisible, true);
        setWillNotDraw(!mShadowVisible || mShadowDrawable == null);

        a.recycle();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mWidth = w;
        mHeight = h;
        updateShadowBounds();
    }

    private void updateShadowBounds() {
        if (mShadowDrawable != null) {
            mShadowDrawable.setBounds(0, 0, mWidth, mHeight);
        }
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        if (mShadowDrawable != null && mShadowVisible) {
            if (mShadowNinePatchDrawable != null) {
                mShadowNinePatchDrawable.getPaint().setAlpha((int) (255 * mAlpha));
            }
            mShadowDrawable.draw(canvas);
        }
    }

}
