package cn.wolfcodde.shiro.realm;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

public class MyRealm extends AuthorizingRealm {

    /*
    * 授权操作
    * */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        return null;
    }


    /*
    * 认证操作
    * */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        //  token中包装了传进来的账号密码
        String username = (String) token.getPrincipal();   //获取用户名  获取token中的用户名
        //通过用户名查询数据库
         //模拟
        if(!username.equals("zhangsan")){
            return null;
        }
        //  数据库中查询出来的密码
        String password = "666";

        //info 对象表示realm登录对比的信息  参数1：用户信息（真实场景中是登录的user对象） 参数2：密码   参数3：当前realm的名字
        SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(username, password, getName());
        return authenticationInfo;
    }

    @Override
    public String getName() {
       return "MyRealm";
    }
}
