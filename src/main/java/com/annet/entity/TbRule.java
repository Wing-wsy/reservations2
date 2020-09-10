package com.annet.entity;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;

import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author XiaYu
 * @since 2019-04-28
 */
@TableName("tb_rule")
public class TbRule extends Model<TbRule> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "RuleKey", type = IdType.AUTO)
    private String RuleKey;
    private String RuleName;
    private String Comment;


    public String getRuleKey() {
        return RuleKey;
    }

    public void setRuleKey(String RuleKey) {
        this.RuleKey = RuleKey;
    }

    public String getRuleName() {
        return RuleName;
    }

    public void setRuleName(String RuleName) {
        this.RuleName = RuleName;
    }

    public String getComment() {
        return Comment;
    }

    public void setComment(String Comment) {
        this.Comment = Comment;
    }

    @Override
    protected Serializable pkVal() {
        return this.RuleKey;
    }

    @Override
    public String toString() {
        return "TbRule{" +
        ", RuleKey=" + RuleKey +
        ", RuleName=" + RuleName +
        ", Comment=" + Comment +
        "}";
    }
}
