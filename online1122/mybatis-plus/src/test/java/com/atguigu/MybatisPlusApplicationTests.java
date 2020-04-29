package com.atguigu;


import com.atguigu.bean.User;
import com.atguigu.mapper.UserMapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Map;

@RunWith(SpringRunner.class) //是junit4提供的注解 将spring环境与juit融合起来
@SpringBootTest  //相当于提供了一个spring容器
public class MybatisPlusApplicationTests {

    @Autowired
    private UserMapper userMapper;

    //1.查询所有用户
    @Test
    public void findAllUser(){
        List<User> userList = userMapper.selectList(null);
        for (User user : userList) {
            System.out.println(user);
        }
    }
    //2.添加新用户
    @Test
    public void addUser(){
        //id自动生成 不能是基本数据类型
        User user = new User();
        user.setName("隔壁老王");
        user.setAge(38);
        user.setEmail("zhangsan@atguigu.com");
        user.setVersion(1);
        userMapper.insert(user);
        System.out.println("over");
    }
    //测试时间  修改用户
    @Test
    public void updateUser(){
        User user = new User();
        user.setId(1247371103534321666L);
        user.setAge(38);
        userMapper.updateById(user);
        System.out.println("over");
    }

    /**
     * 测试 乐观锁插件
     */
    @Test
    public void testOptimisticLocker() {
        //查询
        User user = userMapper.selectById(1247483774795517954L);
        //修改数据
        user.setName("Helen Yao");
        user.setEmail("helen@qq.com");
        //执行更新
        userMapper.updateById(user);
    }

    /**
     * 测试乐观锁插件 失败
     */
    @Test
    public void testOptimisticLockerFail() {
        //查询
        User user = userMapper.selectById(1247483774795517954L);
        //修改数据
        user.setName("Helen Yao1");
        user.setEmail("helen@qq.com1");
        //模拟取出数据后，数据库中version实际数据比取出的值大，即已被其它线程修改并更新了version
        user.setVersion(user.getVersion() - 1);
        //执行更新
        userMapper.updateById(user);
    }

    //6.简单条件查询 构造多个查询条件
    @Test
    public void testSimpleQuery1(){
        //有两种方式 map wrapper
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.like("name","王");
        List<User> userList = userMapper.selectList(wrapper);
        for (User user : userList) {
            System.out.println(user);
        }
    }

    @Test
    public void testSelectPage() {//测试分页

        Page<User> page = new Page<>(2,2);
        userMapper.selectPage(page, null);

        page.getRecords().forEach(System.out::println);
        System.out.println(page.getCurrent());
        System.out.println(page.getPages());
        System.out.println(page.getSize());
        System.out.println(page.getTotal());
        System.out.println(page.hasNext());
        System.out.println(page.hasPrevious());
    }

    @Test
    public void testSelectMapPage(){
        Page<User> page = new Page<>(1,5);
        IPage<Map<String, Object>> mapIPage = userMapper.selectMapsPage(page,null);
        mapIPage.getRecords().forEach(System.out::println);
        System.out.println(page.getCurrent());
        System.out.println(page.getPages());
        System.out.println(page.getSize());
        System.out.println(page.getTotal());
        System.out.println(page.hasNext());
        System.out.println(page.hasPrevious());
    }

    /**
     * 测试 逻辑删除
     */
    @Test
    public void testLogicDelete() {
        int result = userMapper.deleteById(1L);
        System.out.println(result);
    }
}
