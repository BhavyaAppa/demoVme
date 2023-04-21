package com.base.hilt.utils;

import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class CustomDividerItemDecoration extends RecyclerView.ItemDecoration {

    private final Drawable mDivider;
    private final int mLeftInset;
    private final int mRightInset;
    private final boolean mIsShowInLastItem;
    private int mOrientation;

    /**
     * Sole constructor. Takes in a {@link Drawable} to be used as the interior
     * divider.
     *
     * @param divider A divider {@code Drawable} to be drawn on the RecyclerView
     */
    public CustomDividerItemDecoration(Drawable divider, int leftInset, int rightInset, boolean isShowInLastItem) {
        mDivider = divider;
        mLeftInset = leftInset;
        mRightInset = rightInset;
        this.mIsShowInLastItem = isShowInLastItem;

    }

    /**
     * Draws horizontal or vertical dividers onto the parent RecyclerView.
     *
     * @param canvas The {@link Canvas} onto which dividers will be drawn
     * @param parent The RecyclerView onto which dividers are being added
     * @param state  The current RecyclerView.State of the RecyclerView
     */
    @Override
    public void onDraw(Canvas canvas, RecyclerView parent, RecyclerView.State state) {
        if (mOrientation == LinearLayoutManager.HORIZONTAL) {
            drawHorizontalDividers(canvas, parent);
        } else if (mOrientation == LinearLayoutManager.VERTICAL) {
            drawVerticalDividers(canvas, parent);
        }
    }

    /**
     * Determines the size and location of offsets between items in the parent
     * RecyclerView.
     *
     * @param outRect The {@link Rect} of offsets to be added around the child
     *                view
     * @param view    The child view to be decorated with an offset
     * @param parent  The RecyclerView onto which dividers are being added
     * @param state   The current RecyclerView.State of the RecyclerView
     */
    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
//        super.getItemOffsets(outRect, view, parent, state);
        int position = parent.getChildAdapterPosition(view);
        if (parent.getAdapter() != null && position == parent.getAdapter().getItemCount() - 1) {
            outRect.set(0, 0, 0, mDivider.getIntrinsicHeight());
        } else {
            super.getItemOffsets(outRect, view, parent, state);
        }
        if (parent.getChildAdapterPosition(view) == 0) {
            return;
        }

        mOrientation = ((LinearLayoutManager) parent.getLayoutManager()).getOrientation();
        if (mOrientation == LinearLayoutManager.HORIZONTAL) {
            outRect.left = mDivider.getIntrinsicWidth();
        } else if (mOrientation == LinearLayoutManager.VERTICAL) {
            outRect.top = mDivider.getIntrinsicHeight();
        }
    }

    /**
     * Adds dividers to a RecyclerView with a LinearLayoutManager or its
     * subclass oriented horizontally.
     *
     * @param canvas The {@link Canvas} onto which horizontal dividers will be
     *               drawn
     * @param parent The RecyclerView onto which horizontal dividers are being
     *               added
     */
    private void drawHorizontalDividers(Canvas canvas, RecyclerView parent) {
        int parentTop = parent.getPaddingTop();
        int parentBottom = parent.getHeight() - parent.getPaddingBottom();

        int childCount;
        if (mIsShowInLastItem) {
            childCount = parent.getChildCount();
        } else {
            childCount = parent.getChildCount() - 1;
        }
        for (int i = 0; i < childCount; i++) {
            View child = parent.getChildAt(i);

            RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child.getLayoutParams();

            int parentLeft = mLeftInset + child.getRight() + params.rightMargin;
            int parentRight = mRightInset + parentLeft + mDivider.getIntrinsicWidth();

            mDivider.setBounds(parentLeft, parentTop, parentRight, parentBottom);
            mDivider.draw(canvas);
        }
    }


    /**
     * Adds dividers to a RecyclerView with a LinearLayoutManager or its
     * subclass oriented vertically.
     *
     * @param canvas The {@link Canvas} onto which vertical dividers will be
     *               drawn
     * @param parent The RecyclerView onto which vertical dividers are being
     *               added
     */
    private void drawVerticalDividers(Canvas canvas, RecyclerView parent) {
        int parentLeft = mLeftInset + parent.getPaddingLeft();
        int parentRight = parent.getWidth() - mRightInset - parent.getPaddingRight();

        int childCount;
        if (mIsShowInLastItem) {
            childCount = parent.getChildCount();
        } else {
            childCount = parent.getChildCount() - 1;
        }
        for (int i = 0; i < childCount; i++) {
            View child = parent.getChildAt(i);

            RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child.getLayoutParams();

            int parentTop = child.getBottom() + params.bottomMargin;
            int parentBottom = parentTop + mDivider.getIntrinsicHeight();

            mDivider.setBounds(parentLeft, parentTop, parentRight, parentBottom);
            mDivider.draw(canvas);
        }
    }
}
