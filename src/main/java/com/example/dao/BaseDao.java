package com.example.dao;

import com.example.entity.PageModel;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by nie_jq on 2017/6/2.
 */
public class BaseDao<E> extends SqlSessionDaoSupport{
    protected Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * spring redis 模板
     */
    @Resource
    protected RedisTemplate<Serializable,Serializable> redisTemplate;

    /**
     * spring jdbc 模板
     */
    @Resource
    protected JdbcTemplate jdbcTemplate;

    /**
     * Mybatis
     * @param sqlSessionFactory
     */
    @Resource
    public void setSqlSessionFactory(SqlSessionFactory sqlSessionFactory) {
        super.setSqlSessionFactory(sqlSessionFactory);
    }


    public static final String MAPPER_INSERT = "insert";
    public static final String MAPPER_DELETE = "delete";

    private Class<E> entityClass;
    public BaseDao(){
        Type type = getClass().getGenericSuperclass();
        Type trueType = ((ParameterizedType) type).getActualTypeArguments()[0];
        this.entityClass = (Class<E>) trueType;
    }
    /**
     *
     * 功能描述：查询list
     *
     * @author  bingzhong.qin
     * <p>创建日期 ：2014-1-3 上午10:45:33</p>
     *
     * @param statement
     * @param parameter
     * @return
     *
     * <p>修改历史 ：(修改人，修改时间，修改原因/内容)</p>
     */
    public List<E> findList(String statement, Object parameter) {
        Assert.notNull(statement, "Property statement is required");

        return (List<E>)this.getSqlSession().selectList(statement, parameter);
    }

    /**
     *
     * 功能描述：查询list
     *
     * @author  刘祚家
     * <p>创建日期 ：2014-1-15 </p>
     *
     * @param statement
     * @return
     *
     * <p>修改历史 ：(修改人，修改时间，修改原因/内容)</p>
     */
    public List<E> findList(String statement) {
        Assert.notNull(statement, "Property statement is required");
        return (List<E>)this.getSqlSession().selectList(statement);
    }

    /**
     *
     * 功能描述：selectOne
     *
     * @author  bingzhong.qin
     * <p>创建日期 ：2014-1-3 上午10:45:47</p>
     *
     * @param statement
     * @param parameter
     * @return
     *
     * <p>修改历史 ：(修改人，修改时间，修改原因/内容)</p>
     */
    public Object selectOne(String statement, Object parameter){
        Assert.notNull(statement, "Property statement is required");
        return this.getSqlSession().selectOne(statement, parameter);
    }
    /**
     *
     * 功能描述：批量删除
     *
     * @author  bingzhong.qin
     * <p>创建日期 ：2014-1-3 上午10:45:56</p>
     *
     * @param statement
     * @param list
     * @return
     *
     * <p>修改历史 ：(修改人，修改时间，修改原因/内容)</p>
     */
    public int deleteBatch(String statement, List<?> list) {
        Assert.notNull(statement, "Property statement is required");
        if(list == null || list.isEmpty()){
            return 0;
        }
        SqlSession sqlSession = this.getSqlSession();
        int size = list.size();
        for (int i = 0; i < size; i++) {
            sqlSession.delete(statement, list.get(i));
        }
        return list.size();
    }

