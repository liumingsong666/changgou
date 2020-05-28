package com.song.entity;

import java.io.Serializable;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * tb_role
 * @author 
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel("角色表")
public class Role implements Serializable {

    /**
     * 角色id
     */
    private Integer id;

    /**
     * 角色名
     */
    private String name;



}