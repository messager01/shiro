package cn.wolfcodde.shiro.realm;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;

public class PasswordRealm extends AuthorizingRealm {
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        return null;
    }

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
        String password = new Md5Hash("666","shipeixin",3).toString();

        //info 对象表示realm登录对比的信息
        SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo
        //参数1：用户信息（真实场景中是登录的user对象） 参数2：密码  参数3：盐  参数4：当前realm的名字
                (username, password, ByteSource.Util.bytes("shipeixin"), getName());
        return authenticationInfo;

    }

    @Override
    public String getName() {
        return super.getName();
    }
}
