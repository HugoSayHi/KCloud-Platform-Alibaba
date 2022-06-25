package io.laokou.admin.interfaces.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class DictVO implements Serializable {

    private Long id;
    /**
     * 标签
     */
    private String dictLabel;
    /**
     * 类型
     */
    private String type;
    /**
     * 值
     */
    private String dictValue;
    /**
     * 状态 0 正常 1 停用
     */
    private Integer status;
    /**
     * 备注
     */
    private String remark;

    /**
     * 创建时间
     */
    private Date createDate;

    /**
     * 排序
     */
    private Integer sort;

}