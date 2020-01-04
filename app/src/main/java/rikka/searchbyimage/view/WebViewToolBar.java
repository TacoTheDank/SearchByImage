package rikka.searchbyimage.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Handler;
import android.util.AttributeSet;

import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;

import rikka.searchbyimage.R;
import rikka.searchbyimage.utils.Utils;

/**
 * Created by Rikka on 2016/1/10.
 */
public class WebViewToolBar extends Toolbar {
    private Context mContext;
    private Paint mPaint;

    private int progress;
    private boolean drawProgress;

    private String mTitle;
    private String mURL;

    public WebViewToolBar(Context context) {
        this(context, null);
    }

    public WebViewToolBar(Context context, AttributeSet attrs) {
        this(context, attrs, androidx.appcompat.R.attr.toolbarStyle);
    }

    public WebViewToolBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        mContext = context;
        mPaint = new Paint();

        setTitleTextAppearance(mContext, R.style.WebView_Title);
        setSubtitleTextAppearance(mContext, R.style.WebView_Uri);
    }

    @Override
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if (!drawProgress) {
            return;
        }
        mPaint.setColor(ContextCompat.getColor(mContext, R.color.progressBarDark));
        canvas.drawRect(0, canvas.getHeight() - Utils.dpToPx(2), canvas.getWidth(), canvas.getHeight(), mPaint);
        mPaint.setColor(ContextCompat.getColor(mContext, R.color.progressBar));
        canvas.drawRect(0, canvas.getHeight() - Utils.dpToPx(2), canvas.getWidth() * (float) progress / 100f, canvas.getHeight(), mPaint);
        invalidate();
    }

    public int getProgress() {
        return progress;
    }

    public void setProgress(int progress) {
        this.progress = progress;

        if (progress == 100) {
            new Handler().postDelayed(() -> drawProgress = false, 800);
        }/* else {
            drawProgress = true;
        }*/
    }

    public boolean getCanDrawProgress() {
        return drawProgress;
    }

    public void setCanDrawProgress(boolean drawProgress) {
        this.drawProgress = drawProgress;
    }
}
