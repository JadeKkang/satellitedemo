package com.example.satellite;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.BitmapFactory;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.annotation.IntDef;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * 时间：2018/9/10 11:04
 * 姓名：韩晓康
 * 功能：
 */
public class SatelliteMenu extends LinearLayout {
    private Context context;
    private RelativeLayout llView;
    private TextView ivStart;//外部按钮
    private int ivStartRes;//外部资源
    private int[] img = new int[]{R.mipmap.heart, R.mipmap.heart1, R.mipmap.heart2, R.mipmap.heart3, R.mipmap.heart3};
    private String[] text;//文字数组
    private ObjectAnimator animator;
    private ObjectAnimator animator1;
    private ObjectAnimator animator0;
    private ObjectAnimator animator2;
    private ObjectAnimator animator3;
    private ClickItem clickItem;//回调监听
    private int position;//2左下 1左上 3右下 4 右上 指的位置
    private boolean isText;//下方文字是否显示
    private int circlrR;//基准圆形半径
    private int radius = 50;//基准圆形半径
    private boolean isStart = true;//控制执行 放射动画还是收缩动画
    float mX = 0;
    float mY = 0;
    private ImageView ivAdd;//itemView
    private TextView tvAdd;//itemView
    private float textSize;
    private int textColor;
    private float itemSize;//子View 图片的 大小

    public SatelliteMenu(Context context) {
        this(context, null);
    }

