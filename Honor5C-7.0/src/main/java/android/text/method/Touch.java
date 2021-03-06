package android.text.method;

import android.text.Layout;
import android.text.Layout.Alignment;
import android.text.NoCopySpan;
import android.text.Spannable;
import android.view.MotionEvent;
import android.view.ViewConfiguration;
import android.widget.TextView;
import com.android.internal.os.HwBootFail;
import huawei.cust.HwCfgFilePolicy;
import javax.microedition.khronos.opengles.GL10;

public class Touch {

    private static class DragState implements NoCopySpan {
        public boolean mFarEnough;
        public int mScrollX;
        public int mScrollY;
        public boolean mUsed;
        public float mX;
        public float mY;

        public DragState(float x, float y, int scrollX, int scrollY) {
            this.mX = x;
            this.mY = y;
            this.mScrollX = scrollX;
            this.mScrollY = scrollY;
        }
    }

    private Touch() {
    }

    public static void scrollTo(TextView widget, Layout layout, int x, int y) {
        int left;
        int right;
        int availableWidth = widget.getWidth() - (widget.getTotalPaddingLeft() + widget.getTotalPaddingRight());
        int top = layout.getLineForVertical(y);
        Alignment a = layout.getParagraphAlignment(top);
        boolean ltr = layout.getParagraphDirection(top) > 0;
        if (widget.getHorizontallyScrolling()) {
            int bottom = layout.getLineForVertical((widget.getHeight() + y) - (widget.getTotalPaddingTop() + widget.getTotalPaddingBottom()));
            left = HwBootFail.STAGE_BOOT_SUCCESS;
            right = 0;
            for (int i = top; i <= bottom; i++) {
                left = (int) Math.min((float) left, layout.getLineLeft(i));
                right = (int) Math.max((float) right, layout.getLineRight(i));
            }
        } else {
            left = 0;
            right = availableWidth;
        }
        int actualWidth = right - left;
        if (actualWidth >= availableWidth) {
            x = Math.max(Math.min(x, right - availableWidth), left);
        } else if (a == Alignment.ALIGN_CENTER) {
            x = left - ((availableWidth - actualWidth) / 2);
        } else if (!(ltr && a == Alignment.ALIGN_OPPOSITE) && ((ltr || a != Alignment.ALIGN_NORMAL) && a != Alignment.ALIGN_RIGHT)) {
            x = left;
        } else {
            x = left - (availableWidth - actualWidth);
        }
        widget.scrollTo(x, y);
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static boolean onTouchEvent(TextView widget, Spannable buffer, MotionEvent event) {
        DragState[] ds;
        int i;
        int length;
        switch (event.getActionMasked()) {
            case HwCfgFilePolicy.GLOBAL /*0*/:
                ds = (DragState[]) buffer.getSpans(0, buffer.length(), DragState.class);
                i = 0;
                while (true) {
                    length = ds.length;
                    if (i < r0) {
                        buffer.removeSpan(ds[i]);
                        i++;
                    } else {
                        buffer.setSpan(new DragState(event.getX(), event.getY(), widget.getScrollX(), widget.getScrollY()), 0, 0, 17);
                        return true;
                    }
                }
            case HwCfgFilePolicy.EMUI /*1*/:
                ds = (DragState[]) buffer.getSpans(0, buffer.length(), DragState.class);
                i = 0;
                while (true) {
                    length = ds.length;
                    if (i < r0) {
                        buffer.removeSpan(ds[i]);
                        i++;
                    } else {
                        if (ds.length > 0) {
                            if (ds[0].mUsed) {
                                return true;
                            }
                        }
                        return false;
                    }
                }
            case HwCfgFilePolicy.PC /*2*/:
                ds = (DragState[]) buffer.getSpans(0, buffer.length(), DragState.class);
                if (ds.length > 0) {
                    if (!ds[0].mFarEnough) {
                        int slop = ViewConfiguration.get(widget.getContext()).getScaledTouchSlop();
                        if (Math.abs(event.getX() - ds[0].mX) < ((float) slop)) {
                            break;
                        }
                        ds[0].mFarEnough = true;
                    }
                    if (ds[0].mFarEnough) {
                        float dx;
                        float dy;
                        ds[0].mUsed = true;
                        boolean cap = ((event.getMetaState() & 1) != 0 || MetaKeyKeyListener.getMetaState((CharSequence) buffer, 1) == 1) ? true : MetaKeyKeyListener.getMetaState((CharSequence) buffer, GL10.GL_EXP) != 0;
                        if (cap) {
                            dx = event.getX() - ds[0].mX;
                            dy = event.getY() - ds[0].mY;
                        } else {
                            dx = ds[0].mX - event.getX();
                            dy = ds[0].mY - event.getY();
                        }
                        ds[0].mX = event.getX();
                        ds[0].mY = event.getY();
                        int nx = widget.getScrollX() + ((int) dx);
                        int ny = widget.getScrollY() + ((int) dy);
                        int padding = widget.getTotalPaddingTop() + widget.getTotalPaddingBottom();
                        Layout layout = widget.getLayout();
                        ny = Math.max(Math.min(ny, layout.getHeight() - (widget.getHeight() - padding)), 0);
                        int oldX = widget.getScrollX();
                        int oldY = widget.getScrollY();
                        scrollTo(widget, layout, nx, ny);
                        if (!(oldX == widget.getScrollX() && oldY == widget.getScrollY())) {
                            widget.cancelLongPress();
                        }
                        return true;
                    }
                }
                break;
        }
        return false;
    }

    public static int getInitialScrollX(TextView widget, Spannable buffer) {
        DragState[] ds = (DragState[]) buffer.getSpans(0, buffer.length(), DragState.class);
        return ds.length > 0 ? ds[0].mScrollX : -1;
    }

    public static int getInitialScrollY(TextView widget, Spannable buffer) {
        DragState[] ds = (DragState[]) buffer.getSpans(0, buffer.length(), DragState.class);
        return ds.length > 0 ? ds[0].mScrollY : -1;
    }
}
