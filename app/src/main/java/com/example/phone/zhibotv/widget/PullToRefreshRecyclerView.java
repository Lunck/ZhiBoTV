package com.example.phone.zhibotv.widget;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;

import com.example.phone.zhibotv.R;
import com.handmark.pulltorefresh.library.PullToRefreshBase;

/**
 * Created by Administrator on 2016-12-01.
 */
public class PullToRefreshRecyclerView extends PullToRefreshBase<RecyclerView> {

    public PullToRefreshRecyclerView(Context context) {
        super(context);
    }

    public PullToRefreshRecyclerView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public PullToRefreshRecyclerView(Context context, Mode mode) {
        super(context, mode);
    }

    public PullToRefreshRecyclerView(Context context, Mode mode, AnimationStyle animStyle) {
        super(context, mode, animStyle);
    }

    /**
     * 获取刷新方向
     */
    @Override
    public Orientation getPullToRefreshScrollDirection() {
        return Orientation.VERTICAL;
    }

    /**
     * 创建一个刷新的View
     *
     */

    @Override
    protected RecyclerView createRefreshableView(Context context, AttributeSet attrs) {
        RecyclerView recyclerView = new RecyclerView(context, attrs);
        recyclerView.setId(R.id.recycler_id);
        return recyclerView;
    }

    /**
     * 是否准备好了从结束的地方加载  纵向表示底部  横向表示右侧
     * true  准备好了   false 没准备好
     */
    @Override
    protected boolean isReadyForPullEnd() {
        RecyclerView refreshableView = getRefreshableView();
        //RecyclerView的高度
        int height = refreshableView.getHeight();
        //获取RecyclerView的底部内边距
        int paddingBottom = refreshableView.getPaddingBottom();
        //获取 RecyclerView 中child的个数
        int childCount = refreshableView.getChildCount();
        //获取最后一个child
        View childAt = refreshableView.getChildAt(childCount - 1);
        if (childAt==null) {
            return false;
        }
        //获取最后一个item距离顶部的距离
        int bottom = childAt.getBottom();
        //获取字后一个item的底部外边距
        RecyclerView.LayoutParams layoutParams = (RecyclerView.LayoutParams) childAt.getLayoutParams();

        int bottomMargin = layoutParams.bottomMargin;

        return height==paddingBottom+bottom+bottomMargin;
    }

    /**
     *
     * 是否准备好从开始的地方刷新，开始的地方  竖着的时候指顶部  横着的时候指 左边
     * true 准备好了  false没准备好
     */
    @Override
    protected boolean isReadyForPullStart() {
        //获取刷新的View
        RecyclerView refreshableView = getRefreshableView();
        //获取RecyclerView的顶部内边距
        int paddingTop = refreshableView.getPaddingTop();
        //获取索引为0的child
        View child = refreshableView.getChildAt(0);
        //获取child的顶部外边距
        if (child==null) {
            return false;
        }
        RecyclerView.LayoutParams layoutParams = (RecyclerView.LayoutParams) child.getLayoutParams();
        int topMargin = layoutParams.topMargin;
        //获取child距离RecyclerView顶部的距离
        int topToParent= child.getTop();

        return topToParent==topMargin+paddingTop;

    }
}
