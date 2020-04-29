package com.atguigu.bean;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.Version;
import lombok.*;

import java.util.Date;

/**
 * @author Administrator
 * @create 2020-04-06 21:23
 */
@Data
//@AllArgsConstructor//全参构造器
//@NoArgsConstructor//无参构造器
//@ToString
//@EqualsAndHashCode
//@Getter
//@Setter
public class User {
    private Long id;
    private String name;
    private Integer age;
    private String email;
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;

    @Version
    @TableField(fill = FieldFill.INSERT)
    private Integer version;    //乐观锁，版本号

    @TableLogic
    @TableField(fill = FieldFill.INSERT)
    private Integer deleted;   //逻辑删除
}
