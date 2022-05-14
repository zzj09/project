package com.dao;

import com.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

@Repository
public class UserDaoImpl implements UserDao{
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public Boolean add(User user) {
        boolean b = false;
        String sql = "insert into userinfo values(?,?,?,?,?);";
        int jdbc = jdbcTemplate.update(sql,null,user.getUsername(),user.getPassword(),user.getEmail(),1);
        if (jdbc != 0)
            b=true;
        return b;
    }

    @Override
    public Boolean query(User user) {
        boolean b = false;
        String sql = "select * from userinfo where username=?;";
        try{
            User user1 = jdbcTemplate.queryForObject(sql,new MyRowMapper(),user.getUsername());
            if ((user1.getPassword()).equals(user.getPassword())){
                b = true;
            }
        }catch(Exception e){}
        return b;
    }

    @Override
    public Boolean checkUserName(String username){
        boolean b = false;
        String sql = "select * from userinfo where username=?;";
        try{
            User user = jdbcTemplate.queryForObject(sql,new MyRowMapper(),username);
            if (user != null){
                b = true;
            }
        }catch(Exception e){}
        return b;
    }

    @Override
    public Boolean update(User user, String newpassword, String newpassword2) {
        boolean b = false;
        if (newpassword.equals(newpassword2)){
            String sql = "update userinfo set password=? where username=?;";
            int jdbc = jdbcTemplate.update(sql,newpassword,user.getUsername());
            if (jdbc != 0){
                b = true;
            }
        }
        return b;
    }

    @Override
    public User findUserByUserName(String username) {
        String sql = "select * from userinfo where username=?;";
        try{
            User user = jdbcTemplate.queryForObject(sql,new MyRowMapper(),username);
            user.setPassword(""); //设密码为空，不可见
            return user;
        }catch(Exception e){
            return null;
        }
    }
}

class MyRowMapper implements RowMapper<User>{
//映射数据库数据到具体实现类
    @Override
    public User mapRow(ResultSet resultSet, int i) throws SQLException {
        User user = new User();
        user.setId(resultSet.getInt("id"));
        user.setUsername(resultSet.getString("username"));
        user.setPassword(resultSet.getString("password"));
        user.setEmail(resultSet.getString("email"));
        return user;
    }
}