package com.annet.entity;

import java.util.Date;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.Column;
import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author jh
 * @since 2020-03-17
 */
@TableName("tb_order_File")
public class TbOrderFile extends Model<TbOrderFile> {

    private static final long serialVersionUID = 1L;

    @TableId(value="ID", type= IdType.AUTO)
    private Integer id;
    private Integer BespokeID;
    private String vHISJPGFile;
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date OperatDate;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getBespokeID() {
        return BespokeID;
    }

    public void setBespokeID(Integer BespokeID) {
        this.BespokeID = BespokeID;
    }

    public String getvHISJPGFile() {
        return vHISJPGFile;
    }

    public void setvHISJPGFile(String vHISJPGFile) {
        this.vHISJPGFile = vHISJPGFile;
    }

    public Date getOperatDate() {
        return OperatDate;
    }

    public void setOperatDate(Date OperatDate) {
        this.OperatDate = OperatDate;
    }

    @Override
    protected Serializable pkVal() {
        return null;
    }

    @Override
    public String toString() {
        return "TbOrderFile{" +
        ", id=" + id +
        ", BespokeID=" + BespokeID +
        ", vHISJPGFile=" + vHISJPGFile +
        ", OperatDate=" + OperatDate +
        "}";
    }
}
