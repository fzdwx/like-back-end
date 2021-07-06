package cn.like.base.support.id;

import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.util.IdUtil;

/**
 * desc: id 生成器 <br>
 * details: 使用时，必须注入到spring ioc 容器中。
 *
 * @author like 980650920@qq.com
 * @date 2021-07-06 18:54:18
 */
public class IdGenerator {
	
	/** 雪花算法生成id */
	private final Snowflake SNOWFLAKE;
	
	/**
	 * desc: id 生成器 <br>
	 * details: 最大支节点数0~31，一共32个
	 *
	 * @param workId       终端id
	 * @param dataCenterId 数据中心的id
	 * @return: {@link  }
	 * @author: like 980650920@qq.com
	 * @date 2021-07-06 19:06:08
	 */
	public IdGenerator(final long workId, final long dataCenterId) {
		SNOWFLAKE = IdUtil.getSnowflake(workId, dataCenterId);
	}
	
	// ====================================================== Snowflake
	
	/**
	 * desc: 生成雪花id(19位) <br>
	 * details:
	 * 类似,1412369364488949760<br>
	 *
	 * @return: long
	 * @author: like 980650920@qq.com
	 * @date 2021-07-06 19:12:25
	 */
	public long snowflakeId() {
		return SNOWFLAKE.nextId();
	}
	
	/**
	 * desc:生成雪花id(19位)使用 String类型<br>
	 */
	public String snowflakeIdStr() {
		return SNOWFLAKE.nextIdStr();
	}
	
	// ====================================================== uuid
	
	/**
	 * desc:
	 * 简化的UUID，去掉了横线，使用性能更好的ThreadLocalRandom生成UUID(32位)<br>
	 * <br>
	 * details:
	 * 类似,4f189741b8604b56a8e44010f9ee10c6<br>
	 *
	 * @return: {@link String }
	 * @author: like 980650920@qq.com
	 * @date 2021-07-06 19:08:33
	 */
	public String uuid() {
		return IdUtil.fastSimpleUUID();
	}
	
	// ====================================================== MongoDB ID
	
	/**
	 * desc:
	 * 创建MongoDB ID生成策略实现(24位)<br>
	 * details:
	 * 类似,60e438a8c5d44bcbc6392143<br>
	 * ObjectId由以下几部分组成：
	 * <ul>
	 * 		<li>Time 时间戳。</li>
	 * 		<li>Machine 所在主机的唯一标识符，一般是机器主机名的散列值。</li>
	 * 		<li>PID 进程ID。确保同一机器中不冲突。</li>
	 * 		<li>INC 自增计数器。确保同一秒内产生objectId的唯一性。</li>
	 * </ul>
	 * <p>
	 * 参考：http://blog.csdn.net/qxc1281/article/details/54021882
	 *
	 * @return: {@link String }
	 * @author: like 980650920@qq.com
	 * @date 2021-07-06 19:07:13
	 */
	public String objectId() {
		return IdUtil.objectId();
	}
}