    public SatelliteMenu(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, -1);
    }

    public SatelliteMenu(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        View view = LayoutInflater.from(context).inflate(R.layout.menu_bt, this, true);
        llView = (RelativeLayout) view.findViewById(R.id.ll_view);
        ivStart = (TextView) view.findViewById(R.id.iv_start);

        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.MenuBt);
        if (array != null) {
            position = array.getInteger(R.styleable.MenuBt_orientation, 4);
            ivStartRes = array.getResourceId(R.styleable.MenuBt_bitmap, R.mipmap.heart);
            radius = array.getInteger(R.styleable.MenuBt_radius, 50);
            isText = array.getBoolean(R.styleable.MenuBt_isText, false);

            float size = array.getDimension(R.styleable.MenuBt_textSize, 12);
            textSize = px2sp(context, size);
            itemSize = array.getDimension(R.styleable.MenuBt_itemSize, 45);
            textColor = array.getColor(R.styleable.MenuBt_textColor, ContextCompat.getColor(context,R.color.textColor));
        }
        ivStart.setWidth((int) itemSize);
        ivStart.setHeight((int) itemSize);
        array.recycle();
        text = new String[img.length];
        addView();
        startClick();
    }

    private void startClick() {
        ivStart.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                int visibility = llView.getVisibility();
                if (visibility == 0) {
                    isStart = false;
                } else {
                    isStart = true;
                    llView.setVisibility(VISIBLE);
                }
                start();
            }
        });
    }

    /**
     * 作者  韩晓康
     * 时间  2018/9/11 17:01
     * 描述  添加子View
     */
    private void addView() {
        FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.WRAP_CONTENT, FrameLayout.LayoutParams.WRAP_CONTENT);
        switch (position) {//设置显示的位置
            case 1:
                llView.setGravity(Gravity.BOTTOM | Gravity.LEFT);
                lp.gravity = Gravity.BOTTOM | Gravity.LEFT;
                break;
            case 2:
                llView.setGravity(Gravity.TOP | Gravity.LEFT);
                lp.gravity = Gravity.TOP | Gravity.LEFT;
                break;
            case 3:
                llView.setGravity(Gravity.BOTTOM | Gravity.RIGHT);
                lp.gravity = Gravity.BOTTOM | Gravity.RIGHT;
                break;
            case 4:
                llView.setGravity(Gravity.TOP | Gravity.RIGHT);
                lp.gravity = Gravity.TOP | Gravity.RIGHT;
                break;
        }
        ivStart.setLayoutParams(lp);
        ivStart.setBackgroundResource(ivStartRes);

        llView.removeAllViews();
        for (int i = 0; i < img.length; i++) {//循环添加扩散出去的View
            View itemView=LayoutInflater.from(context).inflate(R.layout.menu_item,null,false);
            RelativeLayout rl = itemView.findViewById(R.id.rl);
            ImageView ivAdd = itemView.findViewById(R.id.iv_add);
            ivAdd.setLayoutParams(new RelativeLayout.LayoutParams((int) itemSize, (int)itemSize));
            ivAdd.setBackgroundResource(img[i]);
            TextView tvAdd = itemView.findViewById(R.id.tv_add);

            tvAdd.setWidth((int) itemSize+(int) textSize/2);
            tvAdd.setTextSize(textSize);
            tvAdd.setTextColor(textColor);
            if (isText){
                tvAdd.setVisibility(VISIBLE);
                if (text.length>0&&text!=null) {
                    tvAdd.setText(text[i]);
                }
            }else {
                tvAdd.setVisibility(GONE);
            }
            rl.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            switch (position) {//设置显示的位置
                case 1:
                    rl.setGravity(Gravity.BOTTOM | Gravity.LEFT);
                    break;
                case 2:
                    rl.setGravity(Gravity.TOP | Gravity.LEFT);
                    break;
                case 3:
                    rl.setGravity(Gravity.BOTTOM | Gravity.RIGHT);
                    break;
                case 4:
                    rl.setGravity(Gravity.TOP | Gravity.RIGHT);
                    break;
            }
            final int finalI = i;
            ivAdd.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (clickItem != null) {
                        clickItem.clickItem(finalI);
                    }
                    isStart = false;
                    start();
                }
            });
            llView.addView(itemView);
        }
    }
    public  float px2sp(Context context, float pxValue) {
            final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (pxValue / fontScale + 0.5f);
    }
    /**
     * 作者  韩晓康
     * 时间  2018/9/11 17:02
     * 描述  确定移动的位置
     */
    private void start() {
        int childCount = llView.getChildCount();
        circlrR = (getWidth() / 2 - radius);
        for (int i = 0; i < childCount; i++) {
            ivAdd = (ImageView) llView.getChildAt(i).findViewById(R.id.iv_add);
            tvAdd = (TextView) llView.getChildAt(i).findViewById(R.id.tv_add);
            switch (position) {
                case 1://按钮显示在左下，扩散子View应该是（x,y）-->(+,-)
                    mX = (float) (circlrR * (Math.cos(Math.PI / 2 / (img.length - 1) * i)));
                    mY = -(float) ((getWidth() / 2 - 50) * (Math.sin(Math.PI / 2 / (img.length - 1) * i)));
                    break;
                case 2://按钮显示在左上，扩散子View应该是（x,y）-->(+,+)
                    mX = (float) (circlrR * (Math.cos(Math.PI / 2 / (img.length - 1) * i)));
                    mY = (float) ((getWidth() / 2 - 50) * (Math.sin(Math.PI / 2 / (img.length - 1) * i)));
                    break;
                case 3://按钮显示在右下，扩散子View应该是（x,y）-->(-,-)
                    mX = -(float) (circlrR * (Math.cos(Math.PI / 2 / (img.length - 1) * i)));
                    mY = -(float) ((getWidth() / 2 - 50) * (Math.sin(Math.PI / 2 / (img.length - 1) * i)));
                    break;
                case 4://按钮显示在右下，扩散子View应该是（x,y）-->(-,+)
                    mX = -(float) (circlrR * (Math.cos(Math.PI / 2 / (img.length - 1) * i)));
                    mY = (float) ((getWidth() / 2 - 50) * (Math.sin(Math.PI / 2 / (img.length - 1) * i)));
                    break;
            }
            startAnimator();
        }
    }

    /**
     * 作者  韩晓康
     * 时间  2018/9/11 17:02
     * 描述  动画开始
     */
    private void startAnimator() {
        if (!isStart) {
            animator0 = ObjectAnimator.ofFloat(ivStart, "rotation", 45, 0);
            animator = ObjectAnimator.ofFloat(ivAdd, "translationY", mY, 0);
            animator1 = ObjectAnimator.ofFloat(ivAdd, "translationX", mX, 0);
            animator2 = ObjectAnimator.ofFloat(tvAdd, "translationY", mY, 0);
            animator3 = ObjectAnimator.ofFloat(tvAdd, "translationX", mX, 0);
        } else {
            animator0 = ObjectAnimator.ofFloat(ivStart, "rotation", 0, 45);
            animator = ObjectAnimator.ofFloat(ivAdd, "translationY", 0, mY);
            animator1 = ObjectAnimator.ofFloat(ivAdd, "translationX", 0, mX);
            animator2 = ObjectAnimator.ofFloat(tvAdd, "translationY", 0, mY);
            animator3 = ObjectAnimator.ofFloat(tvAdd, "translationX", 0, mX);
        }
        animator0.setDuration(300);
        animator0.start();
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.setDuration(300);
        animatorSet.playTogether(animator, animator1,animator2, animator3);
        animatorSet.start();
        if (!isStart) {
            animatorSet.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    super.onAnimationEnd(animation);
                    llView.setVisibility(INVISIBLE);
                    ivStart.setEnabled(true);
                }

                @Override
                public void onAnimationStart(Animator animation) {
                    super.onAnimationStart(animation);
                    ivStart.setEnabled(false);
                }
            });
        }

    }

    /**
     * 设置子View图片
     *
     * @param img
     */
    public void setImg(int[] img) {
        this.img = img;
    }

    /**
     * 设置子View文字
     *
     * @param text
     */
    public void setTexts(String[] text) {
        this.text = text;
        addView();
    }

    /**
     * 子项点击回调
     *
     * @param clickItem
     */
    public void setClickItem(ClickItem clickItem) {
        this.clickItem = clickItem;
    }
}