    /**
     *
     * 功能描述：删除数据
     *
     * @author  Miaoj
     * <p>创建日期 ：2014-2-11 下午3:44:25</p>
     *
     * @param statement
     * @param parameter
     * @return
     *
     * <p>修改历史 ：(修改人，修改时间，修改原因/内容)</p>
     */
    public int delete(String statement,Object  parameter)
    {
        Assert.notNull(statement, "Property statement is required");
        return this.getSqlSession().delete(statement, parameter);
    }
    /**
     *
     * 功能描述：insert
     *
     * @author  bingzhong.qin
     * <p>创建日期 ：2014-1-3 上午10:46:11</p>
     *
     * @param statement
     * @param parameter
     * @return
     *
     * <p>修改历史 ：(修改人，修改时间，修改原因/内容)</p>
     */
    public int insert(String statement, Object parameter) {
        Assert.notNull(statement, "Property statement is required");
        return this.getSqlSession().insert(statement, parameter);
    }
    /**
     *
     * 功能描述： insert
     *
     * @author  bingzhong.qin
     * <p>创建日期 ：2014-1-6 下午1:20:48</p>
     *
     * @param parameter
     * @return
     *
     * <p>修改历史 ：(修改人，修改时间，修改原因/内容)</p>
     */
    public int insert(Object parameter) {
        return this.getSqlSession().insert(generateStatement(MAPPER_INSERT), parameter);
    }
    /**
     *
     * 功能描述：批量插入
     *
     * @author  bingzhong.qin
     * <p>创建日期 ：2014-1-3 上午10:46:27</p>
     *
     * @param statement
     * @param list
     * @return
     *
     * <p>修改历史 ：(修改人，修改时间，修改原因/内容)</p>
     */
    public int insertBatch(String statement, List<?> list) {
        Assert.notNull(statement, "Property statement is required");
        if(list == null || list.isEmpty()){
            return 0;
        }
        SqlSession sqlSession = this.getSqlSession();
        int size = list.size();
        for (int i = 0; i < size ; i++) {
            sqlSession.insert(statement, list.get(i));
        }
        return list.size();
    }
    /**
     *
     * 功能描述：update
     *
     * @author  bingzhong.qin
     * <p>创建日期 ：2014-1-3 上午10:46:37</p>
     *
     * @param statement
     * @param parameter
     * @return
     *
     * <p>修改历史 ：(修改人，修改时间，修改原因/内容)</p>
     */
    public int update(String statement, Object parameter) {
        Assert.notNull(statement, "Property statement is required");
        return this.getSqlSession().update(statement, parameter);

    }
    public int update(Object parameter) {
        return this.getSqlSession().update(generateStatement("update"), parameter);

    }
    /**
     *
     * 功能描述：批量修改
     *
     * @author  bingzhong.qin
     * <p>创建日期 ：2014-1-3 上午10:46:48</p>
     *
     * @param statement
     * @param list
     * @return
     *
     * <p>修改历史 ：(修改人，修改时间，修改原因/内容)</p>
     */
    public int updateBatch(String statement, List<?> list) {
        Assert.notNull(statement, "Property statement is required");
        if(list == null || list.isEmpty()){
            return 0;
        }
        SqlSession sqlSession = this.getSqlSession();
        int size = list.size();
        for (int i = 0; i < size ; i++) {
            sqlSession.update(statement, list.get(i));
        }
        return list.size();
    }
    /**
     *
     * 功能描述：查询分页
     *
     * @author  bingzhong.qin
     * <p>创建日期 ：2014-1-3 上午10:46:59</p>
     *
     * @param statement
     * @param countStatement
     * @param parameter
     * @return
     *
     * <p>修改历史 ：(修改人，修改时间，修改原因/内容)</p>
     */
    public PageModel findPagedList(String statement,String countStatement,PageModel parameter,int... queryTotalCount) {
        Assert.notNull(statement, "Property statement is required");
        Assert.notNull(statement, "Property countStatement is required");

        if(parameter == null){
            return new PageModel();
        }
        if(queryTotalCount==null||queryTotalCount.length==0)
        {
            //首次查询时返回总记录数，以后查询时传入totalItem，则不再查询。
            if(parameter.getTotalItem() <= 0){
                // 得到数据记录总数
                Integer totalItem = (Integer) this.getSqlSession().selectOne(countStatement, parameter);
                if(totalItem != null){
                    parameter.setTotalItem(totalItem.intValue());
                }else{
                    return new PageModel();
                }
            }
        }
        List<E> list = null;
        Map<String,Object> pageParam=new HashMap<String,Object>();
        pageParam.put("start", parameter.getPageSize()*(parameter.getToPage()-1));
        pageParam.put("limit", parameter.getPageSize());
        list = (List<E>)this.getSqlSession().selectList(statement, pageParam);
        parameter.setList(list);
        return parameter;
    }

    /**
     * 分页查找
     * @Title: findPagedList
     * @param @param statement
     * @param @param countStatement
     * @param @param params
     * @param @return
     * @return PageModel    返回类型
     * @throws
     */
    public PageModel findPagedList(String statement,String countStatement,Map<String,Object>params) {
        Assert.notNull(statement, "Property statement is required");
        Assert.notNull(statement, "Property countStatement is required");
        PageModel pageModel=new PageModel();
        //首次查询时返回总记录数，以后查询时传入totalItem，则不再查询。
        // 得到数据记录总数
        Long totalItem = (Long) this.getSqlSession().selectOne(countStatement, params);
        if(totalItem != null){
            pageModel.setTotalItem(totalItem.intValue());
        }else{
            return new PageModel();
        }
        List<E> list = null;
        list = (List<E>)this.getSqlSession().selectList(statement, params);
        pageModel.setList(list);
        return pageModel;
    }
    /**
     *
     * 功能描述： mapperId:sqlmap配置文件sql语句id
     *
     * @author  bingzhong.qin
     * <p>创建日期 ：2014-1-3 上午10:47:16</p>
     *
     * @param mapperId
     * @return
     *
     * <p>修改历史 ：(修改人，修改时间，修改原因/内容)</p>
     */
    public String generateStatement(String mapperId){
        return entityClass.getName() + "." + mapperId;
    }
}
