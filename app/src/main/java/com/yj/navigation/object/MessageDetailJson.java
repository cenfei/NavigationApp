package com.yj.navigation.object;

import java.util.ArrayList;

/**
 * Created by foxcen on 16/12/19.
 */
public class MessageDetailJson extends BaseJson{

    public Integer pageNum;
    public Integer pageSize;
    public Integer pages;
    public Integer total;

    public ArrayList<MessageItemJson> rows;
}
