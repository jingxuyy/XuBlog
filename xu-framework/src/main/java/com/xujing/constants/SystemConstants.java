package com.xujing.constants;

/**
 * @author 86136
 */
public class SystemConstants {

    /**
     * 文章是草稿
     */
    public static final int ARTICLE_STATUS_DRAFT = 1;

    /**
     * 文章是正常状态
     */
    public static final int ARTICLE_STATUS_NORMAL = 0;

    /**
     * 正常状态
     */
    public static final int STATUS_NORMAL = 0;

    /**
     * 文章分类记录状态
     */
    public static final String CATEGORY_STATUS_NORMAL = "0";

    /**
     * link链接状态，审核通过
     */
    public static final String LINK_STATUS_NORMAL = "0";

    /**
     * 表示评论的类型，文章评论
     */
    public static final String ARTICLE_COMMENT = "0";

    /**
     * 表示评论的类型，友链评论
     */
    public static final String LINK_COMMENT = "1";

    /**
     * 表示存储文章浏览量的key
     */
    public static final String REDIS_VIEW_COUNT = "Article:viewCount";

    /**
     * 分页查询的页码
     */
    public static final int PAGE_NUMBER = 1;

    /**
     * 分页查询每一页的条目数
     */
    public static final int PAGE_SIZE = 10;

    /**
     * 文章分类id的最小合法值
     */
    public static final int LEGAL_ID = 1;

    /**
     * 文章浏览量递增间隔
     */
    public static final int REDIS_INCRYBY = 1;

    /**
     * 存入redis的前台用户key前缀
     */
    public static final String REDIS_USER = "bloglogin";

    /**
     * 根评论标识
     */
    public static final int ROOT_FLAG = -1;

    /**
     * 上传图像的格式 jpg
     */
    public static final String IMG_JPG = ".jpg";

    /**
     * 上传图像的格式 png
     */
    public static final String IMG_PNG = ".png";

    /**
     * 上传图像的格式 png
     */
    public static final String UPLOAD_ERROR = "上传失败";

    /**
     * 查询用户不存在
     */
    public static final String USER_NOT_EXIST= "用户不存在!";

    /**
     * 存入redis的后台用户key前缀
     */
    public static final String REDIS_ADMIN_USER = "adminlogin";

    /**
     * 权限1，显示菜单
     */
    public static final String MENU = "C";

    /**
     * 权限2，显示按钮
     */
    public static final String BUTTON = "F";

    /**
     * 文章分类id的最小合法值
     */
    public static final int LEGAL_STATUS = 1;


    /**
     * 后台用户
     */
    public static final String ADMIN= "1";

}
