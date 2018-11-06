package com.example.satellite;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * 时间：2018/9/10 11:04
 * 姓名：韩晓康
 * 功能：
 */
public class SatelliteMenu2 extends LinearLayout {
    private Context context;
    private RelativeLayout llView;
    private ImageView ivStart;
    private int ivStartRes;
    private int[] img = new int[]{R.mipmap.heart, R.mipmap.heart1, R.mipmap.heart2, R.mipmap.heart3, R.mipmap.heart3};
    private String[] text;
    private ObjectAnimator animator;
    private ObjectAnimator animator1;
    private ObjectAnimator animator0;
    private ObjectAnimator animator3;
    private ObjectAnimator animator4;
    private ClickItem clickItem;
    private int position;//2左下 1左上 3右下 4 右上 指的位置
    private boolean isText;//下方文字是否显示
    private int circlrR;//基准圆形半径
    private int radius=50;//基准圆形半径
    private boolean isStart = true;//控制执行 放射动画还是收缩动画
    float mX = 0;
    float mY = 0;
    private ImageView childAt;//itemView
    private TextView childAt2;//itemView
    private float textSize;
    private int textColor;

    public SatelliteMenu2(Context context) {
        this(context, null);
    }

    public SatelliteMenu2(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, -1);
    }

    public SatelliteMenu2(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        View view = LayoutInflater.from(context).inflate(R.layout.menu_bt, this, true);
        llView = (RelativeLayout) view.findViewById(R.id.ll_view);
        ivStart = (ImageView) view.findViewById(R.id.iv_start);
        TypedArray array=context.obtainStyledAttributes(attrs,R.styleable.MenuBt);
        if (array!=null) {
            position=array.getInteger(R.styleable.MenuBt_orientation,4);
            ivStartRes=array.getResourceId(R.styleable.MenuBt_bitmap,R.mipmap.heart);
            radius=array.getInteger(R.styleable.MenuBt_radius,50);
            isText=array.getBoolean(R.styleable.MenuBt_isText,false);
            textSize = array.getDimension(R.styleable.MenuBt_textSize, 18);
            textColor = array.getResourceId(R.styleable.MenuBt_textColor, R.color.textColor);
        }
        array.recycle();
        text=new String[img.length];
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
        ivStart.setImageResource(ivStartRes);

        llView.removeAllViews();
        for (int i = 0; i < img.length; i++) {//循环添加扩散出去的View
            final ImageView imageView = new ImageView(context);
            imageView.setId(R.id.iv_add);
            imageView.setImageResource(img[i]);

            final TextView textView=new TextView(context);
            textView.setId(R.id.tv_add);
            textView.setTextColor(textColor);
            textView.setTextSize(textSize);
            if (text!=null&&text.length>0){
                textView.setText(text[i]);
            }else {
                textView.setText(i+"");
            }
            textView.setGravity(Gravity.CENTER_HORIZONTAL);
            if (isText){
                textView.setVisibility(VISIBLE);
            }else {
                textView.setVisibility(GONE);
            }
            final int finalI = i;
            imageView.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (clickItem!=null){
                        clickItem.clickItem(finalI);
                    }
                    isStart = false;
                    start();
                }
            });
            RelativeLayout relativeLayout=new RelativeLayout(context);
            relativeLayout.setLayoutParams(new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT,   RelativeLayout.LayoutParams.MATCH_PARENT));
            switch (position) {
                case 1:
                    relativeLayout.setGravity(Gravity.BOTTOM | Gravity.LEFT);
                    break;
                case 2:
                    relativeLayout.setGravity(Gravity.TOP | Gravity.LEFT);
                    break;
                case 3:
                    relativeLayout.setGravity(Gravity.BOTTOM | Gravity.RIGHT);
                    break;
                case 4:
                    relativeLayout.setGravity(Gravity.TOP | Gravity.RIGHT);
                    break;
            }

            imageView.post(new Runnable() {
                @Override
                public void run() {
                    RelativeLayout.LayoutParams lp1=new RelativeLayout.LayoutParams(imageView.getWidth()*3,ViewGroup.LayoutParams.WRAP_CONTENT);
                    lp1.addRule(RelativeLayout.BELOW,imageView.getId());
                    textView.setLayoutParams(lp1);
                }
            });

            RelativeLayout.LayoutParams lp2=new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT);
            imageView.setLayoutParams(lp2);

            relativeLayout.addView(imageView);
            relativeLayout.addView(textView);
            llView.addView(relativeLayout);

        }
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
            childAt = (ImageView) llView.getChildAt(i).findViewById(R.id.iv_add);
            childAt2 = (TextView) llView.getChildAt(i).findViewById(R.id.tv_add);
            switch (position) {
                case 1:
                    mX = (float) (circlrR * (Math.cos(Math.PI / 2 / (img.length - 1) * i)));
                    mY = -(float) ((getWidth() / 2 - 50) * (Math.sin(Math.PI / 2 / (img.length - 1) * i)));
                    break;
                case 2:
                    mX = (float) (circlrR * (Math.cos(Math.PI / 2 / (img.length - 1) * i)));
                    mY = (float) ((getWidth() / 2 - 50) * (Math.sin(Math.PI / 2 / (img.length - 1) * i)));
                    break;
                case 3:
                    mX = -(float) (circlrR * (Math.cos(Math.PI / 2 / (img.length - 1) * i)));
                    mY = -(float) ((getWidth() / 2 - 50) * (Math.sin(Math.PI / 2 / (img.length - 1) * i)));
                    break;
                case 4:
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
            animator0= ObjectAnimator.ofFloat(ivStart, "rotation", 45, 0);
            animator = ObjectAnimator.ofFloat(childAt, "translationY", mY, 0);
            animator1 = ObjectAnimator.ofFloat(childAt, "translationX", mX, 0);
            animator3 = ObjectAnimator.ofFloat(childAt2, "translationY", mY, 0);
            animator4 = ObjectAnimator.ofFloat(childAt2, "translationX", mX, 0);
        } else {
            animator0= ObjectAnimator.ofFloat(ivStart, "rotation", 0,45);
            animator = ObjectAnimator.ofFloat(childAt, "translationY", 0, mY);
            animator1 = ObjectAnimator.ofFloat(childAt, "translationX", 0, mX);
            animator3 = ObjectAnimator.ofFloat(childAt2, "translationY", 0, mY);
            animator4 = ObjectAnimator.ofFloat(childAt2, "translationX", 0, mX);
        }
        animator0.setDuration(300);
        animator0.start();
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.setDuration(300);
        animatorSet.playTogether(animator, animator1,animator3, animator4);
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
     * @param img
     */
    public void setImg(int[] img) {
        this.img = img;
    }
    /**
     * 设置子View文字
     * @param text
     */
    public void setTexts(String[] text) {
        this.text = text;
        addView();
    }
    /**
     * 子项点击回调
     * @param clickItem
     */
    public void setClickItem(ClickItem clickItem) {
        this.clickItem = clickItem;
    }
}
