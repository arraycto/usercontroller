package com.platform.uc.service.comparator;

import com.platform.uc.service.vo.Menu;

import java.util.Comparator;

public class SeqComparator implements Comparator<Menu> {

    @Override
    public int compare(Menu o1, Menu o2) {
        long l = o1.getSeq() - o2.getSeq();
        return (int) l;
    }

}
