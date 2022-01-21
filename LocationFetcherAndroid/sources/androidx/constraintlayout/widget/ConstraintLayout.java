package androidx.constraintlayout.widget;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Log;
import android.util.SparseArray;
import android.util.SparseIntArray;
import android.view.View;
import android.view.ViewGroup;
import androidx.constraintlayout.solver.Metrics;
import androidx.constraintlayout.solver.widgets.ConstraintWidget;
import androidx.constraintlayout.solver.widgets.ConstraintWidgetContainer;
import androidx.constraintlayout.solver.widgets.Guideline;
import androidx.constraintlayout.solver.widgets.Optimizer;
import androidx.constraintlayout.solver.widgets.analyzer.BasicMeasure;
import androidx.core.internal.view.SupportMenu;
import androidx.core.view.ViewCompat;
import java.util.ArrayList;
import java.util.HashMap;

public class ConstraintLayout extends ViewGroup {
    private static final boolean DEBUG = false;
    private static final boolean DEBUG_DRAW_CONSTRAINTS = false;
    public static final int DESIGN_INFO_ID = 0;
    private static final boolean MEASURE = false;
    private static final String TAG = "ConstraintLayout";
    private static final boolean USE_CONSTRAINTS_HELPER = true;
    public static final String VERSION = "ConstraintLayout-2.0.1";
    SparseArray<View> mChildrenByIds = new SparseArray<>();
    /* access modifiers changed from: private */
    public ArrayList<ConstraintHelper> mConstraintHelpers = new ArrayList<>(4);
    protected ConstraintLayoutStates mConstraintLayoutSpec = null;
    private ConstraintSet mConstraintSet = null;
    private int mConstraintSetId = -1;
    private ConstraintsChangedListener mConstraintsChangedListener;
    private HashMap<String, Integer> mDesignIds = new HashMap<>();
    protected boolean mDirtyHierarchy = USE_CONSTRAINTS_HELPER;
    private int mLastMeasureHeight = -1;
    int mLastMeasureHeightMode = 0;
    int mLastMeasureHeightSize = -1;
    private int mLastMeasureWidth = -1;
    int mLastMeasureWidthMode = 0;
    int mLastMeasureWidthSize = -1;
    /* access modifiers changed from: protected */
    public ConstraintWidgetContainer mLayoutWidget = new ConstraintWidgetContainer();
    private int mMaxHeight = Integer.MAX_VALUE;
    private int mMaxWidth = Integer.MAX_VALUE;
    Measurer mMeasurer = new Measurer(this);
    private Metrics mMetrics;
    private int mMinHeight = 0;
    private int mMinWidth = 0;
    private int mOnMeasureHeightMeasureSpec = 0;
    private int mOnMeasureWidthMeasureSpec = 0;
    private int mOptimizationLevel = Optimizer.OPTIMIZATION_STANDARD;
    private SparseArray<ConstraintWidget> mTempMapIdToWidget = new SparseArray<>();

    public void setDesignInformation(int type, Object value1, Object value2) {
        if (type == 0 && (value1 instanceof String) && (value2 instanceof Integer)) {
            if (this.mDesignIds == null) {
                this.mDesignIds = new HashMap<>();
            }
            String name = (String) value1;
            int index = name.indexOf("/");
            if (index != -1) {
                name = name.substring(index + 1);
            }
            this.mDesignIds.put(name, Integer.valueOf(((Integer) value2).intValue()));
        }
    }

    public Object getDesignInformation(int type, Object value) {
        if (type != 0 || !(value instanceof String)) {
            return null;
        }
        String name = (String) value;
        HashMap<String, Integer> hashMap = this.mDesignIds;
        if (hashMap == null || !hashMap.containsKey(name)) {
            return null;
        }
        return this.mDesignIds.get(name);
    }

    public ConstraintLayout(Context context) {
        super(context);
        init((AttributeSet) null, 0, 0);
    }

