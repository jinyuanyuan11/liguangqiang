package com.bw.movie.utils;

/**
 * <p>文件描述：<p>
 * <p>作者：${小强}<p>
 * <p>创建时间：2019/1/1810:51<p>
 * <p>更改时间：2019/1/1810:51<p>
 * <p>版本号：1<p>
 */
public class Contacts {
    public static final String BASE_URL="http://www.zhaoapi.cn/";
    //一级列表
    public static final String List_One="product/getCatagory";
    //二级列表
    public static final String List_Two="product/getProductCatagory?cid=";
    //商品列表
    public static final String Shop_list="product/getProducts?pscid=";
    //加入购物车
    public static final String Add_Shop="product/addCart";
    //.查询购物车数据接口
    public static final String UP_LOAD_IMAGE="product/getCarts";
    //上传头像
    public static final String Add_Head="file/upload";

}
