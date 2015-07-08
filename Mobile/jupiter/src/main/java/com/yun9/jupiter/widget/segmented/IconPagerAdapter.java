package com.yun9.jupiter.widget.segmented;

public interface IconPagerAdapter {
    /**
     * Get icon representing the Page at {@code index} in the adapter.
     */
    int getIconResId(int index);

    // From PagerAdapter
    int getCount();
}
