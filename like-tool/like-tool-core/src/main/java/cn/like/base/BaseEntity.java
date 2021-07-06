package cn.like.base;

import com.baomidou.mybatisplus.extension.activerecord.Model;

import java.io.Serializable;

/**
 * desc: base entity <br>
 * details:
 *
 * @author like 980650920@qq.com
 * @date 2021-06-29 21:50:09
 * @see Model
 */
public class BaseEntity<T extends Model<?>> extends Model<T> implements Serializable {

}