    public ConstraintLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs, 0, 0);
    }

    public ConstraintLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs, defStyleAttr, 0);
    }

    public ConstraintLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(attrs, defStyleAttr, defStyleRes);
    }

    public void setId(int id) {
        this.mChildrenByIds.remove(getId());
        super.setId(id);
        this.mChildrenByIds.put(getId(), this);
    }

    class Measurer implements BasicMeasure.Measurer {
        ConstraintLayout layout;
        int layoutHeightSpec;
        int layoutWidthSpec;
        int paddingBottom;
        int paddingHeight;
        int paddingTop;
        int paddingWidth;

        public void captureLayoutInfos(int widthSpec, int heightSpec, int top, int bottom, int width, int height) {
            this.paddingTop = top;
            this.paddingBottom = bottom;
            this.paddingWidth = width;
            this.paddingHeight = height;
            this.layoutWidthSpec = widthSpec;
            this.layoutHeightSpec = heightSpec;
        }

        public Measurer(ConstraintLayout l) {
            this.layout = l;
        }

        /* JADX WARNING: Code restructure failed: missing block: B:27:0x0084, code lost:
            if (r1.wrapMeasure[0] == r31.getWidth()) goto L_0x0089;
         */
        /* JADX WARNING: Removed duplicated region for block: B:101:0x01af  */
        /* JADX WARNING: Removed duplicated region for block: B:105:0x01c8 A[ADDED_TO_REGION] */
        /* JADX WARNING: Removed duplicated region for block: B:116:0x01f1  */
        /* JADX WARNING: Removed duplicated region for block: B:117:0x01fd  */
        /* JADX WARNING: Removed duplicated region for block: B:120:0x0214  */
        /* JADX WARNING: Removed duplicated region for block: B:121:0x0223  */
        /* JADX WARNING: Removed duplicated region for block: B:123:0x0233  */
        /* JADX WARNING: Removed duplicated region for block: B:124:0x0242  */
        /* JADX WARNING: Removed duplicated region for block: B:127:0x0254  */
        /* JADX WARNING: Removed duplicated region for block: B:128:0x0261  */
        /* JADX WARNING: Removed duplicated region for block: B:131:0x0269  */
        /* JADX WARNING: Removed duplicated region for block: B:134:0x0273  */
        /* JADX WARNING: Removed duplicated region for block: B:135:0x0280  */
        /* JADX WARNING: Removed duplicated region for block: B:138:0x0288  */
        /* JADX WARNING: Removed duplicated region for block: B:140:0x0290 A[ADDED_TO_REGION] */
        /* JADX WARNING: Removed duplicated region for block: B:147:0x02b1 A[ADDED_TO_REGION] */
        /* JADX WARNING: Removed duplicated region for block: B:150:0x02bd  */
        /* JADX WARNING: Removed duplicated region for block: B:151:0x02c5  */
        /* JADX WARNING: Removed duplicated region for block: B:153:0x02c9  */
        /* JADX WARNING: Removed duplicated region for block: B:154:0x02ce  */
        /* JADX WARNING: Removed duplicated region for block: B:158:0x02e7  */
        /* JADX WARNING: Removed duplicated region for block: B:159:0x02e9  */
        /* JADX WARNING: Removed duplicated region for block: B:164:0x02f4  */
        /* JADX WARNING: Removed duplicated region for block: B:165:0x02f7  */
        /* JADX WARNING: Removed duplicated region for block: B:168:0x02fe  */
        /* JADX WARNING: Removed duplicated region for block: B:98:0x01a5  */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public final void measure(androidx.constraintlayout.solver.widgets.ConstraintWidget r31, androidx.constraintlayout.solver.widgets.analyzer.BasicMeasure.Measure r32) {
            /*
                r30 = this;
                r0 = r30
                r1 = r31
                r2 = r32
                if (r1 != 0) goto L_0x0009
                return
            L_0x0009:
                int r3 = r31.getVisibility()
                r4 = 8
                r5 = 0
                if (r3 != r4) goto L_0x001f
                boolean r3 = r31.isInPlaceholder()
                if (r3 != 0) goto L_0x001f
                r2.measuredWidth = r5
                r2.measuredHeight = r5
                r2.measuredBaseline = r5
                return
            L_0x001f:
                androidx.constraintlayout.solver.widgets.ConstraintWidget$DimensionBehaviour r3 = r2.horizontalBehavior
                androidx.constraintlayout.solver.widgets.ConstraintWidget$DimensionBehaviour r4 = r2.verticalBehavior
                int r6 = r2.horizontalDimension
                int r7 = r2.verticalDimension
                r8 = 0
                r9 = 0
                int r10 = r0.paddingTop
                int r11 = r0.paddingBottom
                int r10 = r10 + r11
                int r11 = r0.paddingWidth
                r12 = 0
                r13 = 0
                java.lang.Object r14 = r31.getCompanionWidget()
                android.view.View r14 = (android.view.View) r14
                int[] r15 = androidx.constraintlayout.widget.ConstraintLayout.C01201.f45xdde91696
                int r16 = r3.ordinal()
                r15 = r15[r16]
                r5 = 2
                r20 = r8
                r8 = 1
                if (r15 == r8) goto L_0x00d2
                if (r15 == r5) goto L_0x00c0
                r5 = 3
                if (r15 == r5) goto L_0x00aa
                r5 = 4
                if (r15 == r5) goto L_0x0053
                r15 = r20
                r8 = 2
                goto L_0x00de
            L_0x0053:
                int r5 = r0.layoutWidthSpec
                r15 = -2
                int r5 = android.view.ViewGroup.getChildMeasureSpec(r5, r11, r15)
                r12 = 1
                int r15 = r1.mMatchConstraintDefaultWidth
                if (r15 != r8) goto L_0x0061
                r15 = r8
                goto L_0x0062
            L_0x0061:
                r15 = 0
            L_0x0062:
                int[] r8 = r1.wrapMeasure
                r16 = 2
                r20 = 0
                r8[r16] = r20
                boolean r8 = r2.useCurrentDimensions
                if (r8 == 0) goto L_0x00a4
                if (r15 == 0) goto L_0x0087
                int[] r8 = r1.wrapMeasure
                r19 = 3
                r8 = r8[r19]
                if (r8 == 0) goto L_0x0087
                int[] r8 = r1.wrapMeasure
                r16 = 0
                r8 = r8[r16]
                r20 = r5
                int r5 = r31.getWidth()
                if (r8 != r5) goto L_0x008d
                goto L_0x0089
            L_0x0087:
                r20 = r5
            L_0x0089:
                boolean r5 = r14 instanceof androidx.constraintlayout.widget.Placeholder
                if (r5 == 0) goto L_0x008f
            L_0x008d:
                r5 = 1
                goto L_0x0090
            L_0x008f:
                r5 = 0
            L_0x0090:
                if (r15 == 0) goto L_0x0094
                if (r5 == 0) goto L_0x00a6
            L_0x0094:
                int r8 = r31.getWidth()
                r22 = r5
                r5 = 1073741824(0x40000000, float:2.0)
                int r8 = android.view.View.MeasureSpec.makeMeasureSpec(r8, r5)
                r12 = 0
                r15 = r8
                r8 = 2
                goto L_0x00de
            L_0x00a4:
                r20 = r5
            L_0x00a6:
                r15 = r20
                r8 = 2
                goto L_0x00de
            L_0x00aa:
                int r5 = r0.layoutWidthSpec
                int r8 = r31.getHorizontalMargin()
                int r8 = r8 + r11
                r15 = -1
                int r8 = android.view.ViewGroup.getChildMeasureSpec(r5, r8, r15)
                int[] r5 = r1.wrapMeasure
                r20 = r8
                r8 = 2
                r5[r8] = r15
                r15 = r20
                goto L_0x00de
            L_0x00c0:
                r8 = r5
                int r5 = r0.layoutWidthSpec
                r15 = -2
                int r5 = android.view.ViewGroup.getChildMeasureSpec(r5, r11, r15)
                r12 = 1
                r20 = r5
                int[] r5 = r1.wrapMeasure
                r5[r8] = r15
                r15 = r20
                goto L_0x00de
            L_0x00d2:
                r8 = r5
                r5 = 1073741824(0x40000000, float:2.0)
                int r15 = android.view.View.MeasureSpec.makeMeasureSpec(r6, r5)
                int[] r5 = r1.wrapMeasure
                r5[r8] = r6
            L_0x00de:
                int[] r5 = androidx.constraintlayout.widget.ConstraintLayout.C01201.f45xdde91696
                int r20 = r4.ordinal()
                r5 = r5[r20]
                r20 = r6
                r6 = 1
                if (r5 == r6) goto L_0x0160
                if (r5 == r8) goto L_0x0152
                r6 = 3
                if (r5 == r6) goto L_0x0140
                r6 = 4
                if (r5 == r6) goto L_0x00f5
                goto L_0x016c
            L_0x00f5:
                int r5 = r0.layoutHeightSpec
                r6 = -2
                int r9 = android.view.ViewGroup.getChildMeasureSpec(r5, r10, r6)
                r13 = 1
                int r5 = r1.mMatchConstraintDefaultHeight
                r6 = 1
                if (r5 != r6) goto L_0x0104
                r5 = 1
                goto L_0x0105
            L_0x0104:
                r5 = 0
            L_0x0105:
                int[] r6 = r1.wrapMeasure
                r8 = 3
                r16 = 0
                r6[r8] = r16
                boolean r6 = r2.useCurrentDimensions
                if (r6 == 0) goto L_0x013d
                if (r5 == 0) goto L_0x0124
                int[] r6 = r1.wrapMeasure
                r8 = 2
                r6 = r6[r8]
                if (r6 == 0) goto L_0x0124
                int[] r6 = r1.wrapMeasure
                r8 = 1
                r6 = r6[r8]
                int r8 = r31.getHeight()
                if (r6 != r8) goto L_0x0128
            L_0x0124:
                boolean r6 = r14 instanceof androidx.constraintlayout.widget.Placeholder
                if (r6 == 0) goto L_0x012a
            L_0x0128:
                r6 = 1
                goto L_0x012b
            L_0x012a:
                r6 = 0
            L_0x012b:
                if (r5 == 0) goto L_0x012f
                if (r6 == 0) goto L_0x016c
            L_0x012f:
                int r8 = r31.getHeight()
                r17 = r5
                r5 = 1073741824(0x40000000, float:2.0)
                int r9 = android.view.View.MeasureSpec.makeMeasureSpec(r8, r5)
                r13 = 0
                goto L_0x016c
            L_0x013d:
                r17 = r5
                goto L_0x016c
            L_0x0140:
                int r5 = r0.layoutHeightSpec
                int r6 = r31.getVerticalMargin()
                int r6 = r6 + r10
                r8 = -1
                int r9 = android.view.ViewGroup.getChildMeasureSpec(r5, r6, r8)
                int[] r5 = r1.wrapMeasure
                r6 = 3
                r5[r6] = r8
                goto L_0x016c
            L_0x0152:
                r6 = 3
                int r5 = r0.layoutHeightSpec
                r8 = -2
                int r9 = android.view.ViewGroup.getChildMeasureSpec(r5, r10, r8)
                r13 = 1
                int[] r5 = r1.wrapMeasure
                r5[r6] = r8
                goto L_0x016c
            L_0x0160:
                r6 = 3
                r5 = 1073741824(0x40000000, float:2.0)
                int r9 = android.view.View.MeasureSpec.makeMeasureSpec(r7, r5)
                int[] r5 = r1.wrapMeasure
                r5[r6] = r7
            L_0x016c:
                androidx.constraintlayout.solver.widgets.ConstraintWidget$DimensionBehaviour r5 = androidx.constraintlayout.solver.widgets.ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT
                if (r3 != r5) goto L_0x0172
                r5 = 1
                goto L_0x0173
            L_0x0172:
                r5 = 0
            L_0x0173:
                androidx.constraintlayout.solver.widgets.ConstraintWidget$DimensionBehaviour r6 = androidx.constraintlayout.solver.widgets.ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT
                if (r4 != r6) goto L_0x0179
                r6 = 1
                goto L_0x017a
            L_0x0179:
                r6 = 0
            L_0x017a:
                androidx.constraintlayout.solver.widgets.ConstraintWidget$DimensionBehaviour r8 = androidx.constraintlayout.solver.widgets.ConstraintWidget.DimensionBehaviour.MATCH_PARENT
                if (r4 == r8) goto L_0x0185
                androidx.constraintlayout.solver.widgets.ConstraintWidget$DimensionBehaviour r8 = androidx.constraintlayout.solver.widgets.ConstraintWidget.DimensionBehaviour.FIXED
                if (r4 != r8) goto L_0x0183
                goto L_0x0185
            L_0x0183:
                r8 = 0
                goto L_0x0186
            L_0x0185:
                r8 = 1
            L_0x0186:
                androidx.constraintlayout.solver.widgets.ConstraintWidget$DimensionBehaviour r0 = androidx.constraintlayout.solver.widgets.ConstraintWidget.DimensionBehaviour.MATCH_PARENT
                if (r3 == r0) goto L_0x0191
                androidx.constraintlayout.solver.widgets.ConstraintWidget$DimensionBehaviour r0 = androidx.constraintlayout.solver.widgets.ConstraintWidget.DimensionBehaviour.FIXED
                if (r3 != r0) goto L_0x018f
                goto L_0x0191
            L_0x018f:
                r0 = 0
                goto L_0x0192
            L_0x0191:
                r0 = 1
            L_0x0192:
                r17 = 0
                if (r5 == 0) goto L_0x01a0
                r18 = r3
                float r3 = r1.mDimensionRatio
                int r3 = (r3 > r17 ? 1 : (r3 == r17 ? 0 : -1))
                if (r3 <= 0) goto L_0x01a2
                r3 = 1
                goto L_0x01a3
            L_0x01a0:
                r18 = r3
            L_0x01a2:
                r3 = 0
            L_0x01a3:
                if (r6 == 0) goto L_0x01af
                r22 = r4
                float r4 = r1.mDimensionRatio
                int r4 = (r4 > r17 ? 1 : (r4 == r17 ? 0 : -1))
                if (r4 <= 0) goto L_0x01b1
                r4 = 1
                goto L_0x01b2
            L_0x01af:
                r22 = r4
            L_0x01b1:
                r4 = 0
            L_0x01b2:
                android.view.ViewGroup$LayoutParams r17 = r14.getLayoutParams()
                r23 = r7
                r7 = r17
                androidx.constraintlayout.widget.ConstraintLayout$LayoutParams r7 = (androidx.constraintlayout.widget.ConstraintLayout.LayoutParams) r7
                r17 = 0
                r24 = 0
                r25 = 0
                r26 = r10
                boolean r10 = r2.useCurrentDimensions
                if (r10 != 0) goto L_0x01e9
                if (r5 == 0) goto L_0x01e9
                int r10 = r1.mMatchConstraintDefaultWidth
                if (r10 != 0) goto L_0x01e9
                if (r6 == 0) goto L_0x01e9
                int r10 = r1.mMatchConstraintDefaultHeight
                if (r10 == 0) goto L_0x01d5
                goto L_0x01e9
            L_0x01d5:
                r28 = r3
                r27 = r5
                r21 = r11
                r3 = r17
                r5 = r25
                r16 = 0
                r29 = r24
                r24 = r6
                r6 = r29
                goto L_0x02e4
            L_0x01e9:
                boolean r10 = r14 instanceof androidx.constraintlayout.widget.VirtualLayout
                if (r10 == 0) goto L_0x01fd
                boolean r10 = r1 instanceof androidx.constraintlayout.solver.widgets.VirtualLayout
                if (r10 == 0) goto L_0x01fd
                r10 = r1
                androidx.constraintlayout.solver.widgets.VirtualLayout r10 = (androidx.constraintlayout.solver.widgets.VirtualLayout) r10
                r27 = r5
                r5 = r14
                androidx.constraintlayout.widget.VirtualLayout r5 = (androidx.constraintlayout.widget.VirtualLayout) r5
                r5.onMeasure(r10, r15, r9)
                goto L_0x0202
            L_0x01fd:
                r27 = r5
                r14.measure(r15, r9)
            L_0x0202:
                int r5 = r14.getMeasuredWidth()
                int r10 = r14.getMeasuredHeight()
                int r25 = r14.getBaseline()
                r28 = r5
                r17 = r10
                if (r12 == 0) goto L_0x0223
                r24 = r6
                int[] r6 = r1.wrapMeasure
                r16 = 0
                r6[r16] = r5
                int[] r6 = r1.wrapMeasure
                r21 = 2
                r6[r21] = r10
                goto L_0x0231
            L_0x0223:
                r24 = r6
                r16 = 0
                r21 = 2
                int[] r6 = r1.wrapMeasure
                r6[r16] = r16
                int[] r6 = r1.wrapMeasure
                r6[r21] = r16
            L_0x0231:
                if (r13 == 0) goto L_0x0242
                int[] r6 = r1.wrapMeasure
                r21 = 1
                r6[r21] = r10
                int[] r6 = r1.wrapMeasure
                r19 = 3
                r6[r19] = r5
                r16 = 0
                goto L_0x0250
            L_0x0242:
                r19 = 3
                r21 = 1
                int[] r6 = r1.wrapMeasure
                r16 = 0
                r6[r21] = r16
                int[] r6 = r1.wrapMeasure
                r6[r19] = r16
            L_0x0250:
                int r6 = r1.mMatchConstraintMinWidth
                if (r6 <= 0) goto L_0x0261
                int r6 = r1.mMatchConstraintMinWidth
                r19 = r9
                r9 = r28
                int r28 = java.lang.Math.max(r6, r9)
                r9 = r28
                goto L_0x0265
            L_0x0261:
                r19 = r9
                r9 = r28
            L_0x0265:
                int r6 = r1.mMatchConstraintMaxWidth
                if (r6 <= 0) goto L_0x026f
                int r6 = r1.mMatchConstraintMaxWidth
                int r9 = java.lang.Math.min(r6, r9)
            L_0x026f:
                int r6 = r1.mMatchConstraintMinHeight
                if (r6 <= 0) goto L_0x0280
                int r6 = r1.mMatchConstraintMinHeight
                r21 = r11
                r11 = r17
                int r17 = java.lang.Math.max(r6, r11)
                r11 = r17
                goto L_0x0284
            L_0x0280:
                r21 = r11
                r11 = r17
            L_0x0284:
                int r6 = r1.mMatchConstraintMaxHeight
                if (r6 <= 0) goto L_0x028e
                int r6 = r1.mMatchConstraintMaxHeight
                int r11 = java.lang.Math.min(r6, r11)
            L_0x028e:
                if (r3 == 0) goto L_0x029f
                if (r8 == 0) goto L_0x029f
                float r6 = r1.mDimensionRatio
                r28 = r3
                float r3 = (float) r11
                float r3 = r3 * r6
                r17 = 1056964608(0x3f000000, float:0.5)
                float r3 = r3 + r17
                int r3 = (int) r3
                r9 = r3
                goto L_0x02af
            L_0x029f:
                r28 = r3
                if (r4 == 0) goto L_0x02af
                if (r0 == 0) goto L_0x02af
                float r3 = r1.mDimensionRatio
                float r6 = (float) r9
                float r6 = r6 / r3
                r17 = 1056964608(0x3f000000, float:0.5)
                float r6 = r6 + r17
                int r6 = (int) r6
                r11 = r6
            L_0x02af:
                if (r5 != r9) goto L_0x02bb
                if (r10 == r11) goto L_0x02b4
                goto L_0x02bb
            L_0x02b4:
                r3 = r9
                r6 = r11
                r9 = r19
                r5 = r25
                goto L_0x02e4
            L_0x02bb:
                if (r5 == r9) goto L_0x02c5
                r3 = 1073741824(0x40000000, float:2.0)
                int r6 = android.view.View.MeasureSpec.makeMeasureSpec(r9, r3)
                r15 = r6
                goto L_0x02c7
            L_0x02c5:
                r3 = 1073741824(0x40000000, float:2.0)
            L_0x02c7:
                if (r10 == r11) goto L_0x02ce
                int r3 = android.view.View.MeasureSpec.makeMeasureSpec(r11, r3)
                goto L_0x02d0
            L_0x02ce:
                r3 = r19
            L_0x02d0:
                r14.measure(r15, r3)
                int r17 = r14.getMeasuredWidth()
                int r6 = r14.getMeasuredHeight()
                int r25 = r14.getBaseline()
                r9 = r3
                r3 = r17
                r5 = r25
            L_0x02e4:
                r10 = -1
                if (r5 == r10) goto L_0x02e9
                r10 = 1
                goto L_0x02eb
            L_0x02e9:
                r10 = r16
            L_0x02eb:
                int r11 = r2.horizontalDimension
                if (r3 != r11) goto L_0x02f7
                int r11 = r2.verticalDimension
                if (r6 == r11) goto L_0x02f4
                goto L_0x02f7
            L_0x02f4:
                r11 = r16
                goto L_0x02f8
            L_0x02f7:
                r11 = 1
            L_0x02f8:
                r2.measuredNeedsSolverPass = r11
                boolean r11 = r7.needsBaseline
                if (r11 == 0) goto L_0x02ff
                r10 = 1
            L_0x02ff:
                if (r10 == 0) goto L_0x030d
                r11 = -1
                if (r5 == r11) goto L_0x030d
                int r11 = r31.getBaselineDistance()
                if (r11 == r5) goto L_0x030d
                r11 = 1
                r2.measuredNeedsSolverPass = r11
            L_0x030d:
                r2.measuredWidth = r3
                r2.measuredHeight = r6
                r2.measuredHasBaseline = r10
                r2.measuredBaseline = r5
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: androidx.constraintlayout.widget.ConstraintLayout.Measurer.measure(androidx.constraintlayout.solver.widgets.ConstraintWidget, androidx.constraintlayout.solver.widgets.analyzer.BasicMeasure$Measure):void");
        }

        public final void didMeasures() {
            int widgetsCount = this.layout.getChildCount();
            for (int i = 0; i < widgetsCount; i++) {
                View child = this.layout.getChildAt(i);
                if (child instanceof Placeholder) {
                    ((Placeholder) child).updatePostMeasure(this.layout);
                }
            }
            int helperCount = this.layout.mConstraintHelpers.size();
            if (helperCount > 0) {
                for (int i2 = 0; i2 < helperCount; i2++) {
                    ((ConstraintHelper) this.layout.mConstraintHelpers.get(i2)).updatePostMeasure(this.layout);
                }
            }
        }
    }

    /* renamed from: androidx.constraintlayout.widget.ConstraintLayout$1 */
    static /* synthetic */ class C01201 {

        /* renamed from: $SwitchMap$androidx$constraintlayout$solver$widgets$ConstraintWidget$DimensionBehaviour */
        static final /* synthetic */ int[] f45xdde91696;

        static {
            int[] iArr = new int[ConstraintWidget.DimensionBehaviour.values().length];
            f45xdde91696 = iArr;
            try {
                iArr[ConstraintWidget.DimensionBehaviour.FIXED.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                f45xdde91696[ConstraintWidget.DimensionBehaviour.WRAP_CONTENT.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                f45xdde91696[ConstraintWidget.DimensionBehaviour.MATCH_PARENT.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
            try {
                f45xdde91696[ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT.ordinal()] = 4;
            } catch (NoSuchFieldError e4) {
            }
        }
    }

    private void init(AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        this.mLayoutWidget.setCompanionWidget(this);
        this.mLayoutWidget.setMeasurer(this.mMeasurer);
        this.mChildrenByIds.put(getId(), this);
        this.mConstraintSet = null;
        if (attrs != null) {
            TypedArray a = getContext().obtainStyledAttributes(attrs, C0251R.styleable.ConstraintLayout_Layout, defStyleAttr, defStyleRes);
            int N = a.getIndexCount();
            for (int i = 0; i < N; i++) {
                int attr = a.getIndex(i);
                if (attr == C0251R.styleable.ConstraintLayout_Layout_android_minWidth) {
                    this.mMinWidth = a.getDimensionPixelOffset(attr, this.mMinWidth);
                } else if (attr == C0251R.styleable.ConstraintLayout_Layout_android_minHeight) {
                    this.mMinHeight = a.getDimensionPixelOffset(attr, this.mMinHeight);
                } else if (attr == C0251R.styleable.ConstraintLayout_Layout_android_maxWidth) {
                    this.mMaxWidth = a.getDimensionPixelOffset(attr, this.mMaxWidth);
                } else if (attr == C0251R.styleable.ConstraintLayout_Layout_android_maxHeight) {
                    this.mMaxHeight = a.getDimensionPixelOffset(attr, this.mMaxHeight);
                } else if (attr == C0251R.styleable.ConstraintLayout_Layout_layout_optimizationLevel) {
                    this.mOptimizationLevel = a.getInt(attr, this.mOptimizationLevel);
                } else if (attr == C0251R.styleable.ConstraintLayout_Layout_layoutDescription) {
                    int id = a.getResourceId(attr, 0);
                    if (id != 0) {
                        try {
                            parseLayoutDescription(id);
                        } catch (Resources.NotFoundException e) {
                            this.mConstraintLayoutSpec = null;
                        }
                    }
                } else if (attr == C0251R.styleable.ConstraintLayout_Layout_constraintSet) {
                    int id2 = a.getResourceId(attr, 0);
                    try {
                        ConstraintSet constraintSet = new ConstraintSet();
                        this.mConstraintSet = constraintSet;
                        constraintSet.load(getContext(), id2);
                    } catch (Resources.NotFoundException e2) {
                        this.mConstraintSet = null;
                    }
                    this.mConstraintSetId = id2;
                }
            }
            a.recycle();
        }
        this.mLayoutWidget.setOptimizationLevel(this.mOptimizationLevel);
    }

    /* access modifiers changed from: protected */
    public void parseLayoutDescription(int id) {
        this.mConstraintLayoutSpec = new ConstraintLayoutStates(getContext(), this, id);
    }

    public void addView(View child, int index, ViewGroup.LayoutParams params) {
        super.addView(child, index, params);
        if (Build.VERSION.SDK_INT < 14) {
            onViewAdded(child);
        }
    }

    public void removeView(View view) {
        super.removeView(view);
        if (Build.VERSION.SDK_INT < 14) {
            onViewRemoved(view);
        }
    }

    public void onViewAdded(View view) {
        if (Build.VERSION.SDK_INT >= 14) {
            super.onViewAdded(view);
        }
        ConstraintWidget widget = getViewWidget(view);
        if ((view instanceof Guideline) && !(widget instanceof Guideline)) {
            LayoutParams layoutParams = (LayoutParams) view.getLayoutParams();
            layoutParams.widget = new Guideline();
            layoutParams.isGuideline = USE_CONSTRAINTS_HELPER;
            ((Guideline) layoutParams.widget).setOrientation(layoutParams.orientation);
        }
        if (view instanceof ConstraintHelper) {
            ConstraintHelper helper = (ConstraintHelper) view;
            helper.validateParams();
            ((LayoutParams) view.getLayoutParams()).isHelper = USE_CONSTRAINTS_HELPER;
            if (!this.mConstraintHelpers.contains(helper)) {
                this.mConstraintHelpers.add(helper);
            }
        }
        this.mChildrenByIds.put(view.getId(), view);
        this.mDirtyHierarchy = USE_CONSTRAINTS_HELPER;
    }

    public void onViewRemoved(View view) {
        if (Build.VERSION.SDK_INT >= 14) {
            super.onViewRemoved(view);
        }
        this.mChildrenByIds.remove(view.getId());
        this.mLayoutWidget.remove(getViewWidget(view));
        this.mConstraintHelpers.remove(view);
        this.mDirtyHierarchy = USE_CONSTRAINTS_HELPER;
    }

    public void setMinWidth(int value) {
        if (value != this.mMinWidth) {
            this.mMinWidth = value;
            requestLayout();
        }
    }

    public void setMinHeight(int value) {
        if (value != this.mMinHeight) {
            this.mMinHeight = value;
            requestLayout();
        }
    }

    public int getMinWidth() {
        return this.mMinWidth;
    }

    public int getMinHeight() {
        return this.mMinHeight;
    }

    public void setMaxWidth(int value) {
        if (value != this.mMaxWidth) {
            this.mMaxWidth = value;
            requestLayout();
        }
    }

    public void setMaxHeight(int value) {
        if (value != this.mMaxHeight) {
            this.mMaxHeight = value;
            requestLayout();
        }
    }

    public int getMaxWidth() {
        return this.mMaxWidth;
    }

    public int getMaxHeight() {
        return this.mMaxHeight;
    }

    private boolean updateHierarchy() {
        int count = getChildCount();
        boolean recompute = false;
        int i = 0;
        while (true) {
            if (i >= count) {
                break;
            } else if (getChildAt(i).isLayoutRequested()) {
                recompute = USE_CONSTRAINTS_HELPER;
                break;
            } else {
                i++;
            }
        }
        if (recompute) {
            setChildrenConstraints();
        }
        return recompute;
    }

    private void setChildrenConstraints() {
        boolean isInEditMode = isInEditMode();
        int count = getChildCount();
        for (int i = 0; i < count; i++) {
            ConstraintWidget widget = getViewWidget(getChildAt(i));
            if (widget != null) {
                widget.reset();
            }
        }
        if (isInEditMode) {
            for (int i2 = 0; i2 < count; i2++) {
                View view = getChildAt(i2);
                try {
                    String IdAsString = getResources().getResourceName(view.getId());
                    setDesignInformation(0, IdAsString, Integer.valueOf(view.getId()));
                    int slashIndex = IdAsString.indexOf(47);
                    if (slashIndex != -1) {
                        IdAsString = IdAsString.substring(slashIndex + 1);
                    }
                    getTargetWidget(view.getId()).setDebugName(IdAsString);
                } catch (Resources.NotFoundException e) {
                }
            }
        }
        if (this.mConstraintSetId != -1) {
            for (int i3 = 0; i3 < count; i3++) {
                View child = getChildAt(i3);
                if (child.getId() == this.mConstraintSetId && (child instanceof Constraints)) {
                    this.mConstraintSet = ((Constraints) child).getConstraintSet();
                }
            }
        }
        ConstraintSet constraintSet = this.mConstraintSet;
        if (constraintSet != null) {
            constraintSet.applyToInternal(this, USE_CONSTRAINTS_HELPER);
        }
        this.mLayoutWidget.removeAllChildren();
        int helperCount = this.mConstraintHelpers.size();
        if (helperCount > 0) {
            for (int i4 = 0; i4 < helperCount; i4++) {
                this.mConstraintHelpers.get(i4).updatePreLayout(this);
            }
        }
        for (int i5 = 0; i5 < count; i5++) {
            View child2 = getChildAt(i5);
            if (child2 instanceof Placeholder) {
                ((Placeholder) child2).updatePreLayout(this);
            }
        }
        this.mTempMapIdToWidget.clear();
        this.mTempMapIdToWidget.put(0, this.mLayoutWidget);
        this.mTempMapIdToWidget.put(getId(), this.mLayoutWidget);
        for (int i6 = 0; i6 < count; i6++) {
            View child3 = getChildAt(i6);
            this.mTempMapIdToWidget.put(child3.getId(), getViewWidget(child3));
        }
        for (int i7 = 0; i7 < count; i7++) {
            View child4 = getChildAt(i7);
            ConstraintWidget widget2 = getViewWidget(child4);
            if (widget2 != null) {
                this.mLayoutWidget.add(widget2);
                applyConstraintsFromLayoutParams(isInEditMode, child4, widget2, (LayoutParams) child4.getLayoutParams(), this.mTempMapIdToWidget);
            }
        }
    }

    /* access modifiers changed from: protected */
    /* JADX WARNING: Removed duplicated region for block: B:34:0x00a5  */
    /* JADX WARNING: Removed duplicated region for block: B:35:0x00b1  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void applyConstraintsFromLayoutParams(boolean r22, android.view.View r23, androidx.constraintlayout.solver.widgets.ConstraintWidget r24, androidx.constraintlayout.widget.ConstraintLayout.LayoutParams r25, android.util.SparseArray<androidx.constraintlayout.solver.widgets.ConstraintWidget> r26) {
        /*
            r21 = this;
            r0 = r21
            r1 = r23
            r8 = r24
            r9 = r25
            r10 = r26
            r25.validate()
            r11 = 0
            r9.helped = r11
            int r2 = r23.getVisibility()
            r8.setVisibility(r2)
            boolean r2 = r9.isInPlaceholder
            r12 = 1
            if (r2 == 0) goto L_0x0024
            r8.setInPlaceholder(r12)
            r2 = 8
            r8.setVisibility(r2)
        L_0x0024:
            r8.setCompanionWidget(r1)
            boolean r2 = r1 instanceof androidx.constraintlayout.widget.ConstraintHelper
            if (r2 == 0) goto L_0x0037
            r2 = r1
            androidx.constraintlayout.widget.ConstraintHelper r2 = (androidx.constraintlayout.widget.ConstraintHelper) r2
            androidx.constraintlayout.solver.widgets.ConstraintWidgetContainer r3 = r0.mLayoutWidget
            boolean r3 = r3.isRtl()
            r2.resolveRtl(r8, r3)
        L_0x0037:
            boolean r2 = r9.isGuideline
            r3 = 17
            r13 = -1
            if (r2 == 0) goto L_0x0068
            r2 = r8
            androidx.constraintlayout.solver.widgets.Guideline r2 = (androidx.constraintlayout.solver.widgets.Guideline) r2
            int r4 = r9.resolvedGuideBegin
            int r5 = r9.resolvedGuideEnd
            float r6 = r9.resolvedGuidePercent
            int r7 = android.os.Build.VERSION.SDK_INT
            if (r7 >= r3) goto L_0x0051
            int r4 = r9.guideBegin
            int r5 = r9.guideEnd
            float r6 = r9.guidePercent
        L_0x0051:
            r3 = -1082130432(0xffffffffbf800000, float:-1.0)
            int r3 = (r6 > r3 ? 1 : (r6 == r3 ? 0 : -1))
            if (r3 == 0) goto L_0x005b
            r2.setGuidePercent((float) r6)
            goto L_0x0066
        L_0x005b:
            if (r4 == r13) goto L_0x0061
            r2.setGuideBegin(r4)
            goto L_0x0066
        L_0x0061:
            if (r5 == r13) goto L_0x0066
            r2.setGuideEnd(r5)
        L_0x0066:
            goto L_0x032e
        L_0x0068:
            int r2 = r9.resolvedLeftToLeft
            int r4 = r9.resolvedLeftToRight
            int r5 = r9.resolvedRightToLeft
            int r6 = r9.resolvedRightToRight
            int r7 = r9.resolveGoneLeftMargin
            int r14 = r9.resolveGoneRightMargin
            float r15 = r9.resolvedHorizontalBias
            int r11 = android.os.Build.VERSION.SDK_INT
            if (r11 >= r3) goto L_0x00cb
            int r2 = r9.leftToLeft
            int r3 = r9.leftToRight
            int r5 = r9.rightToLeft
            int r6 = r9.rightToRight
            int r7 = r9.goneLeftMargin
            int r14 = r9.goneRightMargin
            float r15 = r9.horizontalBias
            if (r2 != r13) goto L_0x009c
            if (r3 != r13) goto L_0x009c
            int r4 = r9.startToStart
            if (r4 == r13) goto L_0x0094
            int r2 = r9.startToStart
            r4 = r3
            goto L_0x009d
        L_0x0094:
            int r4 = r9.startToEnd
            if (r4 == r13) goto L_0x009c
            int r3 = r9.startToEnd
            r4 = r3
            goto L_0x009d
        L_0x009c:
            r4 = r3
        L_0x009d:
            if (r5 != r13) goto L_0x00c1
            if (r6 != r13) goto L_0x00c1
            int r3 = r9.endToStart
            if (r3 == r13) goto L_0x00b1
            int r5 = r9.endToStart
            r11 = r2
            r16 = r7
            r17 = r14
            r14 = r4
            r7 = r6
            r6 = r15
            r15 = r5
            goto L_0x00d4
        L_0x00b1:
            int r3 = r9.endToEnd
            if (r3 == r13) goto L_0x00c1
            int r6 = r9.endToEnd
            r11 = r2
            r16 = r7
            r17 = r14
            r14 = r4
            r7 = r6
            r6 = r15
            r15 = r5
            goto L_0x00d4
        L_0x00c1:
            r11 = r2
            r16 = r7
            r17 = r14
            r14 = r4
            r7 = r6
            r6 = r15
            r15 = r5
            goto L_0x00d4
        L_0x00cb:
            r11 = r2
            r16 = r7
            r17 = r14
            r14 = r4
            r7 = r6
            r6 = r15
            r15 = r5
        L_0x00d4:
            int r2 = r9.circleConstraint
            if (r2 == r13) goto L_0x00ed
            int r2 = r9.circleConstraint
            java.lang.Object r2 = r10.get(r2)
            androidx.constraintlayout.solver.widgets.ConstraintWidget r2 = (androidx.constraintlayout.solver.widgets.ConstraintWidget) r2
            if (r2 == 0) goto L_0x00e9
            float r3 = r9.circleAngle
            int r4 = r9.circleRadius
            r8.connectCircularConstraint(r2, r3, r4)
        L_0x00e9:
            r2 = r6
            r12 = r7
            goto L_0x025b
        L_0x00ed:
            if (r11 == r13) goto L_0x0114
            java.lang.Object r2 = r10.get(r11)
            r18 = r2
            androidx.constraintlayout.solver.widgets.ConstraintWidget r18 = (androidx.constraintlayout.solver.widgets.ConstraintWidget) r18
            if (r18 == 0) goto L_0x0110
            androidx.constraintlayout.solver.widgets.ConstraintAnchor$Type r3 = androidx.constraintlayout.solver.widgets.ConstraintAnchor.Type.LEFT
            androidx.constraintlayout.solver.widgets.ConstraintAnchor$Type r5 = androidx.constraintlayout.solver.widgets.ConstraintAnchor.Type.LEFT
            int r4 = r9.leftMargin
            r2 = r24
            r19 = r4
            r4 = r18
            r20 = r6
            r6 = r19
            r12 = r7
            r7 = r16
            r2.immediateConnect(r3, r4, r5, r6, r7)
            goto L_0x0133
        L_0x0110:
            r20 = r6
            r12 = r7
            goto L_0x0133
        L_0x0114:
            r20 = r6
            r12 = r7
            if (r14 == r13) goto L_0x0133
            java.lang.Object r2 = r10.get(r14)
            r18 = r2
            androidx.constraintlayout.solver.widgets.ConstraintWidget r18 = (androidx.constraintlayout.solver.widgets.ConstraintWidget) r18
            if (r18 == 0) goto L_0x0134
            androidx.constraintlayout.solver.widgets.ConstraintAnchor$Type r3 = androidx.constraintlayout.solver.widgets.ConstraintAnchor.Type.LEFT
            androidx.constraintlayout.solver.widgets.ConstraintAnchor$Type r5 = androidx.constraintlayout.solver.widgets.ConstraintAnchor.Type.RIGHT
            int r6 = r9.leftMargin
            r2 = r24
            r4 = r18
            r7 = r16
            r2.immediateConnect(r3, r4, r5, r6, r7)
            goto L_0x0134
        L_0x0133:
        L_0x0134:
            if (r15 == r13) goto L_0x0150
            java.lang.Object r2 = r10.get(r15)
            r18 = r2
            androidx.constraintlayout.solver.widgets.ConstraintWidget r18 = (androidx.constraintlayout.solver.widgets.ConstraintWidget) r18
            if (r18 == 0) goto L_0x016c
            androidx.constraintlayout.solver.widgets.ConstraintAnchor$Type r3 = androidx.constraintlayout.solver.widgets.ConstraintAnchor.Type.RIGHT
            androidx.constraintlayout.solver.widgets.ConstraintAnchor$Type r5 = androidx.constraintlayout.solver.widgets.ConstraintAnchor.Type.LEFT
            int r6 = r9.rightMargin
            r2 = r24
            r4 = r18
            r7 = r17
            r2.immediateConnect(r3, r4, r5, r6, r7)
            goto L_0x016c
        L_0x0150:
            if (r12 == r13) goto L_0x016c
            java.lang.Object r2 = r10.get(r12)
            r18 = r2
            androidx.constraintlayout.solver.widgets.ConstraintWidget r18 = (androidx.constraintlayout.solver.widgets.ConstraintWidget) r18
            if (r18 == 0) goto L_0x016d
            androidx.constraintlayout.solver.widgets.ConstraintAnchor$Type r3 = androidx.constraintlayout.solver.widgets.ConstraintAnchor.Type.RIGHT
            androidx.constraintlayout.solver.widgets.ConstraintAnchor$Type r5 = androidx.constraintlayout.solver.widgets.ConstraintAnchor.Type.RIGHT
            int r6 = r9.rightMargin
            r2 = r24
            r4 = r18
            r7 = r17
            r2.immediateConnect(r3, r4, r5, r6, r7)
            goto L_0x016d
        L_0x016c:
        L_0x016d:
            int r2 = r9.topToTop
            if (r2 == r13) goto L_0x018d
            int r2 = r9.topToTop
            java.lang.Object r2 = r10.get(r2)
            r18 = r2
            androidx.constraintlayout.solver.widgets.ConstraintWidget r18 = (androidx.constraintlayout.solver.widgets.ConstraintWidget) r18
            if (r18 == 0) goto L_0x01ad
            androidx.constraintlayout.solver.widgets.ConstraintAnchor$Type r3 = androidx.constraintlayout.solver.widgets.ConstraintAnchor.Type.TOP
            androidx.constraintlayout.solver.widgets.ConstraintAnchor$Type r5 = androidx.constraintlayout.solver.widgets.ConstraintAnchor.Type.TOP
            int r6 = r9.topMargin
            int r7 = r9.goneTopMargin
            r2 = r24
            r4 = r18
            r2.immediateConnect(r3, r4, r5, r6, r7)
            goto L_0x01ad
        L_0x018d:
            int r2 = r9.topToBottom
            if (r2 == r13) goto L_0x01ad
            int r2 = r9.topToBottom
            java.lang.Object r2 = r10.get(r2)
            r18 = r2
            androidx.constraintlayout.solver.widgets.ConstraintWidget r18 = (androidx.constraintlayout.solver.widgets.ConstraintWidget) r18
            if (r18 == 0) goto L_0x01ae
            androidx.constraintlayout.solver.widgets.ConstraintAnchor$Type r3 = androidx.constraintlayout.solver.widgets.ConstraintAnchor.Type.TOP
            androidx.constraintlayout.solver.widgets.ConstraintAnchor$Type r5 = androidx.constraintlayout.solver.widgets.ConstraintAnchor.Type.BOTTOM
            int r6 = r9.topMargin
            int r7 = r9.goneTopMargin
            r2 = r24
            r4 = r18
            r2.immediateConnect(r3, r4, r5, r6, r7)
            goto L_0x01ae
        L_0x01ad:
        L_0x01ae:
            int r2 = r9.bottomToTop
            if (r2 == r13) goto L_0x01ce
            int r2 = r9.bottomToTop
            java.lang.Object r2 = r10.get(r2)
            r18 = r2
            androidx.constraintlayout.solver.widgets.ConstraintWidget r18 = (androidx.constraintlayout.solver.widgets.ConstraintWidget) r18
            if (r18 == 0) goto L_0x01ee
            androidx.constraintlayout.solver.widgets.ConstraintAnchor$Type r3 = androidx.constraintlayout.solver.widgets.ConstraintAnchor.Type.BOTTOM
            androidx.constraintlayout.solver.widgets.ConstraintAnchor$Type r5 = androidx.constraintlayout.solver.widgets.ConstraintAnchor.Type.TOP
            int r6 = r9.bottomMargin
            int r7 = r9.goneBottomMargin
            r2 = r24
            r4 = r18
            r2.immediateConnect(r3, r4, r5, r6, r7)
            goto L_0x01ee
        L_0x01ce:
            int r2 = r9.bottomToBottom
            if (r2 == r13) goto L_0x01ee
            int r2 = r9.bottomToBottom
            java.lang.Object r2 = r10.get(r2)
            r18 = r2
            androidx.constraintlayout.solver.widgets.ConstraintWidget r18 = (androidx.constraintlayout.solver.widgets.ConstraintWidget) r18
            if (r18 == 0) goto L_0x01ef
            androidx.constraintlayout.solver.widgets.ConstraintAnchor$Type r3 = androidx.constraintlayout.solver.widgets.ConstraintAnchor.Type.BOTTOM
            androidx.constraintlayout.solver.widgets.ConstraintAnchor$Type r5 = androidx.constraintlayout.solver.widgets.ConstraintAnchor.Type.BOTTOM
            int r6 = r9.bottomMargin
            int r7 = r9.goneBottomMargin
            r2 = r24
            r4 = r18
            r2.immediateConnect(r3, r4, r5, r6, r7)
            goto L_0x01ef
        L_0x01ee:
        L_0x01ef:
            int r2 = r9.baselineToBaseline
            if (r2 == r13) goto L_0x0246
            android.util.SparseArray<android.view.View> r2 = r0.mChildrenByIds
            int r3 = r9.baselineToBaseline
            java.lang.Object r2 = r2.get(r3)
            android.view.View r2 = (android.view.View) r2
            int r3 = r9.baselineToBaseline
            java.lang.Object r3 = r10.get(r3)
            androidx.constraintlayout.solver.widgets.ConstraintWidget r3 = (androidx.constraintlayout.solver.widgets.ConstraintWidget) r3
            if (r3 == 0) goto L_0x0246
            if (r2 == 0) goto L_0x0246
            android.view.ViewGroup$LayoutParams r4 = r2.getLayoutParams()
            boolean r4 = r4 instanceof androidx.constraintlayout.widget.ConstraintLayout.LayoutParams
            if (r4 == 0) goto L_0x0246
            android.view.ViewGroup$LayoutParams r4 = r2.getLayoutParams()
            androidx.constraintlayout.widget.ConstraintLayout$LayoutParams r4 = (androidx.constraintlayout.widget.ConstraintLayout.LayoutParams) r4
            r5 = 1
            r9.needsBaseline = r5
            r4.needsBaseline = r5
            androidx.constraintlayout.solver.widgets.ConstraintAnchor$Type r6 = androidx.constraintlayout.solver.widgets.ConstraintAnchor.Type.BASELINE
            androidx.constraintlayout.solver.widgets.ConstraintAnchor r6 = r8.getAnchor(r6)
            androidx.constraintlayout.solver.widgets.ConstraintAnchor$Type r7 = androidx.constraintlayout.solver.widgets.ConstraintAnchor.Type.BASELINE
            androidx.constraintlayout.solver.widgets.ConstraintAnchor r7 = r3.getAnchor(r7)
            r0 = 0
            r6.connect(r7, r0, r13, r5)
            r8.setHasBaseline(r5)
            androidx.constraintlayout.solver.widgets.ConstraintWidget r0 = r4.widget
            r0.setHasBaseline(r5)
            androidx.constraintlayout.solver.widgets.ConstraintAnchor$Type r0 = androidx.constraintlayout.solver.widgets.ConstraintAnchor.Type.TOP
            androidx.constraintlayout.solver.widgets.ConstraintAnchor r0 = r8.getAnchor(r0)
            r0.reset()
            androidx.constraintlayout.solver.widgets.ConstraintAnchor$Type r0 = androidx.constraintlayout.solver.widgets.ConstraintAnchor.Type.BOTTOM
            androidx.constraintlayout.solver.widgets.ConstraintAnchor r0 = r8.getAnchor(r0)
            r0.reset()
        L_0x0246:
            r0 = 0
            r2 = r20
            int r3 = (r2 > r0 ? 1 : (r2 == r0 ? 0 : -1))
            if (r3 < 0) goto L_0x0250
            r8.setHorizontalBiasPercent(r2)
        L_0x0250:
            float r3 = r9.verticalBias
            int r0 = (r3 > r0 ? 1 : (r3 == r0 ? 0 : -1))
            if (r0 < 0) goto L_0x025b
            float r0 = r9.verticalBias
            r8.setVerticalBiasPercent(r0)
        L_0x025b:
            if (r22 == 0) goto L_0x026c
            int r0 = r9.editorAbsoluteX
            if (r0 != r13) goto L_0x0265
            int r0 = r9.editorAbsoluteY
            if (r0 == r13) goto L_0x026c
        L_0x0265:
            int r0 = r9.editorAbsoluteX
            int r3 = r9.editorAbsoluteY
            r8.setOrigin(r0, r3)
        L_0x026c:
            boolean r0 = r9.horizontalDimensionFixed
            r3 = -2
            if (r0 != 0) goto L_0x02a3
            int r0 = r9.width
            if (r0 != r13) goto L_0x0299
            boolean r0 = r9.constrainedWidth
            if (r0 == 0) goto L_0x027f
            androidx.constraintlayout.solver.widgets.ConstraintWidget$DimensionBehaviour r0 = androidx.constraintlayout.solver.widgets.ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT
            r8.setHorizontalDimensionBehaviour(r0)
            goto L_0x0284
        L_0x027f:
            androidx.constraintlayout.solver.widgets.ConstraintWidget$DimensionBehaviour r0 = androidx.constraintlayout.solver.widgets.ConstraintWidget.DimensionBehaviour.MATCH_PARENT
            r8.setHorizontalDimensionBehaviour(r0)
        L_0x0284:
            androidx.constraintlayout.solver.widgets.ConstraintAnchor$Type r0 = androidx.constraintlayout.solver.widgets.ConstraintAnchor.Type.LEFT
            androidx.constraintlayout.solver.widgets.ConstraintAnchor r0 = r8.getAnchor(r0)
            int r4 = r9.leftMargin
            r0.mMargin = r4
            androidx.constraintlayout.solver.widgets.ConstraintAnchor$Type r0 = androidx.constraintlayout.solver.widgets.ConstraintAnchor.Type.RIGHT
            androidx.constraintlayout.solver.widgets.ConstraintAnchor r0 = r8.getAnchor(r0)
            int r4 = r9.rightMargin
            r0.mMargin = r4
            goto L_0x02b6
        L_0x0299:
            androidx.constraintlayout.solver.widgets.ConstraintWidget$DimensionBehaviour r0 = androidx.constraintlayout.solver.widgets.ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT
            r8.setHorizontalDimensionBehaviour(r0)
            r0 = 0
            r8.setWidth(r0)
            goto L_0x02b6
        L_0x02a3:
            androidx.constraintlayout.solver.widgets.ConstraintWidget$DimensionBehaviour r0 = androidx.constraintlayout.solver.widgets.ConstraintWidget.DimensionBehaviour.FIXED
            r8.setHorizontalDimensionBehaviour(r0)
            int r0 = r9.width
            r8.setWidth(r0)
            int r0 = r9.width
            if (r0 != r3) goto L_0x02b6
            androidx.constraintlayout.solver.widgets.ConstraintWidget$DimensionBehaviour r0 = androidx.constraintlayout.solver.widgets.ConstraintWidget.DimensionBehaviour.WRAP_CONTENT
            r8.setHorizontalDimensionBehaviour(r0)
        L_0x02b6:
            boolean r0 = r9.verticalDimensionFixed
            if (r0 != 0) goto L_0x02ec
            int r0 = r9.height
            if (r0 != r13) goto L_0x02e2
            boolean r0 = r9.constrainedHeight
            if (r0 == 0) goto L_0x02c8
            androidx.constraintlayout.solver.widgets.ConstraintWidget$DimensionBehaviour r0 = androidx.constraintlayout.solver.widgets.ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT
            r8.setVerticalDimensionBehaviour(r0)
            goto L_0x02cd
        L_0x02c8:
            androidx.constraintlayout.solver.widgets.ConstraintWidget$DimensionBehaviour r0 = androidx.constraintlayout.solver.widgets.ConstraintWidget.DimensionBehaviour.MATCH_PARENT
            r8.setVerticalDimensionBehaviour(r0)
        L_0x02cd:
            androidx.constraintlayout.solver.widgets.ConstraintAnchor$Type r0 = androidx.constraintlayout.solver.widgets.ConstraintAnchor.Type.TOP
            androidx.constraintlayout.solver.widgets.ConstraintAnchor r0 = r8.getAnchor(r0)
            int r3 = r9.topMargin
            r0.mMargin = r3
            androidx.constraintlayout.solver.widgets.ConstraintAnchor$Type r0 = androidx.constraintlayout.solver.widgets.ConstraintAnchor.Type.BOTTOM
            androidx.constraintlayout.solver.widgets.ConstraintAnchor r0 = r8.getAnchor(r0)
            int r3 = r9.bottomMargin
            r0.mMargin = r3
            goto L_0x02ff
        L_0x02e2:
            androidx.constraintlayout.solver.widgets.ConstraintWidget$DimensionBehaviour r0 = androidx.constraintlayout.solver.widgets.ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT
            r8.setVerticalDimensionBehaviour(r0)
            r0 = 0
            r8.setHeight(r0)
            goto L_0x02ff
        L_0x02ec:
            androidx.constraintlayout.solver.widgets.ConstraintWidget$DimensionBehaviour r0 = androidx.constraintlayout.solver.widgets.ConstraintWidget.DimensionBehaviour.FIXED
            r8.setVerticalDimensionBehaviour(r0)
            int r0 = r9.height
            r8.setHeight(r0)
            int r0 = r9.height
            if (r0 != r3) goto L_0x02ff
            androidx.constraintlayout.solver.widgets.ConstraintWidget$DimensionBehaviour r0 = androidx.constraintlayout.solver.widgets.ConstraintWidget.DimensionBehaviour.WRAP_CONTENT
            r8.setVerticalDimensionBehaviour(r0)
        L_0x02ff:
            java.lang.String r0 = r9.dimensionRatio
            r8.setDimensionRatio(r0)
            float r0 = r9.horizontalWeight
            r8.setHorizontalWeight(r0)
            float r0 = r9.verticalWeight
            r8.setVerticalWeight(r0)
            int r0 = r9.horizontalChainStyle
            r8.setHorizontalChainStyle(r0)
            int r0 = r9.verticalChainStyle
            r8.setVerticalChainStyle(r0)
            int r0 = r9.matchConstraintDefaultWidth
            int r3 = r9.matchConstraintMinWidth
            int r4 = r9.matchConstraintMaxWidth
            float r5 = r9.matchConstraintPercentWidth
            r8.setHorizontalMatchStyle(r0, r3, r4, r5)
            int r0 = r9.matchConstraintDefaultHeight
            int r3 = r9.matchConstraintMinHeight
            int r4 = r9.matchConstraintMaxHeight
            float r5 = r9.matchConstraintPercentHeight
            r8.setVerticalMatchStyle(r0, r3, r4, r5)
        L_0x032e:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.constraintlayout.widget.ConstraintLayout.applyConstraintsFromLayoutParams(boolean, android.view.View, androidx.constraintlayout.solver.widgets.ConstraintWidget, androidx.constraintlayout.widget.ConstraintLayout$LayoutParams, android.util.SparseArray):void");
    }

    private final ConstraintWidget getTargetWidget(int id) {
        if (id == 0) {
            return this.mLayoutWidget;
        }
        View view = this.mChildrenByIds.get(id);
        if (view == null && (view = findViewById(id)) != null && view != this && view.getParent() == this) {
            onViewAdded(view);
        }
        if (view == this) {
            return this.mLayoutWidget;
        }
        if (view == null) {
            return null;
        }
        return ((LayoutParams) view.getLayoutParams()).widget;
    }

    public final ConstraintWidget getViewWidget(View view) {
        if (view == this) {
            return this.mLayoutWidget;
        }
        if (view == null) {
            return null;
        }
        return ((LayoutParams) view.getLayoutParams()).widget;
    }

    public void fillMetrics(Metrics metrics) {
        this.mMetrics = metrics;
        this.mLayoutWidget.fillMetrics(metrics);
    }

    /* access modifiers changed from: protected */
    public void resolveSystem(ConstraintWidgetContainer layout, int optimizationLevel, int widthMeasureSpec, int heightMeasureSpec) {
        int paddingX;
        int paddingX2;
        int widthMode = View.MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = View.MeasureSpec.getSize(widthMeasureSpec);
        int heightMode = View.MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = View.MeasureSpec.getSize(heightMeasureSpec);
        int paddingY = Math.max(0, getPaddingTop());
        int paddingBottom = Math.max(0, getPaddingBottom());
        int paddingHeight = paddingY + paddingBottom;
        int paddingWidth = getPaddingWidth();
        this.mMeasurer.captureLayoutInfos(widthMeasureSpec, heightMeasureSpec, paddingY, paddingBottom, paddingWidth, paddingHeight);
        if (Build.VERSION.SDK_INT >= 17) {
            int paddingStart = Math.max(0, getPaddingStart());
            int paddingEnd = Math.max(0, getPaddingEnd());
            if (paddingStart <= 0 && paddingEnd <= 0) {
                paddingX2 = Math.max(0, getPaddingLeft());
            } else if (isRtl() != 0) {
                paddingX2 = paddingEnd;
            } else {
                paddingX2 = paddingStart;
            }
            paddingX = paddingX2;
        } else {
            paddingX = Math.max(0, getPaddingLeft());
        }
        int widthSize2 = widthSize - paddingWidth;
        int heightSize2 = heightSize - paddingHeight;
        setSelfDimensionBehaviour(layout, widthMode, widthSize2, heightMode, heightSize2);
        layout.measure(optimizationLevel, widthMode, widthSize2, heightMode, heightSize2, this.mLastMeasureWidth, this.mLastMeasureHeight, paddingX, paddingY);
    }

    /* access modifiers changed from: protected */
    public void resolveMeasuredDimension(int widthMeasureSpec, int heightMeasureSpec, int measuredWidth, int measuredHeight, boolean isWidthMeasuredTooSmall, boolean isHeightMeasuredTooSmall) {
        int heightPadding = this.mMeasurer.paddingHeight;
        int androidLayoutWidth = measuredWidth + this.mMeasurer.paddingWidth;
        int androidLayoutHeight = measuredHeight + heightPadding;
        if (Build.VERSION.SDK_INT >= 11) {
            int resolvedWidthSize = resolveSizeAndState(androidLayoutWidth, widthMeasureSpec, 0);
            int resolvedHeightSize = resolveSizeAndState(androidLayoutHeight, heightMeasureSpec, 0 << 16);
            int resolvedWidthSize2 = resolvedWidthSize & ViewCompat.MEASURED_SIZE_MASK;
            int resolvedHeightSize2 = resolvedHeightSize & ViewCompat.MEASURED_SIZE_MASK;
            int resolvedWidthSize3 = Math.min(this.mMaxWidth, resolvedWidthSize2);
            int resolvedHeightSize3 = Math.min(this.mMaxHeight, resolvedHeightSize2);
            if (isWidthMeasuredTooSmall) {
                resolvedWidthSize3 |= 16777216;
            }
            if (isHeightMeasuredTooSmall) {
                resolvedHeightSize3 |= 16777216;
            }
            setMeasuredDimension(resolvedWidthSize3, resolvedHeightSize3);
            this.mLastMeasureWidth = resolvedWidthSize3;
            this.mLastMeasureHeight = resolvedHeightSize3;
            return;
        }
        setMeasuredDimension(androidLayoutWidth, androidLayoutHeight);
        this.mLastMeasureWidth = androidLayoutWidth;
        this.mLastMeasureHeight = androidLayoutHeight;
    }

    /* access modifiers changed from: protected */
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        this.mOnMeasureWidthMeasureSpec = widthMeasureSpec;
        this.mOnMeasureHeightMeasureSpec = heightMeasureSpec;
        this.mLayoutWidget.setRtl(isRtl());
        if (this.mDirtyHierarchy) {
            this.mDirtyHierarchy = false;
            if (updateHierarchy()) {
                this.mLayoutWidget.updateHierarchy();
            }
        }
        resolveSystem(this.mLayoutWidget, this.mOptimizationLevel, widthMeasureSpec, heightMeasureSpec);
        resolveMeasuredDimension(widthMeasureSpec, heightMeasureSpec, this.mLayoutWidget.getWidth(), this.mLayoutWidget.getHeight(), this.mLayoutWidget.isWidthMeasuredTooSmall(), this.mLayoutWidget.isHeightMeasuredTooSmall());
    }

    /* access modifiers changed from: protected */
    public boolean isRtl() {
        if (Build.VERSION.SDK_INT < 17) {
            return false;
        }
        if (!((getContext().getApplicationInfo().flags & 4194304) != 0) || 1 != getLayoutDirection()) {
            return false;
        }
        return USE_CONSTRAINTS_HELPER;
    }

    private int getPaddingWidth() {
        int widthPadding = Math.max(0, getPaddingLeft()) + Math.max(0, getPaddingRight());
        int rtlPadding = 0;
        if (Build.VERSION.SDK_INT >= 17) {
            rtlPadding = Math.max(0, getPaddingStart()) + Math.max(0, getPaddingEnd());
        }
        if (rtlPadding > 0) {
            return rtlPadding;
        }
        return widthPadding;
    }

    /* access modifiers changed from: protected */
    public void setSelfDimensionBehaviour(ConstraintWidgetContainer layout, int widthMode, int widthSize, int heightMode, int heightSize) {
        ConstraintWidgetContainer constraintWidgetContainer = layout;
        int i = widthMode;
        int i2 = heightMode;
        int heightPadding = this.mMeasurer.paddingHeight;
        int widthPadding = this.mMeasurer.paddingWidth;
        ConstraintWidget.DimensionBehaviour widthBehaviour = ConstraintWidget.DimensionBehaviour.FIXED;
        ConstraintWidget.DimensionBehaviour heightBehaviour = ConstraintWidget.DimensionBehaviour.FIXED;
        int desiredWidth = 0;
        int desiredHeight = 0;
        int childCount = getChildCount();
        if (i == Integer.MIN_VALUE) {
            int i3 = widthSize;
            widthBehaviour = ConstraintWidget.DimensionBehaviour.WRAP_CONTENT;
            desiredWidth = widthSize;
            if (childCount == 0) {
                desiredWidth = Math.max(0, this.mMinWidth);
            }
        } else if (i == 0) {
            int i4 = widthSize;
            widthBehaviour = ConstraintWidget.DimensionBehaviour.WRAP_CONTENT;
            if (childCount == 0) {
                desiredWidth = Math.max(0, this.mMinWidth);
            }
        } else if (i != 1073741824) {
            int i5 = widthSize;
        } else {
            desiredWidth = Math.min(this.mMaxWidth - widthPadding, widthSize);
        }
        if (i2 == Integer.MIN_VALUE) {
            int i6 = heightSize;
            heightBehaviour = ConstraintWidget.DimensionBehaviour.WRAP_CONTENT;
            desiredHeight = heightSize;
            if (childCount == 0) {
                desiredHeight = Math.max(0, this.mMinHeight);
            }
        } else if (i2 == 0) {
            int i7 = heightSize;
            heightBehaviour = ConstraintWidget.DimensionBehaviour.WRAP_CONTENT;
            if (childCount == 0) {
                desiredHeight = Math.max(0, this.mMinHeight);
            }
        } else if (i2 != 1073741824) {
            int i8 = heightSize;
        } else {
            desiredHeight = Math.min(this.mMaxHeight - heightPadding, heightSize);
        }
        if (!(desiredWidth == layout.getWidth() && desiredHeight == layout.getHeight())) {
            layout.invalidateMeasures();
        }
        constraintWidgetContainer.setX(0);
        constraintWidgetContainer.setY(0);
        constraintWidgetContainer.setMaxWidth(this.mMaxWidth - widthPadding);
        constraintWidgetContainer.setMaxHeight(this.mMaxHeight - heightPadding);
        constraintWidgetContainer.setMinWidth(0);
        constraintWidgetContainer.setMinHeight(0);
        constraintWidgetContainer.setHorizontalDimensionBehaviour(widthBehaviour);
        constraintWidgetContainer.setWidth(desiredWidth);
        constraintWidgetContainer.setVerticalDimensionBehaviour(heightBehaviour);
        constraintWidgetContainer.setHeight(desiredHeight);
        constraintWidgetContainer.setMinWidth(this.mMinWidth - widthPadding);
        constraintWidgetContainer.setMinHeight(this.mMinHeight - heightPadding);
    }

    public void setState(int id, int screenWidth, int screenHeight) {
        ConstraintLayoutStates constraintLayoutStates = this.mConstraintLayoutSpec;
        if (constraintLayoutStates != null) {
            constraintLayoutStates.updateConstraints(id, (float) screenWidth, (float) screenHeight);
        }
    }

    /* access modifiers changed from: protected */
    public void onLayout(boolean changed, int left, int top, int right, int bottom) {
        View content;
        int widgetsCount = getChildCount();
        boolean isInEditMode = isInEditMode();
        for (int i = 0; i < widgetsCount; i++) {
            View child = getChildAt(i);
            LayoutParams params = (LayoutParams) child.getLayoutParams();
            ConstraintWidget widget = params.widget;
            if ((child.getVisibility() != 8 || params.isGuideline || params.isHelper || params.isVirtualGroup || isInEditMode) && !params.isInPlaceholder) {
                int l = widget.getX();
                int t = widget.getY();
                int r = widget.getWidth() + l;
                int b = widget.getHeight() + t;
                child.layout(l, t, r, b);
                if ((child instanceof Placeholder) && (content = ((Placeholder) child).getContent()) != null) {
                    content.setVisibility(0);
                    content.layout(l, t, r, b);
                }
            }
        }
        int helperCount = this.mConstraintHelpers.size();
        if (helperCount > 0) {
            for (int i2 = 0; i2 < helperCount; i2++) {
                this.mConstraintHelpers.get(i2).updatePostLayout(this);
            }
        }
    }

    public void setOptimizationLevel(int level) {
        this.mOptimizationLevel = level;
        this.mLayoutWidget.setOptimizationLevel(level);
    }

    public int getOptimizationLevel() {
        return this.mLayoutWidget.getOptimizationLevel();
    }

    public LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new LayoutParams(getContext(), attrs);
    }

    /* access modifiers changed from: protected */
    public LayoutParams generateDefaultLayoutParams() {
        return new LayoutParams(-2, -2);
    }

    /* access modifiers changed from: protected */
    public ViewGroup.LayoutParams generateLayoutParams(ViewGroup.LayoutParams p) {
        return new LayoutParams(p);
    }

    /* access modifiers changed from: protected */
    public boolean checkLayoutParams(ViewGroup.LayoutParams p) {
        return p instanceof LayoutParams;
    }

    public void setConstraintSet(ConstraintSet set) {
        this.mConstraintSet = set;
    }

    public View getViewById(int id) {
        return this.mChildrenByIds.get(id);
    }

    /* access modifiers changed from: protected */
    public void dispatchDraw(Canvas canvas) {
        float ch;
        float cw;
        int count;
        int helperCount;
        ConstraintLayout constraintLayout = this;
        ArrayList<ConstraintHelper> arrayList = constraintLayout.mConstraintHelpers;
        if (arrayList != null && (helperCount = arrayList.size()) > 0) {
            for (int i = 0; i < helperCount; i++) {
                constraintLayout.mConstraintHelpers.get(i).updatePreDraw(constraintLayout);
            }
        }
        super.dispatchDraw(canvas);
        if (isInEditMode()) {
            int count2 = getChildCount();
            float cw2 = (float) getWidth();
            float ch2 = (float) getHeight();
            int i2 = 0;
            while (i2 < count2) {
                View child = constraintLayout.getChildAt(i2);
                if (child.getVisibility() == 8) {
                    count = count2;
                    cw = cw2;
                    ch = ch2;
                } else {
                    Object tag = child.getTag();
                    if (tag == null || !(tag instanceof String)) {
                        count = count2;
                        cw = cw2;
                        ch = ch2;
                    } else {
                        String[] split = ((String) tag).split(",");
                        if (split.length == 4) {
                            int x = Integer.parseInt(split[0]);
                            int y = Integer.parseInt(split[1]);
                            int x2 = (int) ((((float) x) / 1080.0f) * cw2);
                            int y2 = (int) ((((float) y) / 1920.0f) * ch2);
                            int w = (int) ((((float) Integer.parseInt(split[2])) / 1080.0f) * cw2);
                            int h = (int) ((((float) Integer.parseInt(split[3])) / 1920.0f) * ch2);
                            Paint paint = new Paint();
                            paint.setColor(SupportMenu.CATEGORY_MASK);
                            count = count2;
                            cw = cw2;
                            ch = ch2;
                            Canvas canvas2 = canvas;
                            Paint paint2 = paint;
                            canvas2.drawLine((float) x2, (float) y2, (float) (x2 + w), (float) y2, paint2);
                            canvas2.drawLine((float) (x2 + w), (float) y2, (float) (x2 + w), (float) (y2 + h), paint2);
                            canvas2.drawLine((float) (x2 + w), (float) (y2 + h), (float) x2, (float) (y2 + h), paint2);
                            canvas2.drawLine((float) x2, (float) (y2 + h), (float) x2, (float) y2, paint2);
                            paint.setColor(-16711936);
                            canvas2.drawLine((float) x2, (float) y2, (float) (x2 + w), (float) (y2 + h), paint2);
                            canvas2.drawLine((float) x2, (float) (y2 + h), (float) (x2 + w), (float) y2, paint2);
                        } else {
                            count = count2;
                            cw = cw2;
                            ch = ch2;
                        }
                    }
                }
                i2++;
                constraintLayout = this;
                count2 = count;
                cw2 = cw;
                ch2 = ch;
            }
            float f = cw2;
            float f2 = ch2;
        }
    }

    public void setOnConstraintsChanged(ConstraintsChangedListener constraintsChangedListener) {
        this.mConstraintsChangedListener = constraintsChangedListener;
        ConstraintLayoutStates constraintLayoutStates = this.mConstraintLayoutSpec;
        if (constraintLayoutStates != null) {
            constraintLayoutStates.setOnConstraintsChanged(constraintsChangedListener);
        }
    }

    public void loadLayoutDescription(int layoutDescription) {
        if (layoutDescription != 0) {
            try {
                this.mConstraintLayoutSpec = new ConstraintLayoutStates(getContext(), this, layoutDescription);
            } catch (Resources.NotFoundException e) {
                this.mConstraintLayoutSpec = null;
            }
        } else {
            this.mConstraintLayoutSpec = null;
        }
    }

    public static class LayoutParams extends ViewGroup.MarginLayoutParams {
        public static final int BASELINE = 5;
        public static final int BOTTOM = 4;
        public static final int CHAIN_PACKED = 2;
        public static final int CHAIN_SPREAD = 0;
        public static final int CHAIN_SPREAD_INSIDE = 1;
        public static final int END = 7;
        public static final int HORIZONTAL = 0;
        public static final int LEFT = 1;
        public static final int MATCH_CONSTRAINT = 0;
        public static final int MATCH_CONSTRAINT_PERCENT = 2;
        public static final int MATCH_CONSTRAINT_SPREAD = 0;
        public static final int MATCH_CONSTRAINT_WRAP = 1;
        public static final int PARENT_ID = 0;
        public static final int RIGHT = 2;
        public static final int START = 6;
        public static final int TOP = 3;
        public static final int UNSET = -1;
        public static final int VERTICAL = 1;
        public int baselineToBaseline;
        public int bottomToBottom;
        public int bottomToTop;
        public float circleAngle;
        public int circleConstraint;
        public int circleRadius;
        public boolean constrainedHeight;
        public boolean constrainedWidth;
        public String constraintTag;
        public String dimensionRatio;
        int dimensionRatioSide;
        float dimensionRatioValue;
        public int editorAbsoluteX;
        public int editorAbsoluteY;
        public int endToEnd;
        public int endToStart;
        public int goneBottomMargin;
        public int goneEndMargin;
        public int goneLeftMargin;
        public int goneRightMargin;
        public int goneStartMargin;
        public int goneTopMargin;
        public int guideBegin;
        public int guideEnd;
        public float guidePercent;
        public boolean helped;
        public float horizontalBias;
        public int horizontalChainStyle;
        boolean horizontalDimensionFixed;
        public float horizontalWeight;
        boolean isGuideline;
        boolean isHelper;
        boolean isInPlaceholder;
        boolean isVirtualGroup;
        public int leftToLeft;
        public int leftToRight;
        public int matchConstraintDefaultHeight;
        public int matchConstraintDefaultWidth;
        public int matchConstraintMaxHeight;
        public int matchConstraintMaxWidth;
        public int matchConstraintMinHeight;
        public int matchConstraintMinWidth;
        public float matchConstraintPercentHeight;
        public float matchConstraintPercentWidth;
        boolean needsBaseline;
        public int orientation;
        int resolveGoneLeftMargin;
        int resolveGoneRightMargin;
        int resolvedGuideBegin;
        int resolvedGuideEnd;
        float resolvedGuidePercent;
        float resolvedHorizontalBias;
        int resolvedLeftToLeft;
        int resolvedLeftToRight;
        int resolvedRightToLeft;
        int resolvedRightToRight;
        public int rightToLeft;
        public int rightToRight;
        public int startToEnd;
        public int startToStart;
        public int topToBottom;
        public int topToTop;
        public float verticalBias;
        public int verticalChainStyle;
        boolean verticalDimensionFixed;
        public float verticalWeight;
        ConstraintWidget widget;

        public ConstraintWidget getConstraintWidget() {
            return this.widget;
        }

        public void setWidgetDebugName(String text) {
            this.widget.setDebugName(text);
        }

        public void reset() {
            ConstraintWidget constraintWidget = this.widget;
            if (constraintWidget != null) {
                constraintWidget.reset();
            }
        }

        public LayoutParams(LayoutParams source) {
            super(source);
            this.guideBegin = -1;
            this.guideEnd = -1;
            this.guidePercent = -1.0f;
            this.leftToLeft = -1;
            this.leftToRight = -1;
            this.rightToLeft = -1;
            this.rightToRight = -1;
            this.topToTop = -1;
            this.topToBottom = -1;
            this.bottomToTop = -1;
            this.bottomToBottom = -1;
            this.baselineToBaseline = -1;
            this.circleConstraint = -1;
            this.circleRadius = 0;
            this.circleAngle = 0.0f;
            this.startToEnd = -1;
            this.startToStart = -1;
            this.endToStart = -1;
            this.endToEnd = -1;
            this.goneLeftMargin = -1;
            this.goneTopMargin = -1;
            this.goneRightMargin = -1;
            this.goneBottomMargin = -1;
            this.goneStartMargin = -1;
            this.goneEndMargin = -1;
            this.horizontalBias = 0.5f;
            this.verticalBias = 0.5f;
            this.dimensionRatio = null;
            this.dimensionRatioValue = 0.0f;
            this.dimensionRatioSide = 1;
            this.horizontalWeight = -1.0f;
            this.verticalWeight = -1.0f;
            this.horizontalChainStyle = 0;
            this.verticalChainStyle = 0;
            this.matchConstraintDefaultWidth = 0;
            this.matchConstraintDefaultHeight = 0;
            this.matchConstraintMinWidth = 0;
            this.matchConstraintMinHeight = 0;
            this.matchConstraintMaxWidth = 0;
            this.matchConstraintMaxHeight = 0;
            this.matchConstraintPercentWidth = 1.0f;
            this.matchConstraintPercentHeight = 1.0f;
            this.editorAbsoluteX = -1;
            this.editorAbsoluteY = -1;
            this.orientation = -1;
            this.constrainedWidth = false;
            this.constrainedHeight = false;
            this.constraintTag = null;
            this.horizontalDimensionFixed = ConstraintLayout.USE_CONSTRAINTS_HELPER;
            this.verticalDimensionFixed = ConstraintLayout.USE_CONSTRAINTS_HELPER;
            this.needsBaseline = false;
            this.isGuideline = false;
            this.isHelper = false;
            this.isInPlaceholder = false;
            this.isVirtualGroup = false;
            this.resolvedLeftToLeft = -1;
            this.resolvedLeftToRight = -1;
            this.resolvedRightToLeft = -1;
            this.resolvedRightToRight = -1;
            this.resolveGoneLeftMargin = -1;
            this.resolveGoneRightMargin = -1;
            this.resolvedHorizontalBias = 0.5f;
            this.widget = new ConstraintWidget();
            this.helped = false;
            this.guideBegin = source.guideBegin;
            this.guideEnd = source.guideEnd;
            this.guidePercent = source.guidePercent;
            this.leftToLeft = source.leftToLeft;
            this.leftToRight = source.leftToRight;
            this.rightToLeft = source.rightToLeft;
            this.rightToRight = source.rightToRight;
            this.topToTop = source.topToTop;
            this.topToBottom = source.topToBottom;
            this.bottomToTop = source.bottomToTop;
            this.bottomToBottom = source.bottomToBottom;
            this.baselineToBaseline = source.baselineToBaseline;
            this.circleConstraint = source.circleConstraint;
            this.circleRadius = source.circleRadius;
            this.circleAngle = source.circleAngle;
            this.startToEnd = source.startToEnd;
            this.startToStart = source.startToStart;
            this.endToStart = source.endToStart;
            this.endToEnd = source.endToEnd;
            this.goneLeftMargin = source.goneLeftMargin;
            this.goneTopMargin = source.goneTopMargin;
            this.goneRightMargin = source.goneRightMargin;
            this.goneBottomMargin = source.goneBottomMargin;
            this.goneStartMargin = source.goneStartMargin;
            this.goneEndMargin = source.goneEndMargin;
            this.horizontalBias = source.horizontalBias;
            this.verticalBias = source.verticalBias;
            this.dimensionRatio = source.dimensionRatio;
            this.dimensionRatioValue = source.dimensionRatioValue;
            this.dimensionRatioSide = source.dimensionRatioSide;
            this.horizontalWeight = source.horizontalWeight;
            this.verticalWeight = source.verticalWeight;
            this.horizontalChainStyle = source.horizontalChainStyle;
            this.verticalChainStyle = source.verticalChainStyle;
            this.constrainedWidth = source.constrainedWidth;
            this.constrainedHeight = source.constrainedHeight;
            this.matchConstraintDefaultWidth = source.matchConstraintDefaultWidth;
            this.matchConstraintDefaultHeight = source.matchConstraintDefaultHeight;
            this.matchConstraintMinWidth = source.matchConstraintMinWidth;
            this.matchConstraintMaxWidth = source.matchConstraintMaxWidth;
            this.matchConstraintMinHeight = source.matchConstraintMinHeight;
            this.matchConstraintMaxHeight = source.matchConstraintMaxHeight;
            this.matchConstraintPercentWidth = source.matchConstraintPercentWidth;
            this.matchConstraintPercentHeight = source.matchConstraintPercentHeight;
            this.editorAbsoluteX = source.editorAbsoluteX;
            this.editorAbsoluteY = source.editorAbsoluteY;
            this.orientation = source.orientation;
            this.horizontalDimensionFixed = source.horizontalDimensionFixed;
            this.verticalDimensionFixed = source.verticalDimensionFixed;
            this.needsBaseline = source.needsBaseline;
            this.isGuideline = source.isGuideline;
            this.resolvedLeftToLeft = source.resolvedLeftToLeft;
            this.resolvedLeftToRight = source.resolvedLeftToRight;
            this.resolvedRightToLeft = source.resolvedRightToLeft;
            this.resolvedRightToRight = source.resolvedRightToRight;
            this.resolveGoneLeftMargin = source.resolveGoneLeftMargin;
            this.resolveGoneRightMargin = source.resolveGoneRightMargin;
            this.resolvedHorizontalBias = source.resolvedHorizontalBias;
            this.constraintTag = source.constraintTag;
            this.widget = source.widget;
        }

        private static class Table {
            public static final int ANDROID_ORIENTATION = 1;
            public static final int LAYOUT_CONSTRAINED_HEIGHT = 28;
            public static final int LAYOUT_CONSTRAINED_WIDTH = 27;
            public static final int LAYOUT_CONSTRAINT_BASELINE_CREATOR = 43;
            public static final int LAYOUT_CONSTRAINT_BASELINE_TO_BASELINE_OF = 16;
            public static final int LAYOUT_CONSTRAINT_BOTTOM_CREATOR = 42;
            public static final int LAYOUT_CONSTRAINT_BOTTOM_TO_BOTTOM_OF = 15;
            public static final int LAYOUT_CONSTRAINT_BOTTOM_TO_TOP_OF = 14;
            public static final int LAYOUT_CONSTRAINT_CIRCLE = 2;
            public static final int LAYOUT_CONSTRAINT_CIRCLE_ANGLE = 4;
            public static final int LAYOUT_CONSTRAINT_CIRCLE_RADIUS = 3;
            public static final int LAYOUT_CONSTRAINT_DIMENSION_RATIO = 44;
            public static final int LAYOUT_CONSTRAINT_END_TO_END_OF = 20;
            public static final int LAYOUT_CONSTRAINT_END_TO_START_OF = 19;
            public static final int LAYOUT_CONSTRAINT_GUIDE_BEGIN = 5;
            public static final int LAYOUT_CONSTRAINT_GUIDE_END = 6;
            public static final int LAYOUT_CONSTRAINT_GUIDE_PERCENT = 7;
            public static final int LAYOUT_CONSTRAINT_HEIGHT_DEFAULT = 32;
            public static final int LAYOUT_CONSTRAINT_HEIGHT_MAX = 37;
            public static final int LAYOUT_CONSTRAINT_HEIGHT_MIN = 36;
            public static final int LAYOUT_CONSTRAINT_HEIGHT_PERCENT = 38;
            public static final int LAYOUT_CONSTRAINT_HORIZONTAL_BIAS = 29;
            public static final int LAYOUT_CONSTRAINT_HORIZONTAL_CHAINSTYLE = 47;
            public static final int LAYOUT_CONSTRAINT_HORIZONTAL_WEIGHT = 45;
            public static final int LAYOUT_CONSTRAINT_LEFT_CREATOR = 39;
            public static final int LAYOUT_CONSTRAINT_LEFT_TO_LEFT_OF = 8;
            public static final int LAYOUT_CONSTRAINT_LEFT_TO_RIGHT_OF = 9;
            public static final int LAYOUT_CONSTRAINT_RIGHT_CREATOR = 41;
            public static final int LAYOUT_CONSTRAINT_RIGHT_TO_LEFT_OF = 10;
            public static final int LAYOUT_CONSTRAINT_RIGHT_TO_RIGHT_OF = 11;
            public static final int LAYOUT_CONSTRAINT_START_TO_END_OF = 17;
            public static final int LAYOUT_CONSTRAINT_START_TO_START_OF = 18;
            public static final int LAYOUT_CONSTRAINT_TAG = 51;
            public static final int LAYOUT_CONSTRAINT_TOP_CREATOR = 40;
            public static final int LAYOUT_CONSTRAINT_TOP_TO_BOTTOM_OF = 13;
            public static final int LAYOUT_CONSTRAINT_TOP_TO_TOP_OF = 12;
            public static final int LAYOUT_CONSTRAINT_VERTICAL_BIAS = 30;
            public static final int LAYOUT_CONSTRAINT_VERTICAL_CHAINSTYLE = 48;
            public static final int LAYOUT_CONSTRAINT_VERTICAL_WEIGHT = 46;
            public static final int LAYOUT_CONSTRAINT_WIDTH_DEFAULT = 31;
            public static final int LAYOUT_CONSTRAINT_WIDTH_MAX = 34;
            public static final int LAYOUT_CONSTRAINT_WIDTH_MIN = 33;
            public static final int LAYOUT_CONSTRAINT_WIDTH_PERCENT = 35;
            public static final int LAYOUT_EDITOR_ABSOLUTEX = 49;
            public static final int LAYOUT_EDITOR_ABSOLUTEY = 50;
            public static final int LAYOUT_GONE_MARGIN_BOTTOM = 24;
            public static final int LAYOUT_GONE_MARGIN_END = 26;
            public static final int LAYOUT_GONE_MARGIN_LEFT = 21;
            public static final int LAYOUT_GONE_MARGIN_RIGHT = 23;
            public static final int LAYOUT_GONE_MARGIN_START = 25;
            public static final int LAYOUT_GONE_MARGIN_TOP = 22;
            public static final int UNUSED = 0;
            public static final SparseIntArray map;

            private Table() {
            }

            static {
                SparseIntArray sparseIntArray = new SparseIntArray();
                map = sparseIntArray;
                sparseIntArray.append(C0251R.styleable.ConstraintLayout_Layout_layout_constraintLeft_toLeftOf, 8);
                map.append(C0251R.styleable.ConstraintLayout_Layout_layout_constraintLeft_toRightOf, 9);
                map.append(C0251R.styleable.ConstraintLayout_Layout_layout_constraintRight_toLeftOf, 10);
                map.append(C0251R.styleable.ConstraintLayout_Layout_layout_constraintRight_toRightOf, 11);
                map.append(C0251R.styleable.ConstraintLayout_Layout_layout_constraintTop_toTopOf, 12);
                map.append(C0251R.styleable.ConstraintLayout_Layout_layout_constraintTop_toBottomOf, 13);
                map.append(C0251R.styleable.ConstraintLayout_Layout_layout_constraintBottom_toTopOf, 14);
                map.append(C0251R.styleable.ConstraintLayout_Layout_layout_constraintBottom_toBottomOf, 15);
                map.append(C0251R.styleable.ConstraintLayout_Layout_layout_constraintBaseline_toBaselineOf, 16);
                map.append(C0251R.styleable.ConstraintLayout_Layout_layout_constraintCircle, 2);
                map.append(C0251R.styleable.ConstraintLayout_Layout_layout_constraintCircleRadius, 3);
                map.append(C0251R.styleable.ConstraintLayout_Layout_layout_constraintCircleAngle, 4);
                map.append(C0251R.styleable.ConstraintLayout_Layout_layout_editor_absoluteX, 49);
                map.append(C0251R.styleable.ConstraintLayout_Layout_layout_editor_absoluteY, 50);
                map.append(C0251R.styleable.ConstraintLayout_Layout_layout_constraintGuide_begin, 5);
                map.append(C0251R.styleable.ConstraintLayout_Layout_layout_constraintGuide_end, 6);
                map.append(C0251R.styleable.ConstraintLayout_Layout_layout_constraintGuide_percent, 7);
                map.append(C0251R.styleable.ConstraintLayout_Layout_android_orientation, 1);
                map.append(C0251R.styleable.ConstraintLayout_Layout_layout_constraintStart_toEndOf, 17);
                map.append(C0251R.styleable.ConstraintLayout_Layout_layout_constraintStart_toStartOf, 18);
                map.append(C0251R.styleable.ConstraintLayout_Layout_layout_constraintEnd_toStartOf, 19);
                map.append(C0251R.styleable.ConstraintLayout_Layout_layout_constraintEnd_toEndOf, 20);
                map.append(C0251R.styleable.ConstraintLayout_Layout_layout_goneMarginLeft, 21);
                map.append(C0251R.styleable.ConstraintLayout_Layout_layout_goneMarginTop, 22);
                map.append(C0251R.styleable.ConstraintLayout_Layout_layout_goneMarginRight, 23);
                map.append(C0251R.styleable.ConstraintLayout_Layout_layout_goneMarginBottom, 24);
                map.append(C0251R.styleable.ConstraintLayout_Layout_layout_goneMarginStart, 25);
                map.append(C0251R.styleable.ConstraintLayout_Layout_layout_goneMarginEnd, 26);
                map.append(C0251R.styleable.ConstraintLayout_Layout_layout_constraintHorizontal_bias, 29);
                map.append(C0251R.styleable.ConstraintLayout_Layout_layout_constraintVertical_bias, 30);
                map.append(C0251R.styleable.ConstraintLayout_Layout_layout_constraintDimensionRatio, 44);
                map.append(C0251R.styleable.ConstraintLayout_Layout_layout_constraintHorizontal_weight, 45);
                map.append(C0251R.styleable.ConstraintLayout_Layout_layout_constraintVertical_weight, 46);
                map.append(C0251R.styleable.ConstraintLayout_Layout_layout_constraintHorizontal_chainStyle, 47);
                map.append(C0251R.styleable.ConstraintLayout_Layout_layout_constraintVertical_chainStyle, 48);
                map.append(C0251R.styleable.ConstraintLayout_Layout_layout_constrainedWidth, 27);
                map.append(C0251R.styleable.ConstraintLayout_Layout_layout_constrainedHeight, 28);
                map.append(C0251R.styleable.ConstraintLayout_Layout_layout_constraintWidth_default, 31);
                map.append(C0251R.styleable.ConstraintLayout_Layout_layout_constraintHeight_default, 32);
                map.append(C0251R.styleable.ConstraintLayout_Layout_layout_constraintWidth_min, 33);
                map.append(C0251R.styleable.ConstraintLayout_Layout_layout_constraintWidth_max, 34);
                map.append(C0251R.styleable.ConstraintLayout_Layout_layout_constraintWidth_percent, 35);
                map.append(C0251R.styleable.ConstraintLayout_Layout_layout_constraintHeight_min, 36);
                map.append(C0251R.styleable.ConstraintLayout_Layout_layout_constraintHeight_max, 37);
                map.append(C0251R.styleable.ConstraintLayout_Layout_layout_constraintHeight_percent, 38);
                map.append(C0251R.styleable.ConstraintLayout_Layout_layout_constraintLeft_creator, 39);
                map.append(C0251R.styleable.ConstraintLayout_Layout_layout_constraintTop_creator, 40);
                map.append(C0251R.styleable.ConstraintLayout_Layout_layout_constraintRight_creator, 41);
                map.append(C0251R.styleable.ConstraintLayout_Layout_layout_constraintBottom_creator, 42);
                map.append(C0251R.styleable.ConstraintLayout_Layout_layout_constraintBaseline_creator, 43);
                map.append(C0251R.styleable.ConstraintLayout_Layout_layout_constraintTag, 51);
            }
        }

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        public LayoutParams(Context c, AttributeSet attrs) {
            super(c, attrs);
            int i;
            int value;
            int commaIndex;
            int i2 = -1;
            this.guideBegin = -1;
            this.guideEnd = -1;
            this.guidePercent = -1.0f;
            this.leftToLeft = -1;
            this.leftToRight = -1;
            this.rightToLeft = -1;
            this.rightToRight = -1;
            this.topToTop = -1;
            this.topToBottom = -1;
            this.bottomToTop = -1;
            this.bottomToBottom = -1;
            this.baselineToBaseline = -1;
            this.circleConstraint = -1;
            int i3 = 0;
            this.circleRadius = 0;
            this.circleAngle = 0.0f;
            this.startToEnd = -1;
            this.startToStart = -1;
            this.endToStart = -1;
            this.endToEnd = -1;
            this.goneLeftMargin = -1;
            this.goneTopMargin = -1;
            this.goneRightMargin = -1;
            this.goneBottomMargin = -1;
            this.goneStartMargin = -1;
            this.goneEndMargin = -1;
            this.horizontalBias = 0.5f;
            this.verticalBias = 0.5f;
            this.dimensionRatio = null;
            this.dimensionRatioValue = 0.0f;
            this.dimensionRatioSide = 1;
            this.horizontalWeight = -1.0f;
            this.verticalWeight = -1.0f;
            this.horizontalChainStyle = 0;
            this.verticalChainStyle = 0;
            this.matchConstraintDefaultWidth = 0;
            this.matchConstraintDefaultHeight = 0;
            this.matchConstraintMinWidth = 0;
            this.matchConstraintMinHeight = 0;
            this.matchConstraintMaxWidth = 0;
            this.matchConstraintMaxHeight = 0;
            this.matchConstraintPercentWidth = 1.0f;
            this.matchConstraintPercentHeight = 1.0f;
            this.editorAbsoluteX = -1;
            this.editorAbsoluteY = -1;
            this.orientation = -1;
            this.constrainedWidth = false;
            this.constrainedHeight = false;
            this.constraintTag = null;
            this.horizontalDimensionFixed = ConstraintLayout.USE_CONSTRAINTS_HELPER;
            this.verticalDimensionFixed = ConstraintLayout.USE_CONSTRAINTS_HELPER;
            this.needsBaseline = false;
            this.isGuideline = false;
            this.isHelper = false;
            this.isInPlaceholder = false;
            this.isVirtualGroup = false;
            this.resolvedLeftToLeft = -1;
            this.resolvedLeftToRight = -1;
            this.resolvedRightToLeft = -1;
            this.resolvedRightToRight = -1;
            this.resolveGoneLeftMargin = -1;
            this.resolveGoneRightMargin = -1;
            this.resolvedHorizontalBias = 0.5f;
            this.widget = new ConstraintWidget();
            this.helped = false;
            TypedArray a = c.obtainStyledAttributes(attrs, C0251R.styleable.ConstraintLayout_Layout);
            int N = a.getIndexCount();
            int i4 = 0;
            while (i4 < N) {
                int attr = a.getIndex(i4);
                int look = Table.map.get(attr);
                switch (look) {
                    case 1:
                        int i5 = i3;
                        i = i2;
                        value = i5;
                        this.orientation = a.getInt(attr, this.orientation);
                        break;
                    case 2:
                        value = i3;
                        int resourceId = a.getResourceId(attr, this.circleConstraint);
                        this.circleConstraint = resourceId;
                        i = -1;
                        if (resourceId != -1) {
                            break;
                        } else {
                            this.circleConstraint = a.getInt(attr, -1);
                            break;
                        }
                    case 3:
                        value = i3;
                        this.circleRadius = a.getDimensionPixelSize(attr, this.circleRadius);
                        i = -1;
                        break;
                    case 4:
                        value = i3;
                        float f = a.getFloat(attr, this.circleAngle) % 360.0f;
                        this.circleAngle = f;
                        if (f >= 0.0f) {
                            i = -1;
                            break;
                        } else {
                            this.circleAngle = (360.0f - f) % 360.0f;
                            i = -1;
                            break;
                        }
                    case 5:
                        value = i3;
                        this.guideBegin = a.getDimensionPixelOffset(attr, this.guideBegin);
                        i = -1;
                        break;
                    case 6:
                        value = i3;
                        this.guideEnd = a.getDimensionPixelOffset(attr, this.guideEnd);
                        i = -1;
                        break;
                    case 7:
                        value = i3;
                        this.guidePercent = a.getFloat(attr, this.guidePercent);
                        i = -1;
                        break;
                    case 8:
                        int i6 = i3;
                        int i7 = i2;
                        value = i6;
                        int resourceId2 = a.getResourceId(attr, this.leftToLeft);
                        this.leftToLeft = resourceId2;
                        if (resourceId2 != i7) {
                            i = -1;
                            break;
                        } else {
                            this.leftToLeft = a.getInt(attr, i7);
                            i = -1;
                            break;
                        }
                    case 9:
                        int i8 = i3;
                        i = i2;
                        value = i8;
                        int resourceId3 = a.getResourceId(attr, this.leftToRight);
                        this.leftToRight = resourceId3;
                        if (resourceId3 != i) {
                            break;
                        } else {
                            this.leftToRight = a.getInt(attr, i);
                            break;
                        }
                    case 10:
                        int i9 = i3;
                        i = i2;
                        value = i9;
                        int resourceId4 = a.getResourceId(attr, this.rightToLeft);
                        this.rightToLeft = resourceId4;
                        if (resourceId4 != i) {
                            break;
                        } else {
                            this.rightToLeft = a.getInt(attr, i);
                            break;
                        }
                    case 11:
                        int i10 = i3;
                        i = i2;
                        value = i10;
                        int resourceId5 = a.getResourceId(attr, this.rightToRight);
                        this.rightToRight = resourceId5;
                        if (resourceId5 != i) {
                            break;
                        } else {
                            this.rightToRight = a.getInt(attr, i);
                            break;
                        }
                    case 12:
                        int i11 = i3;
                        i = i2;
                        value = i11;
                        int resourceId6 = a.getResourceId(attr, this.topToTop);
                        this.topToTop = resourceId6;
                        if (resourceId6 != i) {
                            break;
                        } else {
                            this.topToTop = a.getInt(attr, i);
                            break;
                        }
                    case 13:
                        int i12 = i3;
                        i = i2;
                        value = i12;
                        int resourceId7 = a.getResourceId(attr, this.topToBottom);
                        this.topToBottom = resourceId7;
                        if (resourceId7 != i) {
                            break;
                        } else {
                            this.topToBottom = a.getInt(attr, i);
                            break;
                        }
                    case 14:
                        int i13 = i3;
                        i = i2;
                        value = i13;
                        int resourceId8 = a.getResourceId(attr, this.bottomToTop);
                        this.bottomToTop = resourceId8;
                        if (resourceId8 != i) {
                            break;
                        } else {
                            this.bottomToTop = a.getInt(attr, i);
                            break;
                        }
                    case 15:
                        int i14 = i3;
                        i = i2;
                        value = i14;
                        int resourceId9 = a.getResourceId(attr, this.bottomToBottom);
                        this.bottomToBottom = resourceId9;
                        if (resourceId9 != i) {
                            break;
                        } else {
                            this.bottomToBottom = a.getInt(attr, i);
                            break;
                        }
                    case 16:
                        int i15 = i3;
                        i = i2;
                        value = i15;
                        int resourceId10 = a.getResourceId(attr, this.baselineToBaseline);
                        this.baselineToBaseline = resourceId10;
                        if (resourceId10 != i) {
                            break;
                        } else {
                            this.baselineToBaseline = a.getInt(attr, i);
                            break;
                        }
                    case 17:
                        int i16 = i3;
                        i = i2;
                        value = i16;
                        int resourceId11 = a.getResourceId(attr, this.startToEnd);
                        this.startToEnd = resourceId11;
                        if (resourceId11 != i) {
                            break;
                        } else {
                            this.startToEnd = a.getInt(attr, i);
                            break;
                        }
                    case 18:
                        int i17 = i3;
                        i = i2;
                        value = i17;
                        int resourceId12 = a.getResourceId(attr, this.startToStart);
                        this.startToStart = resourceId12;
                        if (resourceId12 != i) {
                            break;
                        } else {
                            this.startToStart = a.getInt(attr, i);
                            break;
                        }
                    case 19:
                        int i18 = i3;
                        i = i2;
                        value = i18;
                        int resourceId13 = a.getResourceId(attr, this.endToStart);
                        this.endToStart = resourceId13;
                        if (resourceId13 != i) {
                            break;
                        } else {
                            this.endToStart = a.getInt(attr, i);
                            break;
                        }
                    case 20:
                        value = i3;
                        int resourceId14 = a.getResourceId(attr, this.endToEnd);
                        this.endToEnd = resourceId14;
                        i = -1;
                        if (resourceId14 != -1) {
                            break;
                        } else {
                            this.endToEnd = a.getInt(attr, -1);
                            break;
                        }
                    case 21:
                        value = i3;
                        this.goneLeftMargin = a.getDimensionPixelSize(attr, this.goneLeftMargin);
                        i = -1;
                        break;
                    case 22:
                        value = i3;
                        this.goneTopMargin = a.getDimensionPixelSize(attr, this.goneTopMargin);
                        i = -1;
                        break;
                    case 23:
                        value = i3;
                        this.goneRightMargin = a.getDimensionPixelSize(attr, this.goneRightMargin);
                        i = -1;
                        break;
                    case 24:
                        value = i3;
                        this.goneBottomMargin = a.getDimensionPixelSize(attr, this.goneBottomMargin);
                        i = -1;
                        break;
                    case 25:
                        value = i3;
                        this.goneStartMargin = a.getDimensionPixelSize(attr, this.goneStartMargin);
                        i = -1;
                        break;
                    case 26:
                        value = i3;
                        this.goneEndMargin = a.getDimensionPixelSize(attr, this.goneEndMargin);
                        i = -1;
                        break;
                    case 27:
                        value = i3;
                        this.constrainedWidth = a.getBoolean(attr, this.constrainedWidth);
                        i = -1;
                        break;
                    case 28:
                        value = i3;
                        this.constrainedHeight = a.getBoolean(attr, this.constrainedHeight);
                        i = -1;
                        break;
                    case 29:
                        value = i3;
                        this.horizontalBias = a.getFloat(attr, this.horizontalBias);
                        i = -1;
                        break;
                    case 30:
                        value = i3;
                        this.verticalBias = a.getFloat(attr, this.verticalBias);
                        i = -1;
                        break;
                    case 31:
                        value = i3;
                        int i19 = a.getInt(attr, value);
                        this.matchConstraintDefaultWidth = i19;
                        if (i19 != 1) {
                            i = -1;
                            break;
                        } else {
                            Log.e(ConstraintLayout.TAG, "layout_constraintWidth_default=\"wrap\" is deprecated.\nUse layout_width=\"WRAP_CONTENT\" and layout_constrainedWidth=\"true\" instead.");
                            i = -1;
                            break;
                        }
                    case 32:
                        value = 0;
                        int i20 = a.getInt(attr, 0);
                        this.matchConstraintDefaultHeight = i20;
                        if (i20 != 1) {
                            i = -1;
                            break;
                        } else {
                            Log.e(ConstraintLayout.TAG, "layout_constraintHeight_default=\"wrap\" is deprecated.\nUse layout_height=\"WRAP_CONTENT\" and layout_constrainedHeight=\"true\" instead.");
                            i = -1;
                            break;
                        }
                    case 33:
                        try {
                            this.matchConstraintMinWidth = a.getDimensionPixelSize(attr, this.matchConstraintMinWidth);
                            value = 0;
                            i = -1;
                            break;
                        } catch (Exception e) {
                            if (a.getInt(attr, this.matchConstraintMinWidth) == -2) {
                                this.matchConstraintMinWidth = -2;
                            }
                            value = 0;
                            i = -1;
                            break;
                        }
                    case 34:
                        try {
                            this.matchConstraintMaxWidth = a.getDimensionPixelSize(attr, this.matchConstraintMaxWidth);
                            value = 0;
                            i = -1;
                            break;
                        } catch (Exception e2) {
                            if (a.getInt(attr, this.matchConstraintMaxWidth) == -2) {
                                this.matchConstraintMaxWidth = -2;
                            }
                            value = 0;
                            i = -1;
                            break;
                        }
                    case 35:
                        this.matchConstraintPercentWidth = Math.max(0.0f, a.getFloat(attr, this.matchConstraintPercentWidth));
                        this.matchConstraintDefaultWidth = 2;
                        value = 0;
                        i = -1;
                        break;
                    case 36:
                        try {
                            this.matchConstraintMinHeight = a.getDimensionPixelSize(attr, this.matchConstraintMinHeight);
                            value = 0;
                            i = -1;
                            break;
                        } catch (Exception e3) {
                            if (a.getInt(attr, this.matchConstraintMinHeight) == -2) {
                                this.matchConstraintMinHeight = -2;
                            }
                            value = 0;
                            i = -1;
                            break;
                        }
                    case 37:
                        try {
                            this.matchConstraintMaxHeight = a.getDimensionPixelSize(attr, this.matchConstraintMaxHeight);
                            value = 0;
                            i = -1;
                            break;
                        } catch (Exception e4) {
                            if (a.getInt(attr, this.matchConstraintMaxHeight) == -2) {
                                this.matchConstraintMaxHeight = -2;
                            }
                            value = 0;
                            i = -1;
                            break;
                        }
                    case 38:
                        this.matchConstraintPercentHeight = Math.max(0.0f, a.getFloat(attr, this.matchConstraintPercentHeight));
                        this.matchConstraintDefaultHeight = 2;
                        value = 0;
                        i = -1;
                        break;
                    default:
                        switch (look) {
                            case 44:
                                String string = a.getString(attr);
                                this.dimensionRatio = string;
                                this.dimensionRatioValue = Float.NaN;
                                this.dimensionRatioSide = i2;
                                if (string == null) {
                                    value = 0;
                                    i = -1;
                                    break;
                                } else {
                                    int len = string.length();
                                    int commaIndex2 = this.dimensionRatio.indexOf(44);
                                    if (commaIndex2 <= 0 || commaIndex2 >= len - 1) {
                                        commaIndex = 0;
                                    } else {
                                        String dimension = this.dimensionRatio.substring(i3, commaIndex2);
                                        if (dimension.equalsIgnoreCase("W")) {
                                            this.dimensionRatioSide = i3;
                                        } else if (dimension.equalsIgnoreCase("H")) {
                                            this.dimensionRatioSide = 1;
                                        }
                                        commaIndex = commaIndex2 + 1;
                                    }
                                    int colonIndex = this.dimensionRatio.indexOf(58);
                                    if (colonIndex < 0 || colonIndex >= len - 1) {
                                        String r = this.dimensionRatio.substring(commaIndex);
                                        if (r.length() > 0) {
                                            try {
                                                this.dimensionRatioValue = Float.parseFloat(r);
                                            } catch (NumberFormatException e5) {
                                            }
                                        }
                                    } else {
                                        String nominator = this.dimensionRatio.substring(commaIndex, colonIndex);
                                        String denominator = this.dimensionRatio.substring(colonIndex + 1);
                                        if (nominator.length() > 0 && denominator.length() > 0) {
                                            try {
                                                float nominatorValue = Float.parseFloat(nominator);
                                                float denominatorValue = Float.parseFloat(denominator);
                                                if (nominatorValue > 0.0f && denominatorValue > 0.0f) {
                                                    if (this.dimensionRatioSide == 1) {
                                                        this.dimensionRatioValue = Math.abs(denominatorValue / nominatorValue);
                                                    } else {
                                                        this.dimensionRatioValue = Math.abs(nominatorValue / denominatorValue);
                                                    }
                                                }
                                            } catch (NumberFormatException e6) {
                                            }
                                        }
                                    }
                                    value = 0;
                                    i = -1;
                                    break;
                                }
                            case 45:
                                this.horizontalWeight = a.getFloat(attr, this.horizontalWeight);
                                int i21 = i3;
                                i = i2;
                                value = i21;
                                break;
                            case 46:
                                this.verticalWeight = a.getFloat(attr, this.verticalWeight);
                                int i22 = i3;
                                i = i2;
                                value = i22;
                                break;
                            case 47:
                                this.horizontalChainStyle = a.getInt(attr, i3);
                                int i23 = i3;
                                i = i2;
                                value = i23;
                                break;
                            case 48:
                                this.verticalChainStyle = a.getInt(attr, i3);
                                int i24 = i3;
                                i = i2;
                                value = i24;
                                break;
                            case 49:
                                this.editorAbsoluteX = a.getDimensionPixelOffset(attr, this.editorAbsoluteX);
                                int i25 = i3;
                                i = i2;
                                value = i25;
                                break;
                            case 50:
                                this.editorAbsoluteY = a.getDimensionPixelOffset(attr, this.editorAbsoluteY);
                                int i26 = i3;
                                i = i2;
                                value = i26;
                                break;
                            case 51:
                                this.constraintTag = a.getString(attr);
                                int i27 = i3;
                                i = i2;
                                value = i27;
                                break;
                            default:
                                int i28 = i3;
                                i = i2;
                                value = i28;
                                break;
                        }
                }
                i4++;
                int i29 = i;
                i3 = value;
                i2 = i29;
            }
            a.recycle();
            validate();
        }

        public void validate() {
            this.isGuideline = false;
            this.horizontalDimensionFixed = ConstraintLayout.USE_CONSTRAINTS_HELPER;
            this.verticalDimensionFixed = ConstraintLayout.USE_CONSTRAINTS_HELPER;
            if (this.width == -2 && this.constrainedWidth) {
                this.horizontalDimensionFixed = false;
                if (this.matchConstraintDefaultWidth == 0) {
                    this.matchConstraintDefaultWidth = 1;
                }
            }
            if (this.height == -2 && this.constrainedHeight) {
                this.verticalDimensionFixed = false;
                if (this.matchConstraintDefaultHeight == 0) {
                    this.matchConstraintDefaultHeight = 1;
                }
            }
            if (this.width == 0 || this.width == -1) {
                this.horizontalDimensionFixed = false;
                if (this.width == 0 && this.matchConstraintDefaultWidth == 1) {
                    this.width = -2;
                    this.constrainedWidth = ConstraintLayout.USE_CONSTRAINTS_HELPER;
                }
            }
            if (this.height == 0 || this.height == -1) {
                this.verticalDimensionFixed = false;
                if (this.height == 0 && this.matchConstraintDefaultHeight == 1) {
                    this.height = -2;
                    this.constrainedHeight = ConstraintLayout.USE_CONSTRAINTS_HELPER;
                }
            }
            if (this.guidePercent != -1.0f || this.guideBegin != -1 || this.guideEnd != -1) {
                this.isGuideline = ConstraintLayout.USE_CONSTRAINTS_HELPER;
                this.horizontalDimensionFixed = ConstraintLayout.USE_CONSTRAINTS_HELPER;
                this.verticalDimensionFixed = ConstraintLayout.USE_CONSTRAINTS_HELPER;
                if (!(this.widget instanceof Guideline)) {
                    this.widget = new Guideline();
                }
                ((Guideline) this.widget).setOrientation(this.orientation);
            }
        }

        public LayoutParams(int width, int height) {
            super(width, height);
            this.guideBegin = -1;
            this.guideEnd = -1;
            this.guidePercent = -1.0f;
            this.leftToLeft = -1;
            this.leftToRight = -1;
            this.rightToLeft = -1;
            this.rightToRight = -1;
            this.topToTop = -1;
            this.topToBottom = -1;
            this.bottomToTop = -1;
            this.bottomToBottom = -1;
            this.baselineToBaseline = -1;
            this.circleConstraint = -1;
            this.circleRadius = 0;
            this.circleAngle = 0.0f;
            this.startToEnd = -1;
            this.startToStart = -1;
            this.endToStart = -1;
            this.endToEnd = -1;
            this.goneLeftMargin = -1;
            this.goneTopMargin = -1;
            this.goneRightMargin = -1;
            this.goneBottomMargin = -1;
            this.goneStartMargin = -1;
            this.goneEndMargin = -1;
            this.horizontalBias = 0.5f;
            this.verticalBias = 0.5f;
            this.dimensionRatio = null;
            this.dimensionRatioValue = 0.0f;
            this.dimensionRatioSide = 1;
            this.horizontalWeight = -1.0f;
            this.verticalWeight = -1.0f;
            this.horizontalChainStyle = 0;
            this.verticalChainStyle = 0;
            this.matchConstraintDefaultWidth = 0;
            this.matchConstraintDefaultHeight = 0;
            this.matchConstraintMinWidth = 0;
            this.matchConstraintMinHeight = 0;
            this.matchConstraintMaxWidth = 0;
            this.matchConstraintMaxHeight = 0;
            this.matchConstraintPercentWidth = 1.0f;
            this.matchConstraintPercentHeight = 1.0f;
            this.editorAbsoluteX = -1;
            this.editorAbsoluteY = -1;
            this.orientation = -1;
            this.constrainedWidth = false;
            this.constrainedHeight = false;
            this.constraintTag = null;
            this.horizontalDimensionFixed = ConstraintLayout.USE_CONSTRAINTS_HELPER;
            this.verticalDimensionFixed = ConstraintLayout.USE_CONSTRAINTS_HELPER;
            this.needsBaseline = false;
            this.isGuideline = false;
            this.isHelper = false;
            this.isInPlaceholder = false;
            this.isVirtualGroup = false;
            this.resolvedLeftToLeft = -1;
            this.resolvedLeftToRight = -1;
            this.resolvedRightToLeft = -1;
            this.resolvedRightToRight = -1;
            this.resolveGoneLeftMargin = -1;
            this.resolveGoneRightMargin = -1;
            this.resolvedHorizontalBias = 0.5f;
            this.widget = new ConstraintWidget();
            this.helped = false;
        }

        public LayoutParams(ViewGroup.LayoutParams source) {
            super(source);
            this.guideBegin = -1;
            this.guideEnd = -1;
            this.guidePercent = -1.0f;
            this.leftToLeft = -1;
            this.leftToRight = -1;
            this.rightToLeft = -1;
            this.rightToRight = -1;
            this.topToTop = -1;
            this.topToBottom = -1;
            this.bottomToTop = -1;
            this.bottomToBottom = -1;
            this.baselineToBaseline = -1;
            this.circleConstraint = -1;
            this.circleRadius = 0;
            this.circleAngle = 0.0f;
            this.startToEnd = -1;
            this.startToStart = -1;
            this.endToStart = -1;
            this.endToEnd = -1;
            this.goneLeftMargin = -1;
            this.goneTopMargin = -1;
            this.goneRightMargin = -1;
            this.goneBottomMargin = -1;
            this.goneStartMargin = -1;
            this.goneEndMargin = -1;
            this.horizontalBias = 0.5f;
            this.verticalBias = 0.5f;
            this.dimensionRatio = null;
            this.dimensionRatioValue = 0.0f;
            this.dimensionRatioSide = 1;
            this.horizontalWeight = -1.0f;
            this.verticalWeight = -1.0f;
            this.horizontalChainStyle = 0;
            this.verticalChainStyle = 0;
            this.matchConstraintDefaultWidth = 0;
            this.matchConstraintDefaultHeight = 0;
            this.matchConstraintMinWidth = 0;
            this.matchConstraintMinHeight = 0;
            this.matchConstraintMaxWidth = 0;
            this.matchConstraintMaxHeight = 0;
            this.matchConstraintPercentWidth = 1.0f;
            this.matchConstraintPercentHeight = 1.0f;
            this.editorAbsoluteX = -1;
            this.editorAbsoluteY = -1;
            this.orientation = -1;
            this.constrainedWidth = false;
            this.constrainedHeight = false;
            this.constraintTag = null;
            this.horizontalDimensionFixed = ConstraintLayout.USE_CONSTRAINTS_HELPER;
            this.verticalDimensionFixed = ConstraintLayout.USE_CONSTRAINTS_HELPER;
            this.needsBaseline = false;
            this.isGuideline = false;
            this.isHelper = false;
            this.isInPlaceholder = false;
            this.isVirtualGroup = false;
            this.resolvedLeftToLeft = -1;
            this.resolvedLeftToRight = -1;
            this.resolvedRightToLeft = -1;
            this.resolvedRightToRight = -1;
            this.resolveGoneLeftMargin = -1;
            this.resolveGoneRightMargin = -1;
            this.resolvedHorizontalBias = 0.5f;
            this.widget = new ConstraintWidget();
            this.helped = false;
        }

        public void resolveLayoutDirection(int layoutDirection) {
            int preLeftMargin = this.leftMargin;
            int preRightMargin = this.rightMargin;
            boolean isRtl = false;
            if (Build.VERSION.SDK_INT >= 17) {
                super.resolveLayoutDirection(layoutDirection);
                isRtl = 1 == getLayoutDirection();
            }
            this.resolvedRightToLeft = -1;
            this.resolvedRightToRight = -1;
            this.resolvedLeftToLeft = -1;
            this.resolvedLeftToRight = -1;
            this.resolveGoneLeftMargin = -1;
            this.resolveGoneRightMargin = -1;
            this.resolveGoneLeftMargin = this.goneLeftMargin;
            this.resolveGoneRightMargin = this.goneRightMargin;
            this.resolvedHorizontalBias = this.horizontalBias;
            this.resolvedGuideBegin = this.guideBegin;
            this.resolvedGuideEnd = this.guideEnd;
            this.resolvedGuidePercent = this.guidePercent;
            if (isRtl) {
                boolean startEndDefined = false;
                int i = this.startToEnd;
                if (i != -1) {
                    this.resolvedRightToLeft = i;
                    startEndDefined = ConstraintLayout.USE_CONSTRAINTS_HELPER;
                } else {
                    int i2 = this.startToStart;
                    if (i2 != -1) {
                        this.resolvedRightToRight = i2;
                        startEndDefined = ConstraintLayout.USE_CONSTRAINTS_HELPER;
                    }
                }
                int i3 = this.endToStart;
                if (i3 != -1) {
                    this.resolvedLeftToRight = i3;
                    startEndDefined = ConstraintLayout.USE_CONSTRAINTS_HELPER;
                }
                int i4 = this.endToEnd;
                if (i4 != -1) {
                    this.resolvedLeftToLeft = i4;
                    startEndDefined = ConstraintLayout.USE_CONSTRAINTS_HELPER;
                }
                int i5 = this.goneStartMargin;
                if (i5 != -1) {
                    this.resolveGoneRightMargin = i5;
                }
                int i6 = this.goneEndMargin;
                if (i6 != -1) {
                    this.resolveGoneLeftMargin = i6;
                }
                if (startEndDefined) {
                    this.resolvedHorizontalBias = 1.0f - this.horizontalBias;
                }
                if (this.isGuideline && this.orientation == 1) {
                    float f = this.guidePercent;
                    if (f != -1.0f) {
                        this.resolvedGuidePercent = 1.0f - f;
                        this.resolvedGuideBegin = -1;
                        this.resolvedGuideEnd = -1;
                    } else {
                        int i7 = this.guideBegin;
                        if (i7 != -1) {
                            this.resolvedGuideEnd = i7;
                            this.resolvedGuideBegin = -1;
                            this.resolvedGuidePercent = -1.0f;
                        } else {
                            int i8 = this.guideEnd;
                            if (i8 != -1) {
                                this.resolvedGuideBegin = i8;
                                this.resolvedGuideEnd = -1;
                                this.resolvedGuidePercent = -1.0f;
                            }
                        }
                    }
                }
            } else {
                int i9 = this.startToEnd;
                if (i9 != -1) {
                    this.resolvedLeftToRight = i9;
                }
                int i10 = this.startToStart;
                if (i10 != -1) {
                    this.resolvedLeftToLeft = i10;
                }
                int i11 = this.endToStart;
                if (i11 != -1) {
                    this.resolvedRightToLeft = i11;
                }
                int i12 = this.endToEnd;
                if (i12 != -1) {
                    this.resolvedRightToRight = i12;
                }
                int i13 = this.goneStartMargin;
                if (i13 != -1) {
                    this.resolveGoneLeftMargin = i13;
                }
                int i14 = this.goneEndMargin;
                if (i14 != -1) {
                    this.resolveGoneRightMargin = i14;
                }
            }
            if (this.endToStart == -1 && this.endToEnd == -1 && this.startToStart == -1 && this.startToEnd == -1) {
                int i15 = this.rightToLeft;
                if (i15 != -1) {
                    this.resolvedRightToLeft = i15;
                    if (this.rightMargin <= 0 && preRightMargin > 0) {
                        this.rightMargin = preRightMargin;
                    }
                } else {
                    int i16 = this.rightToRight;
                    if (i16 != -1) {
                        this.resolvedRightToRight = i16;
                        if (this.rightMargin <= 0 && preRightMargin > 0) {
                            this.rightMargin = preRightMargin;
                        }
                    }
                }
                int i17 = this.leftToLeft;
                if (i17 != -1) {
                    this.resolvedLeftToLeft = i17;
                    if (this.leftMargin <= 0 && preLeftMargin > 0) {
                        this.leftMargin = preLeftMargin;
                        return;
                    }
                    return;
                }
                int i18 = this.leftToRight;
                if (i18 != -1) {
                    this.resolvedLeftToRight = i18;
                    if (this.leftMargin <= 0 && preLeftMargin > 0) {
                        this.leftMargin = preLeftMargin;
                    }
                }
            }
        }

        public String getConstraintTag() {
            return this.constraintTag;
        }
    }

    public void requestLayout() {
        markHierarchyDirty();
        super.requestLayout();
    }

    public void forceLayout() {
        markHierarchyDirty();
        super.forceLayout();
    }

    private void markHierarchyDirty() {
        this.mDirtyHierarchy = USE_CONSTRAINTS_HELPER;
        this.mLastMeasureWidth = -1;
        this.mLastMeasureHeight = -1;
        this.mLastMeasureWidthSize = -1;
        this.mLastMeasureHeightSize = -1;
        this.mLastMeasureWidthMode = 0;
        this.mLastMeasureHeightMode = 0;
    }

    public boolean shouldDelayChildPressedState() {
        return false;
    }
}
